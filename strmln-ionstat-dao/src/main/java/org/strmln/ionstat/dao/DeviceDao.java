package org.strmln.ionstat.dao;

import java.util.List;

import org.strmln.ionstat.model.Device;

public interface DeviceDao extends GenericDao<Device> {

	Device findByIdWithSessionAndSessionTemplate(Long deviceId);

	List<Device> findDevicesByRoomId(Long roomId);

}
