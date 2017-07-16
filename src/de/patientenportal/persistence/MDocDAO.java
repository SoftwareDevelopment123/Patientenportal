package de.patientenportal.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;

public class MDocDAO {
	
	/**
	 * Datenbankzugriff zum: Anlegen eines Medical-Documents
	 * @param newMDoc, das anzulegende MedicalDoc
	 * @return String "success"
	 */
	public static String createMDoc(MedicalDoc newMDoc) {
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

	/**
	 * Datenbankzugriff zum: Aufrufen eines Medical-Documents
	 * @param medDocID, des aufzurufenden MedicalDocs
	 * @return MedicalDoc
	 */
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

	/**
	 * Datenbankzugriff zum: Ändern eines Medical-Documents
	 * @param MedicalDoc, das vollständige geänderte Medical-Document
	 * @return MedicalDoc
	 */
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
		
	/**
	 * Datenbankzugriff zum: Löschen eines Medical-Documents
	 * @param medDocID, des zu löschenden Medical-Documents
	 * @return String "success"
	 */
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
		
	/**
	* Datenbankzugriff zum: Aufrufen meherer Medical-Documents anhand einer Doctor ID
	* @param doctorID, des Arztes
	* @return List<MedicalDoc>
	*/
		public static List<MedicalDoc> getMDocs(int doctorID){
			
			Session session = HibernateUtil.getSessionFactory().openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery <MedicalDoc> query = builder.createQuery(MedicalDoc.class);
				
			Root<MedicalDoc> mdoc = 	query.from(MedicalDoc.class);
							
									Predicate idP = builder.equal(mdoc.get("doctor_fk"), doctorID);
									query.select(mdoc).where(idP).distinct(true);
				
			List <MedicalDoc> result;					
			try {
			result = session.createQuery(query).getResultList();
			} catch (Exception e) {
				return null;
			} finally {
				session.close();
			}

			return result;	
		}
		
}
