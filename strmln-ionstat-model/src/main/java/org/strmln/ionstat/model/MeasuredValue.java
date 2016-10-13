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
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "MEASURED_VALUE")
@BatchSize(size = 100)
public class MeasuredValue implements Serializable {

	private static final long serialVersionUID = -7484289184130791972L;

	private Long _measuredValueId;
	private Measure _measure;
	private String _stringValue;
	private Double _doubleValue;
	private MeasuredType _type;
	private Measurement _measurement;

	@Column(name = "DOUBLE_VALUE")
	public Double getDoubleValue() {
		return _doubleValue;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEASURE_ID")
	public Measure getMeasure() {
		return _measure;
	}

	@Id
	@Column(name = "MEASURED_VALUE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMeasuredValueId() {
		return _measuredValueId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEASUREMENT_ID")
	public Measurement getMeasurement() {
		return _measurement;
	}

	@Column(name = "STRING_VALUE", length = 64)
	public String getStringValue() {
		return _stringValue;
	}

	@Enumerated
	@Column(name = "type")
	public MeasuredType getType() {
		return _type;
	}

	@Transient
	public Object getValue() {
		return _stringValue != null ? _stringValue : _doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		_doubleValue = doubleValue;
	}

	public void setMeasure(Measure measure) {
		_measure = measure;
	}

	public void setMeasuredValueId(Long measuredValueId) {
		_measuredValueId = measuredValueId;
	}

	public void setMeasurement(Measurement measurement) {
		_measurement = measurement;
	}

	public void setStringValue(String stringValue) {
		_stringValue = stringValue;
	}

	public void setType(MeasuredType type) {
		_type = type;
	}

	public void setValue(Object value) {
		if (value instanceof Double) {
			_doubleValue = (Double) value;
		} else {
			_stringValue = (String) value;
		}
	}

}
