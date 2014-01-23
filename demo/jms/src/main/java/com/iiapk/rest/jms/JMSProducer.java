package com.iiapk.rest.jms;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSProducer {
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void send(final Student student) {  
		jmsTemplate.send(new MessageCreator(){
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage(); 
				message.setString("key1", student.getName());  
                message.setString("key2", student.getId());  
                //filter
                //message.setStringProperty("color", "red");
                //ObjectMessage message = session.createObjectMessage();  
                //message.setObject(student);  
				return message;
			}
		});
	}

}
