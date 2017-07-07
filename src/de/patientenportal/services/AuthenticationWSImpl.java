package de.patientenportal.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import de.patientenportal.entities.Gender;
import de.patientenportal.persistence.UserDAO;

/**
 * Autentifizierungsservice: �berpr�ft die �ber den HTTP �bergebenen Usernamen und Passwort
 * und gleicht diese mit den Datenbankeintr�gen ab. Bei erfolgreichem Login wird eine Websession 
 * mit Token erstellt. 
 *@param Username (�ber HTTP-Header)
 *@param Password (�ber HTTP-Header)
 *@return Begr��ung/ Information als <code>String</code>  und Token
 */
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
        
        //User und Passwort-�berpr�fung
        if(UserDAO.getUserByUsername(username) != null){
        	if (username.equals(UserDAO.getUserByUsername(username).getUsername()) && password.equals(UserDAO.getUserByUsername(username).getPassword()))
        	{
        		if (UserDAO.getUserByUsername(username).getGender() != null)
        		{
        			if (UserDAO.getUserByUsername(username).getGender().equals(Gender.MALE))
    				{
    				return "Herzlich willkommen Herr "+UserDAO.getUserByUsername(username).getLastname()+"!";
    				}
        			else if (UserDAO.getUserByUsername(username).getGender().equals(Gender.FEMALE))
        			{
        			return "Herzlich willkommen Frau "+UserDAO.getUserByUsername(username).getLastname()+"!";	
        			}
        			else
        			{
        				return "Herzlich willkommen "+username +"!";	
        			}
        		}
        	}
        	else if (username.equals(UserDAO.getUserByUsername(username).getUsername()))
        	{
        	return "Passwort falsch! Bitte �berpr�fen Sie Ihre Eingaben!";	
        	}
        }
        else 
        {
        	return "Benutzer nicht vorhanden! �berpr�fen Sie Ihre Eingaben oder registrieren Sich sich!"+ username + password;
        }
        return "Fehler";
        }
       
	
}		
		
