package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.AccountWS")
public class AccountWSImpl implements AccountWS {

	/*
	 * Die Delete-Methoden sind eher f�r die Bereinigung der Datenbank von fehlerhaften Eintr�gen (insofern vorhanden)
	 * gedacht und sollten nicht genutzt werden, um aktive Nutzer zu l�schen.
	 * Dementsprechend sind sie auch nicht �ber das Interface abrufbar.
	 */
	
	@Transactional
	public String deleteUser(int userID) {
		String response = UserDAO.deleteUser(userID);
		return response;
	}

	@Transactional
	public String deleteDoctor(int doctorID) {
		String response = DoctorDAO.deleteDoctor(doctorID);
		return response;
	}

	@Transactional
	public String deletePatient(int patientID) {
		String response = PatientDAO.deletePatient(patientID);
		return response;
	}

	@Transactional
	public String deleteRelative(int relativeID) {
		String response = RelativeDAO.deleteRelative(relativeID);
		return response;
	}

	/*
	 * Hinweis f�r die Pr�sentationsschicht
	 * Bei den Update-Methoden ist sicherzustellen, dass vollst�ndige Objekte mitgegeben werden,
	 * zum Beispiel durch Abruf des Users aus der Datenbank und �nderung einzelner Attribute
	 * 
	 * Infos zum Aufbau:
	 * Die User-Actor, Beziehungen werden beim Erstellen festgelegt und k�nnen nicht ge�ndert werden
	 * Patient-Relative-Beziehungen werden von der Patientenseite aus festgelegt
	 * Address - und Contact - Infos werden direkt �ber den User mitgegeben und k�nnen danach
	 * mit den jeweiligen Services ge�ndert werden.
	 */
	
	/**
	 * <b>Userdaten �ndern</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten ge�ndert werden k�nnen.
	 * 
	 * @param Accessor mit <code>String</code> token und dem zu �ndernden <code>User</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateUser(Accessor accessor) {
		User user = new User();
		String token;
		
		try {
			user = (User) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (user == null)	{System.err.println("No user");			return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		if (activeuser.getUserId() != user.getUserId()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
		String response = null;
		try {response = UserDAO.updateUser(user);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}
	
	/**
	 * <b>Doktordaten �ndern</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten ge�ndert werden k�nnen.
	 * 
	 * @param Accessor mit <code>String</code> token und dem zu �ndernden <code>Doctor</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateDoctor(Accessor accessor) {
		Doctor doctor = new Doctor();
		String token;
		
		try {
			doctor = (Doctor) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (doctor == null)	{System.err.println("No doctor");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Doctor activeDoctor = UserDAO.getUser(activeuser.getUserId()).getDoctor();
		if (activeDoctor.getDoctorID() != doctor.getDoctorID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
		String response = null;
		try {response = DoctorDAO.updateDoctor(doctor);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Patientendaten �ndern</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten ge�ndert werden k�nnen.
	 * 
	 * @param Accessor mit <code>String</code> token und dem zu �ndernden <code>Patient</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updatePatient(Accessor accessor) {
		Patient patient = new Patient();
		String token;
		
		try {
			patient = (Patient) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (patient == null){System.err.println("No patient");		return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Patient activePatient = UserDAO.getUser(activeuser.getUserId()).getPatient();
		if (activePatient.getPatientID() != patient.getPatientID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
		String response = null;
		try {response = PatientDAO.updatePatient(patient);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}

	/**
	 * <b>Verwandtendaten �ndern</b><br>
	 * �ber das token wird sichergestellt, dass nur eigene Daten ge�ndert werden k�nnen.<br>
	 * Da die <code>Relative</code>-Entity aktuell keine anderen Attribute au�er der ID besitzt,
	 * ist diese Methode unn�tig und dient als Platzhalter.
	 * 
	 * @param Accessor mit <code>String</code> token und dem zu �ndernden <code>Relative</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateRelative(Accessor accessor) {
		Relative relative = new Relative();
		String token;
		
		try {
			relative = (Relative) accessor.getObject();
			token = (String) accessor.getToken();
		} 
		catch (Exception e) {System.err.println("Invalid access");	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		if (relative == null){System.err.println("No relative");	return null;}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		String authResponse = AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		if (authResponse != null) {
			System.err.println(authResponse);
			return authResponse;
		}
		
		User activeuser = AuthenticationWSImpl.getUserByToken(token);
		Relative activeRelative = UserDAO.getUser(activeuser.getUserId()).getRelative();
		if (activeRelative.getRelativeID() != relative.getRelativeID()){
			System.err.println("No Access to this User");
			return "Kein Zugriff auf diesen Nutzer. Man kann nur eigene Daten �ndern!";
		}
		
		else {
		String response = null;
		try {response = RelativeDAO.updateRelative(relative);}
		catch (Exception e) {System.err.println("Error: " + e); return "Error: " + e;}
		return response;
		}
	}
}