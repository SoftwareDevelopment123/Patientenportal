package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.RegistrationWS")
public class RegistrationWSImpl implements RegistrationWS {
	
/*
 * Anlegen von Usern und den 3 bisher vorgesehenen Akteuren
 */
	
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
	public String createPatient(Patient patient, int userID) {
	
		if (userID == 0)					 {return "Keine User-ID mitgegeben";}	// Sollte in der Client-Logik ausgeschlossen sein
		if (patient.getBloodtype()	== null) {return "Keine Blutgruppe angegeben.";}
		
		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getPatient() != null)	 {return "Zu diesem User ist schon ein Patient eingetragen";}
		
		else {				
			String feedbackCP = RegistrationDAO.createPatient(patient);
				if (feedbackCP == "error")	 {return feedbackCP;}
			
			toupdate.setPatient(patient);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	@Transactional
	public String createDoctor(Doctor doctor, int userID) {
		
		if (doctor.getSpecialization()	== null) {return "Kein Fachgebiet angegeben.";}
		
		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getDoctor() != null)	 	 {return "Zu diesem User ist schon ein Doktor eingetragen";}
		
		else {				
			String feedbackCD = RegistrationDAO.createDoctor(doctor);
				if (feedbackCD == "error")	 	 {return feedbackCD;}
			
			toupdate.setDoctor(doctor);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	@Transactional
	public String createRelative(Relative relative, int userID) {
			
		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getRelative() != null)	 	 {return "Zu diesem User ist schon ein Doktor eingetragen";}
		
		else {				
			String feedbackCD = RegistrationDAO.createRelative(relative);
				if (feedbackCD == "error")	 	 {return feedbackCD;}
			
			toupdate.setRelative(relative);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}
}
