package de.patientenportal.persistence;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;

public class MDocTest {

	//Audführliche Kommentar in InDocTest
	@Test
	public void main() throws IOException{
		
		// Anlegen
		Patient pat = new Patient();
			pat.setBloodtype("B-");
		RegistrationDAO.createPatient(pat);
			
		Case case1 = new Case("Impfpass","erklärt sich von selbst");
		CaseDAO.createCase(case1);
		
		String serveruser1 = new String("admin");
		String passwort1 = new String("12345");
		//String passwort2 =new String("1234");
				
		MedicalDoc mdoc1 = new MedicalDoc();
			mdoc1.setmDocTitle("armbruch");
			mdoc1.setPatient(pat);
			mdoc1.setFileType("docx");
			
		MedicalDoc mdoc2 = new MedicalDoc();
			mdoc2.setmDocTitle("beinbruch");
			mdoc2.setPatient(pat);
			mdoc2.setmDocDescription("Dieses Dokument ist schon einem Fall hinzugefügt");
			mdoc2.setPcase(case1);	// wird nicht mit eingefügt, ich weiß noch nicht warum
			mdoc2.setFileType("txt");
			
		MedicalDoc mdoc3 = new MedicalDoc();
			mdoc3.setmDocTitle("tumor");
			mdoc3.setPatient(pat);
			mdoc3.setFileType("jpg");
			
		String feedbackCMD1 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD1);
		String feedbackCMD2 = MDocDAO.createMDoc(mdoc2);
			Assert.assertEquals("success", feedbackCMD2);
		String feedbackCMD3 = MDocDAO.createMDoc(mdoc3);
			Assert.assertEquals("success", feedbackCMD3);

		//Abrufen - direkt
		MedicalDoc test = MDocDAO.getMedicalDoc(mdoc1.getMedDocID());
			Assert.assertEquals(mdoc1.getMedDocID(), test.getMedDocID());
			Assert.assertEquals(mdoc1.getmDocTitle(), test.getmDocTitle());
		
		//Abrufen - über den Fall
		List<MedicalDoc> casedocs = CaseDAO.getCase(1).getMedicalDocs();	
			Assert.assertEquals(1, casedocs.size());
			Assert.assertEquals(2, casedocs.get(0).getMedDocID());
		
		//Abrufen - über den Patient
		List<MedicalDoc> patdocs = PatientDAO.getPatient(1).getMedicalDocs();
			Assert.assertEquals(3, patdocs.size());
		
		//Upload der Files geschieht nun direkt bei create kann ausgelagert werden falls nötig
		//Download der Files
		FtpMethodenMDocs.downloadFile(mdoc1);
		FtpMethodenMDocs.downloadFile(mdoc2);
		FtpMethodenMDocs.downloadFile(mdoc3);
		
		//Anzeigen der Files auf dem Server
		FtpMethodenMDocs.showAllFiles(serveruser1, passwort1);
	}
	
}
