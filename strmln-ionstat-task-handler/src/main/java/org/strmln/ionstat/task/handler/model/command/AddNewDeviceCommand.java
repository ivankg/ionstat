package org.strmln.ionstat.task.handler.model.command;

public class AddNewDeviceCommand {

	private Long _parentId;
	private String _name;
	private String _manufacturer;
	private String _model;
	private String _serialNumber;
	private String _deviceUsage;

	public String getDeviceUsage() {
		return _deviceUsage;
	}

	public String getManufacturer() {
		return _manufacturer;
	}

	public String getModel() {
		return _model;
	}

	public String getName() {
		return _name;
	}

	public Long getParentId() {
		return _parentId;
	}

	public String getSerialNumber() {
		return _serialNumber;
	}

	public void setDeviceUsage(String deviceUsage) {
		_deviceUsage = deviceUsage;
	}

	public void setManufacturer(String manufacturer) {
		_manufacturer = manufacturer;
	}

	public void setModel(String model) {
		_model = model;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentId(Long parentId) {
		_parentId = parentId;
	}

	public void setSerialNumber(String serialNumber) {
		_serialNumber = serialNumber;
	}

}
