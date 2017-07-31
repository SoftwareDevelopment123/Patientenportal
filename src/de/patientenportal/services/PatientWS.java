package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface PatientWS {

	@WebMethod
	public Patient getPatient(@WebParam(name = "Patient-ID") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public PatientListResponse getPatientsByR(@WebParam(name = "Relative-ID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException,
			InvalidParamException, PersistenceException;

}