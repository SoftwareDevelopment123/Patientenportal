package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;

public class DoctorDAO {
	
	// Doktor abrufen
	public static Doctor getDoctor(int doctorID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Doctor doctor = new Doctor();
		
		try{
		session.beginTransaction();		
		doctor = (Doctor)session.get(Doctor.class, doctorID);
		
			if (doctor != null){
				Hibernate.initialize(doctor.getOffice());
				Hibernate.initialize(doctor.getCases());			// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();
	
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
			
		return doctor;
	}	
	
	// Doktordaten ändern
	public static String updateDoctor(Doctor updateddoctor){
		int id = updateddoctor.getDoctorID();
		if(id!=0){
					
			String specialization = updateddoctor.getSpecialization();
			Office office = updateddoctor.getOffice();
		
			Session session = HibernateUtil.getSessionFactory().openSession();
			try{
			session.beginTransaction();				
			Doctor doctortoupdate = session.get(Doctor.class, id);
				doctortoupdate.setSpecialization(specialization);
				doctortoupdate.setOffice(office);
			session.getTransaction().commit();
			
			} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
			} finally {
			session.close();
			}
			return "success";
		}
		else {
			return "noID";
		}
	}

	public static String deleteDoctor(int doctor_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Doctor doctor = (Doctor)session.get(Doctor.class, doctor_id);
		session.delete(doctor);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
			} finally {
			session.close();
			}
		return "success";
	}
	
}
