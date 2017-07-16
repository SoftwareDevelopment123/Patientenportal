package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface CaseWS {

	@WebMethod
	public Case getCase					(@WebParam (name="Token--Case-ID") 	Accessor accessor);
	
	@WebMethod
	public CaseListResponse getCases	(@WebParam (name="Token--Status") 	Accessor accessor);
	
	@WebMethod
	public String createCase			(@WebParam (name="Token--Case") 	Accessor accessor);
		
	@WebMethod
	public String deleteCase			(@WebParam (name="Token--Case-ID") 	Accessor accessor);
	
	@WebMethod
	public String updateCase			(@WebParam (name="Token--Case") 	Accessor accessor);
}