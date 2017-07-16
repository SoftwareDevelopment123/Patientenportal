package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;

@SuppressWarnings("unused")
public class DoctorDAO {
	
	/**
	 * Datenbankzugriff zum: Aufrufen eines Doktors
	 * @param doctorID
	 * @return Doctor der gesuchte Doctor wird zurückgegeben
	 */
	public static Doctor getDoctor(int doctorID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Doctor doctor = new Doctor();
		
		try{
		session.beginTransaction();		
		doctor = (Doctor)session.get(Doctor.class, doctorID);
		
			if (doctor != null){

				Hibernate.initialize(doctor.getOffice());

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
	/**
	 * Datenbankzugriff zum: Ändern eines Doctors
	 * @param Doctor der geänderte Doctor
	 * @return String "success"
	 */
	public static String updateDoctor(Doctor updateddoctor){
		int id = updateddoctor.getDoctorID();
		if(id!=0){
					
			String specialization = updateddoctor.getSpecialization();
			//Office office = updateddoctor.getOffice();								//geändert, ab jetzt über Office
		
			Session session = HibernateUtil.getSessionFactory().openSession();
			try{
			session.beginTransaction();				
			Doctor doctortoupdate = session.get(Doctor.class, id);
				doctortoupdate.setSpecialization(specialization);
				//doctortoupdate.setOffice(office);
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
	/**
	 * Datenbankzugriff zum: Löschen eines Doctors
	 * @param doctorID des zu löschenden Doctors
	 * @return String "success"
	 */
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
