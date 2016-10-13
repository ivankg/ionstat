package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.UserProfileDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<UserProfile> implements
		UserProfileDao {

	@Override
	public UserProfile findUserByUsername(final String username) {
		return execute(new HibernateCallback<UserProfile>() {

			@Override
			public UserProfile execute(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.getNamedQuery("UserProfile.findUserByUsername");
				query.setParameter("username", username);

				UserProfile userProfile = (UserProfile) query.uniqueResult();

				Hibernate.initialize(userProfile.getRole());
				Hibernate.initialize(userProfile.getRole().getPolicies());

				return userProfile;
			}
		});
	}

	@Override
	protected Class<UserProfile> getEntityPersistanceClass() {
		return UserProfile.class;
	}

}
