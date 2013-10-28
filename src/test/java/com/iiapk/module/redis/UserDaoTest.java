package com.iiapk.module.redis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest {
	
	private ApplicationContext app;  
    private UserDao userDao; 
	
	 @Before  
	    public void before() throws Exception {  
	        app = new ClassPathXmlApplicationContext("applicationContext.xml");  
	        userDao = (UserDao) app.getBean("userDao");  
	    } 
	
	@Test  
    public void crud() {  
        // -------------- Create ---------------  
        String uid = "u123456";  
        String address1 = "上海";  
        User user = new User();  
        user.setName(address1);  
        user.setId(uid);  
        userDao.save(user);  
  
        // ---------------Read ---------------  
        user = userDao.read(uid);  
  
        assertEquals(address1, user.getName());  
  
        // --------------Update ------------  
        String address2 = "北京";  
        user.setName(address2);  
        userDao.save(user);  
  
        user = userDao.read(uid);  
  
        assertEquals(address2, user.getName());  
  
        // --------------Delete ------------  
        userDao.delete(uid);  
        user = userDao.read(uid);  
        assertNull(user);  
    }  
	
	
}
