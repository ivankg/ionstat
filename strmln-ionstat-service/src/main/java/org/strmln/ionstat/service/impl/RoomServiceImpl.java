package org.strmln.ionstat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.DepartmentDao;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.dao.RoomDao;
import org.strmln.ionstat.model.Department;
import org.strmln.ionstat.model.Room;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.RoomService;

@Service("roomService")
public class RoomServiceImpl extends AbstractService<Room> implements
		RoomService {

	@Autowired
	private RoomDao _roomDao;

	@Autowired
	private DepartmentDao _departmentDao;

	@Override
	public Room addNewRoom(Long departmentId, String name,
			Long inspectionInterval) {
		Department department = getDepartmentDao().findById(departmentId);

		Room room = new Room();
		room.setName(name);
		room.setInspectionInterval(inspectionInterval);
		room.setDepartment(department);

		return getRoomDao().save(room);
	}

	@Override
	public List<Room> findRoomsByDepartmentId(Long departmentId) {
		return getRoomDao().findRoomsByDepartmentId(departmentId);
	}

	private DepartmentDao getDepartmentDao() {
		return _departmentDao;
	}

	private RoomDao getRoomDao() {
		return _roomDao;
	}

	@Override
	@Value("#{roomDao}")
	protected void setEntityDao(GenericDao<Room> entityDao) {
		super.setEntityDao(entityDao);
	}
}
