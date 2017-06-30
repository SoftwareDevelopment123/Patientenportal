package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Session;

import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Medicine;



public class MedicineDAO {
	
	//Medicine anlegen
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
				
			session.close();
			return "success";
		}

	//Medicine abrufen
		public static Medicine getMedicine (int MedicineID){
			Session session = HibernateUtil.getSessionFactory().openSession();
			Medicine medicine = new Medicine();
			
			try{
			session.beginTransaction();		
			medicine = (Medicine)session.get(Medicine.class, MedicineID);	
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
		
	//Medicine ändern
		public static String updateMedicine(Medicine updatedmedicine){
		int id = updatedmedicine.getMedicineID();
		if(id!=0){
			
			String name = updatedmedicine.getName();
			String drugmaker = updatedmedicine.getDrugmaker();
			String activeIngredient = updatedmedicine.getActiveIngredient();
			List <Medication> medication = updatedmedicine.getMedication();

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Medicine medicinetoupdate = session.get(Medicine.class, id);
			
			try{		
			medicinetoupdate.setName(name);
			medicinetoupdate.setDrugmaker(drugmaker);
			medicinetoupdate.setActiveIngredient(activeIngredient);
			medicinetoupdate.setMedication(medication);
			
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
		
	//Medicine löschen
		
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
		
		
		
		
}
