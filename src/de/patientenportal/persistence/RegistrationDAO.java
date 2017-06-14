package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.*;

public class RegistrationDAO {

	// User hinzufügen
	public static String createUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
			
		session.close();
		return "success";
	}

	// Doktor hinzufügen
	public static String createDoctor(Doctor doctor) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(doctor);
		session.getTransaction().commit();
				
		session.close();
		return "success";
	}
	
	// Patient hinzufügen
	public static String createPatient(Patient patient) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(patient);
		session.getTransaction().commit();
					
		session.close();
		return "success";
	}
		
	// Relative hinzufügen
	public static String createRelative(Relative relative) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(relative);
		session.getTransaction().commit();
					
		session.close();
		return "success";
	}
	
}
