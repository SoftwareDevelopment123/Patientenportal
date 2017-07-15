package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface RightsWS {
	
	
	@WebMethod
	public RightsListResponse getRights						 (@WebParam (name="Token--Case-ID") 	Accessor accessor);

	@WebMethod
	public String createRight							 (@WebParam (name="Token--Right") 		Accessor accessor);
	
	@WebMethod
	public String updateRight							 (@WebParam (name="Token--Right") 		Accessor accessor);
	
	@WebMethod
	public String deleteRight							 (@WebParam (name="Token--Right--ID") 		Accessor accessor);
}	
