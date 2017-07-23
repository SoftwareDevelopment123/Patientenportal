package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;

public class CaseWSTest {

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
	
	@After
	public void logout() throws MalformedURLException{
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        authWS.logout(token);
	}
	
	@Test
	public void getCase() throws MalformedURLException{
		
		URL urlC = new URL("http://localhost:8080/case?wsdl");
		QName qnameC = new QName("http://services.patientenportal.de/", "CaseWSImplService");
		Service serviceC = Service.create(urlC, qnameC);
		CaseWS casews = serviceC.getPort(CaseWS.class);
		
		Case compareme = CaseDAO.getCase(3);
		Accessor getCase = new Accessor(token, 3);
		Case pcase = casews.getCase(getCase);
		
		// Abruf Casedaten
		Assert.assertEquals(compareme.getCaseID()									, pcase.getCaseID());
		Assert.assertEquals(compareme.getTitle()									, pcase.getTitle());
		Assert.assertEquals(compareme.getDescription()								, pcase.getDescription());

		// Abruf Case-bezogene Patientendaten
		Assert.assertEquals(compareme.getPatient().getBloodtype()					, pcase.getPatient().getBloodtype());
		Assert.assertEquals(compareme.getPatient().getUser().getLastname()			, pcase.getPatient().getUser().getLastname());
		
		// Abruf Case-bezogene Doktoren
		Assert.assertEquals(compareme.getDoctors().get(0).getSpecialization()		, pcase.getDoctors().get(0).getSpecialization());
		Assert.assertEquals(compareme.getDoctors().get(0).getUser().getFirstname()	, pcase.getDoctors().get(0).getUser().getFirstname());
	}
	
	@Test
	public void getCases() throws MalformedURLException{
		
		URL urlC = new URL("http://localhost:8080/case?wsdl");
		QName qnameC = new QName("http://services.patientenportal.de/", "CaseWSImplService");
		Service serviceC = Service.create(urlC, qnameC);
		CaseWS casews = serviceC.getPort(CaseWS.class);
		
		List<Case> comparemePre = PatientDAO.getPatient(3).getCases();
		List<Case> compareme = new ArrayList<Case>();
		for (Case c : comparemePre) {
			compareme.add(CaseDAO.getCase(c.getCaseID()));
		}
		
		Accessor getCases = new Accessor(token);
			boolean status = true;
			getCases.setObject(status);
		
		CaseListResponse response = casews.getCases(getCases);
			Assert.assertEquals("success", response.getResponseCode());
		
		List<Case> cases = response.getResponseList();

		int i = 0;
		for (Case c : compareme){
			Assert.assertEquals(c.getCaseID()							, cases.get(i).getCaseID());
			Assert.assertEquals(c.getTitle()							, cases.get(i).getTitle());
			Assert.assertEquals(c.getDescription()						, cases.get(i).getDescription());
			Assert.assertEquals(c.getPatient().getBloodtype()			, cases.get(i).getPatient().getBloodtype());
			Assert.assertEquals(c.getPatient().getUser().getFirstname()	, cases.get(i).getPatient().getUser().getFirstname());
			Assert.assertEquals(c.getDoctors().size()					, cases.get(i).getDoctors().size());
			i++;
		}
	}
}
