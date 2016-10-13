package org.strmln.ionstat.mvc.commands;

import org.strmln.ionstat.mvc.utils.JsonUtils;

public class JsonCommand {

	private String _jsonData;

	public String getJsonData() {
		return _jsonData;
	}

	public void setJsonData(String jsonData) {
		_jsonData = jsonData;

		JsonUtils.updateExistingCommand(this, jsonData);
	}

}
