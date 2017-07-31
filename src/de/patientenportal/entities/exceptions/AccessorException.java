package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn an mit dem Accessor die falschen Objekte mitgegeben
 * werden.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "AccessorException")
public class AccessorException extends Exception {

	private static final long serialVersionUID = 7300785032288840684L;

	public AccessorException() {
		super();
	}

	public AccessorException(String message) {
		super(message);
	}
}