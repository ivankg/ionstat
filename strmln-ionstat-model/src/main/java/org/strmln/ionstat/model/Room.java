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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "ROOM")
@BatchSize(size = 100)
public class Room extends AbstractNameHolder {

	private static final long serialVersionUID = -7414383868231231589L;

	private Long _roomId;
	private Long _inspectionInterval;
	private Department _department;
	private Set<Device> _devices;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	public Department getDepartment() {
		return _department;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "room")
	public Set<Device> getDevices() {
		return _devices;
	}

	@Override
	@Transient
	public Long getIdentifier() {
		return getRoomId();
	}

	@Column(name = "INSPECTION_INTERVAL")
	public Long getInspectionInterval() {
		return _inspectionInterval;
	}

	@Id
	@Column(name = "ROOM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRoomId() {
		return _roomId;
	}

	public void setDepartment(Department department) {
		_department = department;
	}

	public void setDevices(Set<Device> devices) {
		_devices = devices;
	}

	public void setInspectionInterval(Long inspectionInterval) {
		_inspectionInterval = inspectionInterval;
	}

	public void setRoomId(Long roomId) {
		_roomId = roomId;
	}

}
