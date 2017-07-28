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
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.AccessWS")
public class AccessWSImpl implements AccessWS {

	/**
	 * <b>Alle einsehbaren Fälle abrufen</b><br>
	 * Über das Token werden zu, ensprechenden user die Leserechte abgefragt und
	 * die einsehbaren Fälle angezeigt.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>boolean</code> status
	 * @return <code>CaseListResponse</code> mit allen Fällen mit entsprechendem
	 *         Status
	 * @throws PersistenceException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public CaseListResponse getRCases(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {
		CaseListResponse response = new CaseListResponse();
		String token;
		boolean status = true;

		try {
			token = (String) accessor.getToken();
			status = (boolean) accessor.getObject();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		User user = AuthenticationWSImpl.getUserByToken(token);
		List<Case> caselist = new ArrayList<Case>();

		try {
			if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Doctor) {
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				caselist = RightsDAO.getDocRCases(doctor.getDoctorID());
			}

			if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Relative) {
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				caselist = RightsDAO.getRelRCases(relative.getRelativeID());
			}
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}

		List<Case> rlist = new ArrayList<Case>();
		for (Case c : caselist) {
			if (c.isStatus() == status) {
				rlist.add(c);
			}
		}
		response.setResponseCode("success");
		response.setResponseList(rlist);
		return response;
	}

	/**
	 * <b>Alle einsehbaren Fälle zu einem Patienten abrufen</b><br>
	 * Über das Token werden zu, ensprechenden user die Leserechte abgefragt und
	 * die einsehbaren Fälle angezeigt.<br>
	 * Dann werden nach der patientID die entsprechenden Fälle
	 * herausgefiltert.<br>
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> patientID
	 * @return <code>CaseListResponse</code> mit allen Fällen mit entsprechendem
	 *         Status
	 * @throws PersistenceException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public CaseListResponse getRPatientCases(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {
		CaseListResponse response = new CaseListResponse();
		String token;
		int id;

		try {
			token = (String) accessor.getToken();
			id = accessor.getId();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (id == 0) {
			throw new InvalidParamException("No ID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		User user = AuthenticationWSImpl.getUserByToken(token);
		List<Case> caselist = new ArrayList<Case>();

		try {
			if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Doctor) {
				Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
				caselist = RightsDAO.getDocRCases(doctor.getDoctorID());
			}

			if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Relative) {
				Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
				caselist = RightsDAO.getRelRCases(relative.getRelativeID());
			}
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}

		List<Case> rlist = new ArrayList<Case>();
		for (Case c : caselist) {
			if (c.getPatient().getPatientID() == id) {
				rlist.add(c);
			}
		}
		response.setResponseCode("success");
		response.setResponseList(rlist);
		return response;
	}

	/**
	 * <b>Schreibrecht bei einem Fall abrufen</b><br>
	 * Über das Token und die caseID wird das Schreibrecht abgefragt. Dient nur
	 * zur Anzeige der Information.<br>
	 * Die Schreibrechtprüfung findet bei den entsprechenden Methoden
	 * individuell statt.
	 * 
	 * Zugriffsbeschränkung: <code>Doctor, Relative</code>
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>boolean</code>
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public boolean checkWRight(Accessor accessor)
			throws AuthenticationException, AccessException, AuthorizationException, InvalidParamException, AccessorException {
		String token;
		int id;

		try {
			token = (String) accessor.getToken();
			id = accessor.getId();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (id == 0) {
			throw new InvalidParamException("No caseID found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		return true;
	}
}
