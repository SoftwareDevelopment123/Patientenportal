package de.patientenportal.DAO;


//import java.util.Iterator;
//import java.util.List;
import org.hibernate.Session;

import de.patientenportal.DAO.HibernateUtil;
import de.patientenportal.entities.User;

public class UserDAO {

	// User hinzufügen
	public static void add(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		session.close();
	}

	// User löschen
	public static void delete(int user_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		User user = (User)session.get(User.class, user_id);
		session.delete(user);
		session.getTransaction().commit();
		
		session.close();
	}
	
	// User über ID finden
	public static User getUser(int user_id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = new User();
			
		session.beginTransaction();		
		user = (User)session.get(User.class, user_id);	
		session.getTransaction().commit();

		session.close();

		return user;
	}
	
/*	public static void update(User user){
	factory.getCurrentSession().update(user);
	}*/
	
	
/*	public static void getAllUsers(){
		Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List users = session.createQuery("FROM User").list(); 
	         for (Iterator iterator = 
	                           users.iterator(); iterator.hasNext();){
	            User user = (User) iterator.next(); 
	            System.out.print("First Name: " + user.getUsername()); 
	            System.out.print("Last Name: " + user.getPassword()); 
	            System.out.println("Salary: " + user.getEmail()); 
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }		
		//return factory.getCurrentSession().createQuery("from User").list();
	}*/
	

}