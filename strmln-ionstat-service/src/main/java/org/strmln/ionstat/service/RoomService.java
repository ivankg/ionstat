package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.Room;

public interface RoomService extends GenericService<Room> {

	Room addNewRoom(Long departmentId, String name, Long inspectionInterval);

	List<Room> findRoomsByDepartmentId(Long departmentId);

}
