package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Contact;

public class ContactDAO {
	/**
	 * Datenbankzugriff zum: Ändern eines Kontakts
	 * @param Contact der geänderte Contact
	 * @return String "success"
	 */
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
				contacttoupdate.setPhone(phone);
				contacttoupdate.setMobile(mobile);
				contacttoupdate.setEmail(email);
			session.getTransaction().commit();

			} catch (Exception e) {
				System.err.println("Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			
			return "success";
		
		} else {
			return "noID";
		}
	}
	
	/**
	 * Datenbankzugriff zum: Löschen eines Kontakts
	 * @param contactID
	 * @return String "success"
	 */
	public static String deleteContact(int contactID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Contact contact = (Contact)session.get(Contact.class, contactID);
		session.delete(contact);
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
