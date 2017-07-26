package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface DoctorWS {

	@WebMethod
	public Doctor getDoctor(@WebParam(name = "Token--Doctor-ID") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public DoctorListResponse getDoctorsByC(@WebParam(name = "Token--Case-ID") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

	@WebMethod
	public DoctorListResponse getDoctorsByP(@WebParam(name = "Token") Accessor accessor) throws PersistenceException,
			AccessorException, InvalidParamException, AuthenticationException, AccessException, AuthorizationException;
}