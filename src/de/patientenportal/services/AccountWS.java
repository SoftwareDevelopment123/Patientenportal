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

@WebService
@SOAPBinding(style = Style.RPC)
public interface AccountWS {
		
	@WebMethod
	public String deleteUser		(@WebParam (name="User-ID")int userID);
	
	@WebMethod
	public String deleteDoctor		(@WebParam (name="Doctor-ID")int userID);
	
	@WebMethod
	public String deletePatient		(@WebParam (name="Patient-ID")int userID);
	
	@WebMethod
	public String deleteRelative	(@WebParam (name="Relative-ID")int userID);
	
	@WebMethod
	public String updateUser		(@WebParam (name="User")User user);
	
	@WebMethod
	public String updateDoctor		(@WebParam (name="Doctor")Doctor doctor);
	
	@WebMethod
	public String updatePatient		(@WebParam (name="Patient")Patient patient);
	
	@WebMethod
	public String updateRelative	(@WebParam (name="Relative")Relative relative);
}

