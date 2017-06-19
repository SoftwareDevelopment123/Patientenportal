package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import de.patientenportal.entities.*;

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
	
	// Relative ändern
	public static String updateRelative (Relative updatedrelative){
		int id = updatedrelative.getRelativeID();
		if(id!=0){
			
		User user = updatedrelative.getUser();
		List<Patient> patients = updatedrelative.getPatients();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();				
		Relative relativetoupdate = session.get(Relative.class, id);
		
		relativetoupdate.setUser(user);
		relativetoupdate.setPatients(patients);
		session.getTransaction().commit();
		
		session.close();
		return "success";
		} else {
			return "noID";
		}
	}

	// Relative löschen
	public static String deleteRelative (int relativeID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
			
		try{
		session.beginTransaction();
		Relative relative = (Relative)session.get(Relative.class, relativeID);
		session.delete(relative);
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
		
		
