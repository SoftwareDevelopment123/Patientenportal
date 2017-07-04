package de.patientenportal.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.transaction.Transactional;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

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
        if (username.equals("mkyong123") && password.equals("password")){
        	return "Herzlich willkommen! " + username;
        }else{
        	return "Benutzer oder Passwort nicht vorhanden!";
        }
       
	}
}		
		
	/*	//LoginUtil utility = new LoginUtil();
		
		if (username.equals("")) {
			return "Name is missing";
		}
		
		if (password.equals("")) {
			return "Password is missing";
		}
		
	//	String pass = utility.getCombination().get(name);
		
		if (password.equals(pass)) {
			return "Correct credentials";
		}
		
		if (pass == null) {
			return "User not found. Please register here";
		}
		
		return "Invalid password";
	}
}



//public User authenticateUser(String username, String password) throws Exception{
	UserDAOImpl udao = new UserDAOImpl();
	List<User> users = udao.getAll();
	for (User u : users) {
		if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
			return u;
		}
	}
	return null;
}*/