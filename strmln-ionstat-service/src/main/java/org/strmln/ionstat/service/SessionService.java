package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.SessionStatus;
import org.strmln.ionstat.service.model.RecordedValues;

public interface SessionService extends GenericService<Session> {

	void changeSessionStatus(Long sessionId, SessionStatus newStatus,
			String comment);

	Session createNewSession(Long sessionTemplateId, Long deviceId,
			List<RecordedValues> measurementValues);

	void deleteSession(Long sessionId);

	Session findByIdWithDependents(Long sessionId);

}
