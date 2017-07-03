package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.AccountWS")
public class AccountWSImpl implements AccountWS {

	/*
	 * Die Delete-Methoden sind eher f�r die Bereinigung der Datenbank von fehlerhaften Eintr�gen (insofern vorhanden)
	 * gedacht und sollten nicht genutzt werden, um aktive Nutzer zu l�schen.
	 */
	
	@Transactional
	public String deleteUser(int userID) {
		String response = UserDAO.deleteUser(userID);
		return response;
	}

	@Transactional
	public String deleteDoctor(int doctorID) {
		String response = DoctorDAO.deleteDoctor(doctorID);
		return response;
	}

	@Transactional
	public String deletePatient(int patientID) {
		String response = PatientDAO.deletePatient(patientID);
		return response;
	}

	@Transactional
	public String deleteRelative(int relativeID) {
		String response = RelativeDAO.deleteRelative(relativeID);
		return response;
	}

	/*
	 * Hinweis f�r die Pr�sentationsschicht
	 * Bei den Update-Methoden ist sicherzustellen, dass vollst�ndige Objekte mitgegeben werden,
	 * zum Beispiel durch Abruf des Users aus der Datenbank und �nderung einzelner Attribute
	 * 
	 * Infos zum Aufbau:
	 * Die User-Actor, Beziehungen werden beim Erstellen festgelegt und k�nnen nicht ge�ndert werden
	 * Patient-Relative-Beziehungen werden von der Patientenseite aus festgelegt
	 * 
	 */
	
	@Transactional
	public String updateUser(User user) {
		String response = UserDAO.updateUser(user);
		return response;
	}
	
	@Transactional
	public String updateDoctor(Doctor doctor) {
		String response = DoctorDAO.updateDoctor(doctor);
		return response;
	}

	@Transactional
	public String updatePatient(Patient patient) {
		String response = PatientDAO.updatePatient(patient);
		return response;
	}

	@Transactional
	public String updateRelative(Relative relative) {
		String response = RelativeDAO.updateRelative(relative);
		return response;
	}
}
