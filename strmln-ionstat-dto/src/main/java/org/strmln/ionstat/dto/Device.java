package org.strmln.ionstat.dto;

import java.io.Serializable;

public class Device implements Serializable {

	private static final long serialVersionUID = -2517334239058499804L;

	private Long _id;
	private String _name;
	private String _manufacturer;
	private String _model;
	private String _serialNumber;
	private String _deviceUsage;
	private Session[] _sessions;

	public String getDeviceUsage() {
		return _deviceUsage;
	}

	public Long getId() {
		return _id;
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

	public String getSerialNumber() {
		return _serialNumber;
	}

	public Session[] getSessions() {
		return _sessions;
	}

	public void setDeviceUsage(String deviceUsage) {
		_deviceUsage = deviceUsage;
	}

	public void setId(Long id) {
		_id = id;
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

	public void setSerialNumber(String serialNumber) {
		_serialNumber = serialNumber;
	}

	public void setSessions(Session[] sessions) {
		_sessions = sessions;
	}

}
