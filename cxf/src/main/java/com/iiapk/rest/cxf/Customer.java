package com.iiapk.rest.cxf;

import java.util.Date;

public class Customer {
	
	private String id;
	
	private String name;
	
	private Date birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return id+","+name+","+birthday;
	}
}
