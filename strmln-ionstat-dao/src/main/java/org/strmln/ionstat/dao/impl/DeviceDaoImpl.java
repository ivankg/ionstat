package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.DeviceDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.Device;
import org.strmln.ionstat.model.Session;

@Repository("deviceDao")
public class DeviceDaoImpl extends AbstractDao<Device> implements DeviceDao {

	@Override
	public Device findByIdWithSessionAndSessionTemplate(final Long deviceId) {
		return execute(new HibernateCallback<Device>() {

			@Override
			public Device execute(org.hibernate.Session hibernateSession)
					throws HibernateException, SQLException {
				Device device = findById(deviceId);
				Hibernate.initialize(device.getSessions());
				for (Session session : device.getSessions()) {
					Hibernate.initialize(session.getSessionTemplate());
				}

				return device;
			}
		});
	}

	@Override
	public List<Device> findDevicesByRoomId(final Long roomId) {
		return execute(new HibernateCallback<List<Device>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Device> execute(org.hibernate.Session hibernateSession)
					throws HibernateException, SQLException {

				Query query = hibernateSession
						.getNamedQuery("Device.findDevicesByRoomId");
				query.setParameter("roomId", roomId);

				return query.list();
			}
		});
	}

	@Override
	protected Class<Device> getEntityPersistanceClass() {
		return Device.class;
	}

}
