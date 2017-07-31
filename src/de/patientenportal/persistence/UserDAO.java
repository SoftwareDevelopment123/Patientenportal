package de.patientenportal.persistence;

import java.util.Date;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import de.patientenportal.entities.*;

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
		int id = updateduser.getUserId();
		if (id != 0) {

			String username = updateduser.getUsername();
			String password = updateduser.getPassword();
			String email = updateduser.getEmail();
			String lastname = updateduser.getLastname();
			String firstname = updateduser.getFirstname();
			Date birthdate = updateduser.getBirthdate();
			Gender gender = updateduser.getGender();
			Doctor doctor = updateduser.getDoctor();

			System.out.println("Updating User /w ID " + id + " ... please calm your tits ...");
			Session session = HibernateUtil.getSessionFactory().openSession();

			try {
				session.beginTransaction();
				User usertoupdate = session.get(User.class, id);
				usertoupdate.setUsername(username);
				usertoupdate.setPassword(password);
				usertoupdate.setEmail(email);
				usertoupdate.setLastname(lastname);
				usertoupdate.setFirstname(firstname);
				usertoupdate.setBirthdate(birthdate);

				usertoupdate.setGender(gender);
				usertoupdate.setDoctor(doctor);

				usertoupdate.setGender(gender);

				session.getTransaction().commit();

			} catch (Exception e) {
				System.err.println("Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			return "success";

		} else {
			return "noID";
		}
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