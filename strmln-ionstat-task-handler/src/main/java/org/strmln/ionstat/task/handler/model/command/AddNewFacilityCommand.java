package org.strmln.ionstat.task.handler.model.command;

public class AddNewFacilityCommand {

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
