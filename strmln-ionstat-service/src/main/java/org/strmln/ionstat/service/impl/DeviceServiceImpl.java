package org.strmln.ionstat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.strmln.ionstat.dao.DeviceDao;
import org.strmln.ionstat.dao.GenericDao;
import org.strmln.ionstat.dao.RoomDao;
import org.strmln.ionstat.model.Device;
import org.strmln.ionstat.model.Room;
import org.strmln.ionstat.service.AbstractService;
import org.strmln.ionstat.service.DeviceService;

@Service("deviceService")
public class DeviceServiceImpl extends AbstractService<Device> implements
		DeviceService {

	@Autowired
	private DeviceDao _deviceDao;

	@Autowired
	private RoomDao _roomDao;

	@Override
	public Device addNewDevice(Long roomId, String name, String manufacturer,
			String model, String serialNumber, String deviceUsage) {
		Room room = getRoomDao().findById(roomId);

		Device device = new Device();
		device.setName(name);
		device.setManufacturer(manufacturer);
		device.setModel(model);
		device.setSerialNumber(serialNumber);
		device.setDeviceUsage(deviceUsage);
		device.setRoom(room);

		return getDeviceDao().save(device);
	}

	@Override
	public Device findByIdWithSessionAndSessionTemplate(Long deviceId) {
		return getDeviceDao().findByIdWithSessionAndSessionTemplate(deviceId);
	}

	@Override
	public List<Device> findDevicesByRoomId(Long roomId) {
		return getDeviceDao().findDevicesByRoomId(roomId);
	}

	private DeviceDao getDeviceDao() {
		return _deviceDao;
	}

	private RoomDao getRoomDao() {
		return _roomDao;
	}

	@Override
	@Value("#{deviceDao}")
	protected void setEntityDao(GenericDao<Device> entityDao) {
		super.setEntityDao(entityDao);
	}
}
