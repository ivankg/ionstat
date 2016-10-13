package org.strmln.ionstat.dao.impl;

import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.MeasurementDao;
import org.strmln.ionstat.model.Measurement;

@Repository("measurementDao")
public class MeasurementDaoImpl extends AbstractDao<Measurement> implements
		MeasurementDao {

	@Override
	protected Class<Measurement> getEntityPersistanceClass() {
		return Measurement.class;
	}

}
