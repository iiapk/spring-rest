package com.iiapk.rest.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JmsReceiverDemo {

	public void receive(){
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
			MessageConsumer consumer = session.createConsumer(destination,"color ='red'");
			conn.start();
			Message message = consumer.receive();
			TextMessage textMessage = (TextMessage)message;
			System.out.println(textMessage.getText());
		}
		catch(JMSException e){
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
	}
	
	public static void main(String[] args) {
		new JmsReceiverDemo().receive();
	}
}
