package org.strmln.ionstat.model.converters;

import java.util.ArrayList;
import java.util.List;

import org.strmln.ionstat.dto.DisplayEntity;
import org.strmln.ionstat.model.AbstractNameHolder;

public class DisplayEntityConverter {

	public static DisplayEntity convert(AbstractNameHolder internalEntity) {
		if (internalEntity == null) {
			return null;
		}
		DisplayEntity result = new DisplayEntity();
		result.setId(internalEntity.getIdentifier());
		result.setName(internalEntity.getName());

		return result;
	}

	public static List<DisplayEntity> convert(
			List<? extends AbstractNameHolder> internalEntities) {
		if (internalEntities == null || internalEntities.isEmpty()) {
			return null;
		}
		List<DisplayEntity> result = new ArrayList<>();
		for (AbstractNameHolder internalEntity : internalEntities) {
			result.add(convert(internalEntity));
		}

		return result;
	}

}
