package de.patientenportal.persistence;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import de.patientenportal.entities.WebSession;

public class WebSessionDAO  {
	

	/**
	 * Anlegen einer Websession
	 * @param Websession, die anzulegende Websession
	 * @return WebSession
	 * @since Beta 1.2
	 */
	public static WebSession createWebSession(WebSession websession) {
		WebSession ws = new WebSession();
		ws.setUser(websession.getUser());
		ws.setToken(websession.getToken());
		ws.setValidTill(websession.getValidTill());
		

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			session.beginTransaction();
			session.save(websession);
			session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			
		} finally {
			session.close();
		}	
		return ws;
		}	
	
	public static List<WebSession> getExpiredWebSessions(){

		Session session = HibernateUtil.getSessionFactory().openSession();
		
	    CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <WebSession> query = builder.createQuery(WebSession.class);
			
		Root<WebSession> webSession = 	query.from(WebSession.class);
											Predicate predicate = builder.lessThan(webSession.get("validtill"),Calendar.getInstance().getTime());
										query.select(webSession).where(predicate).distinct(true);
								
		List<WebSession> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (NoResultException e) {
			System.err.println("Error: " + e);
			return null;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		
		return result;
	}
		
	public static List<WebSession> getWebSessionByToken(String token){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	    CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <WebSession> query = builder.createQuery(WebSession.class);
			
		Root<WebSession> webSession = 	query.from(WebSession.class);
											Predicate predicate = builder.equal(webSession.get("token"),token);
										query.select(webSession).where(predicate).distinct(true);
								
		List<WebSession> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (NoResultException e) {
			System.err.println("Error: " + e);
			return null;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		
		return result;
	}
	
	/**
	 * L�schen einer Websession
	 * @param WebSession, die zu l�schende Websession
	 * @return String <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deleteWS(WebSession ws){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.delete(ws);
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
	 * �ndern einer Websession
	 * @param WebSession, die zu l�schende Websession
	 * @return String <code>String</code> mit Erfolgsmeldung oder Fehler
	 */	
	public static String updateWS(WebSession ws){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();				
		session.saveOrUpdate(ws);
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