package com.iiapk.module.mongodb;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository implements AbstractRepository{  
	
	@Autowired
    private MongoTemplate mongoTemplate;  
  
    @Override  
    public List<Person> findAll() {  
        return getMongoTemplate().find(new Query(), Person.class);  
    }  
  
    @Override  
    public void findAndModify(String id) {   
        getMongoTemplate().updateFirst(new Query(Criteria.where("id").is(id)), new Update().inc("age", 3), Person.class);  
    }  
  
    @Override  
    public List<Person> findByRegex(String regex) {  
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Criteria criteria = new Criteria("name").regex(pattern.toString());  
        return getMongoTemplate().find(new Query(criteria), Person.class);  
    }  
  
    @Override  
    public Person findOne(String id) {  
        return getMongoTemplate().findOne(new Query(Criteria.where("id").is(id)), Person.class);  
    }  
  
    @Override  
    public void insert(Person person) {  
        getMongoTemplate().insert(person);  
    }  
  
    @Override  
    public void removeAll() {  
        List<Person> list = this.findAll();  
        if(list != null){  
            for(Person person : list){  
                getMongoTemplate().remove(person);  
            }  
        }  
    }  
  
    @Override  
    public void removeOne(String id){  
        Criteria criteria = Criteria.where("id").in(id);  
        if(criteria == null){  
             Query query = new Query(criteria);  
             if(query != null && getMongoTemplate().findOne(query, Person.class) != null)  
                 getMongoTemplate().remove(getMongoTemplate().findOne(query, Person.class));  
        }  
    }  
  
    public MongoTemplate getMongoTemplate() {  
        return mongoTemplate;  
    }  
  
    public void setMongoTemplate(MongoTemplate mongoTemplate) {  
        this.mongoTemplate = mongoTemplate;  
    }  
      
}  