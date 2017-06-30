package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;

public class MDocDAO {
	
	// MedicalDoc anlegen
	public static String createMDoc(MedicalDoc newMDoc) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(newMDoc);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
	
	//Medicaldocument abrufen
	public static MedicalDoc getMedicalDoc (int medDocID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		MedicalDoc mediacaldoc = new MedicalDoc();
		
		try{
		session.beginTransaction();		
		mediacaldoc = (MedicalDoc)session.get(MedicalDoc.class, medDocID);	
		session.getTransaction().commit();
		session.close();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
			
		return mediacaldoc;
		
	}
	//Medicaldocument ändern
		public static String updateMedicalDoc(MedicalDoc updatedmedicaldoc){
		int id = updatedmedicaldoc.getMedDocID();
		if(id!=0){
			
			Doctor createdBy = updatedmedicaldoc.getCreatedBy();
			String title =  updatedmedicaldoc.getTitle();
			String description =  updatedmedicaldoc.getDescription();
			Case pcase = updatedmedicaldoc.getPcase();
			// Patient ändern?

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			MedicalDoc medicaldoctoupdate = session.get(MedicalDoc.class, id);
					
			try{
			medicaldoctoupdate.setCreatedBy(createdBy);
			medicaldoctoupdate.setDescription(description);
			medicaldoctoupdate.setTitle(title);
			medicaldoctoupdate.setPcase(pcase);

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
		
	//Medicaldocument löschen
		
		public static String deleteMedicalDoc(int medDocID){
			Session session = HibernateUtil.getSessionFactory().openSession();

			try{
			session.beginTransaction();
			MedicalDoc medicaldoc = (MedicalDoc)session.get(MedicalDoc.class, medDocID);
			session.delete(medicaldoc);
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
