package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Patient;

public class PatientDAO {

	// Patient abrufen
	public static Patient getPatient(int patientID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Patient patient = new Patient();
				
		session.beginTransaction();		
		patient = (Patient)session.get(Patient.class, patientID);
		
		if (patient != null){
			Hibernate.initialize(patient.getRelatives());			// LAZY-HIBERNATE-MAGIC
		}
		
		session.getTransaction().commit();

		session.close();
			
		return patient;
	}
	
	
}