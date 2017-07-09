package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface DoctorWS {

	@WebMethod
	public Doctor getDoctor					(@WebParam (name="Doctor-ID") Accessor accessor);
	
	@WebMethod
	public DoctorListResponse getDoctorsByC	(@WebParam (name="Case-ID") Accessor accessor);
	
	@WebMethod
	public DoctorListResponse getDoctorsByP	(@WebParam (name="Patient-ID") Accessor accessor);

}
