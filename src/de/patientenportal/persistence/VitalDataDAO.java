package de.patientenportal.persistence;

import java.util.Date;

import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

public class VitalDataDAO {

	/**
	 * Datenbankzugriff zum: Aufrufen von Vitaldaten
	 * 
	 * @param vitalDataID,
	 *            der aufzurufenden Vitaldaten
	 * @return VitalData
	 */
	public static VitalData getVitalData(int vitalDataID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		VitalData vitalData = new VitalData();

		try {
			session.beginTransaction();
			vitalData = (VitalData) session.get(VitalData.class, vitalDataID);
			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return vitalData;
	}

	/**
	 * Datenbankzugriff zum: Hinzufügen/Anzulegenden von Vitaldaten
	 * 
	 * @param vitalData,
	 *            der hinzufügenden Vitaldaten
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String add(VitalData vitalData) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.save(vitalData);
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
	 * Datenbankzugriff zum: Ändern von Vitaldaten
	 * 
	 * @param VitalData,
	 *            der zu ändernden Vitaldaten
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updateVitalData(VitalData updatedVitalData) {
		int id = updatedVitalData.getVitalDataID();
		if (id != 0) {

			Date timestamp = updatedVitalData.getTimestamp();
			Double value = updatedVitalData.getValue();
			VitalDataType vitalDataType = updatedVitalData.getVitalDataType();
			Case pcase = updatedVitalData.getPcase();

			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				session.beginTransaction();
				VitalData vitaldDataToUpdate = session.get(VitalData.class, id);

				vitaldDataToUpdate.setTimestamp(timestamp);
				vitaldDataToUpdate.setValue(value);
				vitaldDataToUpdate.setVitalDataType(vitalDataType);
				vitaldDataToUpdate.setPcase(pcase);

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
	 * Datenbankzugriff zum: Löschen eines Vitaldaten
	 * 
	 * @param vitalDataID,
	 *            der zu ändernden Vitaldaten
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deleteVitalData(int vitalDataID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			VitalData vitalData = (VitalData) session.get(VitalData.class, vitalDataID);
			session.delete(vitalData);
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