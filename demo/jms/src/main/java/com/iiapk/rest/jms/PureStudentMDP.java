package com.iiapk.rest.jms;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class PureStudentMDP {
	
	//private PoiDataFromProviderService poiDataFromProviderService;
    
	public void process(Map map) {  
        Student student = new Student();  
        student.setName((String) map.get("key1"));  
        student.setId((String) map.get("key2"));  
        System.out.println("PureStudentMDP:");  
        System.out.println(student);  
    }  
    
    /*
    public void process(Objec obj) { 
        Student student = (Student)obj; 
        System.out.println(student); 
    } 
     */  

}
