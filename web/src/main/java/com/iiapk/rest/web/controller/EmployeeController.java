package com.iiapk.rest.web.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iiapk.rest.web.Employee;
import com.iiapk.rest.web.EmployeeList;
import com.iiapk.rest.web.EmployeeService;


//@Controller
public class EmployeeController {

	private ObjectMapper objectMapper = new ObjectMapper();
	private static final String XML_VIEW_NAME = "employees";

	@Autowired
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/*
	 * private Jaxb2Marshaller jaxb2Mashaller;
	 * 
	 * public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
	 * this.jaxb2Mashaller = jaxb2Mashaller; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/employee/{id}")
	public String getEmployee(ModelMap model, @PathVariable String id) {
		Employee employee = employeeService.get(Long.parseLong(id));
		model.put("employee", employee);
		return XML_VIEW_NAME;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employeeTest/{id}")
	public @ResponseBody Employee getEmployeeTest(ModelMap model, @PathVariable String id) {
		Employee employee = employeeService.get(Long.parseLong(id));
		return employee;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/employee/{id}")
	public @ResponseBody Employee updateEmployee(@RequestBody Employee employee ) {
		employeeService.update(employee);
		return employee;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public String getEmployees(ModelMap model) {
		List<Employee> employees = employeeService.getAll();
		EmployeeList list = new EmployeeList(employees);
		model.put("employees", list);
		return XML_VIEW_NAME;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/employees")
	public @ResponseBody EmployeeList removeEmployee(@RequestBody EmployeeList employees) {
		return employees;
	}

	

	/*@RequestMapping(method = RequestMethod.POST, value = "/employee")
	public ModelAndView addEmployee(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
		employeeService.add(e);
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}*/

	@RequestMapping(value = "/test")
	public ModelAndView test(String employees) {
		objectMapper = new ObjectMapper();
		Employee employee = null;
		try {
			employee = objectMapper.readValue(employees, Employee.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView(XML_VIEW_NAME, "employees", employee);
	}

}
