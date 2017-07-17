package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Session;
import de.patientenportal.entities.*;

/*
 * Hier können alle Abfragen/Methoden rein, die nicht für User-Gebrauch bestimmt sind
 * Dazu gehören theoretisch auch alle Delete[Object]-Methoden
 */

@SuppressWarnings("unchecked")
public class AdminDAO {

	/**
	 * Datenbankzugriff zum: Ausgeben aller User
	 * @param Null
	 * @return <code>List User</code>
	 */
	public static List<User> getAllUsers(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<User> users = session.createQuery("FROM User").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return users;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Doktoren
	 * @param Null
	 * @return <code>List Doctor</code>
	 */
	public static List<Doctor> getAllDoctors(){
		Session session = HibernateUtil.getSessionFactory().openSession();   	 
		session.beginTransaction();
			List<Doctor> doctors = session.createQuery("FROM Doctor").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return doctors;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Relatives
	 * @param Null
	 * @return <code>List Relative</code>
	 */
	public static List<Relative> getAllRelatives(){
		Session session = HibernateUtil.getSessionFactory().openSession();	 
		session.beginTransaction();
			List<Relative> relatives = session.createQuery("FROM Relative").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return relatives;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Patienten
	 * @param Null
	 * @return <code>List Patient</code>
	 */
	public static List<Patient> getAllPatients(){
		Session session = HibernateUtil.getSessionFactory().openSession();    	 
		session.beginTransaction();
			List<Patient> patients = session.createQuery("FROM Patient").list(); 
	    session.getTransaction().commit();				
	    session.close();
		
	    return patients;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Offices
	 * @param Null
	 * @return <code>List Office</code>
	 */
	public static List<Office> getAllOffices(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Office> offices = session.createQuery("FROM Office").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return offices;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Insurances
	 * @param Null
	 * @return <code>List Insurance</code>
	 */
	public static List<Insurance> getAllInsurances(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Insurance> insurances = session.createQuery("FROM Insurance").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return insurances;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Adresses
	 * @param Null
	 * @return <code>List Address</code>
	 */
	public static List<Address> getAllAddresses(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Address> addresses = session.createQuery("FROM Address").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return addresses;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Contacts
	 * @param Null
	 * @return <code>List Contact</code>
	 */
	public static List<Contact> getAllContacts(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Contact> contacts = session.createQuery("FROM Contact").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return contacts;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Cases
	 * @param Null
	 * @return <code>List Case</code>
	 */
	public static List<Case> getAllCases(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Case> cases = session.createQuery("FROM Case").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return cases;
	}
	/**
	 * Datenbankzugriff zum: Ausgeben aller Rights
	 * @param Null
	 * @return <code>List Rights</code>
	 */
	public static List<Rights> getAllRights(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		session.beginTransaction();
			List<Rights> rights = session.createQuery("FROM Rights").list(); 
	    session.getTransaction().commit();				
	    session.close();
	    return rights;
	}
}