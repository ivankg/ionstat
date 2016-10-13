package org.strmln.ionstat.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "MEASUREMENT")
@BatchSize(size = 100)
public class Measurement implements Serializable {

	private static final long serialVersionUID = -1408373986628652734L;

	private Long _measurementId;
	private List<MeasuredValue> _measuredValues;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "measurement", orphanRemoval = true)
	@OrderColumn(name = "IDX")
	public List<MeasuredValue> getMeasuredValues() {
		return _measuredValues;
	}

	@Id
	@Column(name = "MEASUREMENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMeasurementId() {
		return _measurementId;
	}

	public void setMeasuredValues(List<MeasuredValue> measuredValues) {
		_measuredValues = measuredValues;
	}

	public void setMeasurementId(Long measurementId) {
		_measurementId = measurementId;
	}

}
