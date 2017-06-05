package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.Doctor;


public class DoctorDAO {
	// Doktor hinzufügen
		public static void add(Doctor doctor) {
			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
			session.save(doctor);
			session.getTransaction().commit();
			
			session.close();
		}
}
