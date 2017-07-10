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
	public Case getCase					(@WebParam (name="Case-ID") Accessor accessor);
	
	@WebMethod
	public CaseListResponse getCases	(@WebParam (name="Patient-ID") Accessor accessor);
	
	@WebMethod
	public String createCase			(@WebParam (name="Case") Accessor accessor);
		
	@WebMethod
	public String deleteCase			(@WebParam (name="Case-ID") Accessor accessor);
	
	@WebMethod
	public String updateCase			(@WebParam (name="Case") Accessor accessor);

}
