package de.patientenportal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.RightsDAO;

@WebService(endpointInterface = "de.patientenportal.services.RightsWS")
public class RightsWSImpl implements RightsWS {

	/**
	 * <b>Alle Rechte zu einem Fall abrufen</b><br>
	 * Über das Token und die caseID wird das Schreibrecht abgefragt. Dient nur
	 * zur Anzeige der Information.<br>
	 * Die Schreibrechtprüfung findet bei den entsprechenden Methoden
	 * individuell statt.
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>RightsListResponse</code> mit Liste der Rechte und
	 *         Erfolgsmeldung oder Fehlermeldung.
	 */
	@Transactional
	public RightsListResponse getRights(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
	AccessorException, InvalidParamException, PersistenceException {
		RightsListResponse response = new RightsListResponse();
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
			throw new InvalidParamException("No ID found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		// TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das
		// Recht abruft

		List<Rights> rights = new ArrayList<Rights>();
		try {
			rights = RightsDAO.getRights(id);
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
	 * @param accessor
	 *            mit <code>String</code> Token und Rights-Entity mit dem zu
	 *            erstellenden Recht <br>
	 *            Parameter : <code>int</code> rightID <br>
	 *            <code>Case</code> pcase <br>
	 *            <code>Doctor</code> doctor ODER <code>Relative</code> relative
	 *            <br>
	 *            <code>boolean</code> rRight <br>
	 *            <code>boolean</code> wRight <br>
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String createRight(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
	AccessorException, InvalidParamException, PersistenceException {
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
			return "Bitte einen Patientencase mit angeben.";
		}
		if (right.getDoctor() == null && right.getRelative() == null) {
			return "Bitte geben Sie an für wen das Recht erteilt werden soll";
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		// TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das
		// Recht anlegt
		/*
		 * CaseDAO.getCase(right.getPcase.getCaseID).getPatient.getPatientID !=
		 * UserbyToken --> Patient.getPatientID --> AccessException
		 * 
		 */

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
	 * @param accessor
	 *            mit <code>String</code> Token und Rights-Entity des
	 *            betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String updateRight(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
	AccessorException, InvalidParamException, PersistenceException {
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
		// TODO Sicherstellen, dass der Fall zum Patienten gehört, welcher das
		// Recht ändert
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
	 * @param accessor
	 *            mit <code>String</code> Token und <code>int</code> rightID des
	 *            betroffenen Rechts
	 * @return <code>String</code> mit Erfolgsmeldung oder Fehler
	 */
	@Transactional
	public String deleteRight(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
	AccessorException, InvalidParamException, PersistenceException {
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = RightsDAO.removeRight(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

}
