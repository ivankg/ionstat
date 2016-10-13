package org.strmln.ionstat.dto;

import java.io.Serializable;

public class Facility implements Serializable {

	private static final long serialVersionUID = 1840977661330333747L;

	private Long _id;
	private String _name;
	private String _city;
	private String _address;
	private String _contactPerson;
	private String _email;
	private String _phone;
	private Long _pib;

	public String getAddress() {
		return _address;
	}

	public String getCity() {
		return _city;
	}

	public String getContactPerson() {
		return _contactPerson;
	}

	public String getEmail() {
		return _email;
	}

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getPhone() {
		return _phone;
	}

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

	public void setEmail(String email) {
		_email = email;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPhone(String phone) {
		_phone = phone;
	}

	public void setPib(Long pib) {
		_pib = pib;
	}

}
