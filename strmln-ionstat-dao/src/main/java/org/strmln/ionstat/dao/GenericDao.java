package org.strmln.ionstat.dao;

import java.io.Serializable;

public interface GenericDao<T extends Serializable> {

	void delete(T entity);

	T findById(Long id);

	T save(T entity);

	void update(T entity);
}
