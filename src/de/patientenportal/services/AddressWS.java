package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.response.Accessor;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AddressWS {

	@WebMethod
	public String updateAddress		(@WebParam (name="Token--Address")Accessor accessor);
	
	@WebMethod
	public String deleteAddress		(@WebParam (name="Token--AddressID")Accessor accessor);
}