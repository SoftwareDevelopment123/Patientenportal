/*package de.patientenportal.services;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;


public class TokenTest {
private static final String WS_URL = "http://localhost:8080/authentication?wsdl";
	
	public static void main(String[] args) throws Exception {

		
		URL url = new URL(WS_URL);
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");

        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        *//*******************Wrong UserName & Password ******************************//*
        Map<String, Object> req_ctx = ((BindingProvider)authWS).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Token", Collections.singletonList("hm01teanb0c02qs9chd3rbu7op"));
 
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        *//**********************************************************************//*
        
        System.out.println(authWS.authenticateToken());
      

    }
}
*/