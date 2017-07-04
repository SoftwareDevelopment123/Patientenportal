package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import de.patientenportal.entities.*;

public class UserDAO {
	
	// User abrufen
	public static User getUser(int user_id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = new User();
		
		try{
		session.beginTransaction();		
		user = (User)session.get(User.class, user_id);
		session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return user;
	}
	
	// Alle User abrufen
	//private Class<T> persistentClass;

		public static List<User> getAllUsers(){
			Session session = HibernateUtil.getSessionFactory().openSession();
		//	User user = new User();
			
			try{
			session.beginTransaction();		
			List<User> allUsers  = new ArrayList<User>();
			
					allUsers = (List<User>) session.createCriteria(User.class).list();
					
		/*			// Create CriteriaBuilder
					CriteriaBuilder builder = session.getCriteriaBuilder();

					// Create CriteriaQuery
					allUsers = (List<User>) builder.createQuery(User.class);
					
					(allUsers)session.createSQLQuery("SELECT * FROM Products").addEntity(User.class).list();
					
					*/
					
			session.getTransaction().commit();

			return allUsers;
			
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return null;
			} finally {
				session.close();
			}
		//	return allUsers;
		}
	
	
	// Userdaten ändern
	public static String updateUser(User updateduser){
		int id = updateduser.getUserId();
		if(id!=0){
			
			String username = 	updateduser.getUsername();
			String password = 	updateduser.getPassword();
			String email = 		updateduser.getEmail();
			String lastname = 	updateduser.getLastname();
			String firstname = 	updateduser.getFirstname();
			String birthdate = 	updateduser.getBirthdate();
			String gender = 	updateduser.getGender();

			System.out.println("Updating User /w ID "+ id +" ... please calm your tits ...");
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			User usertoupdate = session.get(User.class, id);
				usertoupdate.setUsername(username);
				usertoupdate.setPassword(password);
				usertoupdate.setEmail(email);
				usertoupdate.setLastname(lastname);
				usertoupdate.setFirstname(firstname);	
				usertoupdate.setBirthdate(birthdate);			
				usertoupdate.setGender(gender);		
			session.getTransaction().commit();
			
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			return "success";
			
		}
		else {
			return "noID";
		}
	}		
	
	// User löschen
	public static String deleteUser(int user_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		User user = (User)session.get(User.class, user_id);
		session.delete(user);
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