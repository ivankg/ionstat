package org.strmln.ionstat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.FacilityDao;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.model.Facility;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.FacilityService;

@Service("facilityService")
public class FacilityServiceImpl extends AbstractService<Facility> implements
		FacilityService {

	@Autowired
	private FacilityDao _facilityDao;

	@Override
	public Facility addNewFacility(String address, String city,
			String contactPerson, String email, String name, String phone,
			Long pib) {
		Facility facility = new Facility();
		facility.setAddress(address);
		facility.setCity(city);
		facility.setContactPerson(contactPerson);
		facility.setEmail(email);
		facility.setName(name);
		facility.setPhone(phone);
		facility.setPib(pib);

		return getFacilityDao().save(facility);
	}

	@Override
	public List<Facility> findUserFacilities(Long userProfileId) {
		return getFacilityDao().findUserFacilities(userProfileId);
	}

	private FacilityDao getFacilityDao() {
		return _facilityDao;
	}

	@Override
	@Value("#{facilityDao}")
	protected void setEntityDao(GenericDao<Facility> entityDao) {
		super.setEntityDao(entityDao);
	}
}
