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
@Table(name = "DEPARTMENT")
@BatchSize(size = 100)
public class Department extends AbstractNameHolder {

	private static final long serialVersionUID = -7232940586704469108L;

	private Long _departmentId;

	private Facility _facility;

	private Set<Room> _rooms;

	@Id
	@Column(name = "DEPARTMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getDepartmentId() {
		return _departmentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID", nullable = false)
	public Facility getFacility() {
		return _facility;
	}

	@Override
	@Transient
	public Long getIdentifier() {
		return getDepartmentId();
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "department")
	public Set<Room> getRooms() {
		return _rooms;
	}

	public void setDepartmentId(Long departmentId) {
		_departmentId = departmentId;
	}

	public void setFacility(Facility facility) {
		_facility = facility;
	}

	public void setRooms(Set<Room> rooms) {
		_rooms = rooms;
	}

}
