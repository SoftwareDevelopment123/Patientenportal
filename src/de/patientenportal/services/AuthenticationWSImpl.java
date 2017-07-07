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
 * Autentifizierungsservice: Überprüft die über den HTTP übergebenen Usernamen und Passwort
 * und gleicht diese mit den Datenbankeinträgen ab. Bei erfolgreichem Login wird eine Websession 
 * mit Token erstellt. 
 *@param Username (über HTTP-Header)
 *@param Password (über HTTP-Header)
 *@return Begrüßung/ Information als <code>String</code>  und Token
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
        
        //User und Passwort-Überprüfung
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
        	return "Passwort falsch! Bitte überprüfen Sie Ihre Eingaben!";	
        	}
       
        	return "Benutzer nicht vorhanden! Überprüfen Sie Ihre Eingaben oder registrieren Sich sich!"+ username + password;
        }
        return "Fehler";
        }
       
	/*public boolean authenticateToken(String token){
		WebSessionDAOImpl wsdi = new WebSessionDAOImpl();
		List<WebSession> sessions = wsdi.findByCriteria(Restrictions.eq("token", token));
		if (sessions.size() != 1) return false;
		sessionService.updateToken(sessions.get(0));
		return true;
	}
	
	public User getUserByToken(String token) {
		WebSessionDAOImpl wsdi = new WebSessionDAOImpl();
		List<WebSession> sessions = wsdi.findByCriteria(Restrictions.eq("token", token));
		if (sessions.size() != 1) throw new DataNotFoundException("No user found for token " + token);
		return sessions.get(0).getUser();
	}
	
	public Response logout(String token){
		WebSessionDAOImpl wsdi = new WebSessionDAOImpl();
		List<WebSession> sessions = wsdi.getAll();
		for (WebSession ws : sessions) {
			if (ws.getToken().equals(token)){
				wsdi.deleteEntity(ws);
				return Response.status(Status.OK)
						.entity("User logged out sucesfully")
						.build();
			}
		}
		throw new DataNotFoundException("No session found for token token " + token);
		
	}*/
	
	
}		
		
