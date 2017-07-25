package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicineListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedicineWS {

	@WebMethod
	public Medicine getMedicine(@WebParam(name = "Token--MedicineID") Accessor accessor) throws AuthenticationException,
			AccessException, AuthorizationException, InvalidParamException, AccessorException, PersistenceException;

	@WebMethod
	public String createMedicine(@WebParam(name = "Token--Medicine") Accessor accessor) throws InvalidParamException,
			AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException;

	@WebMethod
	public String deleteMedicine(@WebParam(name = "Token--MedicineID") Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, AccessorException,
			InvalidParamException, PersistenceException;

	@WebMethod
	public String updateMedicine(@WebParam(name = "Token--Medicine") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;

	@WebMethod
	public MedicineListResponse getAllMedicine(@WebParam(name = "Token") Accessor accessor)
			throws AccessorException, InvalidParamException, AuthenticationException, AccessException,
			AuthorizationException, PersistenceException;
}