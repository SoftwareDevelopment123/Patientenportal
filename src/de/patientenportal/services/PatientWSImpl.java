package de.patientenportal.services;

import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Patient;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService (endpointInterface = "de.patientenportal.services.PatientWS")
public class PatientWSImpl implements PatientWS {

	@Transactional
	public Patient getPatient(int patientID) {
		if (patientID == 0) {return null;}
		else{
			Patient patient = PatientDAO.getPatient(patientID);
			return patient;
		}	
	}

	@Transactional
	public List<Patient> getPatientsByR(int relativeID) {
		if (relativeID == 0) {return null;}
		else{
			List<Patient> patients = RelativeDAO.getRelative(relativeID).getPatients();
			return patients;
		}
	}
}
