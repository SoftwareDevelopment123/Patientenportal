package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Test;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.Address;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.persistence.UserDAO;

public class RegistrationWSTest {

	@Test
	public void registerUser() throws MalformedURLException, ParseException, InvalidParamException {

		URL url = new URL("http://localhost:8080/registration?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RegistrationWSImplService");
		Service service = Service.create(url, qname);
		RegistrationWS regWS = service.getPort(RegistrationWS.class);

		// User anlegen
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

		if (UserDAO.getUserByUsername("Tommy") == null) {
			regWS.createUser(neu);
		}
		Assert.assertEquals(UserDAO.getUserByUsername("Tommy").getUsername(), "Tommy");
		
		// Doctor anlegen
		Doctor neuD = new Doctor();
		neuD.setSpecialization("Kardiologe");
		int userID = UserDAO.getUserByUsername(neu.getUsername()).getUserId();
		
		if (UserDAO.getUserByUsername("Tommy").getDoctor() == null) {
			regWS.createDoctor(neuD, userID);
		}
				
		// Patient anlegen
		Patient neuP = new Patient();
		neuP.setBloodtype("B");
		if (UserDAO.getUserByUsername("Tommy").getPatient() == null) {
			regWS.createPatient(neuP, userID);
		}

	}
}
