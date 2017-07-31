package de.patientenportal.services;

import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.InDocListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.InstructionDocDAO;
import de.patientenportal.persistence.UserDAO;

@WebService(endpointInterface = "de.patientenportal.services.InDocWS")
public class InDocWSImpl implements InDocWS {

	@Transactional
	public InstructionDoc getInDoc(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {

		String token;
		int id;
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
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		InstructionDoc indoc = new InstructionDoc();
		try {

			indoc = InstructionDocDAO.getInstructionDoc(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return indoc;

	}

	@Transactional
	public InDocListResponse getInDocssbyC(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		InDocListResponse response = new InDocListResponse();

		String token;
		int id;
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

		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient) {

			User user = AuthenticationWSImpl.getUserByToken(token);
			Patient patient = UserDAO.getUser(user.getUserId()).getPatient();
			Case pcase = CaseDAO.getCase(id);

			if (pcase.getPatient().getPatientID() != patient.getPatientID()) {
				throw new AccessException("Access denied");
			}
		}

		try {
			List<InstructionDoc> rlist = CaseDAO.getCase(id).getIdoc();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public InDocListResponse getAllInDocs(Accessor accessor) throws AccessorException, InvalidParamException,
			PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		InDocListResponse response = new InDocListResponse();
		String token;
		int id;
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
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		try {

			List<InstructionDoc> rlist = InstructionDocDAO.getAllIDocs();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public String updateInDoc(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, AccessorException, InvalidParamException, PersistenceException {
		InstructionDoc indoc = new InstructionDoc();
		String token;
		try {
			indoc = (InstructionDoc) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (indoc.getTitle() == null) {
			throw new InvalidParamException("No Title found");
		}
		if (indoc.getFileType() == null) {
			throw new InvalidParamException("No Filetyp found");
		}
		if (indoc.getInstructionType() == null) {
			throw new InvalidParamException("No Instructiontyp found");
		}

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = InstructionDocDAO.updateInstructionDoc(indoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public String deleteInDoc(Accessor accessor) throws AccessorException, InvalidParamException,
			AuthenticationException, AccessException, AuthorizationException, PersistenceException {
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

		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = InstructionDocDAO.deleteInstructionDoc(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public String createInstructionDoc(Accessor accessor) throws AuthenticationException, AccessException,
			AuthorizationException, InvalidParamException, AccessorException, PersistenceException {
		InstructionDoc indoc = new InstructionDoc();
		String token;
		try {
			indoc = (InstructionDoc) accessor.getObject();
			token = (String) accessor.getToken();
		} catch (Exception e) {
			throw new AccessorException("Incorrect Accessor");
		}
		if (token == null) {
			throw new InvalidParamException("No Token found");
		}
		if (indoc.getTitle() == null) {
			throw new InvalidParamException("No Title found");
		}
		if (indoc.getFileType() == null) {
			throw new InvalidParamException("No Filetyp found");
		}
		if (indoc.getInstructionType() == null) {
			throw new InvalidParamException("No Instructiontyp found");
		}
		List<ActiveRole> accesslist = Arrays.asList(ActiveRole.Doctor);
		AuthenticationWSImpl.tokenRoleAccessCheck(accessor, accesslist, Access.Default);

		String response = null;
		try {
			response = InstructionDocDAO.createInstructionDoc(indoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}
}