package org.strmln.ionstat.task.handler.model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class TaskResponse {

	private Map<String, Object> _responseModel;
	private Task[] _supportedTasks;
	private InputStream _inputStream;
	private String _resourceName;

	public void add(String key, Object value) {
		if (_responseModel == null) {
			_responseModel = new HashMap<>();
		}
		_responseModel.put(key, value);
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public Map<String, Object> getResponseModel() {
		return _responseModel;
	}

	public Task[] getSupportedTasks() {
		return _supportedTasks;
	}

	public void setInputStream(InputStream inputStream) {
		_inputStream = inputStream;
	}

	public void setResourceName(String resourceName) {
		_resourceName = resourceName;
	}

	public void setResponseModel(Map<String, Object> responseModel) {
		_responseModel = responseModel;
	}

	public void setSupportedTasks(Task[] supportedTasks) {
		_supportedTasks = supportedTasks;
	}

}
