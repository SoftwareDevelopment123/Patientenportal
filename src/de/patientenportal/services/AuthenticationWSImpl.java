package de.patientenportal.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.transaction.Transactional;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.hibernate.criterion.Restrictions;

import de.patientenportal.entities.Gender;
import de.patientenportal.entities.User;
import de.patientenportal.entities.WebSession;
import de.patientenportal.persistence.UserDAO;
import de.patientenportal.persistence.WebSessionDAO;

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
		
  @Transactional
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
    return checkUsernamePassword(username, password);
    
  }
    
  @Transactional
  public boolean authenticateToken(){
	  MessageContext mctx = wsctx.getMessageContext();
		
		//get detail from request headers
	    Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
	    List tokenList = (List) http_headers.get("Token");

	    String token = "";
	    
	    if(tokenList!=null){
	    	//get username
	    	token = tokenList.get(0).toString();
	    }	
	  
	  
	List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return false;
	updateToken(sessions.get(0));
	return true;
  }
  
  @Transactional
  public User getUserByToken(String token) {
	List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return null;
	return sessions.get(0).getUser();
  } 
  
  @Transactional
  public String logout(String token){
	List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return "Fehler";
	WebSessionDAO.deleteWS(sessions.get(0));
	return "Erfolgreich ausgeloggt! Bis zum n�chsten Mal!";
  }
  
  
  
  
  
  
  //Alle ben�tigten Methoden:  
  
  //Gleicht Username und Passwort mit der Datenbank ab und erstellt bei �bereinstimmung
  //einen Token. Dieser wird mit zur�ckgegeben --> anpassen!
  private String checkUsernamePassword(String username, String password){
    
    if(UserDAO.getUserByUsername(username) != null){
    	if (username.equals(UserDAO.getUserByUsername(username).getUsername()) && password.equals(UserDAO.getUserByUsername(username).getPassword()))
    	{
    		if (UserDAO.getUserByUsername(username).getGender() != null)
    		{
    			if (UserDAO.getUserByUsername(username).getGender().equals(Gender.MALE))
				{
    			User user = UserDAO.getUserByUsername(username);
    			String token = createSessionToken(user);
				return "Herzlich willkommen Herr "+user.getLastname()+"! Token:" + token;
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
  
  
  
  //SessionService: --> alle Methoden f�r die Verwaltung der Sessions:
  
  /**Erstellt eine Websession und gibt einen zuf�llig generierten Token zur�ck.
   * Die Session l�uft nach 15 Minuten Inaktivit�t ab.
   * @param
   * @return Token
   */
  public String createSessionToken(User user){
	WebSession wss = new WebSession();
	wss.setUser(user);
	wss.setToken(getNewToken());
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 15);
	wss.setValidTill(c.getTime());
	
	return (WebSessionDAO.createWebSession(wss)).getToken();
  }
  
  //Erstellt einen zuf�llig generierten Token
  public String getNewToken() {
		Random random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
  }
  
  //L�scht abgelaufene Token
  public static void deleteInvalidTokens() {
	List<WebSession> invalidSessions = WebSessionDAO.findByCriteria(Restrictions.or(
			Restrictions.isNull("validtill"),
			Restrictions.le("validtill", Calendar.getInstance().getTime())));
	for(WebSession ws: invalidSessions) {
		WebSessionDAO.deleteWS(ws);
	}
  }
  
  //Verl�ngert die Session um weitere 15 min
  public void updateToken(WebSession ws) {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 15);
	ws.setValidTill(c.getTime());
	WebSessionDAO.updateWS(ws);
  }
  
}
       
	
		
		
