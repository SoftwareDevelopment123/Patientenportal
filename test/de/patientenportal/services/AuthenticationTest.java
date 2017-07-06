package de.patientenportal.services;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;


public class AuthenticationTest {
	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";
	
	public static void main(String[] args) throws Exception {
	   
		//Vor Test:
		
		User neu = new User();
		
		neu.setUsername("staps12");
		neu.setPassword("pass");
		neu.setEmail("stap.staptp@mustermail.com");
		neu.setLastname("Stupser");
		neu.setFirstname("Staps1");
		
		RegistrationDAO.createUser(neu);
		
		if(UserDAO.getUserByUsername2(neu.getUsername())!= null){
		System.out.println(UserDAO.getUserByUsername2(neu.getUsername()).getUsername());
		System.out.println(UserDAO.getUserByUsername2(neu.getUsername()).getPassword());
		}
		
		//eigentlicher Test
		//mit richtigem PW
		
		
		URL url = new URL(WS_URL);
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        /*******************Wrong UserName & Password ******************************/
        Map<String, Object> req_ctx = ((BindingProvider)authWS).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(neu.getUsername()));
        headers.put("Password", Collections.singletonList(neu.getPassword()));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser());
      
        //mit falschem PW
        neu.setPassword("falsch");
        
        /*******************UserName & Password ******************************/
        Map<String, Object> req_ctx2 = ((BindingProvider)authWS).getRequestContext();
        req_ctx2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers2 = new HashMap<String, List<String>>();
        headers2.put("Username", Collections.singletonList(neu.getUsername()));
        headers2.put("Password", Collections.singletonList(neu.getPassword()));
        req_ctx2.put(MessageContext.HTTP_REQUEST_HEADERS, headers2);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser());
        
        //mit falschem Usernamen
        neu.setUsername("Unbekannt");
        
        /*******************UserName & Password ******************************/
        Map<String, Object> req_ctx3 = ((BindingProvider)authWS).getRequestContext();
        req_ctx3.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers3 = new HashMap<String, List<String>>();
        headers3.put("Username", Collections.singletonList(neu.getUsername()));
        headers3.put("Password", Collections.singletonList(neu.getPassword()));
        req_ctx3.put(MessageContext.HTTP_REQUEST_HEADERS, headers3);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser());
    }

}
