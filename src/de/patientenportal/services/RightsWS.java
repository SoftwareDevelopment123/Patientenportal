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
import de.patientenportal.entities.response.RightsListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface RightsWS {

	@WebMethod
	public RightsListResponse getRights(@WebParam(name = "Token--Case-ID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException,
			InvalidParamException, PersistenceException;

	@WebMethod
	public String createRight(@WebParam(name = "Token--Right") Accessor accessor) 
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public String updateRight(@WebParam(name = "Token--Right") Accessor accessor) 
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public String deleteRight(@WebParam(name = "Token--Right--ID") Accessor accessor) 
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;
}
