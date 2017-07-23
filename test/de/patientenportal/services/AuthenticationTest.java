package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.Before;
import org.junit.Test;

import de.patienportal.demo.ClientHelper;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;



public class AuthenticationTest {
	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";
	
	//public static void main(String[] args) throws Exception {
	   
		//Vor Test:
	@Before
	public void setUp(){
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
		
	
	//eigentlicher Test
	//Rolle Doctor
	
	@Test
	public void testAuthentication() throws MalformedURLException{
		String username = "Jonny";
		String password = "123456";
		
		URL url = new URL(WS_URL);
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
       
    	//mit richtigem PW
        ClientHelper.putUsernamePassword(username, password, authWS);

     
        System.out.println("Begrüßung: " +authWS.authenticateUser(ActiveRole.Patient));
        System.out.println("Token: " + authWS.getSessionToken(username));
        String tokenTest = authWS.getSessionToken(username);
       
        System.out.println("Tokenauthentifizierung: " + authWS.authenticateToken(tokenTest));
      //  Assert.assertEquals(true, authWS.authenticateToken(authWS.getSessionToken(username)));
       
 
        System.out.println("Anmeldung mit nicht zugeordneter Rolle: " + authWS.authenticateUser(ActiveRole.Doctor));
        
        //mit falschem Usernamen
        username = "Unbekannt";
        ClientHelper.putUsernamePassword(username, password,authWS);
        
        System.out.println("Anmeldung mit nicht vorhandenem Username: " +authWS.authenticateUser(ActiveRole.Patient));
        
  
    }

}
