package com.iiapk.module.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void save(final User user) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)throws DataAccessException {
				connection.set(
						redisTemplate.getStringSerializer().serialize("user.id." + user.getId()),
						redisTemplate.getStringSerializer().serialize(user.getName()));
				return null;
			}
		});
	}

	@Override
	public User read(final String uid) {
		return redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection)throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("user.id." + uid);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String address = redisTemplate.getStringSerializer().deserialize(value);
					User user = new User();
					user.setName(address);
					user.setId(uid);
					return user;
				}
				return null;
			}
		});
	}

	@Override
	public void delete(final String uid) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) {
				connection.del(redisTemplate.getStringSerializer().serialize("user.id." + uid));
				return null;
			}
		});
	}

}
