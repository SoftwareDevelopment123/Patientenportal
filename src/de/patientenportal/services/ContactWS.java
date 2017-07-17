package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.response.Accessor;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ContactWS {

	@WebMethod
	public String updateContact		(@WebParam (name="Token--Contact")Accessor accessor);
	
	@WebMethod
	public String deleteContact		(@WebParam (name="Token--ContactID")Accessor accessor);
}