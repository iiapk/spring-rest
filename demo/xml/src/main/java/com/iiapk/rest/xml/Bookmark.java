package com.iiapk.rest.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Bookmark {
	
	@XStreamAlias("mark")
	private String title;

	private String location;
	
	public Bookmark() {
		super();
	}

	public Bookmark(String title, String location) {
		super();
		this.title = title;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
