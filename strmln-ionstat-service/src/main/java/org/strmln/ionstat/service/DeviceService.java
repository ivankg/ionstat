package org.strmln.ionstat.service;

import java.util.List;

import org.strmln.ionstat.model.Device;

public interface DeviceService extends GenericService<Device> {

	Device addNewDevice(Long roomId, String name, String manufacturer,
			String model, String serialNumber, String deviceUsage);

	Device findByIdWithSessionAndSessionTemplate(Long deviceId);

	List<Device> findDevicesByRoomId(Long roomId);

}
