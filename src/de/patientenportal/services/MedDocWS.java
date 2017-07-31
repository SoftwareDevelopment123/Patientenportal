package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MDocListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedDocWS {

	@WebMethod
	public MedicalDoc getMDoc(@WebParam(name = "MDocID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException,
			AccessException, AuthorizationException;

	public MDocListResponse getMDocsbyC(@WebParam(name = "CaseID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	public MDocListResponse getMDocsbyP(@WebParam(name = "PatientID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	public MDocListResponse getMDocsbyD(@WebParam(name = "DoctorID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	public String createMedicalDoc(@WebParam(name = "Medicaldocument") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AccessException, AuthenticationException, AuthorizationException;

	public String updateMDoc(@WebParam(name = "Medicaldocument") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	/*public String deleteMDoc(@WebParam(name = "MDocID") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, AccessorException, InvalidParamException, PersistenceException;*/

}
