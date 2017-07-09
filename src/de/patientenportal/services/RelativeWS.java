package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.transaction.Transactional;

import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;

@WebService
@SOAPBinding(style = Style.RPC)			// Behebt aktuell den Fehler beim Publisher, weiter testen
public interface RelativeWS {

	@WebMethod
	public Relative getRelative					(@WebParam (name="relativeID")Accessor accessor);
	
	@WebMethod
	public RelativeListResponse getRelativesByP	(@WebParam (name="patientID")Accessor accessor);
}
