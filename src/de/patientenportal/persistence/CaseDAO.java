package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;

public class CaseDAO {

	// Fall abrufen
	public static Case getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Case getcase = new Case();
			
		try{
		session.beginTransaction();
		getcase = (Case)session.get(Case.class, caseID);	
		
			if (getcase != null){
				Hibernate.initialize(getcase.getVitaldata());		// LAZY-HIBERNATE-MAGIC
				Hibernate.initialize(getcase.getMedicalDocs());
				Hibernate.initialize(getcase.getDoctors());
				Hibernate.initialize(getcase.getMedication());
				Hibernate.initialize(getcase.getIdoc());
			}
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return getcase;
		}
	
	// Falldaten �ndern
	public static String updateCase(Case updatedcase){
		int id = updatedcase.getCaseID();
		if(id!=0){
				
			String title = updatedcase.getTitle();
			String descr = updatedcase.getDescription();
			boolean status = updatedcase.isStatus();
			List <Doctor> doctors = updatedcase.getDoctors();
			Patient patient = updatedcase.getPatient();				// sollte man normalerweise nicht �ndern m�ssen
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Case casetoupdate = session.get(Case.class, id);	
				casetoupdate.setTitle(title);
				casetoupdate.setDescription(descr);
				casetoupdate.setStatus(status);
				casetoupdate.setDoctors(doctors);
				casetoupdate.setPatient(patient);
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
	
	// Fall anlegen
	public static String createCase(Case newcase) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(newcase);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
	
	// Fall l�schen
	public static String deleteCase(int caseID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Case removecase = (Case)session.get(Case.class, caseID);
		session.delete(removecase);
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
