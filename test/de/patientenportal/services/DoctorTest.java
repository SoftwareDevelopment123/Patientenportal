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
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.DoctorDAO;

public class DoctorTest {

private String token;

	@Before
	public void login() throws MalformedURLException{
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
	public void logout() throws MalformedURLException{
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        authWS.logout(token);
	}
	
	@Test
	public void main () throws MalformedURLException, AuthenticationException, AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException{
	
	System.out.print("Testing Doctor WebService ...");
	
	URL url = new URL("http://localhost:8080/doctor?wsdl");
	QName qname = new QName("http://services.patientenportal.de/", "DoctorWSImplService");
	Service service = Service.create(url, qname);
	DoctorWS doc = service.getPort(DoctorWS.class);
	
	// Get Doctor
	Doctor comparedoc = DoctorDAO.getDoctor(1);
	Accessor getdoc = new Accessor();
		getdoc.setObject(1);
		getdoc.setToken(token);
		
	Doctor doctor = doc.getDoctor(getdoc);

		Assert.assertEquals(comparedoc.getDoctorID() 			, doctor.getDoctorID());
		Assert.assertEquals(comparedoc.getSpecialization() 		, doctor.getSpecialization());
		Assert.assertEquals(comparedoc.getUser().getLastname() 	, doctor.getUser().getLastname());
	
	// Get DoctorList by Case	
	List<Doctor> compareDocList = CaseDAO.getCase(3).getDoctors();
	Accessor getDocList = new Accessor();
	getDocList.setObject(3);
	getDocList.setToken(token);
	DoctorListResponse docListResponse = doc.getDoctorsByC(getDocList);
	List<Doctor> docList = docListResponse.getResponseList();
	
	int i = 0;
	for (Doctor d : compareDocList){
		Assert.assertEquals(d.getDoctorID()				, docList.get(i).getDoctorID());
		Assert.assertEquals(d.getSpecialization()		, docList.get(i).getSpecialization());
		Assert.assertEquals(d.getUser().getFirstname()	, docList.get(i).getUser().getFirstname());
		i++;
	}
	
	/*
	 * Get DoctorList by Patient
	 * zum manuellen Testen vorgesehen, wird sonst zu kompliziert (für mehr Komplexität mehr Fälle zu Patient 1 zuweisen
	 * Aktueller Stand: funktioniert
	 */
	
	/*Accessor getDocList2 = new Accessor(1);
	DoctorListResponse docListResponse2 = doc.getDoctorsByP(getDocList2);
		
	List<Doctor> docList2 = docListResponse2.getResponseList();
	for (Doctor d : docList2) {
		System.out.print("Doktor-ID: " + d.getDoctorID());
		System.out.println(" - " + d.getSpecialization());
	}*/
	
	
	System.out.println("Success!");	
	}
}