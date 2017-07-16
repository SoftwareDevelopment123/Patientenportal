package de.patientenportal.persistence;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import de.patientenportal.entities.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RegistrationDAO {

	/**
	 * Datenbankzugriff zum: Anlegen eines Users
	 * @param User, das anzulegende Office
	 * @return User, der erzeugte User
	 */
	public static User createUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		} catch (PropertyValueException e) {
			System.err.println("Error: " + e);
			return null;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * Datenbankzugriff zum: Anlegen eines Doctors
	 * @param Doctor, der anzulegende Doctor
	 * @return String "success"
	 */
	public static String createDoctor(Doctor doctor) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
		session.beginTransaction();
		session.save(doctor);
		session.getTransaction().commit();
		
		} catch (PropertyValueException e) {
			System.err.println("Error: " + e);
			return "NotNullError";
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
	
	/**
	 * Datenbankzugriff zum: Anlegen eines Patient
	 * @param Patient, der anzulegende Patient
	 * @return String "success"
	 */
	public static String createPatient(Patient patient) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
		session.beginTransaction();
		session.save(patient);
		session.getTransaction().commit();
		
		} catch (PropertyValueException e) {
			System.err.println("Error: " + e);
			return "NotNullError";
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
		
	/**
	 * Datenbankzugriff zum: Anlegen eines Relative
	 * @param Relative, der anzulegende Relative
	 * @return String "success"
	 */
	public static String createRelative(Relative relative) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
		session.beginTransaction();
		session.save(relative);
		session.getTransaction().commit();
		
		} catch (PropertyValueException e) {
			System.err.println("Error: " + e);
			return "NotNullError";
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}

	/**
	 * Datenbankzugriff zum: Überprüfen ob der Username schon vorhanden ist
	 * @param Patient, der anzulegende Patient
	 * @return boolean, true bedeutet der USername ist bereits vergeben
	 */
	public static boolean checkUsername(String username){
			
			Session session = HibernateUtil.getSessionFactory().openSession();
						
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery <User> query = builder.createQuery(User.class);
			
			Root<User> user = 	query.from(User.class);
									Predicate nameP = builder.equal(user.get("username"), username);
								query.select(user).where(nameP).distinct(true);
			
			User result;					
			try {
			result = session.createQuery(query).getSingleResult();
			} catch (Exception e) {
				return false;
			} finally {
				session.close();
			}
			
			System.out.println("------- Hello! This is your RegistrationDAO, I found the User!  ------");
			System.out.println("Username:   " + result.getUsername());
			System.out.println("Short-Data: " + result.getFirstname() + " / " +  result.getLastname() + " / " + result.getEmail());	
			System.out.println("------------------------------ See Ya! -------------------------------");
			
		return true;	
	}
	
}
