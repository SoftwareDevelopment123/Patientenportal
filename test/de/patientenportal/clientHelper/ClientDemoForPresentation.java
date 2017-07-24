package de.patientenportal.clientHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.Test;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Address;

import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.UserListResponse;
import de.patientenportal.services.AuthenticationWS;
import de.patientenportal.services.RegistrationWS;

public class ClientDemoForPresentation {
	private static   String token;
	
	@Test

	public  void registerUser() throws MalformedURLException, ParseException {

		URL url = new URL("http://localhost:8080/registration?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "RegistrationWSImplService");
        Service service = Service.create(url, qname);
        RegistrationWS regWS = service.getPort(RegistrationWS.class);
        
        //User anlegen
        User neu = new User();
		neu.setUsername("Tommy");
		neu.setPassword("123456");
		neu.setEmail("thomas.müller@mustermail.com");
		neu.setLastname("Müller");
		neu.setFirstname("Thomas");
		
		Date geburtstag = ClientHelper.parseStringtoDate("04.12.1991");
		neu.setBirthdate(geburtstag);
		neu.setGender(Gender.MALE);
		
		Address neuA = new Address();
		neuA.setCity("Stapshausen");
		neuA.setNumber("1a");
		neuA.setPostalCode("01234");
		neuA.setStreet("Superstrasse");
		
		Contact neuC = new Contact();
		neuC.setMobile("01731234567");
		neuC.setPhone("03512646152");
		
		neu.setAddress(neuA);
		neu.setContact(neuC);
		
		int userID = 0;
		UserListResponse response = regWS.createUser(neu);
		if (response.getResponseList() == null) {
			System.out.println("Fehler: " + response.getResponseCode());
		}
		else {
			System.out.println(response.getResponseCode());
			userID = response.getResponseList().get(0).getUserId();
		}
		
		//Doctor anlegen
		Doctor neuD = new Doctor();
		neuD.setSpecialization("Kardiologe");
		regWS.createDoctor(neuD, userID);
				
		//Patient anlegen
		Patient neuP = new Patient();
		neuP.setBloodtype("AB");
		regWS.createPatient(neuP, userID);
/*	}

	@Test
	public  void loginUser() throws MalformedURLException {*/
		
		String username = "Tommy";
		String password = "123456";
		
		URL url2 = new URL("http://localhost:8080/authentication?wsdl");
        QName qname2 = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service2 = Service.create(url2, qname2);
        AuthenticationWS authWS = service2.getPort(AuthenticationWS.class);
        
        ClientHelper.putUsernamePassword(username, password, authWS);
        System.out.println(authWS.authenticateUser(ActiveRole.Doctor)); 
        token = authWS.getSessionToken(username);
        System.out.println(token);
	
	

        
	}
}
