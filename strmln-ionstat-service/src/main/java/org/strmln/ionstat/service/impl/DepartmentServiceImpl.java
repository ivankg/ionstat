package org.strmln.ionstat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.DepartmentDao;
import org.strmln.ionstat.dao.FacilityDao;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.model.Department;
import org.strmln.ionstat.model.Facility;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl extends AbstractService<Department>
		implements DepartmentService {

	@Autowired
	private DepartmentDao _departmentDao;

	@Autowired
	private FacilityDao _facilityDao;

	@Override
	public Department addNewDepartment(Long facilityId, String departmentName) {
		Facility facility = getFacilityDao().findById(facilityId);

		Department department = new Department();
		department.setName(departmentName);
		department.setFacility(facility);

		return getDepartmentDao().save(department);
	}

	@Override
	public List<Department> findDepartmentsByFacilityId(Long facilityId) {
		return getDepartmentDao().findDepartmentsByFacilityId(facilityId);
	}

	public FacilityDao getFacilityDao() {
		return _facilityDao;
	}

	private DepartmentDao getDepartmentDao() {
		return _departmentDao;
	}

	@Override
	@Value("#{departmentDao}")
	protected void setEntityDao(GenericDao<Department> entityDao) {
		super.setEntityDao(entityDao);
	}

}
