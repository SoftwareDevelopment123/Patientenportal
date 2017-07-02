package de.patientenportal.persistence;

import org.junit.Test;

import java.util.List;

import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;

public class MDocTest {

	/*
	 * 
	 * Das hier kann alles in den DocumentsTest, zusammen mit allen Dokumenten-Funktionen (sobalt das klappt)
	 * 
	 */
	
	@Test
	public void main(){
		
		// Anlegen
		Patient pat = new Patient();
			pat.setBloodtype("B-");
		RegistrationDAO.createPatient(pat);
			
		Case case1 = new Case("Impfpass","erklärt sich von selbst");
		CaseDAO.createCase(case1);
				
		MedicalDoc mdoc1 = new MedicalDoc();
			mdoc1.setmDocTitle("Dokument 1");
			mdoc1.setPatient(pat);
			
		MedicalDoc mdoc2 = new MedicalDoc();
			mdoc2.setmDocTitle("Dokument 2");
			mdoc2.setPatient(pat);
			mdoc2.setmDocDescription("Dieses Dokument ist schon einem Fall hinzugefügt");
			mdoc2.setPcase(case1);	// wird nicht mit eingefügt, ich weiß noch nicht warum
			
		MedicalDoc mdoc3 = new MedicalDoc();
			mdoc3.setmDocTitle("Dokument 3");
			mdoc3.setPatient(pat);
			
		String feedbackCMD1 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD1);
		String feedbackCMD2 = MDocDAO.createMDoc(mdoc2);
			Assert.assertEquals("success", feedbackCMD2);
		String feedbackCMD3 = MDocDAO.createMDoc(mdoc3);
			Assert.assertEquals("success", feedbackCMD3);

		//Abrufen - direkt
		MedicalDoc test = MDocDAO.getMedicalDoc(1);
			Assert.assertEquals(1, test.getMedDocID());
			Assert.assertEquals("Dokument 1", test.getmDocTitle());
		
		//Abrufen - über den Fall
		List<MedicalDoc> casedocs = CaseDAO.getCase(1).getMedicalDocs();	
			Assert.assertEquals(1, casedocs.size());
			Assert.assertEquals(2, casedocs.get(0).getMedDocID());
		
		//Abrufen - über den Patient
		List<MedicalDoc> patdocs = PatientDAO.getPatient(1).getMedicalDocs();
			Assert.assertEquals(3, patdocs.size());
		
	}
	
}
