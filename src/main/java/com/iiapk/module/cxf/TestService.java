package com.iiapk.module.cxf;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface TestService {
	
	@WebMethod
	@WebResult String sayHi(@WebParam String text);

}
