package de.patientenportal.services;

import javax.transaction.Transactional;
import de.patientenportal.entities.Case;
import de.patientenportal.persistence.CaseDAO;


public class CaseWSImpl {

	@Transactional
	public static Case getCase(int caseID) throws Exception {
		return CaseDAO.getCase(caseID);
	}

}
