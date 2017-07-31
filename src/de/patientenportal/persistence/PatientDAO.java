package de.patientenportal.persistence;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;

public class PatientDAO {

	/**
	 * Datenbankzugriff zum: Aufrufen eines Patients
	 * 
	 * @param patientID,
	 *            des aufzurufenden Patients
	 * @return Patient
	 */
	public static Patient getPatient(int patientID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Patient patient = new Patient();

		try {
			session.beginTransaction();
			patient = (Patient) session.get(Patient.class, patientID);

			if (patient != null) {
				Hibernate.initialize(patient.getRelatives());
				Hibernate.initialize(patient.getCases());
				Hibernate.initialize(patient.getMedicalDocs());
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return patient;
	}

	/**
	 * Datenbankzugriff zum: Ändern eines Patients
	 * 
	 * @param Patient,
	 *            das vollständige geänderte Patient Objekt
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String updatePatient(Patient updatedpatient) {
		int id = updatedpatient.getPatientID();
		if (id != 0) {

			String bloodtype = updatedpatient.getBloodtype();
			List<Relative> relatives = updatedpatient.getRelatives();
			Insurance insurance = updatedpatient.getInsurance();

			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				session.beginTransaction();
				Patient patienttoupdate = session.get(Patient.class, id);
				patienttoupdate.setBloodtype(bloodtype);
				patienttoupdate.setRelatives(relatives);
				patienttoupdate.setInsurance(insurance);
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
	 * Datenbankzugriff zum: Löschen eines Patients
	 * 
	 * @param patientID,
	 *            des zu löschenden Patient
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	public static String deletePatient(int patientID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, patientID);
			session.delete(patient);
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