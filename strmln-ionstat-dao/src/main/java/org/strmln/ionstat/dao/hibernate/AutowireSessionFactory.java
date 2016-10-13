package org.strmln.ionstat.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AutowireSessionFactory {

	@Autowired
	private SessionFactory _sessionFactory;

	public <T> T execute(HibernateCallback<T> callback) {
		Session session = getSessionFactory().getCurrentSession();

		try {
			return callback.execute(session);
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public SessionFactory getSessionFactory() {
		return _sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

}
