package com.iiapk.rest.cxf;

import java.net.MalformedURLException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Client {

	public static void main(String[] args) throws MalformedURLException {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(TestService.class);
		jaxWsProxyFactoryBean.setAddress("http://localhost:9000/ws/TestService");
		TestService testService=(TestService)jaxWsProxyFactoryBean.create();
		System.out.println(testService.sayHi("test"));
	}
}
