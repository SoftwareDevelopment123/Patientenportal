package de.patientenportal.persistence;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import de.patientenportal.entities.User;

public class UserDAO {

	/**
	 * Datenbankzugriff zum: Aufrufen eines Users mit der UserID
	 * 
	 * @param userID,
	 *            des aufzurufenden Users
	 * @return User
	 */
	public static User getUser(int userID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = new User();

		try {
			session.beginTransaction();
			user = (User) session.get(User.class, userID);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * Datenbankzugriff zum: Aufrufen eines Users mit dem Username
	 * 
	 * @param username,
	 *            des aufzurufenden Users
	 * @return User
	 */
	public static User getUserByUsername(String username) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);

		Root<User> user = query.from(User.class);
		Predicate predicate = builder.equal(user.get("username"), username);
		query.select(user).where(predicate).distinct(true);

		User requestedUser;
		try {
			requestedUser = session.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("Error: " + e);
			return null;
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return requestedUser;
	}

	/**
	 * Datenbankzugriff zum: Ändern eines Users
	 * 
	 * @param User,
	 *            das vollständige geänderte User Objekt
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateUser(User updateduser) {
			Session session = HibernateUtil.getSessionFactory().openSession();

			try {
				session.beginTransaction();
				session.saveOrUpdate(updateduser);
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
	 * Datenbankzugriff zum: Löschen eines Users
	 * 
	 * @param userID
	 *            - des zu löschenden Users
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deleteUser(int userID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			User user = (User) session.get(User.class, userID);
			session.delete(user);
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