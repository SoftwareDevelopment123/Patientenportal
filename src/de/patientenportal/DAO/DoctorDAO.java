package de.patientenportal.DAO;

import org.hibernate.Session;

import de.patientenportal.entities.Doctor;


public class DoctorDAO {
	// User hinzufügen
		public static void add(Doctor doctor) {
			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
			session.save(doctor);
			session.getTransaction().commit();
			
			session.close();
		}
}
