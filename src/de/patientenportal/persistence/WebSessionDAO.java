package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import de.patientenportal.entities.WebSession;

public class WebSessionDAO  {
	
//quick and dirty... muss noch überarbeitet werden
	
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
	
	//Gibt WebSession zurück die bestimmtem Kriterium entsprechen - bsp. ungültig
	public static List<WebSession> findByCriteria(Criterion...criterion){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
		session.beginTransaction();
		
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