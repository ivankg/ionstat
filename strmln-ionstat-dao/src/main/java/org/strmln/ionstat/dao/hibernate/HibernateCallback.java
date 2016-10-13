package org.strmln.ionstat.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface HibernateCallback<T> {

	public T execute(Session session) throws HibernateException, SQLException;

}
