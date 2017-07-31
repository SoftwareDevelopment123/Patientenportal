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

@WebService
@SOAPBinding(style = Style.RPC)
public interface ContactWS {

	@WebMethod
	public String updateContact(@WebParam(name = "Token--Contact") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public String deleteContact(@WebParam(name = "Token--ContactID") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;
}