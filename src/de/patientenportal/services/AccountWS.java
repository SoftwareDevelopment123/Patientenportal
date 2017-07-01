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
	public void deleteUser		(@WebParam (name="User-ID")int user_id);
	
	@WebMethod
	public void deleteDoctor	(@WebParam (name="Doctor-ID")int user_id);
	
	@WebMethod
	public void deletePatient	(@WebParam (name="Patient-ID")int user_id);
	
	@WebMethod
	public void deleteRelative	(@WebParam (name="Relative-ID")int user_id);
	
	@WebMethod
	public void updateUser		(@WebParam (name="User")User user);
	
	@WebMethod
	public void updateDoctor	(@WebParam (name="Doctor")Doctor doctor);
	
	@WebMethod
	public void updatePatient	(@WebParam (name="Patient")Patient patient);
	
	@WebMethod
	public void updateRelative	(@WebParam (name="Relative")Relative relative);
}

