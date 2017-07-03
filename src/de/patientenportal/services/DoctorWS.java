package de.patientenportal.services;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Doctor;

@WebService
@SOAPBinding(style = Style.RPC)
public interface DoctorWS {

	@WebMethod
	public Doctor getDoctor				(@WebParam (name="Doctor-ID")int doctorID);
	
	@WebMethod
	public List<Doctor> getDoctorsByC	(@WebParam (name="Case-ID")int caseID);
	
	@WebMethod
	public List<Doctor> getDoctorsByP	(@WebParam (name="Patient-ID")int patientID);

}
