package org.strmln.ionstat.dao;

import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.SessionStatus;

public interface SessionDao extends GenericDao<Session> {

	void changeSessionStatus(Long sessionId, SessionStatus newStatus,
			String comment);

	Session findByIdWithDependents(Long sessionId);

}
