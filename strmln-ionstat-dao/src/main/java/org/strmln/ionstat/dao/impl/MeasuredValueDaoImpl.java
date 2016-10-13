package org.strmln.ionstat.dao.impl;

import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.MeasuredValueDao;
import org.strmln.ionstat.model.MeasuredValue;

@Repository("measuredValueDao")
public class MeasuredValueDaoImpl extends AbstractDao<MeasuredValue> implements
		MeasuredValueDao {

	@Override
	protected Class<MeasuredValue> getEntityPersistanceClass() {
		return MeasuredValue.class;
	}

}
