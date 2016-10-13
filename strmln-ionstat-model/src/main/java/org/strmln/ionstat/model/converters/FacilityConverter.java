package org.strmln.ionstat.model.converters;

import org.strmln.ionstat.dto.Facility;

public class FacilityConverter {

	public static Facility fromInternalToDto(
			org.strmln.ionstat.model.Facility internalEntity) {
		if (internalEntity == null) {
			return null;
		}
		Facility result = new Facility();
		result.setAddress(internalEntity.getAddress());
		result.setCity(internalEntity.getCity());
		result.setContactPerson(internalEntity.getContactPerson());
		result.setEmail(internalEntity.getEmail());
		result.setId(internalEntity.getFacilityId());
		result.setName(internalEntity.getName());
		result.setPhone(internalEntity.getPhone());
		result.setPib(internalEntity.getPib());

		return result;
	}

}
