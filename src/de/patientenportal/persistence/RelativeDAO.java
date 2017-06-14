package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Relative;

public class RelativeDAO {

	// Relative abrufen
	public static Relative getRelative(int relativeID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Relative relative = new Relative();
				
		session.beginTransaction();		
		relative = (Relative)session.get(Relative.class, relativeID);
		
		if (relative != null){
			Hibernate.initialize(relative.getPatients());			// LAZY-HIBERNATE-MAGIC
		}
		
		session.getTransaction().commit();

		session.close();
			
		return relative;
	}
	
	
}
