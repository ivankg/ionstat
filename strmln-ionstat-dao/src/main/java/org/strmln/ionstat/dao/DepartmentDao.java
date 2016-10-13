package org.strmln.ionstat.dao;

import java.util.List;

import org.strmln.ionstat.model.Department;

public interface DepartmentDao extends GenericDao<Department> {

	List<Department> findDepartmentsByFacilityId(Long facilityId);

}
