package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import de.patientenportal.entities.*;

public class RelativeDAO {

	/**
	 * Datenbankzugriff zum: Aufrufen eines Relative
	 * 
	 * @param relativeID,
	 *            des aufzurufenden Relatives
	 * @return Relative
	 */
	public static Relative getRelative(int relativeID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Relative relative = new Relative();

		try {
			session.beginTransaction();
			relative = (Relative) session.get(Relative.class, relativeID);

			if (relative != null) {
				Hibernate.initialize(relative.getPatients());
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}

		return relative;
	}

	/**
	 * Datenbankzugriff zum: �ndern eines Relatives
	 * 
	 * @param Relative,
	 *            das vollst�ndige ge�nderte Relative Objekt
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateRelative(Relative updatedrelative) {
		int id = updatedrelative.getRelativeID();
		if (id != 0) {

			/*
			 * Platzhalter, da der Relative aktuell keine Attribute besitzt, die
			 * ge�ndert werden m�ssen/k�nnen
			 * 
			 * Session session =
			 * HibernateUtil.getSessionFactory().openSession();
			 * 
			 * try{ session.beginTransaction(); Relative relativetoupdate =
			 * session.get(Relative.class, id);
			 * 
			 * session.getTransaction().commit();
			 * 
			 * } catch (Exception e) { System.err.println("Error: " + e); return
			 * "error"; } finally { session.close(); }
			 */
			return "success";
		} else {
			return "noID";
		}
	}

	/**
	 * Datenbankzugriff zum: L�schen eines Relatives
	 * 
	 * @param relativeID,
	 *            des zu l�schenden Relatives
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deleteRelative(int relativeID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			Relative relative = (Relative) session.get(Relative.class, relativeID);
			session.delete(relative);
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