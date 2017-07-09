package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface PatientWS {
		
	@WebMethod
	public Patient getPatient					(@WebParam (name="Patient-ID")Accessor accessor);
	
	@WebMethod
	public PatientListResponse getPatientsByR	(@WebParam (name="Relative-ID")Accessor accessor);

}