package com.iiapk.rest.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iiapk.rest.cxf.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext-cxf.xml","classpath:config/applicationContext-web.xml"})
public class SpringCXFClient {
	
	@Autowired
	private TestService testService;
	
	@Test
	public void testCXFBean(){
		Assert.assertEquals(testService.sayHi("test"),"test");
	}
	
	@Test
	public void testCXFCustomerBean(){
		Assert.assertEquals(testService.findCustomer("123").getName(),"customer_name");
	}
}
