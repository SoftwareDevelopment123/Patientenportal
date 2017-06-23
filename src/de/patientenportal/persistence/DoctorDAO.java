package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.User;
public class DoctorDAO {
	
	// Doktor abrufen
	public static Doctor getDoctor(int doctorID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Doctor doctor = new Doctor();
				
		session.beginTransaction();		
		doctor = (Doctor)session.get(Doctor.class, doctorID);
		
		if (doctor != null){
			Hibernate.initialize(doctor.getOffice());
			Hibernate.initialize(doctor.getCases());// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();
	
		session.close();
			
		return doctor;
	}	
	
	// Doktordaten ändern
	public static String updateDoctor(Doctor updateddoctor){
		int id = updateddoctor.getDoctorID();
		if(id!=0){
					
			String specialization = updateddoctor.getSpecialization();
			User user = updateddoctor.getUser();
			Office office = updateddoctor.getOffice();

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Doctor doctortoupdate = session.get(Doctor.class, id);
						
				doctortoupdate.setSpecialization(specialization);
				doctortoupdate.setUser(user);
				doctortoupdate.setOffice(office);

			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
	}
	//Doktordaten löschen
	//Info - Rückwärtskaskadierung ist nicht bei allen Verknüpfungen eingestellt
	//Bevor der Eintrag gelöscht werden kann, müssen diese Verknüfungen entfernt werden
	
	public static String deleteDoctor(int doctor_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Doctor doctor = (Doctor)session.get(Doctor.class, doctor_id);
		session.delete(doctor);
		session.getTransaction().commit();
		
		session.close();
		return "success";
	}
	
}
