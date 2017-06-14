package de.patientenportal.persistence;

import java.util.List;
//import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.Rights;

//@Transactional
public class CaseDAO {

	// Fall abrufen
	public static Case getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Case getcase = new Case();
			
		session.beginTransaction();
		getcase = (Case)session.get(Case.class, caseID);	
		
		if (getcase != null){
			Hibernate.initialize(getcase.getVitaldata());		// LAZY-HIBERNATE-MAGIC
			Hibernate.initialize(getcase.getRights());
		}
		session.getTransaction().commit();
				
		session.close();
		return getcase;
		}
	
	// Falldaten ändern
	public static String updateCase(Case updatedcase){
		int id = updatedcase.getCaseID();
		if(id!=0){
				
			String title = updatedcase.getTitle();
			String descr = updatedcase.getDescription();
			boolean status = updatedcase.isStatus();
			List <VitalData> vital = updatedcase.getVitaldata();
			List <Rights> rights = updatedcase.getRights();						// sollte explizit ausgeschlossen werden
																				// und in ein eigenes DAO kommen
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Case casetoupdate = session.get(Case.class, id);
					
				casetoupdate.setTitle(title);
				casetoupdate.setDescription(descr);
				casetoupdate.setStatus(status);
				casetoupdate.setVitaldata(vital);
				casetoupdate.setRights(rights);
				
			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
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
	
	// Fall löschen
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
