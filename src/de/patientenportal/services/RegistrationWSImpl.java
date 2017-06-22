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
	public String checkUsername(String username) {

		// ggf. nur interne Methode, die beim Anlegen eines Users läuft
	
		return null;

	
	
	}
	
	@Transactional
	public String createUser(User user) {
		
		// hier kommt noch die ganze not-null-Logik hin
		
		String feedback = RegistrationDAO.createUser(user);
		return feedback;
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
