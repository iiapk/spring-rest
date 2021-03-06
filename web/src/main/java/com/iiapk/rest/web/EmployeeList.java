package com.iiapk.rest.web;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("employeeList")
public class EmployeeList implements Serializable{

	private static final long serialVersionUID = 1L;
	private int count;
	private List<Employee> employees;

	public EmployeeList() {
	}

	public EmployeeList(List<Employee> employees) {
		this.employees = employees;
		this.count = employees.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@XmlElement(name = "employee")
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
