package org.strmln.ionstat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "SESSION_TEMPLATE_MEASURE")
@BatchSize(size = 100)
public class SessionTemplateMeasure implements Serializable {

	private static final long serialVersionUID = 8198358984454665278L;

	private Long _sessionTemplateMeasureId;
	private SessionTemplate _sessionTemplate;
	private Measure _measure;
	private SessionTemplateMeasureType _type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEASURE_ID")
	public Measure getMeasure() {
		return _measure;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SESSION_TEMPLATE_ID")
	public SessionTemplate getSessionTemplate() {
		return _sessionTemplate;
	}

	@Id
	@Column(name = "SESSION_TEMPLATE_MEASURE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSessionTemplateMeasureId() {
		return _sessionTemplateMeasureId;
	}

	@Enumerated
	@Column(name = "TYPE")
	public SessionTemplateMeasureType getType() {
		return _type;
	}

	public void setMeasure(Measure measure) {
		_measure = measure;
	}

	public void setSessionTemplate(SessionTemplate sessionTemplate) {
		_sessionTemplate = sessionTemplate;
	}

	public void setSessionTemplateMeasureId(Long sessionTemplateMeasureId) {
		_sessionTemplateMeasureId = sessionTemplateMeasureId;
	}

	public void setType(SessionTemplateMeasureType type) {
		_type = type;
	}

}
