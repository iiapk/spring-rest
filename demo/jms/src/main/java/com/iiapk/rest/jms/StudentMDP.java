package com.iiapk.rest.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class StudentMDP implements MessageListener{

	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;  
        Student student = new Student();  
        try {  
            student.setName(mapMessage.getString("key1"));  
            student.setId(mapMessage.getString("key2"));  
            System.out.println(student.getName());  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
	}

}
