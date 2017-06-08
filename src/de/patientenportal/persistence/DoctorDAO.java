package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Doctor;
public class DoctorDAO {
	
	// Doktor abrufen
	public static Doctor getDoctor(int doctorID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Doctor doctor = new Doctor();
				
		session.beginTransaction();		
		doctor = (Doctor)session.get(Doctor.class, doctorID);
		
		if (doctor != null){
			Hibernate.initialize(doctor.getOffice());			// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();
	
		session.close();
			
		return doctor;
	}	
	
}
