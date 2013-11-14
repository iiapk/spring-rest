package com.iiapk.rest.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iiapk.rest.cxf.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext-cxf.xml","classpath:config/applicationContext-web.xml"})
public class SpringClient {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TestService testService;
	
	@Before
	public void prepare(){
	}
	
	@After
	public void destroy(){
		
	}
	
	@Test
	public void testLocalBean(){
		Assert.assertEquals(employeeService.getAll().size(),2);
	}
	
	@Test
	public void testCXFBean(){
		Assert.assertEquals(testService.sayHi("test"),"test");
	}
}
