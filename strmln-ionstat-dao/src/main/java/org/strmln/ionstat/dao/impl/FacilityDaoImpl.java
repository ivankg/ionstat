package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.FacilityDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.Facility;

@Repository("facilityDao")
public class FacilityDaoImpl extends AbstractDao<Facility> implements
		FacilityDao {

	@Override
	public List<Facility> findUserFacilities(final Long userProfileId) {
		return execute(new HibernateCallback<List<Facility>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Facility> execute(Session session)
					throws HibernateException, SQLException {

				Query query = session
						.getNamedQuery("Facility.findUserFacilities");
				query.setParameter("userProfileId", userProfileId);
				return query.list();
			}
		});
	}

	@Override
	protected Class<Facility> getEntityPersistanceClass() {
		return Facility.class;
	}

}
