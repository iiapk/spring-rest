package com.iiapk.rest.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/applicationContext-redis.xml" })
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	private User tmp;
	
	@Before
	public void before() throws Exception {
		tmp = new User();
	}

	@Test
	public void crud() {
		// -------------- Create ---------------
		String uid = "u123456";
		String address1 = "上海";
		tmp.setAddress(address1);
		tmp.setId(uid);
		userDao.save(tmp);
		// ---------------Read ---------------
		User user = userDao.read(tmp.getId());
		assertEquals(address1, user.getAddress());
		// --------------Update ------------
		String address2 = "北京";
		uid = "u12345678";
		tmp.setId(uid);
		tmp.setAddress(address2);
		userDao.save(tmp);
		user = userDao.read(tmp.getId());
		assertEquals(address2, user.getAddress());
		// --------------Delete ------------
		userDao.delete(uid);
		user = userDao.read(uid);
		assertNull(user);
	}

}
