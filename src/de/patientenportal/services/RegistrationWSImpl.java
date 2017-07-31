package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.RegistrationWS")
public class RegistrationWSImpl implements RegistrationWS {

	/*
	 * Anlegen von Usern und den 3 bisher vorgesehenen Akteuren Hier keine
	 * Token-Checks durchführen
	 */

	/**
	 * <b>Neuen Benutzer anlegen</b><br>
	 * Sollten kein Username, Passwort, Vor-/Nachname, Geburtsdatum angegeben
	 * sein, so wird eine entsprechende Fehlermeldung ausgegeben. Ebenso wird
	 * überprüft, ob der Benutzername schon vergeben ist.<br>
	 * 
	 * @param user
	 * @return <code>UserListResponse</code> mit angelegtem Benutzer und
	 *         Erfolgsmeldung oder Fehlermeldung.
	 * @throws InvalidParamException
	 */
	@Transactional
	public User createUser(User user) throws InvalidParamException {

		if (user.getUsername() == null) {
			throw new InvalidParamException("No username found");
		}
		if (user.getPassword() == null) {
			throw new InvalidParamException("No password found");
		}
		if (user.getFirstname() == null) {
			throw new InvalidParamException("No firstname found");
		}
		if (user.getLastname() == null) {
			throw new InvalidParamException("No lastname found");
		}
		if (user.getBirthdate() == null) {
			throw new InvalidParamException("No birthdate found");
		}

		else {
			boolean usercheck = RegistrationDAO.checkUsername(user.getUsername());
			if (usercheck == true) {
				throw new InvalidParamException("Username already in use");
			}

			User newuser = RegistrationDAO.createUser(user);
			return newuser;
		}
	}

	/**
	 * <b>Neuen Patienten anlegen</b><br>
	 * Sollten keine UserID oder Blutgruppe angegeben sein, so wird eine
	 * entsprechende Fehlermeldung ausgegeben.<br>
	 * 
	 * @param Patient
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 * @throws InvalidParamException
	 */
	@Transactional
	public String createPatient(Patient patient, int userID) throws InvalidParamException {

		if (userID == 0) {
			throw new InvalidParamException("No userID given");
		}
		if (patient.getBloodtype() == null) {
			throw new InvalidParamException("No bloodtype found");
		}

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getPatient() != null) {
			throw new InvalidParamException("This user has already set a Patient");
		}

		else {
			String feedbackCP = RegistrationDAO.createPatient(patient);
			if (feedbackCP == "error") {
				return feedbackCP;
			}

			toupdate.setPatient(patient);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	/**
	 * <b>Neuen Doktor anlegen</b><br>
	 * Sollten keine UserID oder Spezialisierung angegeben sein, so wird eine
	 * entsprechende Fehlermeldung ausgegeben.<br>
	 * 
	 * @param doctor
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 * @throws InvalidParamException
	 */
	@Transactional
	public String createDoctor(Doctor doctor, int userID) throws InvalidParamException {

		if (userID == 0) {
			throw new InvalidParamException("No userID given");
		}
		if (doctor.getSpecialization() == null) {
			throw new InvalidParamException("No specialization found");
		}

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getDoctor() != null) {
			throw new InvalidParamException("This user has already set a Doctor");
		}

		else {
			String feedbackCD = RegistrationDAO.createDoctor(doctor);
			if (feedbackCD == "error") {
				return feedbackCD;
			}

			toupdate.setDoctor(doctor);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}

	/**
	 * <b>Neuen Verwandten anlegen</b><br>
	 * Sollte keine UserID angegeben sein, so wird eine entsprechende
	 * Fehlermeldung ausgegeben.<br>
	 * 
	 * @param relative
	 * @param userID
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehlermeldung.
	 * @throws InvalidParamException
	 */
	@Transactional
	public String createRelative(Relative relative, int userID) throws InvalidParamException {

		if (userID == 0) {
			throw new InvalidParamException("No userID given");
		}

		User toupdate = UserDAO.getUser(userID);
		if (toupdate.getRelative() != null) {
			throw new InvalidParamException("This user has already set a Relative");
		}

		else {
			String feedbackCD = RegistrationDAO.createRelative(relative);
			if (feedbackCD == "error") {
				return feedbackCD;
			}

			toupdate.setRelative(relative);
			String feedbackUU = UserDAO.updateUser(toupdate);
			return feedbackUU;
		}
	}
}