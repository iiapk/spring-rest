package com.iiapk.rest.web;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext-web.xml"})
public class SpringClient {
	
	@Autowired
	private EmployeeService employeeService;
	
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
	
	public static void main(String[] args) {
		HttpRequest request = new BasicHttpRequest("GET", "/", HttpVersion.HTTP_1_1);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK, "OK1");
		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
	}
	
}
