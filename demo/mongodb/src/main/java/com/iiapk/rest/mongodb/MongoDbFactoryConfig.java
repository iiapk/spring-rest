package com.iiapk.rest.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

//@Configuration
public class MongoDbFactoryConfig {
	
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials userCredentials = new UserCredentials("joe", "secret");
		return new SimpleMongoDbFactory(new Mongo(), "mongodb",userCredentials);
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
}
