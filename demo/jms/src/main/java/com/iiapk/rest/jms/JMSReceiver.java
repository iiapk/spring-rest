package com.iiapk.rest.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class JMSReceiver {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public MapMessage receive(){
		MapMessage message = (MapMessage)jmsTemplate.receive();
		try {
			if(message!=null){
				System.out.println(message.getString("key1")+message.getString("key2"));
			}
			else{
				System.out.println("未接收到内容");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return message;
	}

}
