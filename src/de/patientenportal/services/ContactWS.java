package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Contact;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ContactWS {

	@WebMethod
	public String updateContact		(@WebParam (name="Contact")Contact contact);
	
	@WebMethod
	public String deleteContact		(@WebParam (name="Contact-ID")int contactID);

}
