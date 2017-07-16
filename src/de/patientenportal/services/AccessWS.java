package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AccessWS {

	@WebMethod
	public CaseListResponse getRCases			(@WebParam (name="Token--Status") 	Accessor accessor);
	
	@WebMethod
	public CaseListResponse getRPatientCases	(@WebParam (name="Token--PatientID")Accessor accessor);
			
	@WebMethod
	public boolean checkWRight					(@WebParam (name="Token--Case-ID") 	Accessor accessor);
}