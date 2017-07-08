package de.patientenportal.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.transaction.Transactional;

import de.patientenportal.entities.Relative;

@WebService
@SOAPBinding(style = Style.RPC)			// Behebt aktuell den Fehler beim Publisher, weiter testen
public interface RelativeWS {

	@WebMethod
	public Relative getRelative				(@WebParam (name="relativeID")int relativeID);
	
	/*@WebMethod
	public List<Relative> getRelativesByP	(@WebParam (name="patientID")int patientID);*/
}
