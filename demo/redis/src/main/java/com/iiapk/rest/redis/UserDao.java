package com.iiapk.rest.redis;

public interface UserDao {
	
	void save(User user);
	
	User read(String uid);

	void delete(String uid);
}
