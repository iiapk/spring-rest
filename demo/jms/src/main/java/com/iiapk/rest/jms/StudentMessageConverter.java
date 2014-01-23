package com.iiapk.rest.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;

public class StudentMessageConverter {
	
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		if (!(message instanceof MapMessage)) {
			throw new MessageConversionException("Message is not a MapMessage");
		}
		MapMessage mapMessage = (MapMessage) message;
		Student student = new Student();
		student.setName(mapMessage.getString("key1"));
		student.setId(mapMessage.getString("key2"));
		return student;
	}

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		if (!(object instanceof Student)) {
			throw new MessageConversionException("Object is not a student");
		}
		Student student = (Student) object;
		MapMessage message = session.createMapMessage();
		message.setString("key1", student.getName());
		message.setString("key2", student.getId());
		return message;
	}

}
