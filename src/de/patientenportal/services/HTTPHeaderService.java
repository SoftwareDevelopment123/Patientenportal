package de.patientenportal.services;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;



public class HTTPHeaderService {

	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";
	
	public static void putUsernamePassword(String username, String password,AuthenticationWS authWS) throws MalformedURLException{
		
        
		Map<String, Object> req_ctx = ((BindingProvider)authWS).getRequestContext();
	        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

	        Map<String, List<String>> headers = new HashMap<String, List<String>>();
	        headers.put("Username", Collections.singletonList(username));
	        headers.put("Password", Collections.singletonList(password));
	        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}	
}
