package de.patientenportal.persistence;

import de.patientenportal.entities.Insurance;

import org.hibernate.Hibernate;
import org.hibernate.Session;

public class InsuranceDAO {

	/**
	 * Datenbankzugriff zum: Aufrufen einer Insurance
	 * 
	 * @param insuranceID
	 * @return Insurance
	 */
	public static Insurance getInsurance(int insuranceID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Insurance insurance = new Insurance();

		try {
			session.beginTransaction();
			insurance = (Insurance) session.get(Insurance.class, insuranceID);

			if (insurance != null) {
				Hibernate.initialize(insurance.getPatients());
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return null;
		} finally {
			session.close();
		}

		return insurance;
	}

	/**
	 * Datenbankzugriff zum: Anlegen einer Insurance
	 * 
	 * @param Insurance,
	 *            die anzulegende Insurance
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String createInsurance(Insurance insurance) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.save(insurance);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}

	/**
	 * Datenbankzugriff zum: Ändern einer Insurance
	 * 
	 * @param Insurance,
	 *            die zu ändernde Insurance, diese muss vollständig sein
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateInsurance(Insurance updatedinsurance) {
		int id = updatedinsurance.getInsuranceID();
		if (id != 0) {

			String Name = updatedinsurance.getName();
			int InsuranceNr = updatedinsurance.getInsuranceNr();

			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				session.beginTransaction();
				Insurance insurancetoupdate = session.get(Insurance.class, id);
				insurancetoupdate.setName(Name);
				insurancetoupdate.setInsuranceNr(InsuranceNr);
				session.getTransaction().commit();

			} catch (Exception e) {
				System.err.println("Flush-Error: " + e);
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
	 * Datenbankzugriff zum: Löschen einer Insurance
	 * 
	 * @param insuranceID
	 *            der zu löschenden Insurance
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deleteInsurance(int insuranceID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			Insurance insurance = (Insurance) session.get(Insurance.class, insuranceID);
			session.delete(insurance);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
}