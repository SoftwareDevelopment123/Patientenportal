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
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.DoctorWS")
public class DoctorWSImpl implements DoctorWS {

	/**
	 * <b>Doktor per ID abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: keine
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> doctorID
	 * @return <code>Doctor</code>
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws PersistenceException
	 */
	@Transactional
	public Doctor getDoctor(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
			AccessorException, InvalidParamException, PersistenceException {
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
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		Doctor doctor = new Doctor();
		try {
			doctor = DoctorDAO.getDoctor(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return doctor;
	}

	/**
	 * <b>Alle Doktoren von einem Fall abrufen</b><br>
	 * 
	 * Zugriffsbeschränkung: Doctor, Relative mit Leserecht beim betroffenen
	 * Fall<br>
	 * (Patienten können ohnehin ihre Fälle vollständig einsehen - siehe CaseWS)
	 * 
	 * @param accessor
	 *            mit <code>String</code> token und <code>int</code> caseID
	 * @return <code>DoctorListResponse</code>
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 * @throws PersistenceException
	 */
	@Transactional
	public DoctorListResponse getDoctorsByC(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
		DoctorListResponse response = new DoctorListResponse();
		int caseId;
		String token;

		try {
			caseId =  accessor.getId();
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);

		try {
			List<Doctor> rlist = CaseDAO.getCase(caseId).getDoctors();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	/**
	 * <b>Alle Doktoren eines Patienten abrufen</b><br>
	 * Über die CaseList des Patienten werden alle Doktoren abgerufen. Damit nur
	 * eigene Fälle abgerufen werden, wird der Patient direkt aus dem Token
	 * ermittelt. <br>
	 * Eine dritte for-Schleife sortiert dir finale Liste-sodass bei den
	 * Doktoren keine Dopplungen auftreten. <br>
	 * 
	 * Zugriffsbeschränkung: Patient
	 * 
	 * @param accessor
	 *            mit <code>String</code> token
	 * @return <code>DoctorListResponse</code>
	 * @throws PersistenceException
	 * @throws AccessorException
	 * @throws InvalidParamException
	 * @throws AuthorizationException
	 * @throws AccessException
	 * @throws AuthenticationException
	 */
	@Transactional
	public DoctorListResponse getDoctorsByP(Accessor accessor) throws PersistenceException, AccessorException,
			InvalidParamException, AuthenticationException, AccessException, AuthorizationException {
		DoctorListResponse response = new DoctorListResponse();
		String token;

		try {
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		User user = AuthenticationWSImpl.getUserByToken(token);
		Patient patient = UserDAO.getUser(user.getUserId()).getPatient();

		try {
			List<Case> cases = PatientDAO.getPatient(patient.getPatientID()).getCases();
			List<Doctor> pDoctors = new ArrayList<Doctor>();

			for (Case c : cases) {
				List<Doctor> caselist = CaseDAO.getCase(c.getCaseID()).getDoctors();

				for (Doctor d : caselist) {
					boolean alreadyInList = false;

					for (Doctor dfromlist : pDoctors) {
						if (d.getDoctorID() == dfromlist.getDoctorID()) {
							alreadyInList = true;
						}
					}
					if (alreadyInList == false) {
						pDoctors.add(d);
					}
				}
			}
			response.setResponseCode("success");
			response.setResponseList(pDoctors);

		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}