package de.patientenportal.services;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.Address;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;

public class UserTest {

	public static void main(String[] args) throws Exception {

		System.out.println("Test starting ...");
		System.out.println("Creating new User ...");
		
		User newuser = new User();
			newuser.setUsername("maxmuster10");
			newuser.setPassword("musterpass");
			newuser.setEmail("max.muster@mustermail.com");
			newuser.setLastname("Mustermann");
			newuser.setFirstname("Max");
			newuser.setBirthdate("01.01.2000");
			newuser.setGender("male");
		
			Address neuA = new Address();
				neuA.setCity("Musterstadt");
				neuA.setNumber("1a");
				neuA.setPostalCode("01234");
				neuA.setStreet("Musterstrasse");
		
			Contact neuC = new Contact();
				neuC.setEmail("anderemail.als@oben.com");
				neuC.setMobile("01731234567");
				neuC.setPhone("03512646152");
		
			Patient neuP = new Patient();
				neuP.setBloodtype("ABC");
		
			Doctor neuD = new Doctor();
				neuD.setSpecialization("Kardiologe");
		
			Relative neuR = new Relative();

			newuser.setAddress(neuA);
			newuser.setContact(neuC);
			newuser.setPatient(neuP);
			newuser.setDoctor(neuD);
			newuser.setRelative(neuR);
		
		System.out.println("Saving User in Database ...");
			
		try{
		URL url = new URL("http://localhost:8080/registration?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RegistrationWSImplService");

		Service service = Service.create(url, qname);
		RegistrationWS reg = service.getPort(RegistrationWS.class);
			reg.createUser(newuser);
		}
		catch (Exception e) { 
			System.err.println("Zugriffsfehler");
		}
		
	}
}