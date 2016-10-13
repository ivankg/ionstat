package org.strmln.ionstat.task.handler.groovy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import groovy.util.ObjectGraphBuilder.NewInstanceResolver;

@SuppressWarnings("rawtypes")
public class TestModelNewInstanceResolver implements NewInstanceResolver {

	private static final String COMMA = ","; //$NON-NLS-1$
	private static final Log LOGGER = LogFactory.getLog(TestModelChildSetter.class);

	@Override
	public Object newInstance(Class clazz, Map arg1) throws InstantiationException, IllegalAccessException {
		Constructor[] constructors = clazz.getConstructors();

		// try to find public constructor with minimal number of arguments
		Constructor minConstructor = findConstructorWithMinimumArguments(constructors);

		// public constructor needed
		if (minConstructor == null) {
			throw new RuntimeException(String.format("Could not find public constructor for: %s", clazz)); //$NON-NLS-1$
		}

		Class[] parameterTypes = minConstructor.getParameterTypes();
		logConstructor(parameterTypes);

		LOGGER.trace(String.format("Insatiating class: %s", clazz)); //$NON-NLS-1$

		if (parameterTypes.length == 0) {
			return clazz.newInstance();
		} else {
			Object[] constructorArguments = findConstructorArguments(arg1, parameterTypes);

			try {
				return minConstructor.newInstance(constructorArguments);
			} catch (IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(String.format("Problem instantiating %s", clazz.getName()), e); //$NON-NLS-1$
			}
		}
	}

	private Object[] findConstructorArguments(Map arg1, Class[] parameterTypes) {
		Object[] constructorArguments = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Class parameterType = parameterTypes[i];
			// find first argument from groovy script that matches this
			// parameter type
			// and pass it to constructor arguments
			Object groovyArgument = findGroovyArgument(arg1, parameterType);

			if (groovyArgument == null) {
				throw new RuntimeException(
						String.format("Could not find groovy argument of type: %s", parameterType.getName())); //$NON-NLS-1$
			}

			constructorArguments[i] = groovyArgument;
		}
		return constructorArguments;
	}

	private Constructor findConstructorWithMinimumArguments(Constructor[] constructors) {
		Constructor minConstructor = null;
		for (Constructor constructor : constructors) {
			if (!Modifier.isPublic(constructor.getModifiers())) {
				continue;
			}

			Class[] parameterTypes = constructor.getParameterTypes();

			if (minConstructor == null) {
				minConstructor = constructor;
			} else {
				Class[] minConstructorParameterTypes = minConstructor.getParameterTypes();
				if (parameterTypes.length < minConstructorParameterTypes.length) {
					minConstructor = constructor;
				}
			}
		}

		return minConstructor;
	}

	private Object findGroovyArgument(Map arg1, Class parameterType) {
		Object groovyArgument = null;
		for (Object entryObject : arg1.entrySet()) {
			Map.Entry entry = (Map.Entry) entryObject;
			Object value = entry.getValue();
			Class<? extends Object> groovyArgumentClass = value.getClass();
			if (groovyArgumentClass.isAssignableFrom(parameterType)) {
				LOGGER.debug(String.format("Found groovy argument type: %s with value: %s", //$NON-NLS-1$
						groovyArgumentClass.getName(), value));

				groovyArgument = value;
			}
		}
		return groovyArgument;
	}

	private void logConstructor(Class[] parameterTypes) {
		if (LOGGER.isTraceEnabled()) {
			if (parameterTypes.length == 0) {
				LOGGER.trace("Using no args constructor"); //$NON-NLS-1$
			} else {
				StringBuilder parameterNames = new StringBuilder();

				parameterNames.append(parameterTypes[0].getName());
				for (int i = 1; i < parameterTypes.length; i++) {
					parameterNames.append(COMMA);
					parameterNames.append(parameterTypes[i]);
				}

				LOGGER.trace(String.format("Using constructor with arguments: %s", parameterNames.toString())); //$NON-NLS-1$
			}
		}
	}
}
