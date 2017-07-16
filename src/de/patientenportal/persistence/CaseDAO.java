package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;

public class CaseDAO {

	/**
	 * Datenbankzugriff zum: Ausgeben eines Falls anhand der FallID, Es wird dabei Lazy Loading genutzt
	 * @param caseID
	 * @return Case
	 */
	public static Case getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Case getcase = new Case();
			
		try{
		session.beginTransaction();
		getcase = (Case)session.get(Case.class, caseID);	
		
			if (getcase != null){
				Hibernate.initialize(getcase.getVitaldata());		
				Hibernate.initialize(getcase.getMedicalDocs());
				Hibernate.initialize(getcase.getDoctors());
				Hibernate.initialize(getcase.getMedication());
				Hibernate.initialize(getcase.getIdoc());
				Hibernate.initialize(getcase.getPatient());
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
	/**
	 * Datenbankzugriff zum: �ndern eines Falls
	 * @param updatedcase der Case der die Neuerungen enth�lt, er muss vollst�ndig sein und darf nicht nur die �nderungen enthalten
	 * @return String "success"
	 */
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
	
	/**
	 * Datenbankzugriff zum: Anlegen eines Falls
	 * @param Case das anzulegende Case Objekt
	 * @return String "success"
	 */
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
	
	/**
	 * Datenbankzugriff zum: L�schen eines Falls
	 * @param caseID die caseID des zu l�schenden Cases
	 * @return String "success"
	 */
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
