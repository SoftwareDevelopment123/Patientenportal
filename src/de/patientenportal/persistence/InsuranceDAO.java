package de.patientenportal.persistence;

import org.hibernate.cfg.Configuration;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Insurance;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InsuranceDAO {
	
	public static String createInsurance(Insurance insurance) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(insurance);
		session.getTransaction().commit();
			
		session.close();
		return "success";
		
}
}
