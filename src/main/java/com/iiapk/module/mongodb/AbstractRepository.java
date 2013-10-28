package com.iiapk.module.mongodb;

import java.util.List;

public interface  AbstractRepository {
	public void insert(Person person);  
    public Person findOne(String id);  
    public List<Person> findAll();  
    public List<Person> findByRegex(String regex);  
    public void removeOne(String id);  
    public void removeAll();  
    public void findAndModify(String id);  
}
