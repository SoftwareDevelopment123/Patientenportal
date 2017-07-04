package de.patientenportal.persistence;

import org.junit.Test;

import java.io.File;
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
		
		File mdocDatei1 = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"armbruch.txt");
		
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
				"Notizen Schädelbasisbruch.txt");
				
		MedicalDoc mdoc1 = new MedicalDoc();
			mdoc1.setmDocTitle("armbruch");
			mdoc1.setPatient(pat);
			mdoc1.setFile(mdocDatei1);
			
		MedicalDoc mdoc2 = new MedicalDoc();
			mdoc2.setmDocTitle("beinbruch");
			mdoc2.setPatient(pat);
			mdoc2.setmDocDescription("Dieses Dokument ist schon einem Fall hinzugefügt");
			mdoc2.setPcase(case1);	// wird nicht mit eingefügt, ich weiß noch nicht warum
			mdoc2.setFile(mdocDatei2);
			
		MedicalDoc mdoc3 = new MedicalDoc();
			mdoc3.setmDocTitle("Notizen Schädelbasisbruch");
			mdoc3.setPatient(pat);
			mdoc3.setFile(mdocDatei3);
			
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
		
		//Abrufen - über den Fall
		List<MedicalDoc> casedocs = CaseDAO.getCase(1).getMedicalDocs();	
			Assert.assertEquals(1, casedocs.size());
			Assert.assertEquals(2, casedocs.get(0).getMedDocID());
		
		//Abrufen - über den Patient
		List<MedicalDoc> patdocs = PatientDAO.getPatient(1).getMedicalDocs();
			Assert.assertEquals(3, patdocs.size());
		
		FtpMethodenMDocs.uploadMDoc(mdoc1);
		FtpMethodenMDocs.downloadFile(mdoc1);
		FtpMethodenMDocs.uploadMDoc(mdoc2);
		FtpMethodenMDocs.downloadFile(mdoc2);
		FtpMethodenMDocs.uploadMDoc(mdoc3);
		FtpMethodenMDocs.downloadFile(mdoc3);
	}
	
}
