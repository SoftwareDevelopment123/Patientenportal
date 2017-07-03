package de.patientenportal.persistence;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Address;
import de.patientenportal.services.AddressWS;

@WebService (endpointInterface = "de.patientenportal.services.AddressWS")
public class AddressWSImpl implements AddressWS {

	/*
	 * Hinweis f�r die Pr�sentationsschicht
	 * Bei der Update-Methode ist sicherzustellen, dass das vollst�ndig Objekt mitgegeben wird,
	 * zum Beispiel durch Abruf der Adresse aus der Datenbank und �nderung einzelner Attribute
	 */
	
	@Transactional
	public String updateAddress(Address address) {
		String response = AddressDAO.updateAddress(address);
		return response;
	}

	@Transactional
	public String deleteAddress(int addressID) {
		String response = AddressDAO.deleteAddress(addressID);
		return response;
	}

}
