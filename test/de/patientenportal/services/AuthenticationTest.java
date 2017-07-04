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


public class AuthenticationTest {
	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";
	
	public static void main(String[] args) throws Exception {
	   
		
		URL url = new URL(WS_URL);
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        /*******************Wrong UserName & Password ******************************/
        Map<String, Object> req_ctx = ((BindingProvider)authWS).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList("mkyong"));
        headers.put("Password", Collections.singletonList("password"));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser());
       
        
        /*******************UserName & Password ******************************/
        Map<String, Object> req_ctx2 = ((BindingProvider)authWS).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers2 = new HashMap<String, List<String>>();
        headers2.put("Username", Collections.singletonList("mkyong123"));
        headers2.put("Password", Collections.singletonList("password"));
        req_ctx2.put(MessageContext.HTTP_REQUEST_HEADERS, headers2);
        /**********************************************************************/
        
        System.out.println(authWS.authenticateUser());
    }

}
