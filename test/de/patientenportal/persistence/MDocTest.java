package de.patientenportal.persistence;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;

public class MDocTest {

	/*
	 * 
	 * Das hier kann alles in den DocumentsTest, zusammen mit allen Dokumenten-Funktionen (sobalt das klappt)
	 * 
	 */
	
	@Test
	public void main() throws IOException{
		
		// Anlegen
		Patient pat = new Patient();
			pat.setBloodtype("B-");
		RegistrationDAO.createPatient(pat);
			
		Case case1 = new Case("Impfpass","erkl�rt sich von selbst");
		CaseDAO.createCase(case1);
		
		String serveruser1 = new String("admin");
		String passwort1 = new String("12345");
		//String passwort2 =new String("1234");
		
		File mdocDatei1 = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"armbruch.docx");
		
		File mdocDatei2 = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"beinbruch.txt");
		File mdocDatei3 = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"tumor.jpg");
				
		MedicalDoc mdoc1 = new MedicalDoc();
			mdoc1.setmDocTitle("armbruch");
			mdoc1.setPatient(pat);
			mdoc1.setFile(mdocDatei1);
			mdoc1.setFileType(".docx");
			
		MedicalDoc mdoc2 = new MedicalDoc();
			mdoc2.setmDocTitle("beinbruch");
			mdoc2.setPatient(pat);
			mdoc2.setmDocDescription("Dieses Dokument ist schon einem Fall hinzugef�gt");
			mdoc2.setPcase(case1);	// wird nicht mit eingef�gt, ich wei� noch nicht warum
			mdoc2.setFile(mdocDatei2);
			mdoc2.setFileType(".txt");
			
		MedicalDoc mdoc3 = new MedicalDoc();
			mdoc3.setmDocTitle("tumor");
			mdoc3.setPatient(pat);
			mdoc3.setFile(mdocDatei3);
			mdoc3.setFileType(".jpg");
			
		String feedbackCMD1 = MDocDAO.createMDoc(mdoc1);
			Assert.assertEquals("success", feedbackCMD1);
		String feedbackCMD2 = MDocDAO.createMDoc(mdoc2);
			Assert.assertEquals("success", feedbackCMD2);
		String feedbackCMD3 = MDocDAO.createMDoc(mdoc3);
			Assert.assertEquals("success", feedbackCMD3);

		//Abrufen - direkt
		MedicalDoc test = MDocDAO.getMedicalDoc(1);
			Assert.assertEquals(1, test.getMedDocID());
			Assert.assertEquals("armbruch", test.getmDocTitle());
		
		//Abrufen - �ber den Fall
		List<MedicalDoc> casedocs = CaseDAO.getCase(1).getMedicalDocs();	
			Assert.assertEquals(1, casedocs.size());
			Assert.assertEquals(2, casedocs.get(0).getMedDocID());
		
		//Abrufen - �ber den Patient
		List<MedicalDoc> patdocs = PatientDAO.getPatient(1).getMedicalDocs();
			Assert.assertEquals(3, patdocs.size());
		
		//Upload der Files
		FtpMethodenMDocs.uploadMDoc(mdoc1);
		FtpMethodenMDocs.uploadMDoc(mdoc2);
		FtpMethodenMDocs.uploadMDoc(mdoc3);
		
		//Download der Files
		FtpMethodenMDocs.downloadFile(mdoc1);
		FtpMethodenMDocs.downloadFile(mdoc2);
		FtpMethodenMDocs.downloadFile(mdoc3);
		
		//Anzeigen der Files auf dem Server
		FtpMethodenMDocs.showAllFiles(serveruser1, passwort1);
	}
	
}
