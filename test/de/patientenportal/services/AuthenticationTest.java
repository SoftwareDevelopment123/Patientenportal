package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Map<String, Object> req_ctx = ((BindingProvider)authWS).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        /**********************************************************************/
     
        System.out.println(authWS.authenticateUser(ActiveRole.Doctor));
        System.out.println(authWS.getSessionToken(username));
        System.out.println(authWS.authenticateToken(authWS.getSessionToken(username)));
      //  Assert.assertEquals(true, authWS.authenticateToken(authWS.getSessionToken(username)));
       
        //mit Rolle Patient
     
        
        Map<String, Object> req_ctx2 = ((BindingProvider)authWS).getRequestContext();
        req_ctx2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers2 = new HashMap<String, List<String>>();
        headers2.put("Username", Collections.singletonList(username));
        headers2.put("Password", Collections.singletonList(password));
        req_ctx2.put(MessageContext.HTTP_REQUEST_HEADERS, headers2);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser(ActiveRole.Patient));
        
        //mit falschem Usernamen
        username = "Unbekannt";
        
        Map<String, Object> req_ctx3 = ((BindingProvider)authWS).getRequestContext();
        req_ctx3.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers3 = new HashMap<String, List<String>>();
        headers3.put("Username", Collections.singletonList(username));
        headers3.put("Password", Collections.singletonList(password));
        req_ctx3.put(MessageContext.HTTP_REQUEST_HEADERS, headers3);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser(ActiveRole.Patient));
    }

}
