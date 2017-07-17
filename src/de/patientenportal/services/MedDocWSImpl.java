package de.patientenportal.services;

import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MDocListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MDocDAO;
import de.patientenportal.persistence.PatientDAO;

@WebService (endpointInterface = "de.patientenportal.services.MedDocWS")
public class MedDocWSImpl implements MedDocWS{
	
	@Transactional
	public MedicalDoc getMDoc(Accessor accessor) {

		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			MedicalDoc mdoc = new MedicalDoc();
			try { mdoc = MDocDAO.getMedicalDoc(id);}
			catch (Exception e) { System.out.println("Error: " + e); }
		return mdoc;
		}
	}
	
	@Transactional
	public MDocListResponse getMDocsbyC(Accessor accessor) {
		MDocListResponse response = new MDocListResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
			List<MedicalDoc> rlist = CaseDAO.getCase(id).getMedicalDocs();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	@Transactional
	public MDocListResponse getMDocsbyP(Accessor accessor) {
		MDocListResponse response = new MDocListResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
			List<MedicalDoc> rlist = PatientDAO.getPatient(id).getMedicalDocs();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	@Transactional
	public MDocListResponse getMDocsbyD(Accessor accessor) {
		MDocListResponse response = new MDocListResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
			List<MedicalDoc> rlist = MDocDAO.getMDocs(id);
						
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	@Transactional
	public String createMedicalDoc(MedicalDoc mdoc) {
	
		if (mdoc.getmDocTitle()	== null) {return "Es wurde kein Titel angegeben.";}
		if (mdoc.getmDocDescription()== null){return "Es wurde keine Beschreibung eingegeben";}
		
		String feedbackCP = MDocDAO.createMDoc(mdoc);
		return feedbackCP;
		}
	
	@Transactional
	public String updateMDoc(MedicalDoc mdoc) {
		String response = MDocDAO.updateMedicalDoc(mdoc);
		return response;
	}	
	
	
	@Transactional
	public String deleteMDoc(int MDocID) {
		String response = MDocDAO.deleteMedicalDoc(MDocID);
		return response;
	}
	
	
	}



