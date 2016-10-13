package org.strmln.ionstat.dao;

import java.util.List;

import org.strmln.ionstat.model.SessionTemplate;

public interface SessionTemplateDao extends GenericDao<SessionTemplate> {

	SessionTemplate findByIdWithDependants(Long sessionTemplateId);

	List<SessionTemplate> findSessionTemplatesByDeviceId(Long deviceId);

}
