package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.RightsWS")
public class RightsWSImpl implements RightsWS {

	/**
	 * <b>Alle Rechte zu einem Fall abrufen</b><br>
	 * Über das Token und die caseID wird das Schreibrecht abgefragt. Dient nur
	 * zur Anzeige der Information.<br>
	 * Die Schreibrechtprüfung findet bei den entsprechenden Methoden
	 * individuell statt.
	 * 
	 * Zugriffsbeschränkung: <code> Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>RightsListResponse</code> mit Liste der Rechte und
	 *         Erfolgsmeldung
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public RightsListResponse getRights(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		RightsListResponse response = new RightsListResponse();
		int caseId;
		String token;

		try {
			caseId = accessor.getId();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (caseId == 0) {
			throw new InvalidParamException("No ID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		// Überprüfung, ob Fall zum Patienten gehört, welcher das Recht abruft
		Case pcase = new Case();
		Patient patient = new Patient();
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();
			pcase = CaseDAO.getCase(caseId);
		} catch (Exception e1) {
			throw new PersistenceException("Error 404: Database not found");
		}
		if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
			throw new AccessException("No Access to this Case!");
		}

		// Get
		List<Rights> rights = new ArrayList<Rights>();
		try {
			rights = RightsDAO.getRights(caseId);
			response.setResponseList(rights);
			response.setResponseCode("success");
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}

		return response;
	}

	/**
	 * <b>Erstellen eines Rechts</b>
	 * 
	 * Zugriffsbeschränkung: <code> Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> Token und Rights-Entity mit dem zu
	 *            erstellenden Recht <br>
	 *            Parameter : <code>int</code> rightID <br>
	 *            <code>Case</code> pcase <br>
	 *            <code>Doctor</code> doctor ODER <code>Relative</code> relative
	 *            <br>
	 *            <code>boolean</code> rRight <br>
	 *            <code>boolean</code> wRight <br>
	 * @return <code>String</code> mit Erfolgsmeldung
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public String createRight(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		Rights right = new Rights();
		String token;

		try {
			right = (Rights) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (right.getPcase() == null) {
			throw new InvalidParamException("No PatientCase found");
		}
		if (right.getDoctor() == null && right.getRelative() == null) {
			throw new InvalidParamException("Wrong Parameter Input");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		// Überprüfung, ob Fall zum Patienten gehört, welcher das Recht abruft
		Case pcase = new Case();
		Patient patient = new Patient();
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();
			pcase = right.getPcase();
		} catch (Exception e1) {
			throw new PersistenceException("Error 404: Database not found");
		}
		if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
			throw new AccessException("No Access to this Case!");
		}

		// Create
		String response = null;
		try {
			response = RightsDAO.createRight(right);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Ändern eines Rechts</b><br>
	 * 
	 * Zugriffsbeschränkung: <code> Patient</code>
	 *
	 * @param accessor
	 *            mit <code>String</code> Token und Rights-Entity des
	 *            betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public String updateRight(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		Rights right = new Rights();
		String token;

		try {
			right = (Rights) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		// Überprüfung, ob Fall zum Patienten gehört, welcher das Recht abruft
		Case pcase = new Case();
		Patient patient = new Patient();
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();
			pcase = right.getPcase();
		} catch (Exception e1) {
			throw new PersistenceException("Error 404: Database not found");
		}
		if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
			throw new AccessException("No Access to this Case!");
		}

		// Update
		String response = null;
		try {
			response = RightsDAO.updateRight(right);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * Entfernen eines Rechts <br>
	 * 
	 * Zugriffsbeschränkung: <code> Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> Token und <code>int</code> rightID des
	 *            betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */

	@Transactional
	public String deleteRight(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		Rights right = new Rights();
		String token;

		try {
			right = (Rights) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (right == null) {
			throw new InvalidParamException("No Right found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		// Überprüfung, ob Fall zum Patienten gehört, welcher das Recht abruft
		Case pcase = new Case();
		Patient patient = new Patient();
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();
			pcase = right.getPcase();
		} catch (Exception e1) {
			throw new PersistenceException("Error 404: Database not found");
		}
		if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
			throw new AccessException("No Access to this Case!");
		}

		// Delete
		int rightId;
		String response = null;
		try {
			rightId = right.getRightID();
			response = RightsDAO.removeRight(rightId);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

}
