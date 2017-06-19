package de.patientenportal.services;

import javax.jws.WebService;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;

@WebService (endpointInterface = "de.patientenportal.services.RegistrationWS")
public class RegistrationWSImpl implements RegistrationWS {

	public String checkUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createUser(User user) {
		
		// hier kommt noch die ganze not-null-Logik hin
		
		String feedback = RegistrationDAO.createUser(user);
		return feedback;
	}

	public String createPatient(Patient patient) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return null;
	}

	public String createRelative(Relative relative) {
		// TODO Auto-generated method stub
		return null;
	}

}
