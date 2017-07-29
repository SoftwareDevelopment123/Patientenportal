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
import de.patientenportal.entities.Office;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.RelativeDAO;
import de.patientenportal.persistence.RightsDAO;

@SuppressWarnings("unused")
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
        
        ClientHelper.putUsernamePassword(username, password, authWS);
        authWS.authenticateUser(ActiveRole.Patient);
        token = authWS.getSessionToken(username);
	}
	
	/*@After
	public void logout() throws MalformedURLException{
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        authWS.logout(token);
	}*/
	
	@Test
	public void main() throws MalformedURLException, AuthenticationException, AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		
		URL urlR = new URL("http://localhost:8080/rights?wsdl");
		QName qnameR = new QName("http://services.patientenportal.de/", "RightsWSImplService");
		Service serviceR = Service.create(urlR, qnameR);
		RightsWS rightsws = serviceR.getPort(RightsWS.class);
		
		List <Rights> compareme = RightsDAO.getRights(3);
		Accessor getRights = new Accessor(token);
				getRights.setId(3);
		RightsListResponse response = rightsws.getRights(getRights);
		int compare1 = response.getResponseList().size();
		Assert.assertEquals("success", response.getResponseCode());
	
		List<Rights> rights = response.getResponseList();
		int i = 0;
			for (Rights r : compareme){
				if(r.getDoctor()!=null){
				Assert.assertEquals(r.getDoctor().getDoctorID()				, rights.get(i).getDoctor().getDoctorID());
				Assert.assertEquals(r.getDoctor().getSpecialization()		, rights.get(i).getDoctor().getSpecialization());
				Assert.assertEquals(r.getDoctor().getUser().getBirthdate()	, rights.get(i).getDoctor().getUser().getBirthdate());
				}
				if(r.getRelative()!=null){
					Assert.assertEquals(r.getRelative().getRelativeID()			, rights.get(i).getRelative().getRelativeID());
				}
				Assert.assertEquals(r.getRightID()							, rights.get(i).getRightID());
				i++;
	}
	//TODO so anpassen, dass der Test beliebig oft ausgeführt werden kann
			
		/*Rights righttoupdate = rights.get(0);
		righttoupdate.setwRight(false);
		
		Accessor updateRight = new Accessor(token);
		updateRight.setObject(righttoupdate);
		String feedbackUR = rightsws.updateRight(updateRight);
		Assert.assertEquals("success", feedbackUR);
		
		Accessor updatedRights = new Accessor(token, 3);
		RightsListResponse updatedRight = rightsws.getRights(updatedRights);
		Rights rightupdated = updatedRight.getResponseList().get(0);
		//Rights daoright =RightsDAO.getRights(3).get(1);
		
			Assert.assertEquals(false, rightupdated.iswRight());
			
		
			Accessor deleteright = new Accessor(token, 3);
			String feedbackDR = rightsws.deleteRight(deleteright);
				Assert.assertEquals("success", feedbackDR);
				Accessor Rightsafterdelete = new Accessor(token, 3);
				
				RightsListResponse response1 = rightsws.getRights(Rightsafterdelete);
				int compare2 = response1.getResponseList().size();
				Assert.assertEquals(compare1-1, compare2);*/
				
	}
			
}
	
