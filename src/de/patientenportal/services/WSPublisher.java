package de.patientenportal.services;

import javax.xml.ws.Endpoint;

public class WSPublisher {

	public static void main(String[] args) {

		String URI = "http://localhost:8080";

		System.out.println("Web-Services running on " + URI);
		
//		try {
		Endpoint.publish(URI + "/account", new AccountWSImpl());
		System.out.println(" Account-WebService : 				/account?wsdl");
	
		Endpoint.publish(URI + "/registration", new RegistrationWSImpl());
		System.out.println(" Registration-WebService : 			/registration?wsdl");
		
/*		Endpoint.publish(URI + "/patient", new PatientWSImpl());
		System.out.println(" Patient-WebService : 			/patient?wsdl");*/
		
/*		Endpoint.publish(URI + "/doctor", new DoctorWSImpl());
		System.out.println(" Doctor-WebService : 			/doctor?wsdl");*/
		
		Endpoint.publish(URI + "/relative", new RelativeWSImpl());
		System.out.println(" Relative-WebService : 				/relative?wsdl");
		
/*		Endpoint.publish(URI + "/office", new OfficeWSImpl());
		System.out.println(" Office-WebService : 			/office?wsdl");*/
		
		Endpoint.publish(URI + "/address", new AddressWSImpl());
		System.out.println(" Address-WebService : 				/address?wsdl");
		
		Endpoint.publish(URI + "/contact", new ContactWSImpl());
		System.out.println(" Contact-WebService : 				/contact?wsdl");
		
/*		} catch (Exception e) {
			System.out.println("Error - " + e);
		} finally {
			System.out.println("Shutting down WS (for testing only)...");
			System.exit(0);
		}*/

		Endpoint.publish(URI + "/authentication", new AuthenticationWSImpl());
		System.out.println(" Authentication-WebService : 			/authentication?wsdl");
		/*
		 * 
		 * usw. für alle WS
		 * 
		 */
	}
}