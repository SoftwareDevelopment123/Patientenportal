package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.Office;


@WebService
@SOAPBinding(style = Style.RPC)
public interface OfficeWS {

	@WebMethod
	public Office getOffice		(@WebParam (name="Office-ID") int officeID);
	
	@WebMethod
	public String createOffice	(@WebParam (name="Office") Office office);
	
	@WebMethod
	public String deleteOffice	(@WebParam (name="Office-ID") int officeID);
	
	@WebMethod
	public String updateOffice	(@WebParam (name="Office") Office office);

}