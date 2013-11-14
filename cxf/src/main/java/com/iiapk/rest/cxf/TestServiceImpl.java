package com.iiapk.rest.cxf;

import javax.xml.ws.Endpoint;


public class TestServiceImpl implements TestService{

	@Override
	public String sayHi(String text) {
		return text;
	}
	
	public static void main(String[] args) {
		TestService testService = new TestServiceImpl();
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/test", testService);
	}
	
}
