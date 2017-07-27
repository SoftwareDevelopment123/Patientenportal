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
public interface AccountWS {

	@WebMethod
	public String updateUser(@WebParam(name = "Token--User") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public String updateDoctor(@WebParam(name = "Token--Doctor") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public String updatePatient(@WebParam(name = "Token--Patient") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public String updateRelative(@WebParam(name = "Token--Relative") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;
}