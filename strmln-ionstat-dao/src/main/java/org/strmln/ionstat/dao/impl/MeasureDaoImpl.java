package org.strmln.ionstat.dao.impl;

import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.MeasureDao;
import org.strmln.ionstat.model.Measure;

@Repository("measureDao")
public class MeasureDaoImpl extends AbstractDao<Measure> implements MeasureDao {

	@Override
	protected Class<Measure> getEntityPersistanceClass() {
		return Measure.class;
	}

}
