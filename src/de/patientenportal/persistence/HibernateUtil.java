package de.patientenportal.persistence;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * Hilfsmethode für den Aufbau der Session mit der Datenbank. <br>
 * Hier erfolgt der Zugriff auf die hibernate.cfg.xml
 * 
 * @return SessionFactory
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}