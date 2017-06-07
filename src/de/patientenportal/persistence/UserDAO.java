package de.patientenportal.persistence;

//import org.hibernate.HibernateException;
import java.util.List;
import org.hibernate.Session;
import de.patientenportal.entities.*;
import de.patientenportal.persistence.HibernateUtil;

public class UserDAO {

	// User löschen
	public static String deleteUser(int user_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		User user = (User)session.get(User.class, user_id);
		session.delete(user);
		session.getTransaction().commit();
		
		session.close();
		return "success";
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
	
	// Userdaten ändern
	public static String updateUser(User updateduser){
		int id = updateduser.getUserId();
		if(id!=0){
			
			String username = updateduser.getUsername();
			String password = updateduser.getPassword();
			String email = updateduser.getEmail();
			String lastname = updateduser.getLastname();
			String firstname = updateduser.getFirstname();
			String birthdate = updateduser.getBirthdate();
			String gender = updateduser.getGender();

			System.out.println("Updating User /w ID "+ id +" ... Please calm your tits ...");
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			User usertoupdate = session.get(User.class, id);
				
				if(username!=null)	{usertoupdate.setUsername(username);}
				if(password!=null)	{usertoupdate.setPassword(password);}
				if(email!=null)		{usertoupdate.setEmail(email);}
				if(lastname!=null)	{usertoupdate.setLastname(lastname);}
				if(firstname!=null)	{usertoupdate.setFirstname(firstname);}				
				if(birthdate!=null)	{usertoupdate.setBirthdate(birthdate);}				
				if(gender!=null)	{usertoupdate.setGender(gender);}
							
			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
	}		
	
	// Adressdaten ändern
	public static String updateAddress(Address updatedaddress){
		int id = updatedaddress.getAddressID();
		if(id!=0){
			
			String postalCode = updatedaddress.getPostalCode();
			String street = updatedaddress.getStreet();
			String number = updatedaddress.getNumber();
			String city = updatedaddress.getCity();
					
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Address addresstoupdate = session.get(Address.class, id);
			
				if(postalCode!=null)	{addresstoupdate.setPostalCode(postalCode);}
				if(street!=null)		{addresstoupdate.setStreet(street);}
				if(number!=null)		{addresstoupdate.setNumber(number);}
				if(city!=null)			{addresstoupdate.setCity(city);}				

			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
	}		
	
	// Kontaktdaten ändern
	public static String updateContact(Contact updatedcontact){
		int id = updatedcontact.getContactID();
		if(id!=0){
			
			String phone = updatedcontact.getPhone();
			String mobile = updatedcontact.getMobile();
			String email = updatedcontact.getEmail();
								
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Contact contacttoupdate = session.get(Contact.class, id);

				if(phone!=null)		{contacttoupdate.setPhone(phone);}
				if(mobile!=null)	{contacttoupdate.setMobile(mobile);}
				if(email!=null)		{contacttoupdate.setEmail(email);}

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