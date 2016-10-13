package org.strmln.ionstat.task.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.strmln.ionstat.model.Measure;
import org.strmln.ionstat.model.SessionTemplate;
import org.strmln.ionstat.model.SessionTemplateMeasure;
import org.strmln.ionstat.model.exception.UserException;
import org.strmln.ionstat.service.SessionTemplateService;
import org.strmln.ionstat.service.model.HeaderInfo;
import org.strmln.ionstat.service.model.MeasurementInfo;
import org.strmln.ionstat.service.model.MeasurementValues;
import org.strmln.ionstat.task.handler.model.TaskResponse;
import org.strmln.ionstat.task.handler.model.command.UploadSessionCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoTaskHandlerCommand;
import org.strmln.ionstat.task.handler.model.command.dto.DtoUploadSessionCommand;

public class UploadSessionTaskHandler extends AbstractTaskHandler {

	private static final String FIRST_COLUMN_CONTENT = "#";
	private static final String MEASUREMENT_INFO_KEY = "measurementInfo"; //$NON-NLS-1$
	
	@Autowired
	private SessionTemplateService _sessionTemplateService;

	@Override
	public Class<? extends DtoTaskHandlerCommand<?>> getCommandClass() {
		return DtoUploadSessionCommand.class;
	}

	@Override
	public TaskResponse processTask(Object command, List<Path> files) {
		Path file = files.get(0);
		try {
			UploadSessionCommand uploadSessionCommand = (UploadSessionCommand) command;

			SessionTemplate sessionTemplate = getSessionTemplateService()
					.findByIdWithDependants(uploadSessionCommand.getSessionTemplateId());

			MeasurementInfo measurementInfo = parseSessionFile(file, sessionTemplate);

			TaskResponse response = new TaskResponse();
			response.add(MEASUREMENT_INFO_KEY, measurementInfo);

			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<HeaderInfo> collectMeasurementHeaders(Cell rootCell,
			Set<SessionTemplateMeasure> sessionTemplateMeasures) {
		List<HeaderInfo> result = new ArrayList<>();
		Row headerRow = rootCell.getRow();
		for (Cell cell : headerRow) {
			String cellValue = cell.getStringCellValue();
			if (cellValue.equals(FIRST_COLUMN_CONTENT)) { //$NON-NLS-1$
				continue;
			}
			Measure foundMeasure = null;
			for (SessionTemplateMeasure sessionTemplateMeasure : sessionTemplateMeasures) {
				Measure measure = sessionTemplateMeasure.getMeasure();
				if (cellValue.contains(measure.getName()) && cellValue.contains(measure.getDisplayUnit())) {
					foundMeasure = measure;
					break;
				}
			}

			if (foundMeasure == null) {
				throw new UserException(String.format(Messages.getString("UploadSessionTaskHandler.2"), //$NON-NLS-1$
						cellValue));
			}
			HeaderInfo headerInfo = new HeaderInfo(foundMeasure.getName(), foundMeasure.getUnit(),
					foundMeasure.getMeasureId());
			result.add(headerInfo);
		}

		if (result.size() != sessionTemplateMeasures.size()) {
			throw new UserException(Messages.getString("UploadSessionTaskHandler.3")); //$NON-NLS-1$
		}

		return result;
	}

	private List<MeasurementValues> collectValues(SessionTemplate sessionTemplate, Sheet sheet, Cell rootCell) {
		// index of first column with recorded values (# column is before this
		// one)
		int firstColumnIndex = rootCell.getColumnIndex() + 1;
		// index of first row with recorded values (row below header)
		int rowIndex = rootCell.getRowIndex() + 1;
		int currentColumnIndex = firstColumnIndex;

		Row headersRow = rootCell.getRow();

		List<MeasurementValues> values = new ArrayList<>();

		int j = 0;
		while (true) {
			Row row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
			MeasurementValues measurement = new MeasurementValues();

			// in case of row with blank cells (which can be parsed as 0.0
			// value) this variable is used to indicate that first row of
			// this kind has been reached and that parsing should finish
			boolean foundBlankRow = false;
			try {
				while (true) {
					Cell cell = row.getCell(currentColumnIndex);
					if (cell == null) {
						foundBlankRow = j == 0;
						break;
					}

					String measureName = getMeasureName(sessionTemplate, headersRow, currentColumnIndex);
					measurement.addValue(measureName, cell.getNumericCellValue());

					currentColumnIndex++;
					j++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new UserException(Messages.getString("UploadSessionTaskHandler.4")); //$NON-NLS-1$
			}
			if (foundBlankRow) {
				break;
			}

			rowIndex++;
			j = 0;
			currentColumnIndex = firstColumnIndex;
			values.add(measurement);
		}
		return values;
	}

	private Cell findRootCell(Sheet sheet) {
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().equals(FIRST_COLUMN_CONTENT)) { //$NON-NLS-1$
					return cell;
				}
			}
		}

		throw new UserException(Messages.getString("UploadSessionTaskHandler.6")); //$NON-NLS-1$

	}

	private String getMeasureName(SessionTemplate sessionTemplate, Row headersRow, int currentColumnIndex) {
		String result = null;
		for (SessionTemplateMeasure sessionTemplateMeasure : sessionTemplate.getSessionTemplateMeasures()) {
			Measure measure = sessionTemplateMeasure.getMeasure();
			Cell cell = headersRow.getCell(currentColumnIndex);
			String measureNameUnit = cell.getStringCellValue();
			if (measureNameUnit.contains(measure.getDisplayUnit()) && measureNameUnit.contains(measure.getName())) {
				result = measure.getName();
				break;
			}
		}
		return result;
	}

	private SessionTemplateService getSessionTemplateService() {
		return _sessionTemplateService;
	}

	private Workbook getWorkBook(Path file) throws FileNotFoundException, IOException {
		Workbook workbook = null;

		try (InputStream inputStream = Files.newInputStream(file)) {
			String fileName = file.getFileName().toString();
			if (fileName.endsWith("xls")) { //$NON-NLS-1$
				workbook = new HSSFWorkbook(inputStream);
			} else if (fileName.endsWith("xlsx")) { //$NON-NLS-1$
				workbook = new XSSFWorkbook(inputStream);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (workbook == null) {
			throw new RuntimeException(Messages.getString("UploadSessionTaskHandler.9")); //$NON-NLS-1$
		}

		workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);
		return workbook;
	}

	private MeasurementInfo parseSessionFile(Path file, SessionTemplate sessionTemplate) {
		Workbook workbook;
		try {
			workbook = getWorkBook(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Sheet sheet = workbook.getSheetAt(0);

		Cell rootCell = findRootCell(sheet);

		List<HeaderInfo> headerInfos = collectMeasurementHeaders(rootCell,
				sessionTemplate.getSessionTemplateMeasures());

		List<MeasurementValues> measurements = collectValues(sessionTemplate, sheet, rootCell);

		MeasurementInfo result = new MeasurementInfo();
		result.setHeaderInfos(headerInfos);
		result.setMeasurements(measurements);

		return result;
	}

	@Override
	protected TaskResponse executeGetTaskInfos(Object command) {
		return null;
	}
}
