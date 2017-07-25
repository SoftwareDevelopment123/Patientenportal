package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn beim Datenbankzugriff ein Fehler auftritt.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "PersistenceException")
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 4850864185359718444L;

	public PersistenceException() {
		super();
	}

	public PersistenceException(String message) {
		super(message);
	}
}