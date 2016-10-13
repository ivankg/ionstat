package org.strmln.ionstat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "FACILITY")
@BatchSize(size = 100)
public class Facility extends AbstractNameHolder {

	private static final long serialVersionUID = 1190917941000235900L;

	private Long _facilityId;
	private String _city;
	private String _address;
	private String _contactPerson;
	private String _email;
	private String _phone;
	private Long _pib;
	private Set<Department> _departments;

	@Column(name = "ADDRESS", length = 256)
	public String getAddress() {
		return _address;
	}

	@Column(name = "CITY", length = 256)
	public String getCity() {
		return _city;
	}

	@Column(name = "CONTACT_PERSON", length = 256)
	public String getContactPerson() {
		return _contactPerson;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "facility")
	public Set<Department> getDepartments() {
		return _departments;
	}

	@Column(name = "EMAIL", length = 256)
	public String getEmail() {
		return _email;
	}

	@Id
	@Column(name = "FACILITY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFacilityId() {
		return _facilityId;
	}

	@Override
	@Transient
	public Long getIdentifier() {
		return getFacilityId();
	}

	@Column(name = "PHONE", length = 32)
	public String getPhone() {
		return _phone;
	}

	@Column(name = "PIB", length = 256)
	public Long getPib() {
		return _pib;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setContactPerson(String contactPerson) {
		_contactPerson = contactPerson;
	}

	public void setDepartments(Set<Department> departments) {
		_departments = departments;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setFacilityId(Long facilityId) {
		_facilityId = facilityId;
	}

	public void setPhone(String phone) {
		_phone = phone;
	}

	public void setPib(Long pib) {
		_pib = pib;
	}

}
