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

	@Transactional
	public void deleteUser(int userID) {
		UserDAO.deleteUser(userID);
	}

	@Transactional
	public void deleteDoctor(int doctorID) {
		DoctorDAO.deleteDoctor(doctorID);
	}

	@Transactional
	public void deletePatient(int patientID) {
		PatientDAO.deletePatient(patientID);
	}

	@Transactional
	public void deleteRelative(int relativeID) {
		RelativeDAO.deleteRelative(relativeID);
	}

	@Transactional
	public void updateUser(User user) {
		UserDAO.updateUser(user);
	}
	
	@Transactional
	public void updateDoctor(Doctor doctor) {
		DoctorDAO.updateDoctor(doctor);
		
	}

	@Transactional
	public void updatePatient(Patient patient) {
		PatientDAO.updatePatient(patient);		
	}

	@Transactional
	public void updateRelative(Relative relative) {
		RelativeDAO.updateRelative(relative);
	}

}
