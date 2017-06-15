package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Session;
import de.patientenportal.entities.*;

/*
 * Hier können alle Abfragen/Methoden rein, die nicht für User-Gebrauch bestimmt sind
 * Dazu gehören theoretisch auch alle Delete[Object]-Methoden
 */

public class AdminDAO {

	public static List<User> getAllUsers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		    	 
		session.beginTransaction();
	    @SuppressWarnings("unchecked")
		List<User> users = session.createQuery("FROM User").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return users;
	}
	
	public static List<Doctor> getAllDoctors(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		    	 
		session.beginTransaction();
	    @SuppressWarnings("unchecked")
		List<Doctor> doctors = session.createQuery("FROM Doctor").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return doctors;
	}
	
	public static List<Relative> getAllRelatives(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		    	 
		session.beginTransaction();
	    @SuppressWarnings("unchecked")
		List<Relative> relatives = session.createQuery("FROM Relative").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return relatives;
	}
	
	public static List<Patient> getAllPatients(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		    	 
		session.beginTransaction();
	    @SuppressWarnings("unchecked")
		List<Patient> patients = session.createQuery("FROM Patient").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return patients;
	}
	
	public static List<Office> getAllOffices(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		    	 
		session.beginTransaction();
	    @SuppressWarnings("unchecked")
		List<Office> offices = session.createQuery("FROM Office").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return offices;
	}
	
}
