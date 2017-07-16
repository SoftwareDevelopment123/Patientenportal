package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import de.patientenportal.entities.WebSession;

public class WebSessionDAO  {
	

	/**
	 * Anlegen einer Websession, muss noch �berarbeitet werden
	 * @param Websession, die anzulegende Websession
	 * @return WebSession
	 * @since Beta 1.2
	 */
	public static WebSession createWebSession(WebSession entity) {
		WebSession ws = new WebSession();
		ws.setUser(entity.getUser());
		ws.setToken(entity.getToken());
		ws.setValidTill(entity.getValidTill());
		

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			
		} finally {
			session.close();
		}	
		return ws;
		}
	
	/**
	 * Gibt WebSession zur�ck die bestimmtem Kriterium entsprechen - bsp. ung�ltig
	 * @param Criterion, Kriterien 
	 * @return List<WebSession>
	 * @since Beta 1.2
	 */
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
	
	/**
	 * L�schen einer Websession
	 * @param WebSession, die zu l�schende Websession
	 * @return String "success"
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
	 * @return String "success"
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