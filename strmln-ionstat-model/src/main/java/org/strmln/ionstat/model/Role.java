package org.strmln.ionstat.model;

import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "ROLE")
@BatchSize(size = 100)
public class Role implements Identifiable {

	private static final long serialVersionUID = -7964325787245585771L;

	private Long _roleId;
	private String _roleName;
	private Set<Policy> _policies;

	@Override
	@Transient
	public Long getIdentifier() {
		return _roleId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_POLICY", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "POLICY_ID"))
	public Set<Policy> getPolicies() {
		return _policies;
	}

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRoleId() {
		return _roleId;
	}

	@Column(name = "ROLE_NAME", length = 32)
	public String getRoleName() {
		return _roleName;
	}

	public void setPolicies(Set<Policy> policies) {
		_policies = policies;
	}

	public void setRoleId(Long roleId) {
		_roleId = roleId;
	}

	public void setRoleName(String roleName) {
		_roleName = roleName;
	}

	public void addPolicy(Policy policy) {
		if (_policies == null) {
			_policies = new HashSet<>();
		}
		_policies.add(policy);
	}

}
