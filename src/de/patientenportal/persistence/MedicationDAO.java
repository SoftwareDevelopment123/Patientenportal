package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Medicine;

public class MedicationDAO {

	/**
	 * Datenbankzugriff zum: Anlegen einer Medication
	 * @param Medication, die anzulegende Medication
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String createMedication(Medication newMedication) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(newMedication);
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
	 * Datenbankzugriff zum: Aufrufen einer Medication
	 * @param medicationID, der aufzurufenden Medication
	 * @return Medication
	 */
	public static Medication getMedication (int medicationID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Medication medication = new Medication();
		
		try{
		session.beginTransaction();		
		medication = (Medication)session.get(Medication.class, medicationID);
		
		if (medication != null){
			Hibernate.initialize(medication.getMedicine());
			Hibernate.initialize(medication.getPrescribedBy());
			Hibernate.initialize(medication.getPcase());
		}
		
		session.getTransaction().commit();
		session.close();
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return medication;
		
	}
	
	/**
	 * Datenbankzugriff zum: Ändern einer Medication
	 * @param Medication, das vollständige geänderte Medication Objekt
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateMedication(Medication updatedmedication){
		int id = updatedmedication.getMedicationID();
		if(id!=0){
		
			Medicine medicine = updatedmedication.getMedicine();
			String dosage = updatedmedication.getDosage();
			String duration = updatedmedication.getDuration();
			Doctor prescribedBy = updatedmedication.getPrescribedBy();
			Case pcase = updatedmedication.getPcase();

			Session session = HibernateUtil.getSessionFactory().openSession();
		
			try{
			session.beginTransaction();				
				Medication medicationtoupdate = session.get(Medication.class, id);
				medicationtoupdate.setMedicine(medicine);
				medicationtoupdate.setDosage(dosage);
				medicationtoupdate.setDuration(duration);
				medicationtoupdate.setPrescribedBy(prescribedBy);
				medicationtoupdate.setPcase(pcase);
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
	 * Datenbankzugriff zum: Löschen eines Instruction-Documents
	 * @param medicationID, die ID der zu löschende Medication
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */	
	public static String deleteMedication(int medicationID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{	
		session.beginTransaction();
		Medication medication = (Medication)session.get(Medication.class, medicationID);
		session.delete(medication);
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