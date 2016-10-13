package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.Department;

public interface DepartmentService extends GenericService<Department> {

	Department addNewDepartment(Long facilityId, String departmentName);

	List<Department> findDepartmentsByFacilityId(Long facilityId);

}
