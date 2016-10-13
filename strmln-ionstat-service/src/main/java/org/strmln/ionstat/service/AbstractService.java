package org.strmln.ionstat.service;

import java.io.Serializable;

import org.strmln.ionstat.dao.GenericDao;

public abstract class AbstractService<T extends Serializable> implements
		GenericService<T> {

	private GenericDao<T> _entityDao;

	public T findById(Long id) {
		return getEntityDao().findById(id);
	}

	public void save(T entity) {
		getEntityDao().save(entity);
	}

	protected GenericDao<T> getEntityDao() {
		return _entityDao;
	}

	protected void setEntityDao(GenericDao<T> entityDao) {
		_entityDao = entityDao;
	};

}
