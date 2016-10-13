package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.SessionTemplateDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.MeasuredValue;
import org.strmln.ionstat.model.Measurement;
import org.strmln.ionstat.model.SessionTemplate;
import org.strmln.ionstat.model.SessionTemplateMeasure;

@Repository("sessionTemplateDao")
public class SessionTemplateDaoImpl extends AbstractDao<SessionTemplate>
		implements SessionTemplateDao {

	@Override
	public SessionTemplate findByIdWithDependants(Long sessionTemplateId) {
		SessionTemplate sessionTemplate = findById(sessionTemplateId);

		Hibernate.initialize(sessionTemplate.getSessionTemplateMeasures());
		for (SessionTemplateMeasure sessionTemplateMeasure : sessionTemplate
				.getSessionTemplateMeasures()) {
			Hibernate.initialize(sessionTemplateMeasure.getMeasure());
		}
		Hibernate.initialize(sessionTemplate.getNominalMeasurements());
		for (Measurement nominalMeasurement : sessionTemplate
				.getNominalMeasurements()) {
			Hibernate.initialize(nominalMeasurement.getMeasuredValues());
			for (MeasuredValue nominalValue : nominalMeasurement
					.getMeasuredValues()) {
				Hibernate.initialize(nominalValue.getMeasure());
			}
		}
		return sessionTemplate;
	}

	@Override
	public List<SessionTemplate> findSessionTemplatesByDeviceId(
			final Long deviceId) {
		return execute(new HibernateCallback<List<SessionTemplate>>() {

			@Override
			@SuppressWarnings("unchecked")
			public List<SessionTemplate> execute(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.getNamedQuery("SessionTemplate.findSessionTemplatesByDeviceId");
				query.setParameter("deviceId", deviceId);

				List<SessionTemplate> sessionTemplates = query.list();
				for (SessionTemplate sessionTemplate : sessionTemplates) {
					Hibernate.initialize(sessionTemplate
							.getNominalMeasurements());
					for (Measurement nominalMeasurement : sessionTemplate
							.getNominalMeasurements()) {
						Hibernate.initialize(nominalMeasurement
								.getMeasuredValues());
						for (MeasuredValue measuredValue : nominalMeasurement
								.getMeasuredValues()) {
							Hibernate.initialize(measuredValue.getMeasure());
						}
					}
					Hibernate.initialize(sessionTemplate
							.getSessionTemplateMeasures());
					for (SessionTemplateMeasure sessionTemplateMeasure : sessionTemplate
							.getSessionTemplateMeasures()) {
						Hibernate.initialize(sessionTemplateMeasure
								.getMeasure());

					}
				}

				return sessionTemplates;
			}
		});
	}

	@Override
	protected Class<SessionTemplate> getEntityPersistanceClass() {
		return SessionTemplate.class;
	}
}
