package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;

@WebService (endpointInterface = "de.patientenportal.services.RegistrationWS")
public class RegistrationWSImpl implements RegistrationWS {
	
	@Transactional
	public String createUser(User user) {
		
		if (user.getUsername()	== null){return "Bitte einen Username angeben.";}
		if (user.getPassword()	== null){return "Kein Passwort angegeben.";}
		if (user.getFirstname()	== null){return "Kein Vorname angegeben.";}
		if (user.getLastname()	== null){return "Kein Nachname angegeben.";}		
		if (user.getBirthdate()	== null){return "Kein Geburtsdatum angegeben.";}
				
		else {		
			boolean usercheck = RegistrationDAO.checkUsername(user.getUsername());
				if (usercheck == true) 	{return "Username schon vergeben.";}
			
			String feedback = RegistrationDAO.createUser(user);
			return feedback;
		}
	}

	@Transactional
	public String createPatient(Patient patient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String createDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String createRelative(Relative relative) {
		// TODO Auto-generated method stub
		return null;
	}

}
