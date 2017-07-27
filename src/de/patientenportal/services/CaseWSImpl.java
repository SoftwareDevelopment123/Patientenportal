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
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.CaseWS")
public class CaseWSImpl implements CaseWS {

	/**
	 * <b>Einzelnen Fall vollst�ndig abrufen</b><br>
	 * Der Patient kann nur seine eigenen F�lle �ber diese Methode aufrufen.<br>
	 * Bei Doktoren und Verwandten wird gepr�ft, ob man beim angefragten Fall
	 * Leserechte hat.<br>
	 * 
	 * Zugriffsbeschr�nkung: <code>Patient, Doctor, Relative</code>
	 * 
	 * @param Accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>Case</code> mit dem angefragten Fall
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public Case getCase(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
			InvalidParamException, AccessorException, PersistenceException {
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient, ActiveRole.Doctor, ActiveRole.Relative);
		ActiveRole role = AuthenticationWSImpl.getActiveRole(token);

		if (role == ActiveRole.Patient) {
			AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

			Case pcase = new Case();
			Patient patient = new Patient();
			try {
				User user = AuthenticationWSImpl.getUserByToken(token);
				patient = UserDAO.getUser(user.getUserId()).getPatient();
				pcase = CaseDAO.getCase(id);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
				throw new AccessException("No Access to this Case!");
			}
			return pcase;
		}

		if (role != ActiveRole.Patient) {
			AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);

			Case pcase = new Case();
			try {
				pcase = CaseDAO.getCase(id);
			} catch (Exception e) {
				throw new PersistenceException("Error 404: Database not found");
			}
			return pcase;
		}
		return null;
	}

	/**
	 * <b>Alle F�lle eines Patienten abrufen</b><br>
	 * Methode ist f�r den Patienten gedacht, um seine F�lle einzusehen.<br>
	 * Dementsprechend wird nur das Token ben�tigt, aus dem dann die ihm
	 * zugeh�rige ID abgerufen wird.<br>
	 * 
	 * Zugriffsbeschr�nkung: <code>Patient</code>
	 * 
	 * @param Accessor
	 *            mit <code>String</code> token und <code>boolean</code> status
	 * @return <code>CaseListResponse</code> mit allen F�llen mit entsprechendem
	 *         Status
	 * @throws TokenException
	 * @throws InvalidParamException
	 * @throws AccessorException
	 * @throws PersistenceException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public CaseListResponse getCases(Accessor accessor) throws InvalidParamException, AccessorException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		Patient patient = new Patient();
		try {
			User user = AuthenticationWSImpl.getUserByToken(token);
			patient = UserDAO.getUser(user.getUserId()).getPatient();

			List<Case> caselist1 = PatientDAO.getPatient(patient.getPatientID()).getCases();
			List<Case> caselist2 = new ArrayList<Case>();
			for (Case c : caselist1) {
				caselist2.add(CaseDAO.getCase(c.getCaseID()));
			}

			response.setResponseCode("success");

			List<Case> rlist = new ArrayList<Case>();
			for (Case c : caselist2) {
				if (c.isStatus() == status) {
					rlist.add(c);
				}
			}
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Neuen Fall anlegen</b><br>
	 * Der erstellende Doktor bekommt direkt Lese- und Schreibrechte im
	 * erstellten Fall und wird der Doktorliste des Falls hinzugef�gt. Hier wird
	 * sichergestellt, dass auch mitgegebene Doktoren dem Fall hinzugef�gt
	 * werden. Diese bekommen aber nicht automatisch Lese- und
	 * Schreibrechte.<br>
	 * 
	 * Zugriffsbeschr�nkung: <code>Doctor</code>
	 *
	 * @param Accessor
	 *            mit <code>String</code> token und dem zu erstellenden
	 *            <code>Case</code>
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
	public String createCase(Accessor accessor) throws InvalidParamException, AccessorException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {
		Case pcase = new Case();
		String token;

		try {
			pcase = (Case) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (pcase.getTitle() == null) {
			throw new InvalidParamException("No Title found");
		}
		if (pcase.getPatient() == null) {
			throw new InvalidParamException("No Patient found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			User creatinguser = AuthenticationWSImpl.getUserByToken(token);
			Doctor creatingdoctor = UserDAO.getUser(creatinguser.getUserId()).getDoctor();
			List<Doctor> doctors = pcase.getDoctors();

			boolean doublecheck = false;
			for (Doctor d : doctors) {
				if (d.getDoctorID() == creatingdoctor.getDoctorID()) {
					doublecheck = true;
				}
			}
			if (doublecheck == false) {
				doctors.add(creatingdoctor);
			}

			pcase.setDoctors(doctors);
			CaseDAO.createCase(pcase);

			Rights right = new Rights(pcase, creatingdoctor, null, true, true);
			response = RightsDAO.createRight(right);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * <b>Fall l�schen</b><br>
	 * F�lle sollten eigentlich nicht gel�scht, sondern nur geschlossen
	 * werden.<br>
	 * Zur Vollst�ndigkeit ist diese Methode trotzdem vorhanden, die
	 * Berechtigung f�r den Doctor kann ggf. noch entfernt werden.
	 * 
	 * Zugriffsbeschr�nkung: <code>Doctor</code> (unter Vorbehalt) mit
	 * Schreibrecht beim Fall
	 * 
	 * @param Accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
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
	public String deleteCase(Accessor accessor) throws InvalidParamException, AccessorException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {
		int id;
		String token;

		try {
			id = accessor.getId();
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = CaseDAO.deleteCase(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	/**
	 * <b>Fall updaten</b><br>
	 * Hier ist zu beachten, dass m�glichst eine Vollst�ndige
	 * <code>Case</code>-Entity mitgegeben werden sollte.<br>
	 * 
	 * Zugriffsbeschr�nkung: <code>Doctor</code> mit Schreibrecht beim Fall
	 *
	 * @param Accessor
	 *            mit <code>String</code> token und dem zu �ndernden
	 *            <code>Case</code>
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
	public String updateCase(Accessor accessor) throws InvalidParamException, AccessorException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {
		Case pcase = new Case();
		String token;

		try {
			pcase = (Case) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (pcase == null) {
			throw new InvalidParamException("No Case found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		accessor.setObject(pcase.getCaseID());
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		String response = null;
		try {
			response = CaseDAO.updateCase(pcase);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

}