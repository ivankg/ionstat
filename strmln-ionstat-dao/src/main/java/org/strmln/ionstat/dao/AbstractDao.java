package org.strmln.ionstat.dao;

import java.io.Serializable;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.strmln.ionstat.dao.hibernate.AutowireSessionFactory;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;

public abstract class AbstractDao<T extends Serializable> extends
		AutowireSessionFactory implements GenericDao<T> {

	public void delete(final T entity) {
		execute(new HibernateCallback<Void>() {

			@Override
			public Void execute(Session session) throws HibernateException,
					SQLException {
				session.delete(entity);
				return null;
			}
		});

	}

	public T findById(final Long id) {
		return execute(new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(Session session) throws HibernateException,
					SQLException {
				T result = (T) session.load(getEntityPersistanceClass(), id);
				Hibernate.initialize(result);

				return result;
			}
		});
	};

	public T save(final T entity) {
		return execute(new HibernateCallback<T>() {

			@Override
			public T execute(Session session) throws HibernateException,
					SQLException {
				session.save(entity);
				return entity;
			}
		});
	}

	public void update(final T entity) {
		execute(new HibernateCallback<Void>() {

			@Override
			public Void execute(Session session) throws HibernateException,
					SQLException {
				session.update(entity);
				return null;
			}
		});
	}

	protected abstract Class<T> getEntityPersistanceClass();

}
