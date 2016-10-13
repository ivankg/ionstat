package org.strmln.ionstat.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "SESSION_TEMPLATE")
@BatchSize(size = 100)
public class SessionTemplate implements Serializable {

	private static final long serialVersionUID = 2049454057548530861L;

	private Long _sessionTemplateId;
	private String _name;
	private String _description;
	private TestingParameterType _testingParameter;
	private Measure _testingMeasure;
	private List<Measurement> _nominalMeasurements;
	private Set<SessionTemplateMeasure> _sessionTemplateMeasures;
	private Boolean _uploadRequired;

	@Column(name = "DESCRIPTION", length = 1024)
	public String getDescription() {
		return _description;
	}

	@Column(name = "NAME", length = 64)
	public String getName() {
		return _name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SESSION_TEMPLATE_ID")
	@OrderColumn(name = "IDX")
	public List<Measurement> getNominalMeasurements() {
		return _nominalMeasurements;
	}

	@Id
	@Column(name = "SESSION_TEMPLATE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSessionTemplateId() {
		return _sessionTemplateId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SESSION_TEMPLATE_ID")
	public Set<SessionTemplateMeasure> getSessionTemplateMeasures() {
		return _sessionTemplateMeasures;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TESTING_MEASURE_ID")
	public Measure getTestingMeasure() {
		return _testingMeasure;
	}

	@Enumerated
	@Column(name = "TESTING_PARAMETER")
	public TestingParameterType getTestingParameter() {
		return _testingParameter;
	}

	@Column(name = "UPLOAD_REQUIRED")
	public Boolean getUploadRequired() {
		return _uploadRequired;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNominalMeasurements(List<Measurement> nominalMeasurements) {
		_nominalMeasurements = nominalMeasurements;
	}

	public void setSessionTemplateId(Long sessionTemplateId) {
		_sessionTemplateId = sessionTemplateId;
	}

	public void setSessionTemplateMeasures(Set<SessionTemplateMeasure> sessionTemplateMeasures) {
		_sessionTemplateMeasures = sessionTemplateMeasures;
	}

	public void setTestingMeasure(Measure testingMeasure) {
		_testingMeasure = testingMeasure;
	}

	public void setTestingParameter(TestingParameterType testingParameter) {
		_testingParameter = testingParameter;
	}

	public void setUploadRequired(Boolean uploadRequired) {
		_uploadRequired = uploadRequired;
	}

}
