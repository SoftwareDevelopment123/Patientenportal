package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;

public class OfficeDAO {

	// Office abrufen
	public static Office getOffice(int officeID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Office office = new Office();
		
		try{
		session.beginTransaction();
		office = (Office)session.get(Office.class, officeID);	
			
			if (office != null){
				Hibernate.initialize(office.getDoctors());			// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();		
		
		} catch(Exception e) {
			System.err.println("Error: " + e);
			return null;
			
		} finally{
			session.close();
		}
		return office;		
	}
	
	// Officedaten ändern
	public static String updateOffice(Office updatedoffice){
		int id = updatedoffice.getOfficeID();
		if(id!=0){
			
			String name = updatedoffice.getName();
			List<Doctor> dlist = updatedoffice.getDoctors();
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Office officetoupdate = session.get(Office.class, id);
				officetoupdate.setName(name);
				officetoupdate.setDoctors(dlist);
			session.getTransaction().commit();
			
			} catch(Exception e) {
				System.err.println("Error: " + e);
				return "error";
				
			} finally{
				session.close();
			}
			return "success";
		
		}
		else {
			return "noID";
		}
	}
	
	// Office hinzufügen
	public static String createOffice(Office office){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(office);
		session.getTransaction().commit();
		
		} catch(Exception e) {
			System.err.println("Error: " + e);
			return "error";
			
		} finally{
			session.close();
		}	
		return "success";
	}
	
	//Office löschen
	public static String deleteOffice(int officeID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Office officeD = (Office)session.get(Office.class, officeID);
		session.delete(officeD);
		session.getTransaction().commit();
		
		} catch(Exception e) {
			System.err.println("Error: " + e);
			return "error";
			
		} finally{
			session.close();
		}
		return "success";
	}

}
