package de.patientenportal.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.RightsDAO;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.AccessWS")
public class AccessWSImpl implements AccessWS {

	@Transactional
	public CaseListResponse getRCases(Accessor accessor) {
		CaseListResponse response = new CaseListResponse();
		String token;
		boolean status;
		
		try {
			token = (String) accessor.getToken();
			status = (boolean) accessor.getObject();
		}
		catch (Exception e) {System.err.println("Invalid access"); 	return null;}
		if (token == null) 	{System.err.println("No token");		return null;}
		
		AuthenticationWSImpl auth = new AuthenticationWSImpl();
		if (auth.authenticateToken(token) == false){
							System.err.println("Invalid token"); 	return null;}
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Patient){
			response.setResponseCode("Wrong Web-Service!");
			return response;
		}
		
		User user = auth.getUserByToken(token);
		List<Case> caselist = new ArrayList<Case>();
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Doctor){
			Doctor doctor = UserDAO.getUser(user.getUserId()).getDoctor();
			caselist = RightsDAO.getDocRCases(doctor.getDoctorID());
		}
		
		if (AuthenticationWSImpl.getActiveRole(token) == ActiveRole.Relative){
			Relative relative = UserDAO.getUser(user.getUserId()).getRelative();
			caselist = RightsDAO.getRelRCases(relative.getRelativeID());
		}

		List <Case> rlist = new ArrayList<Case>();
		for (Case c : caselist) {
			if (c.isStatus() == status) {
				rlist.add(c);
			}
		}
		response.setResponseCode("success");
		response.setResponseList(rlist);
		return response;
	}

	@Transactional
	public CaseListResponse getRPatientCases(Accessor accessor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public boolean checkWRight(Accessor accessor) {
		// TODO Auto-generated method stub
		return false;
	}

}
