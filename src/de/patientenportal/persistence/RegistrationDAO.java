package de.patientenportal.persistence;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import de.patientenportal.entities.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class RegistrationDAO {

	// User hinzufügen
	public static String createUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
		session.beginTransaction();
		session.save(user);
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

	// Doktor hinzufügen
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
	
	// Patient hinzufügen
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
		
	// Relative hinzufügen
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

	// Prüfen, ob der Username schon vergeben ist
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
