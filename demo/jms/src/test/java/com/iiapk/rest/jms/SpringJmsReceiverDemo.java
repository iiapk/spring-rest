package com.iiapk.rest.jms;

import javax.jms.JMSException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:config/applicationContext-jms.xml"})
public class SpringJmsReceiverDemo {
	
	@Autowired
	private JMSReceiver receiver;
	
	@Test
	public void receive() throws JMSException {
		Assert.assertEquals(receiver.receive().getString("key1").toString(), "zhangqi");;
	}
	
}
