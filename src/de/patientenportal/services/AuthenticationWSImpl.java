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
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;
import de.patientenportal.persistence.WebSessionDAO;

/**
 * Autentifizierungsservice: Stellt s�mtliche f�r die Authentifizierungs- und
 * Authorisierungprozesse ben�tigeten Methoden bereit
 */
@WebService(endpointInterface = "de.patientenportal.services.AuthenticationWS")
public class AuthenticationWSImpl implements AuthenticationWS {

	@Resource
	static WebServiceContext wsctx;

	/**
	 * <b>Benutzer-Authentifizierung mit gew�nschter Benutzer-Rolle</b><br>
	 * Gleicht den �ber den HTTP-Header �bermittelten Username, sowie das
	 * Passwort mit der Datenbank ab. Au�erdem wird �berpr�ft, ob der User auch
	 * �ber die angeforderte Benutzer-Rolle einnehmen darf.
	 *
	 * @return <code>String</code> response mit Begr��ung oder Fehlermeldung
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String authenticateUser(ActiveRole activeRole) {

		MessageContext mctx = wsctx.getMessageContext();

		// Auslesen der HTTP-Header
		Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
		List userList = (List) http_headers.get("Username");
		List passList = (List) http_headers.get("Password");

		String username = "";
		String password = "";

		if (userList != null) {
			// get username
			username = userList.get(0).toString();
		}
		if (passList != null) {
			// get password
			password = passList.get(0).toString();
		}
		// User und Passwort-�berpr�fung
		if (checkUsernamePassword(username, password) == true) {
			if ((UserDAO.getUserByUsername(username).getPatient() != null && activeRole == ActiveRole.Patient)
					|| (UserDAO.getUserByUsername(username).getDoctor() != null && activeRole == ActiveRole.Doctor)
					|| (UserDAO.getUserByUsername(username).getRelative() != null && activeRole == ActiveRole.Relative))

				if (UserDAO.getUserByUsername(username).getWebSession() == null) {
					createSessionToken(UserDAO.getUserByUsername(username), activeRole);
				} else {
					return "Keine Berechtigung f�r die gew�nschte Rolle";
				}
			return getGreeting(username);

		}
		return "Benutzer nicht vorhanden oder Passwort falsch! �berpr�fen Sie Ihre Eingaben oder registrieren Sich sich!";
	}

	/**
	 * <b>Gibt den aktuellen Token des Users zur�ck</b><br>
	 * 
	 * @param username
	 * @return <code>String</code> token oder Fehlermeldung
	 */
	@Transactional
	public String getSessionToken(String username) {
		if (UserDAO.getUserByUsername(username) != null) {
			if (UserDAO.getUserByUsername(username).getWebSession() != null) {
				return UserDAO.getUserByUsername(username).getWebSession().getToken();
			}
		}
		return "Zugriffsehler";
	}

	/**
	 * <b>Token-�berpr�fung</b><br>
	 * Abgelaufene Token werden zun�chst gel�scht, anschlie�end wird der vom
	 * Client �bergebene Token mit der Datenbank abgeglichen. Ist der Token noch
	 * vorhanden, so wird die zugeh�rige Websession verl�ngert und True
	 * zur�ckgegeben.
	 *
	 * @param token
	 * @return Boolean
	 */
	@Transactional
	public boolean authenticateToken(String token) {
		deleteExpiredWebSession();
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1)
			return false;
		extendWebSession(sessions.get(0));
		return true;
	}

	/**
	 * <b>Userabruf �ber Token</b><br>
	 * Gibt den Besitzer des Tokens zur�ck.
	 * 
	 * @param token
	 * @return User
	 */
	@Transactional
	public static User getUserByToken(String token) {
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1)
			return null;
		return sessions.get(0).getUser();
	}

	/**
	 * <b>Logout</b><br>
	 * Meldet den User �ber den Token ab, indem die entsprechende WebSession des
	 * Users gel�scht wird.
	 * 
	 * @param token
	 * @return <code>String</code> response mit Erfolgsmeldung oder
	 *         Fehlermeldung
	 */
	@Transactional
	public String logout(String token) {
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1)
			return "Fehler";
		WebSessionDAO.deleteWS(sessions.get(0));
		return "Erfolgreich ausgeloggt! Bis zum n�chsten Mal!";
	}

	// Alle ben�tigten privaten Methoden:

	/**
	 * <b>Passwort�berpr�fung</b><br>
	 * Gleicht Username und Passwort mit der Datenbank ab.
	 * 
	 * @param username
	 * @param password
	 * @return Boolean
	 */
	private boolean checkUsernamePassword(String username, String password) {
		if (UserDAO.getUserByUsername(username) != null) {
			if (username.equals(UserDAO.getUserByUsername(username).getUsername())
					&& password.equals(UserDAO.getUserByUsername(username).getPassword())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <b>Begr��ung</b><br>
	 * Gibt je nach Geschlecht des Users die Begr��ung "Herzlich willkommen
	 * Herr/Frau..." zur�ck.
	 * 
	 * @param username
	 * @return <code>String</code> Begr��ung
	 * 
	 */
	private String getGreeting(String username) {
		if (UserDAO.getUserByUsername(username).getGender() != null) {
			if (UserDAO.getUserByUsername(username).getGender().equals(Gender.MALE)) {
				return "Herzlich willkommen Herr " + UserDAO.getUserByUsername(username).getLastname() + "!";
			} else if (UserDAO.getUserByUsername(username).getGender().equals(Gender.FEMALE)) {
				return "Herzlich willkommen Frau " + UserDAO.getUserByUsername(username).getLastname() + "!";
			}
		}
		return "Herzlich willkommen " + username + "!";
	}

	// SessionService: --> alle Methoden f�r die Verwaltung der Sessions:

	/**
	 * Erstellt eine Websession und gibt einen zuf�llig generierten Token
	 * zur�ck. Die Session ist 15 Minuten g�ltig.
	 * 
	 * @param user
	 * @param activeRole
	 * @return <code>String</code> Token
	 */
	private String createSessionToken(User user, ActiveRole activeRole) {
		WebSession wss = new WebSession();
		wss.setUser(user);
		wss.setToken(getNewToken());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 15);
		wss.setValidTill(c.getTime());
		wss.setActiveRole(activeRole);
		return (WebSessionDAO.createWebSession(wss)).getToken();
	}

	/**
	 * Erstellt einen zuf�llig generierten Token
	 * 
	 */
	private String getNewToken() {
		Random random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * L�scht abgelaufene Websessions.
	 * 
	 */
	private void deleteExpiredWebSession() {
		List<WebSession> expiredSessions = WebSessionDAO.getExpiredWebSessions();

		for (WebSession ws : expiredSessions) {
			WebSessionDAO.deleteWS(ws);
			System.out.println("Abgelaufene Websessions gel�scht!");
		}
	}

	/**
	 * Verl�ngert die G�ltigkeit der �bergebenen Websession um 15 min.
	 * 
	 * @param ws
	 *            Websession
	 */
	private void extendWebSession(WebSession ws) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 15);
		ws.setValidTill(c.getTime());
		WebSessionDAO.updateWS(ws);
	}

	/**
	 * Diese Methode gibt die aktuelle Rolle des angemeldeten Users zur�ck.
	 * 
	 * @param token
	 * @return ActiveRole
	 */
	public static ActiveRole getActiveRole(String token) {
		List<WebSession> sessions = WebSessionDAO.getWebSessionByToken(token);
		if (sessions.size() != 1)
			return null;
		return sessions.get(0).getActiveRole();
	}

	/**
	 * Diese Methode f�hrt in 3 Stufen Authentifizierung, Authorisierung und
	 * Pr�fung der Schreibrechte (falls ben�tigt) durch.
	 * 
	 * @param accessor
	 *            token und (falls ben�tigt) CaseID
	 * @param expected
	 *            erlaubte Rollen f�r die Methode
	 * @param wRightcheck
	 *            true, wenn f�r die Methode Schreibrechte zum Fall gefordert
	 *            sind
	 * @return <code>String</code> Fehlermeldung
	 * @throws AuthenticationException
	 * @throws AccessException
	 * @throws AuthorizationException
	 */
	public static void tokenRoleAccessCheck(Accessor accessor, List<ActiveRole> expected, Access access)
			throws AuthenticationException, AccessException, AuthorizationException {
		String token = accessor.getToken();
		int actorID = 0;

		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false) {
			System.err.println("Error: Invalid token");
			throw new AuthenticationException("Authentication failed! Invalid token.");
		}

		ActiveRole role = AuthenticationWSImpl.getActiveRole(token);
		if (expected.contains(role) == false) {
			System.err.println("Error: No Access for this role");
			throw new AuthorizationException("Authorization failed! No Access to this Method.");
		}

		if (access == Access.WriteCase) {
			boolean wcheck = false;
			User user = AuthenticationWSImpl.getUserByToken(token);
			if (role == ActiveRole.Doctor) {
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				actorID = doctor.getDoctorID();

				wcheck = RightsDAO.checkDocRight(actorID, (int) accessor.getObject(), access);
				if (wcheck == false) {
					System.err.println("Error: No Access to this case");
					throw new AccessException("No Access to this Case!");
				}
			}

			if (role == ActiveRole.Relative) {
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				actorID = relative.getRelativeID();

				wcheck = RightsDAO.checkRelRight(actorID, (int) accessor.getObject(), access);
				if (wcheck == false) {
					System.err.println("Error: No Access to this case");
					throw new AccessException("No Access to this Case!");
				}
			}
		}

		if (access == Access.ReadCase) {
			boolean rcheck = false;
			User user = AuthenticationWSImpl.getUserByToken(token);
			if (role == ActiveRole.Doctor) {
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				actorID = doctor.getDoctorID();

				rcheck = RightsDAO.checkDocRight(actorID, (int) accessor.getObject(), access);
				if (rcheck == false) {
					System.err.println("Error: No Access to this case");
					throw new AccessException("No Access to this Case!");
				}
			}

			if (role == ActiveRole.Relative) {
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				actorID = relative.getRelativeID();

				rcheck = RightsDAO.checkRelRight(actorID, (int) accessor.getObject(), access);
				if (rcheck == false) {
					System.err.println("Error: No Access to this case");
					throw new AccessException("No Access to this Case!");
				}
			}
		}
	}
}