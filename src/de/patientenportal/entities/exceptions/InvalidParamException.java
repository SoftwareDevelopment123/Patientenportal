package de.patientenportal.entities.exceptions;

import javax.xml.ws.WebFault;

/**
 * Wird geworfen, wenn an eine Methode ein Objekt mit unvollständigen Parametern
 * übergeben wurde.
 * 
 * @param message
 *            Fehlermeldung
 */

@WebFault(name = "InvalidParamException")
public class InvalidParamException extends Exception {

	private static final long serialVersionUID = 6818177939168541553L;

	public InvalidParamException() {
		super();
	}

	public InvalidParamException(String message) {
		super(message);
	}
}