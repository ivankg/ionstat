package org.strmln.ionstat.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNameHolder implements Identifiable {

	private static final long serialVersionUID = 7694898172781261373L;

	@Column(name = "NAME", length = 128)
	private String _name;

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
}
