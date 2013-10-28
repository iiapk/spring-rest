package com.iiapk.module.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;

public class MongoApp {
	
	private static final Logger log = LoggerFactory.getLogger(MongoApp.class);
	
	public void testWithConsole() throws UnknownHostException{
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "test"));
		mongoOps.dropCollection("person");
		
		Person p1 = new Person("Joe", 34);
		Person p2 = new Person("hey", 31);
		Person p3 = new Person("Jogh", 34);
		
		List<Person> list = new ArrayList<Person>();
		list.add(p2);
		list.add(p3);
		p1.setFriends(list);
		
		mongoOps.insert(p2);
		mongoOps.insert(p3);
		mongoOps.insert(p1);
		
		Person person = mongoOps.findOne(new Query(where("name").is("Joe")),Person.class);
		log.info(person.toString());
		//mongoOps.dropCollection("person");
	}
	
	public void testWithApplicationContext() throws UnknownHostException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/applicationContext-mongodb.xml"); 
		PersonRepository personRepository = (PersonRepository)ctx.getBean("personRepository");
		Person p1 =  personRepository.getMongoTemplate().find(new Query(where("name").is("Joe")), Person.class).get(0);
		System.out.println(p1.getFriends());
	}

	public static void main(String[] args) throws Exception {
		new MongoApp().testWithApplicationContext();
		//new MongoApp().testWithConsole();
	}
}
