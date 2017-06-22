package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;


public class MDocDAO {
	
	//Medicaldocument abrufen
	public static MedicalDoc getMedicalDoc (int medDocID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		MedicalDoc mediacaldoc = new MedicalDoc();
				
		session.beginTransaction();		
		mediacaldoc = (MedicalDoc)session.get(MedicalDoc.class, medDocID);	
		session.getTransaction().commit();
		session.close();
			
		return mediacaldoc;
		
	}
	//Medicaldocument �ndern
		public static String updateMedicalDoc(MedicalDoc updatedmedicaldoc){
		int id = updatedmedicaldoc.getMedDocID();
		if(id!=0){
			
			Doctor createdBy = updatedmedicaldoc.getCreatedBy();
			String title =  updatedmedicaldoc.getTitle();
			String description =  updatedmedicaldoc.getDescription();

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			MedicalDoc medicaldoctoupdate = session.get(MedicalDoc.class, id);
					
				medicaldoctoupdate.setCreatedBy(createdBy);
				medicaldoctoupdate.setDescription(description);
				medicaldoctoupdate.setTitle(title);

			session.getTransaction().commit();
			session.close();
			return "success";
			}
			else {
				return "noID";
			}	
		}
		
	//Medicaldocument l�schen
		
		public static String deleteMedicalDoc(int medDocID){
			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
			MedicalDoc medicaldoc = (MedicalDoc)session.get(MedicalDoc.class, medDocID);
			session.delete(medicaldoc);
			session.getTransaction().commit();
			
			session.close();
			return "success";
		}
			
		
		
		
}
