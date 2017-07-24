package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;

public class RightsDAO {
	
	/**
	 * Datenbankzugriff zum: Abruf aller Rechte zu einem Fall
	 * @param  caseID des betroffenen Falls
	 * @return <code>List Rights</code>
	 */
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

	/**
	 * Datenbankzugriff zum: Erstellen eines Rechts (Eintrag in der DB)
	 * 
	 * @param rights - Entity mit dem zu erstellenden Recht <br>
	 * Parameter : 	<code>int</code> rightID <br>
					<code>Case</code> pcase <br>
					<code>Doctor</code> doctor ODER <code>Relative</code> relative <br>
					<code>boolean</code> rRight <br>
					<code>boolean</code> wRight <br>
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String createRight(Rights right){
		Session session = HibernateUtil.getSessionFactory().openSession();

		if (right.getPcase() == null){
			return "no case defined";
		}
		
		try {
			session.beginTransaction();
			session.save(right);
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
	 * Datenbankzugriff zum: Ändern eines Rechts <br>
	 * Sinnvollerweise kann nur das Schreibrecht bearbeitet werden, in anderen Use-Cases sollte ein neues Recht angelegt,
	 * bzw., dieses gelöscht werden.
	 * @param rights Entity des betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
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
	
	/**
	 * Datenbankzugriff zum: Entfernen eines Rechts <br>
	 * 
	 * @param <code>int</code> rightID des betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
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
	
	/**
	 * Datenbankzugriff zum: Abruf aller Fälle, bei denen ein Doktor Leserechte hat.<br>
	 * Da hier oft eine große Menge an Daten abgerufen wird, werden primär nicht benötigte Informationen auf <code>null</code>
	 * gesetzt und können mit getCase dann vollständig abgerufen werden, falls benötigt.
	 * @param doctorID
	 * @return <code> List Case </code>
	 */
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
			c.setIdoc(null);
			c.setMedication(null);
			c.setVitaldata(null);
			cases.add(c);
		}
		return cases;	
	}
	
	/**
	 * Datenbankzugriff zum: Abruf aller Fälle, bei denen ein Verwandter Leserechte hat. <br>
	 * Da hier oft eine große Menge an Daten abgerufen wird, werden primär nicht benötigte Informationen auf <code>null</code>
	 * gesetzt und können mit getCase dann vollständig abgerufen werden, falls benötigt.
	 * @param relativeID
	 * @return <code> List Case </code>
	 */
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
			c.setIdoc(null);
			c.setMedication(null);
			c.setVitaldata(null);
			cases.add(c);
		}
		return cases;	
	}
	
	/**
	 * Datenbankzugriff zum: Prüfen, ob ein Doktor beim gegebenen Fall Lese- oder Schreibrechte hat.
	 * @param access <code>Access</code> Enum: WriteCase oder ReadCase, je nach Prüfung
	 * @return <code> boolean</code> true, wenn der Doktor Lese-, bzw. Schreibrechte hat
	 */
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

	/**
	 * Datenbankzugriff zum: Prüfen, ob ein Verwandter beim gegebenen Fall Lese- oder Schreibrechte hat.
	 * @param access <code>Access</code> Enum: WriteCase oder ReadCase, je nach Prüfung
	 * @return <code> boolean</code> true, wenn der Verwandter Lese-, bzw. Schreibrechte hat
	 */
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