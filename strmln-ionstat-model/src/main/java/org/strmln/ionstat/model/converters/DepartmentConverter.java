package org.strmln.ionstat.model.converters;

import org.strmln.ionstat.dto.Department;

public class DepartmentConverter {

	public static Department fromInternalToDto(
			org.strmln.ionstat.model.Department internalEntity) {
		if (internalEntity == null) {
			return null;
		}
		Department result = new Department();
		result.setId(internalEntity.getDepartmentId());
		result.setName(internalEntity.getName());

		return result;
	}

}
