package de.patientenportal.services;

import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.InDocResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.InstructionDocDAO;


@WebService (endpointInterface = "de.patientenportal.services.InDocWS")
public class InDocWSImpl implements InDocWS{
	
	@Transactional
	public InstructionDoc getInDoc(Accessor accessor) {

		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			InstructionDoc indoc = new InstructionDoc();
			try { indoc = InstructionDocDAO.getInstructionDoc(id);}
			catch (Exception e) { System.out.println("Error: " + e); }
		return indoc;
		
		}
	}
	
	@Transactional
	public InDocResponse getInDocssbyC(Accessor accessor) {
		InDocResponse response = new InDocResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
			List<InstructionDoc> rlist = CaseDAO.getCase(id).getIdoc();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	@Transactional
	public InDocResponse getAllInDocs(Accessor accessor) {
		InDocResponse response = new InDocResponse();
		
		int id;
		try { id = (int) accessor.getObject(); }
		catch (Exception e) { System.out.println("Invalid access"); return null; }
		
		if (id == 0) {System.out.println("Id null"); return null;}
		
		else{
			try {
				//mit while Schleife alle ids durchgehen?
			List<InstructionDoc> rlist = InstructionDocDAO.getAllIDocs();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}
	
	@Transactional
	public String updateInDoc(InstructionDoc indoc) {
		String response = InstructionDocDAO.updateInstructionDoc(indoc);
		return response;
	}	
	
	
	@Transactional
	public String deleteInDoc(int InDocID) {
		String response = InstructionDocDAO.deleteInstructionDoc(InDocID);
		return response;
	}

	@Transactional
	public String createInstructionDoc(InstructionDoc indoc) {
	
		if (indoc.getTitle()		== null) {return "Es wurde kein Titel angegeben.";}
		if (indoc.getDescription() 	== null) {return "Es wurde keine Beschreibung eingegeben";}
		
		String feedbackCP = InstructionDocDAO.createInstructionDoc(indoc);
		return feedbackCP;
		}
	
}
