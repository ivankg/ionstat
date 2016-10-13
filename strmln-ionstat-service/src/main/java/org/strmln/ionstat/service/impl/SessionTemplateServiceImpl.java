package org.strmln.ionstat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.dao.SessionTemplateDao;
import org.strmln.ionstat.model.SessionTemplate;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.SessionTemplateService;

@Service("sessionTemplateService")
public class SessionTemplateServiceImpl extends
		AbstractService<SessionTemplate> implements SessionTemplateService {

	@Autowired
	private SessionTemplateDao _sessionTemplateDao;

	@Override
	public SessionTemplate findByIdWithDependants(Long sessionTemplateId) {
		return getSessionTemplateDao()
				.findByIdWithDependants(sessionTemplateId);
	}

	@Override
	public List<SessionTemplate> findSessionTemplatesByDeviceId(Long deviceId) {
		return getSessionTemplateDao().findSessionTemplatesByDeviceId(deviceId);
	}

	private SessionTemplateDao getSessionTemplateDao() {
		return _sessionTemplateDao;
	}

	@Override
	@Value("#{sessionTemplateDao}")
	protected void setEntityDao(GenericDao<SessionTemplate> entityDao) {
		super.setEntityDao(entityDao);
	}

}
