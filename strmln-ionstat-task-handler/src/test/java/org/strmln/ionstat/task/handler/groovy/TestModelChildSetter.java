package org.strmln.ionstat.task.handler.groovy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import groovy.util.ObjectGraphBuilder.ChildPropertySetter;

public class TestModelChildSetter implements ChildPropertySetter {

	private static final Log LOGGER = LogFactory.getLog(TestModelChildSetter.class);

	private static final String UNDERSCORE = "_";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setChild(Object parent, Object child, String parentName, String propertyName) {
		try {
			propertyName = UNDERSCORE.concat(propertyName);

			Field field = findField(parent, propertyName);
			field.setAccessible(true);
			Object property = field.get(parent);

			Class<?> propertyClass = field.getType();
			if (Collection.class.isAssignableFrom(propertyClass)) {
				if (property == null) {
					if (List.class.isAssignableFrom(propertyClass)) {
						property = new ArrayList();
					} else if (Set.class.isAssignableFrom(propertyClass)) {
						property = new HashSet();
					}
				}

				((Collection) property).add(child);
				field.set(parent, property);
			} else {
				field.set(parent, child);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Field findField(Object obj, String fieldName) {
		Field field = null;
		Class<?> clazz = obj.getClass();
		while (field == null && clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				LOGGER.trace("Ignore this error: ", e);
			}
			clazz = clazz.getSuperclass();
		}
		if (field == null) {
			throw new RuntimeException("There is no field " + fieldName + " in " + obj.getClass());
		}
		return field;
	}
}
