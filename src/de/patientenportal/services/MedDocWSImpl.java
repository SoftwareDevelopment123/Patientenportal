package de.patientenportal.services;

import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.MedicalDoc;
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

@WebService(endpointInterface = "de.patientenportal.services.MedDocWS")
public class MedDocWSImpl implements MedDocWS {

	@Transactional
	public MedicalDoc getMDoc(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException,
			AuthenticationException, AccessException, AuthorizationException {

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

		MedicalDoc mdoc = new MedicalDoc();
		try {
			mdoc = MDocDAO.getMedicalDoc(id);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return mdoc;
	}

	@Transactional
	public MDocListResponse getMDocsbyC(Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException {
		MDocListResponse response = new MDocListResponse();

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
		try {
			List<MedicalDoc> rlist = CaseDAO.getCase(id).getMedicalDocs();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public MDocListResponse getMDocsbyP(Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException {
		MDocListResponse response = new MDocListResponse();

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
		try {
			List<MedicalDoc> rlist = PatientDAO.getPatient(id).getMedicalDocs();
			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public MDocListResponse getMDocsbyD(Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException {
		MDocListResponse response = new MDocListResponse();
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
		try {
			List<MedicalDoc> rlist = MDocDAO.getMDocs(id);

			response.setResponseCode("success");
			response.setResponseList(rlist);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;
	}

	@Transactional
	public String createMedicalDoc(Accessor accessor)
			throws AccessorException, InvalidParamException, PersistenceException {
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

		String response = null;
		try {
			response = MDocDAO.createMDoc(mdoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	@Transactional
	public String updateMDoc(Accessor accessor) throws AccessorException, InvalidParamException, PersistenceException {
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
		String response = null;
		try {
			response = MDocDAO.updateMedicalDoc(mdoc);
		} catch (Exception e) {
			throw new PersistenceException("Error 404: Database not found");
		}
		return response;

	}

	@Transactional
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
	}
}
