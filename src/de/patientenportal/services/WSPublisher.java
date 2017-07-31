package de.patientenportal.services;

import javax.xml.ws.Endpoint;

public class WSPublisher {

	public static void main(String[] args) {

		String URI = "http://localhost:8080";

		System.out.println("Starting Web-Services ...");
		System.out.println("Web-Services running on " + URI);

		try {
			Endpoint.publish(URI + "/registration", new RegistrationWSImpl());
			System.out.println(" Registration-WebService : 			/registration?wsdl");

			Endpoint.publish(URI + "/account", new AccountWSImpl());
			System.out.println(" Account-WebService : 				/account?wsdl");

			Endpoint.publish(URI + "/authentication", new AuthenticationWSImpl());
			System.out.println(" Authentication-WebService : 			/authentication?wsdl");

			Endpoint.publish(URI + "/patient", new PatientWSImpl());
			System.out.println(" Patient-WebService : 				/patient?wsdl");

			Endpoint.publish(URI + "/doctor", new DoctorWSImpl());
			System.out.println(" Doctor-WebService : 				/doctor?wsdl");

			Endpoint.publish(URI + "/relative", new RelativeWSImpl());
			System.out.println(" Relative-WebService : 				/relative?wsdl");

			Endpoint.publish(URI + "/office", new OfficeWSImpl());
			System.out.println(" Office-WebService : 				/office?wsdl");

			Endpoint.publish(URI + "/address", new AddressWSImpl());
			System.out.println(" Address-WebService : 				/address?wsdl");

			Endpoint.publish(URI + "/contact", new ContactWSImpl());
			System.out.println(" Contact-WebService : 				/contact?wsdl");

			Endpoint.publish(URI + "/case", new CaseWSImpl());
			System.out.println(" Case-WebService : 				/case?wsdl");

			Endpoint.publish(URI + "/rights", new RightsWSImpl());
			System.out.println(" Rights-WebService : 				/rights?wsdl");

			Endpoint.publish(URI + "/access", new AccessWSImpl());
			System.out.println(" Access-WebService : 				/access?wsdl");

			Endpoint.publish(URI + "/medicine", new MedicineWSImpl());
			System.out.println(" Medicine-WebService : 				/medicine?wsdl");

			Endpoint.publish(URI + "/medication", new MedicationWSImpl());
			System.out.println(" Medication-WebService : 			/medication?wsdl");

			Endpoint.publish(URI + "/vitaldata", new VitalDataWSImpl());
			System.out.println(" VitalData-WebService : 			/vitaldata?wsdl");

		} catch (Exception e) {
			System.err.println("Error - " + e);
			System.err.println("Shutting down WS ...");
			System.exit(0);
		}
		System.out.println("... Web-Services started successfully!");
	}
}