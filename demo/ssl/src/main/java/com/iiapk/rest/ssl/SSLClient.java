package com.iiapk.rest.ssl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	
	private static String CLIENT_KEY_STORE = "src/main/resources/tomcat-client.keystore";   
    
    private Socket clientWithoutCert() throws Exception {   
        SocketFactory sf = SSLSocketFactory.getDefault();   
        Socket s = sf.createSocket("localhost", 8443);   
        return s;   
    }

	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);   
        System.setProperty("javax.net.debug", "ssl,handshake");  
        //System.out.println(System.getProperty("javax.net.ssl.trustStore"));
        SSLClient client = new SSLClient();   
        Socket s = client.clientWithoutCert();   
        PrintWriter writer = new PrintWriter(s.getOutputStream());   
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));   
        writer.println("hello");   
        writer.flush();   
        System.out.println(reader.readLine());   
        s.close();   
	}
	
	

}