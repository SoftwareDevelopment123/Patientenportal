package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.Case;
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
public interface CaseWS {

	@WebMethod
	public Case getCase(@WebParam(name = "Token--Case-ID") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public CaseListResponse getCases(@WebParam(name = "Token--Status") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String createCase(@WebParam(name = "Token--Case") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String deleteCase(@WebParam(name = "Token--Case-ID") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String updateCase(@WebParam(name = "Token--Case") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;
}