package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface RelativeWS {

	@WebMethod
	public Relative getRelative(@WebParam(name = "relativeID") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

	@WebMethod
	public RelativeListResponse getRelativesByP(@WebParam(name = "patientID") Accessor accessor)
			throws PersistenceException, AccessorException, InvalidParamException, AuthenticationException,
			AccessException, AuthorizationException;
}
