package de.patientenportal.services;

import javax.xml.ws.Endpoint;

public class WSPublisher {

	public static void main(String[] args) {

		String URI = "http://localhost:8080";
		
		System.out.println("Web-Services running on " + URI);
		
		Endpoint.publish(URI + "/user", new UserWSImpl());
		System.out.println(" User-WebService : 				/user?wsdl");
	
		Endpoint.publish(URI + "/registration", new RegistrationWSImpl());
		System.out.println(" Registration-WebService : 			/registration?wsdl");
		
		/*
		 * 
		 * usw. für alle WS
		 * 
		 */
	}
}