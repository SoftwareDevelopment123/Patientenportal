package de.patienportal.demo;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Before;
import org.junit.Test;

import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Address;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.services.AuthenticationWS;
import de.patientenportal.services.DoctorWS;
import de.patientenportal.services.HTTPHeaderService;
import de.patientenportal.services.RegistrationWS;

public class ClientDemoForPresentation {
	private static   String token;
	
	@Before
	public  void registerUser() throws MalformedURLException {
		URL url = new URL("http://localhost:8080/registration?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "RegistrationWSImplService");
        Service service = Service.create(url, qname);
        RegistrationWS regWS = service.getPort(RegistrationWS.class);
        
        
        User neu = new User();
		neu.setUsername("Tommy");
		neu.setPassword("123456");
		neu.setEmail("thomas.müller@mustermail.com");
		neu.setLastname("Müller");
		neu.setFirstname("Thomas");
		neu.setBirthdate("01.01.1980");
		neu.setGender(Gender.MALE);
		
		Address neuA = new Address();
		neuA.setCity("Stapshausen");
		neuA.setNumber("1a");
		neuA.setPostalCode("01234");
		neuA.setStreet("Superstrasse");
		
		Contact neuC = new Contact();
		neuC.setMobile("01731234567");
		neuC.setPhone("03512646152");
		
		Patient neuP = new Patient();
		neuP.setBloodtype("AB");
		
		Doctor neuD = new Doctor();
		neuD.setSpecialization("Kardiologe");
		
		Relative neuR = new Relative();
		
		neu.setAddress(neuA);
		neu.setContact(neuC);
		neu.setPatient(neuP);
		neu.setDoctor(neuD);
		neu.setRelative(neuR);
		
		RegistrationDAO.createUser(neu);
		//TODO
		//System.out.println(regWS.createUser(neu));
		
        
	}

	@Test
	public  void loginUser() throws MalformedURLException {
		
		String username = "Tommy";
		String password = "123456";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        HTTPHeaderService.putUsernamePassword(username, password, authWS);
        System.out.println(authWS.authenticateUser(ActiveRole.Doctor)); 
        token = authWS.getSessionToken(username);
        System.out.println(token);
	}
	
	@Test
	public void createCase() throws MalformedURLException {
		URL url = new URL("http://localhost:8080/doctor?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "DoctorWSImplService");
        Service service = Service.create(url, qname);
        DoctorWS docWS = service.getPort(DoctorWS.class);

        //getAllPatients
        //selectPatient
        Case newCase	= new Case();
        newCase.setTitle("Herzmuskelentzündung");
        //newCase.setPatient(patient);
        newCase.setDescription("Verusacht durch Virusinfektion...");
        
        //WebService anpassen 
        //FIXME
        //XXX
        CaseDAO.createCase(newCase);
        
       /* Accessor acc = new Accessor();
		acc.setToken(token);
		acc.setObject(1);
		System.out.println(docWS.createCase(acc));
        */
	}
}
