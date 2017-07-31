package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService(endpointInterface = "de.patientenportal.services.PatientWS")
public class PatientWSImpl implements PatientWS {

	// TODO Überprüfen ob Relative auch wirklich Verwandter des Patienten ist?
	// Methode überaupt notwendig?
	/**
	 * <b>Patienten abrufen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> patientID
	 * @return patient
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public Patient getPatient(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
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
			throw new InvalidParamException("No PatientID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		Patient patient = new Patient();
		try {
			patient = PatientDAO.getPatient(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return patient;

	}

	/**
	 * <b>Alle zu einem Verwandten zugeordneten Patienten abrufen</b><br>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token
	 * @return <code>PatientListResponse</code> mit den dem Verwandten
	 *         zugeordneten Patienten.
	 */
	@Transactional
	public PatientListResponse getPatientsByR(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
	AccessorException, InvalidParamException, PersistenceException {
		PatientListResponse response = new PatientListResponse();
		int relativeId;
		String token;

		try {
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		try {
			relativeId = AuthenticationWSImpl.getUserByToken(token).getRelative().getRelativeID();

			List<Patient> rlist = RelativeDAO.getRelative(relativeId).getPatients();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}
}
