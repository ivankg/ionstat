package org.strmln.ionstat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "DEVICE")
@BatchSize(size = 100)
public class Device extends AbstractNameHolder {

	private static final long serialVersionUID = 4461981950809167526L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Long _deviceId;
	private String _manufacturer;
	private String _model;
	private String _serialNumber;
	private String _deviceUsage;
	private Room _room;
	private Set<Session> _sessions;
	private Set<SessionTemplate> _sessionTemplates;

	@Id
	@Column(name = "DEVICE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDeviceId() {
		return _deviceId;
	}

	@Column(name = "DEVICE_USAGE", length = 256)
	public String getDeviceUsage() {
		return _deviceUsage;
	}

	@Override
	@Transient
	public Long getIdentifier() {
		return getDeviceId();
	}

	@Column(name = "MANUFACTURER", length = 256)
	public String getManufacturer() {
		return _manufacturer;
	}

	@Column(name = "MODEL", length = 128)
	public String getModel() {
		return _model;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROOM_ID")
	public Room getRoom() {
		return _room;
	}

	@Column(name = "SERIAL_NUMBER", length = 128)
	public String getSerialNumber() {
		return _serialNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "device")
	public Set<Session> getSessions() {
		return _sessions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "DEVICE_SESSION_TEMPLATE", joinColumns = @JoinColumn(name = "DEVICE_ID"), inverseJoinColumns = @JoinColumn(name = "SESSION_TEMPLATE_ID"))
	public Set<SessionTemplate> getSessionTemplates() {
		return _sessionTemplates;
	}

	public void setDeviceId(Long deviceId) {
		_deviceId = deviceId;
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

	public void setRoom(Room room) {
		_room = room;
	}

	public void setSerialNumber(String serialNumber) {
		_serialNumber = serialNumber;
	}

	public void setSessions(Set<Session> sessions) {
		_sessions = sessions;
	}

	public void setSessionTemplates(Set<SessionTemplate> sessionTemplates) {
		_sessionTemplates = sessionTemplates;
	}

}
