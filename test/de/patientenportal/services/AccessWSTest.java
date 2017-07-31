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
import de.patientenportal.entities.Case;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;

public class AccessWSTest {

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
	public void getRCases() throws MalformedURLException, AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {

		URL urlA = new URL("http://localhost:8080/access?wsdl");
		QName qnameA = new QName("http://services.patientenportal.de/", "AccessWSImplService");
		Service serviceA = Service.create(urlA, qnameA);
		AccessWS acc = serviceA.getPort(AccessWS.class);

		Accessor getRCases = new Accessor(token, true);
		List<Case> clist = acc.getRCases(getRCases).getResponseList();

		int i = 1;
		for (Case c : clist) {
			/*
			 * System.out.print(c.getCaseID() + " - ");
			 * System.out.println(c.getTitle());
			 * System.out.println(c.getPatient().getUser().getEmail());
			 * System.out.println(c.getDoctors().get(0).getUser().getUsername())
			 * ;
			 */
			Assert.assertEquals(c.getCaseID(), i);
			i++;
		}
	}
}
