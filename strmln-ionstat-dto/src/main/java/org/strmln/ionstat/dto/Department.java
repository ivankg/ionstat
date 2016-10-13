package org.strmln.ionstat.dto;

import java.io.Serializable;

public class Department implements Serializable {

	private static final long serialVersionUID = 2583894547599635320L;

	private Long _id;
	private String _name;

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

}
