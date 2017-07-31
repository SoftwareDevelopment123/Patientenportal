package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.persistence.RegistrationDAO;

public class AuthenticationTest {
	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";

	@Before
	public void setUp() {
		User neu = new User();

		neu.setUsername("Jonny");
		neu.setPassword("123456");
		neu.setEmail("jon.doe@mustermail.com");
		neu.setLastname("Doe");
		neu.setFirstname("Jon");
		neu.setGender(Gender.MALE);

		Patient patient = new Patient();
		patient.setBloodtype("AB");
		RegistrationDAO.createPatient(patient);
		neu.setPatient(patient);

		RegistrationDAO.createUser(neu);
	}

	@Test
	public void testAuthentication()
			throws MalformedURLException, PersistenceException, AccessException, InvalidParamException {
		String username = "Jonny";
		String password = "123456";

		URL url = new URL(WS_URL);
		QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

		Service service = Service.create(url, qname);
		AuthenticationWS authWS = service.getPort(AuthenticationWS.class);

		// mit richtigem PW
		ClientHelper.putUsernamePassword(username, password, authWS);

		System.out.println("Begrüßung: " + authWS.authenticateUser(ActiveRole.Patient));
		System.out.println("Token: " + authWS.getSessionToken(username));
		String tokenTest = authWS.getSessionToken(username);

		System.out.println("Tokenauthentifizierung: " + authWS.authenticateToken(tokenTest));

		Assert.assertEquals(true, authWS.authenticateToken(tokenTest));

	}

	@Test(expected = AccessException.class)
	public void testAccessExceptionRole()
			throws PersistenceException, AccessException, InvalidParamException, MalformedURLException {
		String username = "Jonny";
		String password = "123456";

		URL url = new URL(WS_URL);
		QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

		Service service = Service.create(url, qname);
		AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		ClientHelper.putUsernamePassword(username, password, authWS);
		// mit falscher Rolle
		authWS.authenticateUser(ActiveRole.Doctor);
	}
	
	@Test(expected = AccessException.class)
	public void testAccessExceptionUsername()
			throws PersistenceException, AccessException, InvalidParamException, MalformedURLException {
		String username = "Jonny";
		String password = "123456";

		URL url = new URL(WS_URL);
		QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

		Service service = Service.create(url, qname);
		AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		// mit falschem Usernamen
		username = "Unbekannt";
		ClientHelper.putUsernamePassword(username, password, authWS);
		authWS.authenticateUser(ActiveRole.Patient);
	}
}
