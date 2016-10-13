package org.strmln.ionstat.mvc.utils;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.strmln.ionstat.mvc.commands.JsonCommand;

public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = initializeDeserializers();

	public static <T> T readValue(String json, Class<T> expectedType) {
		try {
			return OBJECT_MAPPER.readValue(json, expectedType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void updateExistingCommand(JsonCommand command,
			String jsonData) {
		try {
			OBJECT_MAPPER.readerForUpdating(command).readValue(jsonData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static ObjectMapper initializeDeserializers() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(
				org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true);
		objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		SimpleModule module = new SimpleModule("pd module", new Version(1, 9,
				7, null));

		objectMapper.registerModule(module);

		return objectMapper;
	}

}
