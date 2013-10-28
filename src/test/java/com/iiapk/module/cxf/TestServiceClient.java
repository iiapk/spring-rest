package com.iiapk.module.cxf;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class TestServiceClient {

	public static void main(String[] args) throws MalformedURLException {
		/*URL url = new URL("http://localhost:8080/test?wsdl");
		QName SERVICE_NAME = new QName("http://cxf.module.iiapk.com","TestServiceImplService");
		Service service = Service.create(url, SERVICE_NAME);
		TestService testService = service.getPort(TestService.class);*/
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(TestService.class);
		jaxWsProxyFactoryBean.setAddress("http://localhost:8080/test?wsdl");
		TestService testService=(TestService)jaxWsProxyFactoryBean.create();
		System.out.println(testService.sayHi("test"));
	}
}
