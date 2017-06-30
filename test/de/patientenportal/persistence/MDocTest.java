package de.patientenportal.persistence;

import org.junit.Test;

import java.util.List;

import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;

public class MDocTest {

	@Test
	public void main(){
		
		// Anlegen
		Patient pat = new Patient();
			pat.setBloodtype("B-");
		RegistrationDAO.createPatient(pat);
			
		Case case1 = new Case("Impfpass","erkl�rt sich von selbst");
		CaseDAO.createCase(case1);
				
		MedicalDoc mdoc1 = new MedicalDoc();
			mdoc1.setTitle("Dokument1");
			mdoc1.setPatient(pat);
			System.out.println(mdoc1.getTitle());
			
		MedicalDoc mdoc2 = new MedicalDoc();
			mdoc2.setTitle("Dokument 2");
			mdoc2.setPatient(pat);
			mdoc2.setDescription("Dieses Dokument ist schon einem Fall hinzugef�gt");
			mdoc2.setPcase(case1);	// wird nicht mit eingef�gt, ich wei� noch nicht warum
			System.out.println(mdoc2.getTitle());
			
		MedicalDoc mdoc3 = new MedicalDoc();
			mdoc3.setTitle("Dokument 3");
			mdoc2.setPatient(pat);
			
		String feedbackCMD1 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD1);
		String feedbackCMD2 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD2);
		String feedbackCMD3 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD3);

		//Abrufen - direkt
		MedicalDoc test = MDocDAO.getMedicalDoc(1);
			Assert.assertEquals(1, test.getMedDocID());
			//Assert.assertEquals("Dokument 1", test.getTitle());			// geht noch nicht, erst Superklasse-Hibernate-Problem l�sen
		
		//Abrufen - �ber den Fall
		List<MedicalDoc> casedocs = CaseDAO.getCase(1).getMedicalDocs();	
			Assert.assertEquals(1, casedocs.size());
			Assert.assertEquals(2, casedocs.get(0).getMedDocID());
		
		//Abrufen - �ber den Patient
		List<MedicalDoc> patdocs = PatientDAO.getPatient(1).getMedicalDocs();
			Assert.assertEquals(3, patdocs.size());
		
	}
	
	
	
}
