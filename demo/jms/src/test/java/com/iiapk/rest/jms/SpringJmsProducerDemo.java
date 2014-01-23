package com.iiapk.rest.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJmsProducerDemo {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"config/applicationContext-jms.xml");
		JMSProducer jmsProducer =(JMSProducer)context.getBean("producer");
		Student student = new Student();
		student.setName("zhangqi");
		student.setId("25");
		jmsProducer.send(student);
		System.out.println("消息已经发送....."); 
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
