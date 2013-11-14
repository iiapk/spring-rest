package com.iiapk.rest.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext-mongodb.xml"})
public class MongoAppTest {

	private static final Logger log = LoggerFactory.getLogger(MongoAppTest.class);
	private Person p1,p2,p3;
	private List<Person> list = new ArrayList<Person>();
	private MongoOperations mongoOps;
	
	 @Autowired
	private PersonRepository personRepository;
	
	@Before  
    public void setup() throws Exception {  
		p1 = new Person("Joe", 34);
		p2 = new Person("hey", 31);
		p3 = new Person("Jogh", 34);
		list = new ArrayList<Person>();
		list.add(p2);
		list.add(p3);
		p1.setFriends(list);
		
		mongoOps = new MongoTemplate(new Mongo(), "mongodb");
		mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(),"mongodb"));
		mongoOps.dropCollection("person");
		mongoOps.insert(p2);
		mongoOps.insert(p3);
		mongoOps.insert(p1);
    } 
	
	@Test
	public void testFindOne() throws UnknownHostException {
		Person person = mongoOps.findOne(new Query(where("name").is("Joe")),Person.class);
		Assert.assertEquals(person.getAge(), 34);
		log.info(p1.getFriends().toString());
		Assert.assertEquals(person.getFriends().get(0).getName(), "hey");
	}
	
	@After
	 public void setdown() throws Exception {  
		 mongoOps.dropCollection("person");
	 }

}
