package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.Office;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;

@WebService
@SOAPBinding(style = Style.RPC)
public interface OfficeWS {

	@WebMethod
	public Office getOffice(@WebParam(name = "Office-ID") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public String createOffice(@WebParam(name = "Office") Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException;

	@WebMethod
	public String deleteOffice(@WebParam(name = "Office-ID") Accessor accessor)
			throws PersistenceException, InvalidParamException, AccessorException;

	@WebMethod
	public String updateOffice(@WebParam(name = "Office") Accessor accessor)
			throws AccessorException, PersistenceException;
}