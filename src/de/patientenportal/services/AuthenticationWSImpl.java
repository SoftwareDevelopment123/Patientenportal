package de.patientenportal.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.AuthenticationWS")
public class AuthenticationWSImpl implements AuthenticationWS {

	@Resource
    WebServiceContext wsctx;
		
	@Override
	public String authenticateUser(){ //(String username, String password) {
	
		MessageContext mctx = wsctx.getMessageContext();
		
		//get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");

        String username = "";
        String password = "";
        
        if(userList!=null){
        	//get username
        	username = userList.get(0).toString();
        }
        	
        if(passList!=null){
        	//get password
        	password = passList.get(0).toString();
        }
        
        //User und Passwort-Überprüfung
        if(UserDAO.getUserByUsername2(username) != null){
        	if (username.equals(UserDAO.getUserByUsername2(username).getUsername()) && password.equals(UserDAO.getUserByUsername2(username).getPassword()))
        	{
        		if (UserDAO.getUserByUsername2(username).getGender() != null)
        		{
        			if (UserDAO.getUserByUsername2(username).getGender().equals("male"))
    				{
    				return "Herzlich willkommen Herr "+UserDAO.getUserByUsername2(username).getLastname()+"!";
    				}
        			else if (UserDAO.getUserByUsername2(username).getGender().equals("female"))
        			{
        			return "Herzlich willkommen Frau "+UserDAO.getUserByUsername2(username).getLastname()+"!";	
        			}
        			else
        			{
        				return "Herzlich willkommen "+username +"!";	
        			}
        		}
        	}
        	else if (username.equals(UserDAO.getUserByUsername2(username).getUsername()))
        	{
        	return "Passwort falsch! Bitte überprüfen Sie Ihre Eingaben!";	
        	}
        }
        else 
        {
        	return "Benutzer nicht vorhanden! Überprüfen Sie Ihre Eingaben oder registrieren Sich sich!"+ username + password;
        }
        return "Fehler";
        }
       
	
}		
		
