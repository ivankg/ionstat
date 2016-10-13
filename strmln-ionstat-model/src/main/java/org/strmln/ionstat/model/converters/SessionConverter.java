package org.strmln.ionstat.model.converters;

import java.text.SimpleDateFormat;

import org.strmln.ionstat.dto.Session;

public class SessionConverter {

	public static Session fromInternalToDto(
			org.strmln.ionstat.model.Session session) {
		if (session == null) {
			return null;
		}
		Session result = new Session();
		result.setId(session.getSessionId());

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// temporary setting session status
		result.setSessionPerformDate(dateFormat
				.format(session.getSessionPerformDate()).concat(" - ")
				.concat(session.getStatus().toString()));

		return result;
	}
}
