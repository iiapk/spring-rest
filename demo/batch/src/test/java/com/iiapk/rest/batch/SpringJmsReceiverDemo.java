package com.iiapk.rest.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:config/applicationContext-batch.xml"})
public class SpringJmsReceiverDemo {
	
	@Autowired
	private SimpleJob footballJob;
	
	@Test
	public void receive() {
		Assert.assertEquals(footballJob.getName(),"test");
	}
	
}
