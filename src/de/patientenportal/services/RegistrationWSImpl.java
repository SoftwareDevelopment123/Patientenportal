package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.UserListResponse;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.RegistrationWS")
public class RegistrationWSImpl implements RegistrationWS {

	/*
	 * Anlegen von Usern und den 3 bisher vorgesehenen Akteuren Hier keine
	 * Token-Checks durchführen
	 */

	/**
	 * <b>Neuen Benutzer anlegen</b><br>
	 * Sollten kein Username, Passwort, Vor-/Nachname, Geburtsdatum angegeben
	 * sein, so wird eine entsprechende Fehlermeldung ausgegeben. Ebenso wird
	 * überprüft, ob der Benutzername schon vergeben ist.<br>
	 * 
	 * @param user
	 * @return <code>UserListResponse</code> mit angelegtem Benutzer und
	 *         Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public UserListResponse createUser(User user) {
		UserListResponse response = new UserListResponse();

		if (user.getUsername() == null) {
			response.setResponseCode("Bitte einen Username angeben.");
			return response;
		}
		if (user.getPassword() == null) {
			response.setResponseCode("Kein Passwort angegeben.");
			return response;
		}
		if (user.getFirstname() == null) {
			response.setResponseCode("Kein Vorname angegeben.");
			return response;
		}
		if (user.getLastname() == null) {
			response.setResponseCode("Kein Nachname angegeben.");
			return response;
		}
		if (user.getBirthdate() == null) {
			response.setResponseCode("Kein Geburtsdatum angegeben.");
			return response;
		}

		else {
			boolean usercheck = RegistrationDAO.checkUsername(user.getUsername());
			if (usercheck == true) {
				response.setResponseCode("Username schon vergeben.");
				return response;
			}

			User newuser = RegistrationDAO.createUser(user);
			response.getResponseList().add(newuser);
			response.setResponseCode("success");
			return response;
		}
	}

	/**
	 * <b>Neuen Patienten anlegen</b><br>
	 * Sollten keine UserID oder Blutgruppe angegeben sein, so wird eine
	 * entsprechende Fehlermeldung ausgegeben.<br>
	 * 
	 * @param Patient
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public String createPatient(Patient patient, int userID) {

		if (userID == 0) {
			return "Keine User-ID mitgegeben";
		} // Sollte in der Client-Logik ausgeschlossen sein
		if (patient.getBloodtype() == null) {
			return "Keine Blutgruppe angegeben.";
		}

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getPatient() != null) {
			return "Zu diesem User ist schon ein Patient eingetragen";
		}

		else {
			String feedbackCP = RegistrationDAO.createPatient(patient);
			if (feedbackCP == "error") {
				return feedbackCP;
			}

			toupdate.setPatient(patient);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	/**
	 * <b>Neuen Doktor anlegen</b><br>
	 * Sollten keine UserID oder Spezialisierung angegeben sein, so wird eine
	 * entsprechende Fehlermeldung ausgegeben.<br>
	 * 
	 * @param doctor
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public String createDoctor(Doctor doctor, int userID) {

		if (userID == 0) {
			return "Keine User-ID mitgegeben";
		} // Sollte in der Client-Logik ausgeschlossen sein
		if (doctor.getSpecialization() == null) {
			return "Kein Fachgebiet angegeben.";
		}

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getDoctor() != null) {
			return "Zu diesem User ist schon ein Doktor eingetragen";
		}

		else {
			String feedbackCD = RegistrationDAO.createDoctor(doctor);
			if (feedbackCD == "error") {
				return feedbackCD;
			}

			toupdate.setDoctor(doctor);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	/**
	 * <b>Neuen Verwandten anlegen</b><br>
	 * Sollte keine UserID angegeben sein, so wird eine entsprechende
	 * Fehlermeldung ausgegeben.<br>
	 * 
	 * @param relative
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public String createRelative(Relative relative, int userID) {

		if (userID == 0) {
			return "Keine User-ID mitgegeben";
		} // Sollte in der Client-Logik ausgeschlossen sein

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getRelative() != null) {
			return "Zu diesem User ist schon ein Doktor eingetragen";
		}

		else {
			String feedbackCD = RegistrationDAO.createRelative(relative);
			if (feedbackCD == "error") {
				return feedbackCD;
			}

			toupdate.setRelative(relative);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}
}
