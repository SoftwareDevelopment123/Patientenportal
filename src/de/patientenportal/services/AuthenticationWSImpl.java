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
 * Autentifizierungsservice: Stellt sämtliche für die Authentifizierungs- und
 * Authorisierungprozesse benötigeten Methoden bereit
 */
@WebService(endpointInterface = "de.patientenportal.services.AuthenticationWS")
public class AuthenticationWSImpl implements AuthenticationWS {

	@Resource
	static WebServiceContext wsctx;

	/**
	 * <b>Benutzer-Authentifizierung mit gewünschter Benutzer-Rolle</b><br>
	 * Gleicht den über den HTTP-Header übermittelten Username, sowie das
	 * Passwort mit der Datenbank ab. Außerdem wird überprüft, ob der User auch
	 * über die angeforderte Benutzer-Rolle einnehmen darf.
	 *
	 * @return <code>String</code> response mit Begrüßung oder Fehlermeldung
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
		// User und Passwort-Überprüfung
		if (checkUsernamePassword(username, password) == true) {
			if ((UserDAO.getUserByUsername(username).getPatient() != null && activeRole == ActiveRole.Patient)
					|| (UserDAO.getUserByUsername(username).getDoctor() != null && activeRole == ActiveRole.Doctor)
					|| (UserDAO.getUserByUsername(username).getRelative() != null && activeRole == ActiveRole.Relative))

				if (UserDAO.getUserByUsername(username).getWebSession() == null) {
					createSessionToken(UserDAO.getUserByUsername(username), activeRole);
				} else {
					return "Keine Berechtigung für die gewünschte Rolle";
				}
			return getGreeting(username);

		}
		return "Benutzer nicht vorhanden oder Passwort falsch! Überprüfen Sie Ihre Eingaben oder registrieren Sich sich!";
	}

	/**
	 * <b>Gibt den aktuellen Token des Users zurück</b><br>
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
	 * <b>Token-Überprüfung</b><br>
	 * Abgelaufene Token werden zunächst gelöscht, anschließend wird der vom
	 * Client übergebene Token mit der Datenbank abgeglichen. Ist der Token noch
	 * vorhanden, so wird die zugehörige Websession verlängert und True
	 * zurückgegeben.
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
	 * <b>Userabruf über Token</b><br>
	 * Gibt den Besitzer des Tokens zurück.
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
	 * Meldet den User über den Token ab, indem die entsprechende WebSession des
	 * Users gelöscht wird.
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
		return "Erfolgreich ausgeloggt! Bis zum nächsten Mal!";
	}

	// Alle benötigten privaten Methoden:

	/**
	 * <b>Passwortüberprüfung</b><br>
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
	 * <b>Begrüßung</b><br>
	 * Gibt je nach Geschlecht des Users die Begrüßung "Herzlich willkommen
	 * Herr/Frau..." zurück.
	 * 
	 * @param username
	 * @return <code>String</code> Begrüßung
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

	// SessionService: --> alle Methoden für die Verwaltung der Sessions:

	/**
	 * Erstellt eine Websession und gibt einen zufällig generierten Token
	 * zurück. Die Session ist 15 Minuten gültig.
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
	 * Erstellt einen zufällig generierten Token
	 * 
	 */
	private String getNewToken() {
		Random random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * Löscht abgelaufene Websessions.
	 * 
	 */
	private void deleteExpiredWebSession() {
		List<WebSession> expiredSessions = WebSessionDAO.getExpiredWebSessions();

		for (WebSession ws : expiredSessions) {
			WebSessionDAO.deleteWS(ws);
			System.out.println("Abgelaufene Websessions gelöscht!");
		}
	}

	/**
	 * Verlängert die Gültigkeit der übergebenen Websession um 15 min.
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
	 * Diese Methode gibt die aktuelle Rolle des angemeldeten Users zurück.
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
	 * Diese Methode führt in 3 Stufen Authentifizierung, Authorisierung und
	 * Prüfung der Schreibrechte (falls benötigt) durch.
	 * 
	 * @param accessor
	 *            token und (falls benätigt) CaseID
	 * @param expected
	 *            erlaubte Rollen für die Methode
	 * @param wRightcheck
	 *            true, wenn für die Methode Schreibrechte zum Fall gefordert
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