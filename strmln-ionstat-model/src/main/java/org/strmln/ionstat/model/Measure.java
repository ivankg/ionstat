package org.strmln.ionstat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "MEASURE")
@BatchSize(size = 100)
public class Measure implements Serializable {

	private static final String OPEN_BRACKET = "(";
	private static final String CLOSE_BRACKET = ")";

	private static final long serialVersionUID = 1965765750658680393L;

	private Long _measureId;
	private String _name;
	private String _unit;

	@Transient
	public String getDisplayUnit() {
		return OPEN_BRACKET.concat(_unit).concat(CLOSE_BRACKET);
	}

	@Id
	@Column(name = "MEASURE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMeasureId() {
		return _measureId;
	}

	@Column(name = "NAME", length = 256)
	public String getName() {
		return _name;
	}

	@Column(name = "UNIT", length = 8)
	public String getUnit() {
		return _unit;
	}

	public void setMeasureId(Long measureId) {
		_measureId = measureId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUnit(String unit) {
		_unit = unit;
	}

}
