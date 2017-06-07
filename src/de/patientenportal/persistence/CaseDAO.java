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
		
		if (getcase != null){
		Hibernate.initialize(getcase.getVitaldata());			// LAZY-HIBERNATE-MAGIC
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
				//boolean status = updatedcase.isStatus();
				
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();				
				Case casetoupdate = session.get(Case.class, id);
					
					if(title!=null)		{casetoupdate.setTitle(title);}
					if(descr!=null)		{casetoupdate.setDescription(descr);}
					//if(status!=null)	{casetoupdate.setStatus(status);} 			// Das hier soll ja in einen eigenen Befehl

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
