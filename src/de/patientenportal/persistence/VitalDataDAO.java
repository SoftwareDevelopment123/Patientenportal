package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.VitalData;


public class VitalDataDAO {
	
	// VitalDaten hinzufügen // Da das über den Case läuft ist die Methode wsl. unnötig
	public static void add(VitalData vitalData) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(vitalData);
		session.getTransaction().commit();
		
		session.close();
	}
}

