package com.iiapk.module.mongodb;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

//@Configuration
public class AppConfig {

   public @Bean Mongo mongo() throws UnknownHostException {
       return new Mongo("localhost",27017);
   }
   
   public @Bean MongoFactoryBean mongo2() {
       MongoFactoryBean mongo = new MongoFactoryBean();
       mongo.setHost("localhost");
       return mongo;
  }
   
   public @Bean MongoTemplate mongoTemplate() throws Exception {
       return new MongoTemplate(mongo(), "mydatabase");
   }
} 
