package org.strmln.ionstat.dao;

import java.util.List;

import org.strmln.ionstat.model.Room;

public interface RoomDao extends GenericDao<Room> {

	List<Room> findRoomsByDepartmentId(Long departmentId);

}
