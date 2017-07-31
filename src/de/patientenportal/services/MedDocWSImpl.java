package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MDocListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MDocDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.MedDocWS")
public class MedDocWSImpl implements MedDocWS {

	@Transactional
	public MedicalDoc getMDoc(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {

		int id;
		String token;
		try {
			id = (int) accessor.getId();
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

		MedicalDoc mdoc = new MedicalDoc();
		try {
			mdoc = MDocDAO.getMedicalDoc(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}

		int idpat = mdoc.getPatient().getPatientID();

		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient) {

			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();

			if (idpat != patient.getPatientID()) {
				throw new AccessException("Access denied");
			}
		}

		int caseid = mdoc.getPcase().getCaseID();
		accessor.setId(caseid);

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);

		return mdoc;
	}

	@Transactional
	public MDocListResponse getMDocsbyC(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MDocListResponse response = new MDocListResponse();

		int id;
		String token;
		try {
			id = (int) accessor.getId();
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient, ActiveRole.Relative);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.ReadCase);

		Case pcase = new Case();
		try {
			pcase = CaseDAO.getCase(id);
			List<MedicalDoc> rlist = pcase.getMedicalDocs();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}

		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient) {

			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();

			if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
				throw new AccessException("Access denied");
			}
		}
		return response;
	}

	@Transactional
	public MDocListResponse getMDocsbyP(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MDocListResponse response = new MDocListResponse();

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
			List<MedicalDoc> rlist = PatientDAO.getPatient(patient.getPatientID()).getMedicalDocs();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public MDocListResponse getMDocsbyD(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MDocListResponse response = new MDocListResponse();
		String token;
		try {
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		User user = AuthenticationWSImpl.getUserByToken(token);
		Doctor doc = UserDAO.getUser(user.getUserId()).getDoctor();

		try {
			List<MedicalDoc> rlist = MDocDAO.getMDocs(doc.getDoctorID());

			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public String createMedicalDoc(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AccessException, AuthenticationException, AuthorizationException {
		MedicalDoc mdoc = new MedicalDoc();
		String token;
		try {
			mdoc = (MedicalDoc) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}

		if (mdoc.getFileType() == null) {
			throw new InvalidParamException("No FileTyp found");
		}
		if (mdoc.getmDocTitle() == null) {
			throw new InvalidParamException("No Title found");
		}
		if (mdoc.getPatient() == null) {
			throw new InvalidParamException("No Patient found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		ActiveRole activ = AuthenticationWSImpl.getActiveRole(token);
		int caseid = 0;
		if (activ == ActiveRole.Doctor) {
			if (mdoc.getCreatedBy() == null) {
				throw new InvalidParamException("Please say, who is the creator of this document");
			}
			if (mdoc.getPcase() == null) {
				throw new InvalidParamException("Please say, to which Case this document is assigned");
			}
			caseid = mdoc.getPcase().getCaseID();
		}

		accessor.setId(caseid);

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);

		int idpat = mdoc.getPatient().getPatientID();
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient) {

			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();

			if (idpat != patient.getPatientID()) {
				throw new AccessException("Access denied");
			}
		}

		String response = null;
		try {
			response = MDocDAO.createMDoc(mdoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	@Transactional
	public String updateMDoc(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		MedicalDoc mdoc = new MedicalDoc();
		String token;

		try {
			mdoc = (MedicalDoc) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (mdoc.getCreatedBy() == null) {
			throw new InvalidParamException("Please say, who is the creator of this document");
		}
		if (mdoc.getFileType() == null) {
			throw new InvalidParamException("No FileTyp found");
		}
		if (mdoc.getmDocTitle() == null) {
			throw new InvalidParamException("No Title found");
		}
		if (mdoc.getPatient() == null) {
			throw new InvalidParamException("No Patient found");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		
		ActiveRole activ = AuthenticationWSImpl.getActiveRole(token);
		int caseid = 0;
		if (activ == ActiveRole.Doctor) {
			if (mdoc.getCreatedBy() == null) {
				throw new InvalidParamException("Please say, who is the creator of this document");
			}
			if (mdoc.getPcase() == null) {
				throw new InvalidParamException("Please say, to which Case this document is assigned");
			}
			caseid = mdoc.getPcase().getCaseID();
		}

		accessor.setId(caseid);

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor, ActiveRole.Patient);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.WriteCase);
		
		int idpat = mdoc.getPatient().getPatientID();
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient) {

			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();

			if (idpat != patient.getPatientID()) {
				throw new AccessException("Access denied");
			}
		}
		
		String response = null;
		try {
			response = MDocDAO.updateMedicalDoc(mdoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	//DElete Methode muss durch ZUgriffscheck noch gesichert werden da aber Löschen von Dateien eh ein heikles Thema ist haben wir die Methode erstmal auskommentiert
	/*@Transactional
	public String deleteMDoc(Accessor accessor) throws AuthenticationException, AccessException, AuthorizationException,
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

		
		
		String response = null;
		try {
			response = MDocDAO.deleteMedicalDoc(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}*/
}
