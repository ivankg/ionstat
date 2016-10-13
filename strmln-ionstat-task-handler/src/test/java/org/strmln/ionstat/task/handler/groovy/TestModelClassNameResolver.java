package org.strmln.ionstat.task.handler.groovy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import groovy.util.ObjectGraphBuilder.ClassNameResolver;

public class TestModelClassNameResolver implements ClassNameResolver {

	private static final String MAPPING_FILE_NAME = "groovy-model-mapping.config";
	private static final String DOT = "."; //$NON-NLS-1$
	private static final String UNDERSCORE = "_"; //$NON-NLS-1$
	private Map<String, String> _testClassesMap = new HashMap<>();

	public TestModelClassNameResolver() {
		parseClassesQualifiedNames();
	}

	@Override
	public String resolveClassname(String classname) {
		String normalizedName = classname;
		if (normalizedName.startsWith(UNDERSCORE)) {
			normalizedName = normalizedName.substring(1);
		}

		normalizedName = normalizedName.toLowerCase();

		String fullClass = _testClassesMap.get(normalizedName);

		if (fullClass == null) {
			throw new RuntimeException(String.format("Qualified name not found for class: %s", classname)); //$NON-NLS-1$
		}

		return fullClass;
	}

	private void parseClassesQualifiedNames() {
		try (InputStream classesStream = getClass().getClassLoader().getResourceAsStream(MAPPING_FILE_NAME)) { // $NON-NLS-1$

			if (classesStream == null) {
				throw new RuntimeException("groovy-model-mapping.config not found on classpath"); //$NON-NLS-1$
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(classesStream));

			String line;
			while ((line = reader.readLine()) != null) {
				int dotIndex = line.lastIndexOf(DOT);
				String className = line.substring(++dotIndex);
				className = className.toLowerCase();

				_testClassesMap.put(className, line);
			}

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
