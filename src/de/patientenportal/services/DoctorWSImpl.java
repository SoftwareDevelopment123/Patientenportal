package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;

@WebService (endpointInterface = "de.patientenportal.services.DoctorWS")
public class DoctorWSImpl implements DoctorWS {

	@Transactional
	public Doctor getDoctor(int doctorID) {
		
		if (doctorID == 0) {return null;}
		
		else{
			Doctor doctor = DoctorDAO.getDoctor(doctorID);
			return doctor;
		}
	}

	@Transactional
	public List<Doctor> getDoctorsByC(int caseID) {
		
		if (caseID == 0) {return null;}
		
		else{
			List<Doctor> doctors = CaseDAO.getCase(caseID).getDoctors();
			return doctors;
		}
		
	}

	/*
	 * Auf diese Methode sollte nur der Patient zugreifen können (entsprechende Beschränkung noch einfügen)
	 * Die Lösung zur Vermeidung von Dopplungen sollte explizit getestet werden!
	 */
	
	@Transactional
	public List<Doctor> getDoctorsByP(int patientID) {
		
		if (patientID == 0) {return null;}
		
		else{
			List<Case> cases = PatientDAO.getPatient(patientID).getCases();
			
			List<Doctor> pDoctors = new ArrayList<Doctor>();
			
			for (Case c : cases) {
				List <Doctor> caselist = c.getDoctors();
				
				for (Doctor d : caselist){
					boolean alreadyInList = false;
		
					for (Doctor dfromlist : pDoctors){
						if (d.getDoctorID() == dfromlist.getDoctorID()){alreadyInList = true;}
					}
					if (alreadyInList == false){pDoctors.add(d);}
				}	
				pDoctors.addAll(c.getDoctors());	
			}
		return pDoctors;
		}
	}

}
