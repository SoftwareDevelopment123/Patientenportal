package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
//import org.hibernate.PropertyValueException;
import org.hibernate.Session;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;

public class RightsDAO {
	
	// Alle Rechte zu einem Fall ausgeben
	public static List<Rights> getRights(int caseID){
			
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
			
		Root<Rights> right = 	query.from(Rights.class);
									Predicate idP = builder.equal(right.get("pcase"), caseID);
								query.select(right).where(idP).distinct(true);
			
		List <Rights> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}

		return result;	
	}

	// Recht hinzuf�gen
	public static String createRight(Rights right){
		Session session = HibernateUtil.getSessionFactory().openSession();

		if (right.getPcase() == null){					// Alternative zu unten
			return "no case defined";
		}
		
		try {
		session.beginTransaction();
		session.save(right);
		session.getTransaction().commit();
		
		} /*catch (PropertyValueException e) {			// sp�ter implementieren, dass die Datenbank notnull vorraussetzt
			System.err.println("Error: " + e);
			return "NotNullError";
		}*/ catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
	
	/*
	 * Recht �ndern
	 *  Sinnvollerweise soll nur wRight bearbeitet werden k�nnen 
	 *  Zum Entfernen wird das ganze Recht gel�scht
	 */
 
	public static String updateRight(Rights updatedright){
		int id = updatedright.getRightID();
		if(id!=0){
			
			boolean wright = updatedright.iswRight();

			Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
			session.beginTransaction();				
			Rights toupdate = session.get(Rights.class, id);
				toupdate.setwRight(wright);
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
	
	// Recht entfernen
	public static String removeRight(int rightID){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
		session.beginTransaction();
		Rights right = (Rights)session.get(Rights.class, rightID);
		session.delete(right);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}

	// Pr�fen, bei welchen F�llen ich (als Doktor) Leserechte habe
	public static List<Case> getDocRCases(int doctorID){
			
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
			
		Root<Rights> right = 	query.from(Rights.class);
									Predicate idP = builder.equal(right.get("doctor"), doctorID);
								query.select(right).where(idP).distinct(true);
			
		List <Rights> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
		
		List<Case> cases = new ArrayList<Case>();
		for (Rights it : result){
			Case c = CaseDAO.getCase(it.getPcase().getCaseID());
			c.setIdoc(null);
			c.setMedicalDocs(null);
			c.setMedication(null);
			c.setVitaldata(null);
			cases.add(c);
			//old
			//cases.add(CaseDAO.getCase(it.getPcase().getCaseID()));
		}
		return cases;	
	}
	
	// Pr�fen, bei welchen F�llen ich (als Verwandter) Leserechte habe
	public static List<Case> getRelRCases(int relativeID){
			
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
			
		Root<Rights> right = 	query.from(Rights.class);
									Predicate idP = builder.equal(right.get("relative"), relativeID);
								query.select(right).where(idP).distinct(true);
			
		List <Rights> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
				
		List<Case> cases = new ArrayList<Case>();
		for (Rights it : result){
			Case c = CaseDAO.getCase(it.getPcase().getCaseID());
			c.setIdoc(null);
			c.setMedicalDocs(null);
			c.setMedication(null);
			c.setVitaldata(null);
			cases.add(c);
			//old
			//cases.add(CaseDAO.getCase(it.getPcase().getCaseID()));
		}
		return cases;	
	}
	
	// Pr�fen, ob ich (Doctor) beim angegebenen Fall Schreibrechte habe
		public static boolean checkDocRight(int doctorID, int caseID, Access access){
				
			Session session = HibernateUtil.getSessionFactory().openSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
				
			Root<Rights> right = 	query.from(Rights.class);
										Predicate 	idP =    builder.equal(right.get("doctor"), doctorID);
										Predicate 	pcaseP = builder.equal(right.get("pcase"), caseID);
										Predicate 	rightP = null;
										if (access == Access.WriteCase){
													rightP = builder.equal(right.get("wRight"), true);}
										if (access == Access.ReadCase){
													rightP = builder.equal(right.get("rRight"), true);}
									query.select(right).where(idP,pcaseP,rightP).distinct(true);
									
			Rights result;					
			try {
			result = session.createQuery(query).getSingleResult();
			} catch (NoResultException e) {
				System.err.println("Error: " + e);
				return false;
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return false;
			} finally {
				session.close();
			}
					
			return result.iswRight();	
		}

	// Pr�fen, ob ich (Relative) beim angegebenen Fall Schreibrechte habe
	public static boolean checkRelRight(int relativeID, int caseID, Access access){
			
		Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
			
		Root<Rights> right = 	query.from(Rights.class);
									Predicate	idP = 	 builder.equal(right.get("relative"), relativeID);
									Predicate 	pcaseP = builder.equal(right.get("pcase"), caseID);
									Predicate 	rightP = null;
									if (access == Access.WriteCase){
												rightP = builder.equal(right.get("wRight"), true);}
									if (access == Access.ReadCase){
												rightP = builder.equal(right.get("rRight"), true);}
								query.select(right).where(idP,pcaseP,rightP).distinct(true);
								
		Rights result;					
		try {
		result = session.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return false;
		} finally {
			session.close();
		}
				
		return result.iswRight();	
	}
}