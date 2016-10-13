package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.Facility;

public interface FacilityService extends GenericService<Facility> {

	Facility addNewFacility(String address, String city, String contactPerson,
			String email, String name, String phone, Long pib);

	List<Facility> findUserFacilities(Long userProfileId);

}
