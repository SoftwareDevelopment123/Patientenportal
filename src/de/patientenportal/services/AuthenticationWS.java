package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AuthenticationWS {

	@WebMethod
	String authenticateUser(@WebParam(name = "ActiveRole") ActiveRole activeRole)
			throws PersistenceException, AccessException, InvalidParamException;

	@WebMethod
	boolean authenticateToken(@WebParam(name = "Token") String token);

	@WebMethod
	String getSessionToken(@WebParam(name = "Username") String username) 
			throws PersistenceException;

	@WebMethod
	public String logout(String token);

}