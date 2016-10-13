package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.DepartmentDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl extends AbstractDao<Department> implements
		DepartmentDao {

	@Override
	public List<Department> findDepartmentsByFacilityId(final Long facilityId) {
		return execute(new HibernateCallback<List<Department>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Department> execute(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.getNamedQuery("Department.findDepartmentsByFacilityId");
				query.setParameter("facilityId", facilityId);
				return query.list();
			}
		});
	}

	@Override
	protected Class<Department> getEntityPersistanceClass() {
		return Department.class;
	}

}
