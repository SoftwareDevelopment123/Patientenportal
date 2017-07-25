package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicineListResponse;
import smalltests.InvalidParamException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MedicineWS {

	@WebMethod
	public Medicine getMedicine(@WebParam(name = "Token--MedicineID") Accessor accessor);

	@WebMethod
	public String createMedicine(@WebParam(name = "Token--Medicine") Accessor accessor) throws InvalidParamException;

	@WebMethod
	public String deleteMedicine(@WebParam(name = "Token--MedicineID") Accessor accessor);

	@WebMethod
	public String updateMedicine(@WebParam(name = "Token--Medicine") Accessor accessor);

	@WebMethod
	public MedicineListResponse getAllMedicine(@WebParam(name = "Token") Accessor accessor);
}
