package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn für die aktive Rolle kein Zugriff auf die Methode
 * gestattet ist.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "AuthorizationException")
public class AuthorizationException extends Exception {

	private static final long serialVersionUID = 4642592448433194285L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message) {
		super(message);
	}
}