package org.strmln.ionstat.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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
@Table(name = "SESSION")
@BatchSize(size = 100)
public class Session implements Serializable {

	private static final long serialVersionUID = 4306122037002029479L;

	private Long _sessionId;
	private String _comment;
	private Device _device;
	private List<Measurement> _measurements;
	private Date _sessionPerformDate;
	private SessionTemplate _sessionTemplate;
	private SessionStatus _status;

	@Column(name = "COMMENT", length = 1024)
	public String getComment() {
		return _comment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEVICE_ID")
	public Device getDevice() {
		return _device;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SESSION_ID")
	@OrderColumn(name = "IDX")
	public List<Measurement> getMeasurements() {
		return _measurements;
	}

	@Id
	@Column(name = "SESSION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSessionId() {
		return _sessionId;
	}

	@Column(name = "SESSION_PERFORM_DATE")
	public Date getSessionPerformDate() {
		return _sessionPerformDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SESSION_TEMPLATE_ID")
	public SessionTemplate getSessionTemplate() {
		return _sessionTemplate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	public SessionStatus getStatus() {
		return _status;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setDevice(Device device) {
		_device = device;
	}

	public void setMeasurements(List<Measurement> measurements) {
		_measurements = measurements;
	}

	public void setSessionId(Long sessionId) {
		_sessionId = sessionId;
	}

	public void setSessionPerformDate(Date sessionPerformDate) {
		_sessionPerformDate = sessionPerformDate;
	}

	public void setSessionTemplate(SessionTemplate sessionTemplate) {
		_sessionTemplate = sessionTemplate;
	}

	public void setStatus(SessionStatus status) {
		_status = status;
	}

}
