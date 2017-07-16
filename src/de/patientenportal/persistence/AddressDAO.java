package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Address;

public class AddressDAO {

	/**
	 * Datenbankzugriff zum: Ändern einer Adresse
	 * @param updatedaddress Parameter: postalCode, street, number, City
<<<<<<< HEAD
	 * @return String "success"
=======
	 * @return
>>>>>>> refs/heads/Jascha
	 */
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
	
	/**
	 * Datenbankzugriff zum: Löschen einer Adresse
	 * @param addressID from Address to delete 
	 * @return String "success"
	 */
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
