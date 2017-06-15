package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Address;

public class OfficeDAO {

	// Office abrufen
	public static Office getOffice(int officeID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Office office = new Office();
		
		session.beginTransaction();
		office = (Office)session.get(Office.class, officeID);	
		
		if (office != null){
		Hibernate.initialize(office.getDoctors());			// LAZY-HIBERNATE-MAGIC
		}
		session.getTransaction().commit();
				
		session.close();
		return office;		
	}
	
	// Officedaten ändern
	public static String updateOffice(Office updatedoffice){
		int id = updatedoffice.getOfficeID();
		if(id!=0){
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			String name = updatedoffice.getName();
			List<Doctor> doctors = updatedoffice.getDoctors();
			//Contact contact = updatedoffice.getContact();						//Flush-Fehler
			//Address address = updatedoffice.getAddress();						//Flush-Fehler

			
			session.beginTransaction();				
			Office officetoupdate = session.get(Office.class, id);
					
				if(name!=null)		{officetoupdate.setName(name);}
				if(doctors!=null)	{officetoupdate.setDoctors(doctors);}
				//if(contact!=null)	{officetoupdate.setContact(contact);}		//Flush-Fehler
				//if(address!=null)	{officetoupdate.setAddress(address);}		//Flush-Fehler
			
			session.getTransaction().commit();
			
			} catch(Exception e) {
				System.err.println("Flush-Error: " + e);
				return "error";
				
			} finally{
				session.close();
			}
			return "success";
		
		}
		else {
			return "noID";
		}
	}
	
	// Office hinzufügen
	public static String createOffice(Office office){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(office);
		session.getTransaction().commit();
				
		session.close();
		return "success";
	}
	
	//Office löschen
	public static String deleteOffice(int office_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Office officeD = (Office)session.get(Office.class, office_id);
		session.delete(officeD);
		session.getTransaction().commit();
		
		session.close();
		return "success";
	}

}
