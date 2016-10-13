package org.strmln.ionstat.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.strmln.ionstat.dao.AbstractDao;
import org.strmln.ionstat.dao.RoomDao;
import org.strmln.ionstat.dao.hibernate.HibernateCallback;
import org.strmln.ionstat.model.Room;

@Repository("roomDao")
public class RoomDaoImpl extends AbstractDao<Room> implements RoomDao {

	@Override
	public List<Room> findRoomsByDepartmentId(final Long departmentId) {
		return execute(new HibernateCallback<List<Room>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Room> execute(Session session)
					throws HibernateException, SQLException {

				Query query = session
						.getNamedQuery("Room.findRoomsByDepartmentId");
				query.setParameter("departmentId", departmentId);

				return query.list();
			}
		});
	}

	@Override
	protected Class<Room> getEntityPersistanceClass() {
		return Room.class;
	}

}
