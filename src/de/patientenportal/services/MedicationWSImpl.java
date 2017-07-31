package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicationListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MedicationDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.MedicationWS")
public class MedicationWSImpl implements MedicationWS {

	/**
	 * <b>Einem Behandlungsfall zugeordnete Medikationen abrufen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden
	 * Leserechte verfügt.<br>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>MedicationListResponse</code> mit der dem Fall zugeordneten
	 *         Medikation
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public MedicationListResponse getMedicationbyC(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MedicationListResponse response = new MedicationListResponse();
		int id;
		String token;

		try {
			id = (int) accessor.getId();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (id == 0) {
			throw new InvalidParamException("No CaseID found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);

		try {
			List<Medication> mlist = CaseDAO.getCase(id).getMedication();
			List<Medication> medlist = new ArrayList<Medication>();
			for (Medication m : mlist) {
				medlist.add(MedicationDAO.getMedication(m.getMedicationID()));
			}

			response.setResponseCode("success");
			response.setResponseList(medlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * <b>Alle, einem Patienten zugeordneten, Medikationen abrufen</b><br>
	 * Über das Token wird überprüft, ob der User über die entsprechenden
	 * Leserechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token
	 * @return <code>MedicationListResponse</code> mit der dem Patienten
	 *         zugeordneten Medikation
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public MedicationListResponse getMedicationbyP(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MedicationListResponse response = new MedicationListResponse();
		String token;

		try {
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			System.err.println("No token");
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		try {
			Patient patient = new Patient();
			List<Medication> mlist = new ArrayList<Medication>();
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();
			List<Case> cList = PatientDAO.getPatient(patient.getPatientID()).getCases();

			for (Case c : cList) {
				for (Medication m : CaseDAO.getCase(c.getCaseID()).getMedication()) {
					Medication me = MedicationDAO.getMedication(m.getMedicationID());
					mlist.add(me);
				}
			}
			response.setResponseCode("success");
			response.setResponseList(mlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Medikation hinzufügen</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden
	 * Schreibrechte verfügt.<br>
	 * Außerdem wird der anlegende Doktor automatisch der Medikation
	 * hinzugefügt.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token, <code>int </code> caseId und
	 *            der anzulegenden Medikation
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String createMedication(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		Medication medication = new Medication();
		String token;
		int caseId;

		try {
			medication = (Medication) accessor.getObject();
			token = (String) accessor.getToken();
			caseId = accessor.getId();
		}

		catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (caseId == 0) {
			throw new InvalidParamException("No CaseID found");
		}
		if (medication.getMedicine() == null) {
			throw new InvalidParamException("No Medicine found");
		}
		if (medication.getDosage() == null) {
			throw new InvalidParamException("No Dosage found");
		}
		if (medication.getDuration() == null) {
			throw new InvalidParamException("No Duration found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			User creatinguser = AuthenticationWSImpl.getUserByToken(token);
			Doctor creatingdoctor = UserDAO.getUser(creatinguser.getUserId()).getDoctor();

			medication.setPrescribedBy(creatingdoctor);
			medication.setPcase(CaseDAO.getCase(caseId));
			response = MedicationDAO.createMedication(medication);

		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Medikation löschen</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden
	 * Schreibrechte verfügt.<br>
	 *
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code>
	 *            MedicationID der zu löschenden Medikation.
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String deleteMedication(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		int medId;
		String token;

		try {
			medId = (int) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");

		}
		if (medId == 0) {
			throw new InvalidParamException("No MedicationID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setId(MedicationDAO.getMedication(medId).getPcase().getCaseID());
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = MedicationDAO.deleteMedication(medId);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Medikation ändern</b><br>
	 * Über das Token wird überprüft, ob der Doktor über die entsprechenden
	 * Schreibrechte verfügt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und der zu ändernden
	 *            <code>Medication</code>
	 * @return <code>String</code> response mit Erfolgsmeldung oder Fehler
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public String updateMedication(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		Medication medication = new Medication();
		String token;
		int caseID;

		try {
			medication = (Medication) accessor.getObject();
			token = (String) accessor.getToken();
			caseID = accessor.getId();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (caseID == 0) {
			throw new InvalidParamException("No CasID found");
		}
		if (medication.getMedicine() == null) {
			throw new InvalidParamException("No Medicine found");
		}
		if (medication.getDosage() == null) {
			throw new InvalidParamException("No Dosage found");
		}
		if (medication.getDuration() == null) {
			throw new InvalidParamException("No Duration found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = MedicationDAO.updateMedication(medication);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}