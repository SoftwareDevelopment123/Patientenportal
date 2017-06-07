package de.patientenportal.persistence;

//import org.hibernate.HibernateException;
import java.util.List;
import org.hibernate.Session;
import de.patientenportal.entities.*;
import de.patientenportal.persistence.HibernateUtil;

public class UserDAO {

	// User hinzufügen
	public static void add(User user){
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
	

	public static String update(User updateduser){
		int id = updateduser.getUserId();
		if(id!=0){
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			String username = updateduser.getUsername();
			String password = updateduser.getPassword();
			String email = updateduser.getEmail();
			String lastname = updateduser.getLastname();
			String firstname = updateduser.getFirstname();
			String birthdate = updateduser.getBirthdate();
			String gender = updateduser.getGender();
			
			Doctor doctor = updateduser.getDoctor();
			//Patient patient = updateduser.getPatient();
			//Relative
			//Address address = updateduser.getAddress();
			//Contact contact = updateduser.getContact();

			System.out.println("Updating User /w ID "+ id +" ... Please calm your tits ...");
			
			session.beginTransaction();
						
			User usertoupdate = session.get(User.class, id);
				
				if(username!=null)	{usertoupdate.setUsername(username);}
				if(password!=null)	{usertoupdate.setPassword(password);}
				if(email!=null)		{usertoupdate.setEmail(email);}
				if(lastname!=null)	{usertoupdate.setLastname(lastname);}
				if(firstname!=null)	{usertoupdate.setFirstname(firstname);}				
				if(birthdate!=null)	{usertoupdate.setBirthdate(birthdate);}				
				if(gender!=null)	{usertoupdate.setGender(gender);}
			
				if(doctor!=null){
					Doctor newdoctor = new Doctor();
					String spec = doctor.getSpecialization();
					
					if(spec!=null){newdoctor.setSpecialization(spec);}
					
					usertoupdate.setDoctor(newdoctor);
				}
				
			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}

	}		
	
	
	@SuppressWarnings("unchecked")
	public static List<User> getAllUsers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
	    	 
		session.beginTransaction();
	    List<User> users = session.createQuery("FROM User").list(); 
	    session.getTransaction().commit();				
	    session.close();
	
	    return users;
	}
	

}