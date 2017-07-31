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
import de.patientenportal.entities.response.MedicationListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedicationWS {

	@WebMethod
	public MedicationListResponse getMedicationbyC(@WebParam(name = "Token--CaseID") Accessor accessor)
			throws InvalidParamException, AccessorException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;

	@WebMethod
	public MedicationListResponse getMedicationbyP(@WebParam(name = "Token") Accessor accessor)
			throws InvalidParamException, AccessorException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;

	@WebMethod
	public String createMedication(@WebParam(name = "Token--Medication") Accessor accessor)
			throws InvalidParamException, AccessorException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;

	@WebMethod
	public String deleteMedication(@WebParam(name = "Token--MedicationID") Accessor accessor)
			throws InvalidParamException, AccessorException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;

	@WebMethod
	public String updateMedication(@WebParam(name = "Token--Medication") Accessor accessor)
			throws InvalidParamException, AccessorException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;
}