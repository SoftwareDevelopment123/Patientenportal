package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AccessWS {

	@WebMethod
	public CaseListResponse getRCases(@WebParam(name = "Token--Status") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, InvalidParamException, 
			AccessorException, PersistenceException;
	
	@WebMethod
	public CaseListResponse getRPatientCases(@WebParam(name = "Token--PatientID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, InvalidParamException, 
			AccessorException, PersistenceException;
			
	@WebMethod
	public boolean checkWRight(@WebParam(name = "Token--Case-ID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, InvalidParamException;
}