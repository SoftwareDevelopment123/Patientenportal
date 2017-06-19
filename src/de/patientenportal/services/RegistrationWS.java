package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.User;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;

@WebService
@SOAPBinding(style = Style.RPC)
public interface RegistrationWS {
	
	// checkUsername private machen und bei createUser verwenden?
	
	@WebMethod
	public String checkUsername	(@WebParam (name="Username")	String username);
	
	@WebMethod
	public String createUser	(@WebParam (name="User")		User user);
		
	@WebMethod
	public String createPatient	(@WebParam (name="Patient")		Patient patient);
	
	@WebMethod
	public String createDoctor	(@WebParam (name="Doctor")		Doctor doctor);
	
	@WebMethod
	public String createRelative(@WebParam (name="Relative")	Relative relative);
	
}
