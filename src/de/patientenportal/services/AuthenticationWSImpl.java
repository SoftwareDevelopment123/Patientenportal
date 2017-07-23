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
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.WebSession;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;
import de.patientenportal.persistence.WebSessionDAO;

  /**Autentifizierungsservice: �berpr�ft die �ber den HTTP �bergebenen Usernamen und Passwort
  * und gleicht diese mit den Datenbankeintr�gen ab. Bei erfolgreichem Login wird eine Websession 
  * mit Token erstellt. 
  *@param Username (�ber HTTP-Header)
  *@param Password (�ber HTTP-Header)
  *@return Begr��ung/ Information als <code>String</code>  und Token
  */
  @WebService(endpointInterface = "de.patientenportal.services.AuthenticationWS")
  public class AuthenticationWSImpl implements AuthenticationWS {


  @Resource
  static WebServiceContext wsctx;


  /**Login: Gleicht Username und Passwort mit der Datenbank ab. Au�erdem wird �berpr�ft, 
   * ob der User auch �ber die angeforderte Rolle einnehmen darf. 
   * Bei erfolgreichem Login wird eine Begr��ung zur�ck gegeben.
   */
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
    //User und Passwort-�berpr�fung
    if (checkUsernamePassword(username, password) == true){
    	if((UserDAO.getUserByUsername(username).getPatient() != null && activeRole == ActiveRole.Patient)||
    	   (UserDAO.getUserByUsername(username).getDoctor() != null	&& activeRole == ActiveRole.Doctor)||
    	   (UserDAO.getUserByUsername(username).getRelative() != null && activeRole == ActiveRole.Relative))
    	
    		//if(UserDAO.getUserByUsername(username).getWebSession()== null){
    		createSessionToken(UserDAO.getUserByUsername(username), activeRole);
    		//}
    	else{ return "Keine Berechtigung f�r die gew�nschte Rolle";}
    	return getGreeting(username);
    	
    }
    return "Benutzer nicht vorhanden oder Passwort falsch! �berpr�fen Sie Ihre Eingaben oder registrieren Sich sich!";
  }
  

	  /**Gibt den aktuellen Token des Users zur�ck.
	   * @param username
	   * @return token
	   */
	  @Override
	  public String getSessionToken(String username){
		  if(UserDAO.getUserByUsername(username) != null){
			if(UserDAO.getUserByUsername(username).getWebSession()!= null){
			  return  UserDAO.getUserByUsername(username).getWebSession().getToken();
			}
		  }
		  return "Zugriffsehler";
	  }
  
	  /**
	   * Token-�berpr�fung. Abgelaufene Token werden zun�chst gel�scht,
	   * anschlie�end werden die vom Client �bergebenen Token mit der Datenbank abgeglichen.
	   * Ist der Token noch vorhanden, so wird die zugeh�rige Websession verl�ngert und True zur�ckgegeben.
	   *@param token
	   *@return Boolean
	   */
	  @Transactional
	  public boolean authenticateToken(String token){	
		  deleteExpiredWebSession();
		  List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1) return false; //throw new HTTPException(401);
		extendWebSession(sessions.get(0));
		return true;
	  }
	  
	  
	  
	  @Transactional
	  public static User getUserByToken(String token) {
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1) return null;
		return sessions.get(0).getUser();
	  } 
	  
	  @Transactional
	  public String logout(String token){
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1) return "Fehler";
		WebSessionDAO.deleteWS(sessions.get(0));
		return "Erfolgreich ausgeloggt! Bis zum n�chsten Mal!";
	  }

	  //Alle ben�tigten privaten Methoden:  

  
  //Alle ben�tigten Methoden:  
  
  //Gleicht Username und Passwort mit der Datenbank ab und erstellt bei �bereinstimmung
  //einen Token. Dieser wird mit zur�ckgegeben --> anpassen!
  
	  /**Gleicht Username und Passwort mit der Datenbank ab.
	   * 
	   * @param username
	   * @param password
	   * @return Boolean
	   */
	  private boolean checkUsernamePassword(String username, String password){
	    if(UserDAO.getUserByUsername(username) != null){
	    	if (username.equals(UserDAO.getUserByUsername(username).getUsername()) && password.equals(UserDAO.getUserByUsername(username).getPassword()))
	    	{
	    	return true;
	    	}
	    }
	    return false;
	  }

    
	  /**Gibt je nach Geschlecht des Users die Begr��ung "Herzlich willkommen Herr/Frau..." zur�ck.
	  * 
	  * @param username Benutzername
	  * @return Begr��ung
	  * 
	  */
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
  
  
	  //SessionService: --> alle Methoden f�r die Verwaltung der Sessions:
	  
	  /**Erstellt eine Websession und gibt einen zuf�llig generierten Token zur�ck.
	   * Die Session ist 15 Minuten g�ltig.
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
	  
	  //Erstellt einen zuf�llig generierten Token
	  private String getNewToken() {
			Random random = new SecureRandom();
			return new BigInteger(130, random).toString(32);
	  }
	  
	  /**L�scht abgelaufene Websessions.
	   * 
	   */
	  private void deleteExpiredWebSession() {
		List<WebSession> expiredSessions = WebSessionDAO.getExpiredWebSessions();
	
		for(WebSession ws: expiredSessions) {
			WebSessionDAO.deleteWS(ws);
		System.out.println("Abgelaufene Websessions gel�scht!");
		}
	  }
	  
	  /**Verl�ngert die G�ltigkeit der �bergebenen Websession um 15 min.
	   * 
	   * @param ws Websession
	   */
	  private void extendWebSession(WebSession ws) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 15);
		ws.setValidTill(c.getTime());
		WebSessionDAO.updateWS(ws);
	  } 
	
	  /**Liest den vom Client �ber den HTTP-Header �bermittelten Token aus.
	   *        
	   * @return Token
	   */
	  @SuppressWarnings("rawtypes")
	  public static String getToken(){
	
			MessageContext mctx = wsctx.getMessageContext();
			
			//get detail from request headers
		    Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
		    List tokenList = (List) http_headers.get("Token");
	
		    String token = "";
		    
		    if(tokenList!=null){
		    	token = tokenList.get(0).toString();
		    return token;
		    }
		    return null;
		}
	
	  /**Diese Methode gibt die aktuelle Rolle des angemeldeten Users zur�ck.
	   * 
	   * @param token
	   * @return ActiveRole
	   */
	  public static ActiveRole getActiveRole(String token){
		  List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
			if (sessions.size() != 1) return null;
		  return sessions.get(0).getActiveRole();
	  }
	  

  
  /**
   * Diese Methode f�hrt in 3 Stufen Authentifizierung, Authorisierung und Pr�fung der Schreibrechte (falls ben�tigt) durch.
   * 
   * @param accessor	token und (falls ben�tigt) CaseID
   * @param expected	erlaubte Rollen f�r die Methode
   * @param wRightcheck true, wenn f�r die Methode Schreibrechte zum Fall gefordert sind
   * @return Fehlermeldung
   */
  
  public static String tokenRoleAccessCheck (Accessor accessor, List<ActiveRole> expected, Access access){
	  	String token = accessor.getToken();
	  	int actorID = 0;
	  
	  	AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false) {
			System.err.println("Invalid token");
				return "Authentifizierung fehlgeschlagen.";}
		
		ActiveRole role = AuthenticationWSImpl.getActiveRole(token);
		if (expected.contains(role) == false) {
			System.err.println("No Access for this role");
				return "Zugriff auf die Methode f�r diese Rolle nicht gestattet";}
		
		if (access == Access.WriteCase){
			boolean wcheck = false;
			User user = AuthenticationWSImpl.getUserByToken(token);
			if (role == ActiveRole.Doctor){
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				actorID = doctor.getDoctorID();

				wcheck = RightsDAO.checkDocRight(actorID, (int) accessor.getObject(), access);
				if (wcheck == false) {
					System.err.println("No Access to this case");
					return  "Kein Schreibzugriff f�r diesen Fall";}
			}
			
			if (role == ActiveRole.Relative){
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				actorID = relative.getRelativeID();
				
				wcheck = RightsDAO.checkRelRight(actorID, (int) accessor.getObject(), access);
				if (wcheck == false) {
					System.err.println("No Access to this case");
					return  "Kein Schreibzugriff f�r diesen Fall";}
			}
		}
			
		if (access == Access.ReadCase){
			boolean rcheck = false;
			User user = AuthenticationWSImpl.getUserByToken(token);
			if (role == ActiveRole.Doctor){
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				actorID = doctor.getDoctorID();

				rcheck = RightsDAO.checkDocRight(actorID, (int) accessor.getObject(), access);
				if (rcheck == false) {
					System.err.println("No Access to this case");
					return  "Kein Lesezugriff f�r diesen Fall";}
			}
			
			if (role == ActiveRole.Relative){
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				actorID = relative.getRelativeID();
				
				rcheck = RightsDAO.checkRelRight(actorID, (int) accessor.getObject(), access);
				if (rcheck == false) {
					System.err.println("No Access to this case");
					return  "Kein Lesezugriff f�r diesen Fall";}
			}
		}
	  return null;
  }
}
		