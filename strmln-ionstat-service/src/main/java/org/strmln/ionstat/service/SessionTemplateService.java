package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.SessionTemplate;

public interface SessionTemplateService extends GenericService<SessionTemplate> {

	SessionTemplate findByIdWithDependants(Long sessionTemplateId);

	List<SessionTemplate> findSessionTemplatesByDeviceId(Long deviceId);

}
