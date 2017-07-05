package de.patientenportal.persistence;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenInDocs;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;

public class InDocTest {

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
			
			File indocDatei1 = new File(
					"C:" 				+File.separator+
					"Users" 			+File.separator+ 
					"Jani"				+File.separator+
					"Desktop"			+File.separator+
					"Filezilla"			+File.separator+
					"Upload"			+File.separator+
					"MundzuMundBeatmung.docx");
			
			File indocDatei2 = new File(
					"C:" 				+File.separator+
					"Users" 			+File.separator+ 
					"Jani"				+File.separator+
					"Desktop"			+File.separator+
					"Filezilla"			+File.separator+
					"Upload"			+File.separator+
					"ErsteHilfe.txt");
			File indocDatei3 = new File(
					"C:" 				+File.separator+
					"Users" 			+File.separator+ 
					"Jani"				+File.separator+
					"Desktop"			+File.separator+
					"Filezilla"			+File.separator+
					"Upload"			+File.separator+
					"MundzuMund.jpg");
					
			InstructionDoc indoc1 = new InstructionDoc();
				indoc1.setTitle("Mund Beatmung");
				indoc1.setFile(indocDatei1);
				indoc1.setFileType("docx");
				indoc1.setDescription("Eine Beschreibung ....");
				
			InstructionDoc indoc2 = new InstructionDoc();
				indoc2.setTitle("Erste Hilfe");
				indoc2.setDescription("Dieses Dokument ist schon einem Fall hinzugefügt");
				indoc2.setFile(indocDatei2);
				indoc2.setFileType("txt");
				
			InstructionDoc indoc3 = new InstructionDoc();
				indoc3.setTitle("Beatmung Bild");
				indoc3.setFile(indocDatei3);
				indoc3.setFileType("jpg");
				
			String feedbackCMD1 = InstructionDocDAO.createInstructionDoc(indoc1);
				Assert.assertEquals("success", feedbackCMD1);
			String feedbackCMD2 = InstructionDocDAO.createInstructionDoc(indoc2);
				Assert.assertEquals("success", feedbackCMD2);
			String feedbackCMD3 = InstructionDocDAO.createInstructionDoc(indoc3);
				Assert.assertEquals("success", feedbackCMD3);

			//Abrufen - direkt
			InstructionDoc test = InstructionDocDAO.getInstructionDoc(1);
				Assert.assertEquals(1, test.getInstructionDocID());
				Assert.assertEquals("Mund Beatmung", test.getTitle());
			
			//Upload der Files
			FtpMethodenInDocs.uploadInstructionDoc(indoc1);
			FtpMethodenInDocs.uploadInstructionDoc(indoc2);
			FtpMethodenInDocs.uploadInstructionDoc(indoc3);
			
			//Download der Files
			FtpMethodenInDocs.downloadInstructionDoc(indoc1);
			FtpMethodenInDocs.downloadInstructionDoc(indoc2);
			FtpMethodenInDocs.downloadInstructionDoc(indoc3);
			
			//Anzeigen der Files auf dem Server
			FtpMethodenMDocs.showAllFiles(serveruser1, passwort1);
		}
	
	}


