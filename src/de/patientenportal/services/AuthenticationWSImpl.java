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
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.User;
import de.patientenportal.entities.WebSession;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.UserDAO;
import de.patientenportal.persistence.WebSessionDAO;

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
  static WebServiceContext wsctx;
		
  @SuppressWarnings("rawtypes")
  @Transactional
  public String authenticateUser(ActiveRole activeRole){ //(String username, String password) {
	
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
    if (checkUsernamePassword(username, password) == true){
    	if((UserDAO.getUserByUsername(username).getPatient() != null && activeRole == ActiveRole.Patient)||
    	   (UserDAO.getUserByUsername(username).getDoctor() != null	&& activeRole == ActiveRole.Doctor)||
    	   (UserDAO.getUserByUsername(username).getRelative() != null && activeRole == ActiveRole.Relative))
    	
    		//if(UserDAO.getUserByUsername(username).getWebSession()== null){
    		createSessionToken(UserDAO.getUserByUsername(username), activeRole);
    		//}
    	else{ return "Keine Berechtigung für die gewünschte Rolle";}
    	return getGreeting(username);
    	
    }
    return "Benutzer nicht vorhanden oder Passwort falsch! Überprüfen Sie Ihre Eingaben oder registrieren Sich sich!";
  }
  
  @Override
  public String getSessionToken(String username){
	  if(UserDAO.getUserByUsername(username) != null){
		if(UserDAO.getUserByUsername(username).getWebSession()!= null){
		  return  UserDAO.getUserByUsername(username).getWebSession().getToken();
		}
	  }
	  return "Fehler";
  }
  
  /**
   * Token-Überprüfung. Abgelaufene Token werden zunächst gelöscht,
   * anschließend werden die vom Client übergebenen Token mit der Datenbank abgeglichen.
   * Ist der Token noch vorhanden, so wird die zugehörige Websession verlängert und True zurückgegeben.
   *@param
   *@return Boolean
   */
  @Transactional
  public boolean authenticateToken(String token){	
	  deleteInvalidTokens();
	  List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return false; //throw new HTTPException(401);
	extendWebSession(sessions.get(0));
	return true;
  }
  
  
  
  @Transactional
  public static User getUserByToken(String token) {
	List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return null;
	return sessions.get(0).getUser();
  } 
  
  @Transactional
  public String logout(String token){
	List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
	if (sessions.size() != 1) return "Fehler";
	WebSessionDAO.deleteWS(sessions.get(0));
	return "Erfolgreich ausgeloggt! Bis zum nächsten Mal!";
  }
  
  
  
  
  //Alle benötigten Methoden:  
  
  //Gleicht Username und Passwort mit der Datenbank ab und erstellt bei Übereinstimmung
  //einen Token. Dieser wird mit zurückgegeben --> anpassen!
  
  private boolean checkUsernamePassword(String username, String password){
    if(UserDAO.getUserByUsername(username) != null){
    	if (username.equals(UserDAO.getUserByUsername(username).getUsername()) && password.equals(UserDAO.getUserByUsername(username).getPassword()))
    	{
    	return true;
    	}
    }
    return false;
  }
    
  
  private String getGreeting(String username){
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
		}
		return "Herzlich willkommen "+username +"!";	
  }
  
  
  //SessionService: --> alle Methoden für die Verwaltung der Sessions:
  
  /**Erstellt eine Websession und gibt einen zufällig generierten Token zurück.
   * Die Session läuft nach 15 Minuten Inaktivität ab.
   * @param
   * @return Token
   */
  private String createSessionToken(User user, ActiveRole activeRole){
	WebSession wss = new WebSession();
	wss.setUser(user);
	wss.setToken(getNewToken());
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 15);
	wss.setValidTill(c.getTime());
	wss.setActiveRole(activeRole);
	return (WebSessionDAO.createWebSession(wss)).getToken();
  }
  
  //Erstellt einen zufällig generierten Token
  private String getNewToken() {
		Random random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
  }
  
  //Löscht abgelaufene Token
  private void deleteInvalidTokens() {
	List<WebSession> invalidSessions = WebSessionDAO.findByCriteria(Restrictions.or(
			Restrictions.isNull("validtill"),
			Restrictions.le("validtill", Calendar.getInstance().getTime())));
	for(WebSession ws: invalidSessions) {
		WebSessionDAO.deleteWS(ws);
	}
  }
  
  //Verlängert die Session um weitere 15 min
  private void extendWebSession(WebSession ws) {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 15);
	ws.setValidTill(c.getTime());
	WebSessionDAO.updateWS(ws);
  } 

       
  @SuppressWarnings("rawtypes")
public static String getToken(){

		MessageContext mctx = wsctx.getMessageContext();
		
		//get detail from request headers
	    Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
	    List tokenList = (List) http_headers.get("Token");

	    String token = "";
	    
	    if(tokenList!=null){
	    	//get username
	    	token = tokenList.get(0).toString();
	    return token;
	    }
	    return null;
	}
		
  public static ActiveRole getActiveRole(String token){
	  List<WebSession> sessions = WebSessionDAO.findByCriteria(Restrictions.eq("token", token));
		if (sessions.size() != 1) return null;
	  return sessions.get(0).getActiveRole();
  }
  
  /**
   * Diese Methode führt in 3 Stufen Authentifizierung, Authorisierung und Prüfung der Schreibrechte (falls benötigt) durch.
   * 
   * @param accessor	token und (falls benätigt) CaseID
   * @param expected	erlaubte Rollen für die Methode
   * @param wRightcheck true, wenn für die Methode Schreibrechte zum Fall gefordert sind
   * @return
   */
  
  public static String tokenRoleAccessCheck (Accessor accessor, List<ActiveRole> expected, boolean wRightcheck){
	  	String token = accessor.getToken();
	  
	  	AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false) {
			System.err.println("Invalid token");
			return "Authentifizierung fehlgeschlagen.";}
		
		ActiveRole role = AuthenticationWSImpl.getActiveRole(token);
		
		if (!expected.contains(role) == true) {
			System.err.println("No Access for this role");
			return "Zugriff auf die Methode für diese Rolle nicht gestattet";}
		
		if (wRightcheck == true){
			AccessWSImpl acc = new AccessWSImpl();
			if (acc.checkWRight(accessor) == false) {
				System.err.println("No Writing-Rights for this Case");
				return "Keine Schreibrechte für diesen Fall";
			}
		}
	  return null;
  }
  
}		
