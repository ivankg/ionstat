package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.SessionDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.MeasuredValue;
import org.strmln.ionstat.model.Measurement;
import org.strmln.ionstat.model.Session;
import org.strmln.ionstat.model.SessionStatus;

@Repository("sessionDao")
public class SessionDaoImpl extends AbstractDao<Session> implements SessionDao {

	@Override
	public void changeSessionStatus(final Long sessionId,
			final SessionStatus newStatus, final String comment) {
		execute(new HibernateCallback<Void>() {

			@Override
			public Void execute(org.hibernate.Session hibernateSession)
					throws HibernateException, SQLException {
				Session session = findById(sessionId);
				session.setStatus(newStatus);
				session.setComment(comment);
				update(session);
				return null;
			}
		});
	}

	@Override
	public Session findByIdWithDependents(final Long sessionId) {
		return execute(new HibernateCallback<Session>() {

			@Override
			public Session execute(org.hibernate.Session hibernateSession)
					throws HibernateException, SQLException {
				Session session = findById(sessionId);
				Hibernate.initialize(session.getMeasurements());
				for (Measurement measurement : session.getMeasurements()) {
					Hibernate.initialize(measurement.getMeasuredValues());
				}
				for (Measurement measurement : session.getMeasurements()) {
					for (MeasuredValue measuredValue : measurement
							.getMeasuredValues()) {
						Hibernate.initialize(measuredValue.getMeasure());
					}
				}
				Hibernate.initialize(session.getSessionTemplate());
				Hibernate.initialize(session.getSessionTemplate()
						.getNominalMeasurements());
				for (Measurement measurement : session.getSessionTemplate()
						.getNominalMeasurements()) {
					Hibernate.initialize(measurement.getMeasuredValues());
					for (MeasuredValue measuredValue : measurement
							.getMeasuredValues()) {
						Hibernate.initialize(measuredValue.getMeasure());
					}
				}

				return session;
			}
		});
	}

	@Override
	protected Class<Session> getEntityPersistanceClass() {
		return Session.class;
	}

}
