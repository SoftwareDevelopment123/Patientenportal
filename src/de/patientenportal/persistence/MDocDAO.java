package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;

public class MDocDAO {
	
	// MedicalDoc anlegen
	public static String createMDoc(MedicalDoc newMDoc) {
		//newMDoc.getFile().delete();
		Session session = HibernateUtil.getSessionFactory().openSession();
				
		try{
		session.beginTransaction();		
		session.save(newMDoc);
		session.getTransaction().commit();
		FtpMethodenMDocs.uploadMDoc(newMDoc);
		
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
			String title =  updatedmedicaldoc.getmDocTitle();
			String description =  updatedmedicaldoc.getmDocDescription();
			Case pcase = updatedmedicaldoc.getPcase();
			Patient patient = updatedmedicaldoc.getPatient();
			String filetype = updatedmedicaldoc.getFileType();

			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			MedicalDoc medicaldoctoupdate = session.get(MedicalDoc.class, id);
					
			
			medicaldoctoupdate.setCreatedBy(createdBy);
			medicaldoctoupdate.setmDocDescription(description);
			medicaldoctoupdate.setmDocTitle(title);
			medicaldoctoupdate.setPcase(pcase);
			medicaldoctoupdate.setPatient(patient);
			medicaldoctoupdate.setFileType(filetype);
			
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
