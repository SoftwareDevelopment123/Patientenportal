package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.DoctorListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.PatientDAO;

@WebService (endpointInterface = "de.patientenportal.services.DoctorWS")
public class DoctorWSImpl implements DoctorWS {

	@Transactional
	public Doctor getDoctor(Accessor accessor) {
		int id;
		

		try {id = (int) accessor.getObject();}

		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			Doctor doctor = new Doctor();
			try { doctor = DoctorDAO.getDoctor(id); }

			catch (Exception e) {System.err.println("Error: " + e);}

		return doctor;
		}
	}

	@Transactional
	public DoctorListResponse getDoctorsByC(Accessor accessor) {
		DoctorListResponse response = new DoctorListResponse();
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}
		
		else{
			try {
			List<Doctor> rlist = CaseDAO.getCase(id).getDoctors();
				response.setResponseCode("success");
				response.setResponseList(rlist);
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}		
	}

	/*
	 * Auf diese Methode sollte nur der Patient zugreifen können (entsprechende Beschränkung noch einfügen)
	 */
	
	@Transactional
	public DoctorListResponse getDoctorsByP(Accessor accessor) {
		DoctorListResponse response = new DoctorListResponse();
		int id;
		
		try {id = (int) accessor.getObject();}
		catch (Exception e) {System.err.println("Invalid access"); return null;}
		if (id == 0) 		{System.err.println("Id null"); return null;}

		else{
			try {
			List<Case> cases = PatientDAO.getPatient(id).getCases();
			List<Doctor> pDoctors = new ArrayList<Doctor>();
						
				for (Case c : cases) {
					List <Doctor> caselist = CaseDAO.getCase(c.getCaseID()).getDoctors();
				
					for (Doctor d : caselist){
						boolean alreadyInList = false;
		
						for (Doctor dfromlist : pDoctors){
							if (d.getDoctorID() == dfromlist.getDoctorID()){alreadyInList = true;}
						}
						if (alreadyInList == false){pDoctors.add(d);}
					}		
				}
			response.setResponseCode("success");
			response.setResponseList(pDoctors);
			
			} catch (Exception e) {
				response.setResponseCode("Error: " + e);
			} return response;
		}
	}

	@Override
	public String createCase(Accessor accessor) {
		Case case1; 
		String token;
		
		
		try {
			case1 = (Case) accessor.getObject();
			token = (String) accessor.getToken();
		}
		catch (Exception e) 	{System.err.println("Invalid access");	return null;}
		if (case1 == null) 		{System.err.println("Case null");			return null;}
		if (token == null) 		{System.err.println("No token");		return null;}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		
		if (auth.authenticateToken(token) == false) {System.err.println("Invalid token"); return null;}
		// + else if authorizeToken(token) != 2 {error}
		
		try { CaseDAO.createCase(case1); }
		catch (Exception e) {System.out.println("Error: " + e);}
		return "success";
	}


}
