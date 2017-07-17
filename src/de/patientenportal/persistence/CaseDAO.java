package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;

public class CaseDAO {

	/**
	 * Datenbankzugriff zum: Ausgeben eines Falls anhand der FallID. Dabei werden Lazy-Loading-Felder für den Zugriff initialisiert.
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
	
	/**
	 * Datenbankzugriff zum: Ändern eines Falls
	 * @param updatedcase der Case der die Neuerungen enthält, er sollte vollständig sein und darf nicht nur die Änderungen enthalten.
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateCase(Case updatedcase){
		int id = updatedcase.getCaseID();
		if(id!=0){
				
			String title = updatedcase.getTitle();
			String descr = updatedcase.getDescription();
			boolean status = updatedcase.isStatus();
			List <Doctor> doctors = updatedcase.getDoctors();
			Patient patient = updatedcase.getPatient();				// sollte man normalerweise nicht ändern müssen
			
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
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
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
	 * Datenbankzugriff zum: Löschen eines Falls
	 * @param caseID die caseID des zu löschenden Cases
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
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