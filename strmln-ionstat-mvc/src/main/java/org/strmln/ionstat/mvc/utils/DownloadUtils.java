package org.strmln.ionstat.mvc.utils;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class DownloadUtils {

	private static final String CACHE_CONTROL_VALUE = "max-age=0";

	private static final String CACHE_CONTROL = "Cache-Control";

	private static final String PRAGMA_VALUE = "public";

	private static final String PRAGMA = "Pragma";

	public static final String DEFAULT_MIME_TYPE = "text/html;charset=utf-8";

	private static final String CONTENT_ENCODING_TRANSFER_HEADER_NAME = "Content-Transfer-Encoding";

	private static final String CONTENT_ENCODING_TRANSFER_HEADER_VALUE = "binary";

	private static final String DOWNLOAD_MIME_TYPE = "application/pdf";

	private static final String FILENAME_FORMAT_VALUE = "attachment; filename=\"%s\"";

	private static final String FILENAME_NO_ATTACHMENT_VALUE = "inline; filename=\"%s\"";

	private static final String FILENAME_HEADER_NAME = "Content-Disposition";

	public static void fillDownloadResponse(String resourceName,
			InputStream inputStream, HttpServletResponse response) {
		fillResponse(resourceName, inputStream, response, DEFAULT_MIME_TYPE,
				true);
	}

	public static void fillResponse(String resourceName,
			InputStream inputStream, HttpServletResponse response,
			String mimeType, boolean disposition) {
		try {
			ServletOutputStream out = response.getOutputStream();

			response.setContentType(DOWNLOAD_MIME_TYPE);
			response.setContentLength(inputStream.available());

			if (disposition) {
				response.setHeader(FILENAME_HEADER_NAME,
						String.format(FILENAME_FORMAT_VALUE, resourceName));
			} else {
				response.setHeader(FILENAME_HEADER_NAME, String.format(
						FILENAME_NO_ATTACHMENT_VALUE, resourceName));
			}
			response.setHeader(CONTENT_ENCODING_TRANSFER_HEADER_NAME,
					CONTENT_ENCODING_TRANSFER_HEADER_VALUE);

			response.setHeader(PRAGMA, PRAGMA_VALUE);

			response.setHeader(CACHE_CONTROL, CACHE_CONTROL_VALUE);

			IOUtils.copy(inputStream, out);

			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}