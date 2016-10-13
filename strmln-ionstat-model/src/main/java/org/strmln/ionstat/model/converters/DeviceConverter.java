package org.strmln.ionstat.model.converters;

import org.strmln.ionstat.dto.Device;

public class DeviceConverter {

	public static Device fromInternalToDto(
			org.strmln.ionstat.model.Device internalEntity) {
		if (internalEntity == null) {
			return null;
		}
		Device result = new Device();
		result.setDeviceUsage(internalEntity.getDeviceUsage());
		result.setId(internalEntity.getDeviceId());
		result.setManufacturer(internalEntity.getManufacturer());
		result.setModel(internalEntity.getModel());
		result.setName(internalEntity.getName());
		result.setSerialNumber(internalEntity.getSerialNumber());

		return result;
	}

}
