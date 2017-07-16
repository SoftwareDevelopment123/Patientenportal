package de.patientenportal.persistence;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import de.patientenportal.entities.User;
import de.patientenportal.entities.WebSession;

public class WebSessionDAO  {
	

	
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
		
	
	
	
	//Gibt WebSession zurück die bestimmtem Kriterium entsprechen - bsp. ungültig
	@SuppressWarnings("unchecked")
	public static List<WebSession> findByCriteria(Criterion...criterion){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
		session.beginTransaction();
		
		@SuppressWarnings("deprecation")
		Criteria crit = session.createCriteria(WebSession.class);  
	    for (Criterion c : criterion) {  
	        crit.add(c);  
	    }  
	    List<WebSession> wsList = (List<WebSession>) crit.list();
	    
		session.getTransaction().commit();
		
		return wsList;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			
		} finally {
			session.close();
		}
		
		return null;
		
	}
	
	// WS löschen
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
	
	// Userdaten ändern
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