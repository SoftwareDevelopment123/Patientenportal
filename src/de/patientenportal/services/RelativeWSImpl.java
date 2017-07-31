package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

@WebService(endpointInterface = "de.patientenportal.services.RelativeWS")
public class RelativeWSImpl implements RelativeWS {

	/**
	 * <b>Einen Verwandten zu einem Patienten abrufen</b><br>
	 * Diese Methode wird im üblichen Use-Case wahrscheinlich nicht benötigt. Je
	 * nach Nutzung sollte die Methode auskommentiert oder angepasst werden, um
	 * nicht authorisierten Zugriff auf die Relatives zu vermeiden.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Patient</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> relativID
	 * @return <code>Relative</code>
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public Relative getRelative(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);
		Relative relative = new Relative();
		try {
			relative = RelativeDAO.getRelative(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return relative;
	}

	/**
	 * <b>Alle Verwandten zu einem Patienten abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: Doctor, Patient<br>
	 * (Patienten können ohnehin ihre Fälle vollständig einsehen - siehe CaseWS)
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> patientID
	 * @return <code>RelativeListResponse</code> mit allen Verwandten und
	 *         Erfolgsmeldung.
	 * @throws PersistenceException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public RelativeListResponse getRelativesByP(Accessor accessor) throws PersistenceException, AccessorException,
			InvalidParamException, AuthenticationException, AccessException, AuthorizationException {
		RelativeListResponse response = new RelativeListResponse();
		int id;
		String token;

		try {
			id = (int) accessor.getObject();
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		try {
			List<Relative> rlist = PatientDAO.getPatient(id).getRelatives();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}