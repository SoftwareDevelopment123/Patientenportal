package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Address;
import de.patientenportal.persistence.AddressDAO;

@WebService (endpointInterface = "de.patientenportal.services.AddressWS")
public class AddressWSImpl implements AddressWS {

	/*
	 * Hinweis für die Präsentationsschicht
	 * Bei der Update-Methode ist sicherzustellen, dass das vollständig Objekt mitgegeben wird,
	 * zum Beispiel durch Abruf der Addresse aus der Datenbank und Änderung einzelner Attribute
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
