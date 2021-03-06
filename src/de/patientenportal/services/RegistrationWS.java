package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.InvalidParamException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface RegistrationWS {

	@WebMethod
	public User createUser(@WebParam(name = "User") User user) throws InvalidParamException;

	@WebMethod
	public String createPatient(@WebParam(name = "Patient") Patient patient, @WebParam(name = "User-ID") int userID) throws InvalidParamException;

	@WebMethod
	public String createDoctor(@WebParam(name = "Doctor") Doctor doctor, @WebParam(name = "User-ID") int userID) throws InvalidParamException;

	@WebMethod
	public String createRelative(@WebParam(name = "Relative") Relative relative,
			@WebParam(name = "User-ID") int userID) throws InvalidParamException;
}