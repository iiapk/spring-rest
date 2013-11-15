package com.iiapk.rest.cxf;

import java.util.Calendar;

public class TestServiceImpl implements TestService{

	@Override
	public String sayHi(String text) {
		return text;
	}
	
	@Override
	public Customer findCustomer(String id) {  
        Customer customer = new Customer();  
        customer.setId("customer_" + id);  
        customer.setName("customer_name");  
        customer.setBirthday(Calendar.getInstance().getTime());  
        return customer;  
    }  
	
}
