package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.OfficeDAO;

public class OfficeWSTest {

	private String token;

	@Before
	public void login() throws MalformedURLException {
		String username = "user10";
		String password = "pass10";

		URL url = new URL("http://localhost:8080/authentication?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
		Service service = Service.create(url, qname);
		AuthenticationWS authWS = service.getPort(AuthenticationWS.class);

		ClientHelper.putUsernamePassword(username, password, authWS);
		authWS.authenticateUser(ActiveRole.Doctor);
		token = authWS.getSessionToken(username);
	}

	@After
	public void logout() throws MalformedURLException {
		URL url = new URL("http://localhost:8080/authentication?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
		Service service = Service.create(url, qname);
		AuthenticationWS authWS = service.getPort(AuthenticationWS.class);

		authWS.logout(token);
	}

	@Test
	public void main() throws MalformedURLException, AccessorException, PersistenceException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException {

		URL url = new URL("http://localhost:8080/office?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "OfficeWSImplService");
		Service service = Service.create(url, qname);
		OfficeWS off = service.getPort(OfficeWS.class);

		// Get Office
		Office compareOffice = OfficeDAO.getOffice(1);
		Accessor getOffice = new Accessor();
		getOffice.setObject(1);
		Office office = off.getOffice(getOffice);

		Assert.assertEquals(compareOffice.getOfficeID(), office.getOfficeID());
		Assert.assertEquals(compareOffice.getName(), office.getName());
		Assert.assertEquals(compareOffice.getContact().getPhone(), office.getContact().getPhone());
		Assert.assertEquals(compareOffice.getAddress().getStreet(), office.getAddress().getStreet());

		List<Doctor> compareList = compareOffice.getDoctors();
		int i = 0;
		for (Doctor d : compareList) {
			Assert.assertEquals(d.getDoctorID(), office.getDoctors().get(i).getDoctorID());
			Assert.assertEquals(d.getSpecialization(), office.getDoctors().get(i).getSpecialization());
			Assert.assertEquals(d.getUser().getLastname(), office.getDoctors().get(i).getUser().getLastname());
			i++;
		}

		// Update Office
		office.setName("Testoffice 2.0");
		int officeId = office.getOfficeID();
		
		Accessor updateOffice = new Accessor();
		updateOffice.setObject(office);
		updateOffice.setToken(token);
		updateOffice.setId(officeId);
		String feedbackUO = off.updateOffice(updateOffice);
		Assert.assertEquals("success", feedbackUO);

		Office updatedOffice = off.getOffice(getOffice);
		Assert.assertEquals("Testoffice 2.0", updatedOffice.getName());
	}
}
