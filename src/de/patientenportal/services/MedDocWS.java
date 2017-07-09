package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MDocListResponse;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedDocWS {

	@WebMethod
	public MedicalDoc getMDoc					(@WebParam (name="MDocID")Accessor accessor);
	
	public MDocListResponse getMDocsbyC			(@WebParam (name="CaseID")Accessor accessor);
	
	public MDocListResponse getMDocsbyP			(@WebParam (name="PatientID")Accessor accessor);
	
	public MDocListResponse getMDocsbyD			(@WebParam (name="DoctorID")Accessor accessor);
	
	public String createMedicalDoc				(@WebParam (name="Medicaldocument") MedicalDoc medicaldoc;)
	
	public String updateMDoc					(@WebParam (name="Medicaldocument") MedicalDoc medicaldoc);
	
	public String deleteMDoc					(@WebParam (name="MDocID") int MDocID);
	
	
	
	
	
}
