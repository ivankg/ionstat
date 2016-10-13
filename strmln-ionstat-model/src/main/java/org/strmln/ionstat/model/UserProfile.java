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
@Table(name = "USER_PROFILE")
@BatchSize(size = 100)
public class UserProfile implements Identifiable {

	private static final long serialVersionUID = 5523973003604824301L;

	private Long _userProfileId;
	private String _username;
	private String _password;
	private String _firstName;
	private String _lastName;
	private Role _role;
	private Set<Facility> _facilities;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "USER_PROFILE_FACILITY", joinColumns = @JoinColumn(name = "USER_PROFILE_ID"), inverseJoinColumns = @JoinColumn(name = "FACILITY_ID"))
	public Set<Facility> getFacilities() {
		return _facilities;
	}

	@Column(name = "FIRST_NAME", length = 64)
	public String getFirstName() {
		return _firstName;
	}

	@Override
	@Transient
	public Long getIdentifier() {
		return _userProfileId;
	}

	@Column(name = "LAST_NAME", length = 64)
	public String getLastName() {
		return _lastName;
	}

	@Column(name = "PASSWORD", length = 64)
	public String getPassword() {
		return _password;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return _role;
	}

	@Column(name = "USERNAME", length = 64)
	public String getUsername() {
		return _username;
	}

	@Id
	@Column(name = "USER_PROFILE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUserProfileId() {
		return _userProfileId;
	}

	public void setFacilities(Set<Facility> facilities) {
		_facilities = facilities;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setRole(Role role) {
		_role = role;
	}

	public void setUsername(String username) {
		_username = username;
	}

	public void setUserProfileId(Long userProfileId) {
		_userProfileId = userProfileId;
	}


}
