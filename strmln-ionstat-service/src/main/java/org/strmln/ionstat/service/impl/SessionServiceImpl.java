package org.strmln.ionstat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.DeviceDao;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.dao.MeasureDao;
import org.strmln.ionstat.dao.MeasuredValueDao;
import org.strmln.ionstat.dao.MeasurementDao;
import org.strmln.ionstat.dao.SessionDao;
import org.strmln.ionstat.dao.SessionTemplateDao;
import org.strmln.ionstat.model.Device;
import org.strmln.ionstat.model.Measure;
import org.strmln.ionstat.model.MeasuredType;
import org.strmln.ionstat.model.MeasuredValue;
import org.strmln.ionstat.model.Measurement;
import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.SessionStatus;
import org.strmln.ionstat.model.SessionTemplate;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.SessionService;
import org.strmln.ionstat.service.model.RecordedValues;

@Service("sessionService")
public class SessionServiceImpl extends AbstractService<Session> implements
		SessionService {

	@Autowired
	private MeasureDao _measureDao;

	@Autowired
	private MeasuredValueDao _measuredValueDao;

	@Autowired
	private SessionTemplateDao _sessionTemplateDao;

	@Autowired
	private SessionDao _sessionDao;

	@Autowired
	private MeasurementDao _measurementDao;

	@Autowired
	private DeviceDao _deviceDao;

	@Override
	public void changeSessionStatus(Long sessionId,
			SessionStatus newStatus, String comment) {
		getSessionDao().changeSessionStatus(sessionId, newStatus, comment);
	}

	@Override
	public Session createNewSession(Long sessionTemplateId, Long deviceId,
			List<RecordedValues> measurementValues) {

		Map<Measure, List<Object>> valuesByMeasure = new LinkedHashMap<>();
		int numberOfMeasurements = measurementValues.get(0).getRecordedValues()
				.size();
		for (RecordedValues singleMeasurementValues : measurementValues) {
			Long measureId = singleMeasurementValues.getMeasureId();
			Measure measure = getMeasureDao().findById(measureId);
			valuesByMeasure.put(measure,
					singleMeasurementValues.getRecordedValues());
		}

		SessionTemplate sessionTemplate = getSessionTemplateDao()
				.findByIdWithDependants(sessionTemplateId);
		Device device = getDeviceDao().findById(deviceId);

		Session session = new Session();
		session.setSessionTemplate(sessionTemplate);
		session.setDevice(device);
		session.setSessionPerformDate(new Date());
		session.setStatus(SessionStatus.PENDING);

		List<Measurement> sessionMeasurements = new ArrayList<>();
		for (int i = 0; i < numberOfMeasurements; i++) {
			Measurement measurement = new Measurement();
			measurement.setMeasuredValues(new ArrayList<MeasuredValue>());
			sessionMeasurements.add(measurement);
		}
		session.setMeasurements(sessionMeasurements);
		getSessionDao().save(session);

		for (Entry<Measure, List<Object>> entry : valuesByMeasure.entrySet()) {
			for (int i = 0; i < numberOfMeasurements; i++) {
				Measure measure = entry.getKey();
				Measurement measurement = sessionMeasurements.get(i);
				MeasuredValue measuredValue = new MeasuredValue();
				measuredValue.setValue(entry.getValue().get(i));
				measuredValue.setMeasure(measure);
				measuredValue.setType(MeasuredType.MEASUREMENT);
				measurement.getMeasuredValues().add(measuredValue);
			}
		}

		for (Measurement measurement : sessionMeasurements) {
			getMeasurementDao().save(measurement);
		}

		return session;
	}

	@Override
	public void deleteSession(Long sessionId) {
		Session session = getSessionDao().findByIdWithDependents(sessionId);

		List<Measurement> measurements = session.getMeasurements();
		for (Measurement measurement : measurements) {
			List<MeasuredValue> measuredValues = measurement
					.getMeasuredValues();
			for (MeasuredValue measuredValue : measuredValues) {
				if (measuredValue.getType().equals(
						MeasuredType.MEASUREMENT)) {
					getMeasuredValueDao().delete(measuredValue);
				}
			}
			getMeasurementDao().delete(measurement);
		}

		getSessionDao().delete(session);
	}

	@Override
	public Session findByIdWithDependents(Long sessionId) {
		return getSessionDao().findByIdWithDependents(sessionId);
	}

	private DeviceDao getDeviceDao() {
		return _deviceDao;
	}

	private MeasureDao getMeasureDao() {
		return _measureDao;
	}

	private MeasuredValueDao getMeasuredValueDao() {
		return _measuredValueDao;
	}

	private MeasurementDao getMeasurementDao() {
		return _measurementDao;
	}

	private SessionDao getSessionDao() {
		return _sessionDao;
	}

	private SessionTemplateDao getSessionTemplateDao() {
		return _sessionTemplateDao;
	}

	@Override
	@Value("#{sessionDao}")
	protected void setEntityDao(GenericDao<Session> entityDao) {
		super.setEntityDao(entityDao);
	}

}
