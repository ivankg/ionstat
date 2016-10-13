package org.strmln.ionstat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "POLICY")
@BatchSize(size = 100)
public class Policy implements Serializable {

	private static final long serialVersionUID = 3103849504929217779L;

	private String _policyId;

	public Policy() {
	}

	public Policy(String policyId) {
		_policyId = policyId;
	}

	@Id
	@Column(name = "POLICY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getPolicyId() {
		return _policyId;
	}

	public void setPolicyId(String policyId) {
		_policyId = policyId;
	}

	@Override
	public String toString() {
		return _policyId;
	}

}
