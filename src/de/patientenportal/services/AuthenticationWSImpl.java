package de.patientenportal.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.transaction.Transactional;
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
        
        //User in Datenbank suchen
/*        UserDAO udao = new UserDAO();
    	List<User> users = udao.getAll();
    	for (User u : users) {
    		if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
    			return u;
    		}*/
        
        //User und Passwort abgleichen
        
        //Websession erstellen?
        
        
        //Should validate username and password with database
        if (username.equals(UserDAO.getUserByUsername2(username).getUsername()) && password.equals(UserDAO.getUserByUsername2(username).getPassword())){
        	return "Herzlich willkommen! " + username;
        }else{
        	return "Benutzer nicht vorhanden oder Passwort falsch!"+ username + password;
        }
       
	}
}		
		
