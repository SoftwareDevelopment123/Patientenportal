package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.VitalDataType;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.VitalDataListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface VitalDataWS {

	@WebMethod
	public VitalDataListResponse getVitalDatabyC(@WebParam(name = "Token--CaseID") Accessor accessor,
			@WebParam(name = "VitalDataType") VitalDataType vDtype) throws PersistenceException, AccessorException, InvalidParamException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String createVitalData(@WebParam(name = "Token--VitalData") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String deleteVitalData(@WebParam(name = "Token--VitalDataID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException,
			InvalidParamException, PersistenceException;

	@WebMethod
	public String updateVitalData(@WebParam(name = "Token--VitalData") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

}
