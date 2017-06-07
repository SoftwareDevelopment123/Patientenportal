package de.patientenportal.persistence;

//import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;

//@Transactional
public class CaseDAO {

	// Fall abrufen
	public static Case getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Case getcase = new Case();
			
		session.beginTransaction();
		getcase = (Case)session.get(Case.class, caseID);	
		Hibernate.initialize(getcase.getVitaldata());
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
