package org.strmln.ionstat.dao;

import java.util.List;

import org.strmln.ionstat.model.Facility;

public interface FacilityDao extends GenericDao<Facility> {

	List<Facility> findUserFacilities(Long userProfileId);

}