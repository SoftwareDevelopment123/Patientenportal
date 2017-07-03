package de.patientenportal.services;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Patient;

@WebService
@SOAPBinding(style = Style.RPC)
public interface PatientWS {
		
	@WebMethod
	public Patient getPatient			(@WebParam (name="Patient-ID")int patientID);
	
	@WebMethod
	public List<Patient> getPatientsbyR	(@WebParam (name="Relative-ID")int relativeID);

}