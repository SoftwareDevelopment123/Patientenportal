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

import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.RelativeDAO;
import de.patientenportal.persistence.RightsDAO;

public class RightsWSTest {

	private String token;
	
	@Before
	public void login() throws MalformedURLException{
		String username = "user6";
		String password = "pass6";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        HTTPHeaderService.putUsernamePassword(username, password, authWS);
        authWS.authenticateUser(ActiveRole.Patient);
        token = authWS.getSessionToken(username);
	}
	
	@After
	public void logout() throws MalformedURLException{
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        authWS.logout(token);
	}
	
	@Test
	public void main() throws MalformedURLException {
		
		URL urlR = new URL("http://localhost:8080/rights?wsdl");
		QName qnameR = new QName("http://services.patientenportal.de/", "RightsWSImplService");
		Service serviceR = Service.create(urlR, qnameR);
		RightsWS rightsws = serviceR.getPort(RightsWS.class);
		
		List <Rights> compareme = RightsDAO.getRights(3);
		Accessor getRights = new Accessor(token, 3);
		
		//noch keine Responselist Entity
		RightsListResponse response = rightsws.getRights(getRights);
		
		Assert.assertEquals("success", response.getResponseCode());
	
		List<Rights> rights = response.getResponseList();
	
		int i = 0;
			for (Rights r : compareme){
				Assert.assertEquals(r.getDoctor().getSpecialization()		, rights.get(i).getDoctor().getSpecialization());
				Assert.assertEquals(r.getPcase().getTitle()					, rights.get(i).getPcase().getTitle());
				Assert.assertEquals(r.getRelative()							, rights.get(i).getRelative());
				Assert.assertEquals(r.getRightID()							, rights.get(i).getRightID());
				i++;
	}
		
		Rights righttoupdate = rights.get(3);
		Relative relative = RelativeDAO.getRelative(3);
		righttoupdate.setRelative(relative);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		Accessor getRight = new Accessor(token, 3);
		String responsedelete= rightsws.deleteRight(getRight);
		Assert.assertEquals("success", responsedelete);
		
		
	}
			
}
	
