package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn der Lese-/bzw. Schreibzugriff auf den Fall verweigert wird.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "AccessException")
public class AccessException extends Exception {

	private static final long serialVersionUID = -6081585803795350158L;

	public AccessException() {
		super();
	}

	public AccessException(String message) {
		super(message);
	}
}