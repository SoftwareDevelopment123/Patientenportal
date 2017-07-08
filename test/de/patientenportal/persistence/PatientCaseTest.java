package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Patient;
import java.util.Arrays;
import java.util.List;

public class PatientCaseTest {

	@Test
	public void main(){
		
		// Patient und F�lle anlegen
		
		Patient pat = new Patient();
			pat.setBloodtype("AB+");
		RegistrationDAO.createPatient(pat);
		
		Case pcase1 = new Case("Erster Fall"	,"Wichtige Info zum ersten Fall");
		Case pcase2 = new Case("Impfpass"		,"Alle wichtigen Impfungen");
		Case pcase3 = new Case("Knochenbruch"	,"nach Verkehrsunfall");
		
			pcase1.setPatient(pat);
			pcase2.setPatient(pat);
			pcase3.setPatient(pat);
		
		String feedbackCC1 = CaseDAO.createCase(pcase1);
			Assert.assertEquals("success", feedbackCC1);
		String feedbackCC2 = CaseDAO.createCase(pcase2);
			Assert.assertEquals("success", feedbackCC2);
		String feedbackCC3 = CaseDAO.createCase(pcase3);
			Assert.assertEquals("success", feedbackCC3);
		
		// F�lle vom Patient abrufen
		List<Case> compareme = Arrays.asList(pcase1,pcase2,pcase3);
		List<Case> pcases = PatientDAO.getPatient(pat.getPatientID()).getCases();
		
		int i = 0;
		for (Case c : compareme){
			Assert.assertEquals(c.getCaseID()		, pcases.get(i).getCaseID());
			Assert.assertEquals(c.getTitle()		, pcases.get(i).getTitle());
			Assert.assertEquals(c.getDescription()	, pcases.get(i).getDescription());
			i++;
		}
			
		/*List<Case> pcases = PatientDAO.getPatient(1).getCases();
			for (Case c : pcases){
				System.out.println(c.getCaseID() + " / " + c.getTitle() + " / " + c.getDescription());
			}*/
		
		// Clearing up DB
		/*PatientDAO.deletePatient(pat.getPatientID());
		CaseDAO.deleteCase(pcase1.getCaseID());
		CaseDAO.deleteCase(pcase2.getCaseID());
		CaseDAO.deleteCase(pcase3.getCaseID());	*/	
	}
	
}
