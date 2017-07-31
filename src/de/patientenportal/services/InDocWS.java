package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.InDocListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface InDocWS {

	@WebMethod
	public InstructionDoc getInDoc(@WebParam(name = "InDoc-ID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public InDocListResponse getInDocssbyC(@WebParam(name = "Case-ID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public InDocListResponse getAllInDocs(@WebParam(name = "") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;
	// XXX WebParam?!

	@WebMethod
	public String updateInDoc(@WebParam(name = "Indoc") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public String deleteInDoc(@WebParam(name = "Indoc-ID") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

	@WebMethod
	public String createInstructionDoc(@WebParam(name = "Indoc") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

}
