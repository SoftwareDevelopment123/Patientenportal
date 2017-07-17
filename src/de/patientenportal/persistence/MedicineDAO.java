package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import de.patientenportal.entities.Medicine;

public class MedicineDAO {
	
	/**
	 * Datenbankzugriff zum: Anlegen einer Medicine
	 * @param Medicine, die anzulegende Medicine
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String createMedicine(Medicine newMedicine) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(newMedicine);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}

	/**
	 * Datenbankzugriff zum: Aufrufen einer Medicine
	 * @param medicineID, der aufzurufenden Medicine
	 * @return Medicine
	 */
	public static Medicine getMedicine (int medicineID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Medicine medicine = new Medicine();

		try{
		session.beginTransaction();		
		medicine = (Medicine)session.get(Medicine.class, medicineID);
		
		if (medicine != null){
			Hibernate.initialize(medicine.getMedication());
		}
		
		session.getTransaction().commit();
		session.close();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return medicine;
	}		

	/**
	 * Datenbankzugriff zum: Ändern einer Medicine
	 * @param Medicine, das vollständige geänderte Medicine Objekt
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateMedicine(Medicine updatedmedicine){
		int id = updatedmedicine.getMedicineID();
		if(id!=0){
		
			String name = updatedmedicine.getName();
			String drugmaker = updatedmedicine.getDrugmaker();
			String activeIngredient = updatedmedicine.getActiveIngredient();

			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Medicine medicinetoupdate = session.get(Medicine.class, id);
			medicinetoupdate.setName(name);
			medicinetoupdate.setDrugmaker(drugmaker);
			medicinetoupdate.setActiveIngredient(activeIngredient);
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

	/**
	 * Datenbankzugriff zum: Löschen einer Medicine
	 * @param medicineID, der zu löschenden Medicine
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
		public static String deleteMedicine(int medicineID){
			Session session = HibernateUtil.getSessionFactory().openSession();

			try{
				
			session.beginTransaction();
			Medicine medicine = (Medicine)session.get(Medicine.class, medicineID);
			session.delete(medicine);
			session.getTransaction().commit();
			
			} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
			} finally {
			session.close();
			}
			return "success";
		}

		/**
		 * Datenbankzugriff zum: Aufrufen aller Medicine Objekte
		 * @param 
		 * @return List<Medicine>
		 */
		@SuppressWarnings("unchecked")
		public static List<Medicine> getAllMedicine(){
			Session session = HibernateUtil.getSessionFactory().openSession(); 
			
			List<Medicine> meds = new ArrayList<Medicine>();
			try{
			session.beginTransaction();
			meds = session.createQuery("FROM medicine").list(); 
		    session.getTransaction().commit();				
		    
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return null;
			} finally {
				session.close();
			}
		    return meds;
		}	
}