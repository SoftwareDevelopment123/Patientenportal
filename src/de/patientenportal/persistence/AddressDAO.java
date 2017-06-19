package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Address;

public class AddressDAO {

	// Adressdaten ändern
	public static String updateAddress(Address updatedaddress){
		int id = updatedaddress.getAddressID();
		if(id!=0){
				
			String postalCode = updatedaddress.getPostalCode();
			String street = updatedaddress.getStreet();
			String number = updatedaddress.getNumber();
			String city = updatedaddress.getCity();
						
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Address addresstoupdate = session.get(Address.class, id);
				addresstoupdate.setPostalCode(postalCode);
				addresstoupdate.setStreet(street);
				addresstoupdate.setNumber(number);
				addresstoupdate.setCity(city);		
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
	
	// Adresse löschen
	public static String deleteAddress(int addressID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Address address = (Address)session.get(Address.class, addressID);
		session.delete(address);
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
