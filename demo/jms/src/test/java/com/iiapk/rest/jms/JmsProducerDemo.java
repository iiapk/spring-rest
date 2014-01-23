package com.iiapk.rest.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JmsProducerDemo {
	
	
	public void send() {  
		// 产生连接工厂
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		Session session = null;
		try{
			// 产生连接
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 设置消息目的地类型（ActiveMQTopic为订阅目的地）
			Destination destination = new ActiveMQQueue("myQueue");
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage message = session.createTextMessage();
			message.setText("Just a simaple JMS Testing.");
			TextMessage message1 = session.createTextMessage();
			message1.setText("Just a filter JMS Testing.");
			message1.setStringProperty("color", "red");
			producer.send(message1);
			producer.send(message);
			Thread.sleep(10000);
		}
		catch(JMSException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(session!=null){
					session.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new JmsProducerDemo().send();
	}

}
