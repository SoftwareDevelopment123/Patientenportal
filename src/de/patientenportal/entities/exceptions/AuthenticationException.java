package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn das Token für die Authentifizierung ungültig ist.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "AuthenticationException")
public class AuthenticationException extends Exception {

	private static final long serialVersionUID = -5253314249194043867L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}
}