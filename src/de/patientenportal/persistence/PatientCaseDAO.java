package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.PatientCase;


public class PatientCaseDAO {
	// Fall hinzufügen
	public static void add(PatientCase patientCase) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(patientCase);
		session.getTransaction().commit();
		
		session.close();
	}
}

