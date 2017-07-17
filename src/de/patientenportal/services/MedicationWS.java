package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicationListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedicationWS {

	@WebMethod
	public MedicationListResponse getMedicationbyC (@WebParam (name="Token--CaseID") Accessor accessor);
	
	@WebMethod
	public MedicationListResponse getMedicationbyP (@WebParam (name="Token") Accessor accessor);
	
	@WebMethod
	public String createMedication	(@WebParam (name="Token--Medication") Accessor accessor);
	
	@WebMethod
	public String deleteMedication	(@WebParam (name="Token--MedicationID") Accessor accessor);
	
	@WebMethod
	public String updateMedication	(@WebParam (name="Token--Medication") Accessor accessor);
}