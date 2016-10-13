package org.strmln.ionstat.service;

import java.io.Serializable;

public interface GenericService<T extends Serializable> {

	T findById(Long id);

	void save(T entity);

}
