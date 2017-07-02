package de.patientenportal.persistence;

import org.hibernate.Session;

import de.patientenportal.entities.WebSession;

public class WebSessionDAO  {
	
	
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
}