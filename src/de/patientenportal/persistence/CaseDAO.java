package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Case;

public class CaseDAO {

	// Fall abrufen
	public static Case getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Case getcase = new Case();
			
		session.beginTransaction();
		getcase = (Case)session.get(Case.class, caseID);	
		session.getTransaction().commit();
				
		session.close();
		return getcase;
		}
	
	// Fall anlegen
	public static String createCase(Case newcase) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(newcase);
		session.getTransaction().commit();
			
		session.close();
		return "success";
		}
	
	// User löschen
	public static String deleteCase(int caseID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Case removecase = (Case)session.get(Case.class, caseID);
		session.delete(removecase);
		session.getTransaction().commit();
			
		session.close();
		return "success";
	}
	
}
