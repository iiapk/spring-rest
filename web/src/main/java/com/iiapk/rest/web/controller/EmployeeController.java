package com.iiapk.rest.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.xstream.XStreamMarshaller;
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
import com.thoughtworks.xstream.XStream;


@Controller
public class EmployeeController {

	private static final String XML_VIEW_NAME = "xmlView";

	@Autowired
	private EmployeeService employeeService;
	
	private XStream xStream = new XStream();

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employee/{id}")
	public ModelAndView getEmployee(ModelMap model, @PathVariable String id) {
		Employee employee = employeeService.get(Long.parseLong(id));
		return new ModelAndView(XML_VIEW_NAME,"employee", employee);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/employeeTest/{id}")
	public @ResponseBody Employee getEmployeeTest(ModelMap model, @PathVariable String id) {
		Employee employee = employeeService.get(Long.parseLong(id));
		return employee;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public ModelAndView getEmployees(ModelMap model) {
		List<Employee> employees = employeeService.getAll();
		EmployeeList list = new EmployeeList(employees);
		return new ModelAndView(XML_VIEW_NAME,"employees",list);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/employee/{id}")
	public @ResponseBody Employee updateEmployee(@RequestBody Employee employee ) {
		employeeService.update(employee);
		return employee;
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
		return new ModelAndView("employees");
	}

}
