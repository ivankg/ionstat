package org.strmln.ionstat.model.converters;

import org.strmln.ionstat.dto.Room;

public class RoomConverter {

	public static Room fromInternalToDto(
			org.strmln.ionstat.model.Room internalEntity) {
		if (internalEntity == null) {
			return null;
		}
		Room result = new Room();
		result.setId(internalEntity.getRoomId());
		result.setInspectionInterval(internalEntity.getInspectionInterval());
		result.setName(internalEntity.getName());

		return result;
	}

}
