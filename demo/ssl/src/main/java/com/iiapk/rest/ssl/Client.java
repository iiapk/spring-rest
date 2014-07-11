package com.iiapk.rest.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {
		Socket s = new Socket("localhost", 8443);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
		writer.write("12345");
		writer.flush();
		System.out.println(reader.readLine());
		s.close();
	}

}