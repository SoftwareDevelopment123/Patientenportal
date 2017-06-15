package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Contact;

public class ContactDAO {

	// Kontaktdaten ändern
	public static String updateContact(Contact updatedcontact){
		int id = updatedcontact.getContactID();
		if(id!=0){
				
			String phone = updatedcontact.getPhone();
			String mobile = updatedcontact.getMobile();
			String email = updatedcontact.getEmail();
									
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Contact contacttoupdate = session.get(Contact.class, id);

				if(phone!=null)		{contacttoupdate.setPhone(phone);}
				if(mobile!=null)	{contacttoupdate.setMobile(mobile);}
				if(email!=null)		{contacttoupdate.setEmail(email);}

			session.getTransaction().commit();

			} catch (Exception e) {
				System.err.println("Flush-Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			
			return "success";
		
		} else {
			return "noID";
		}
	}
	
}
