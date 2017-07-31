package de.patientenportal.persistence;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import de.patientenportal.entities.Case;
import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.Patient;
import de.patientenpotal.ftpconnection.FtpMethodenInDocs;

public class InDocTest {
	// Kommentare sind auch für MDOC Test gültig
	@Test
	public void main() throws IOException {

		// Anlegen eines Patienten
		Patient pat = new Patient();
		pat.setBloodtype("B-");
		RegistrationDAO.createPatient(pat);

		// Anlegen eines Cases
		Case case1 = new Case("Impfpass", "erklärt sich von selbst");
		CaseDAO.createCase(case1);

		// Serverzugangsdaten mit falscher Variante, die auskommentiert ist
		String serveruser1 = new String("admin");
		String passwort1 = new String("12345");
		// String passwort2 =new String("1234");

		// Anlegen der FIles statt mit/ wurde mit FIle.separator gearbeitet um
		// auch andere System außer windows zu unterstützen
		// In diesem Fall handelt es sich um lokale Dateien diese würden sonst
		// von der Gui übergeben werden

		// Den Objekten werden noch ein paar Attributswerte gegeben
		InstructionDoc indoc1 = new InstructionDoc();
		indoc1.setTitle("MundzuMundBeatmung");
		indoc1.setFileType("docx");
		indoc1.setDescription("Eine Beschreibung ....");

		InstructionDoc indoc2 = new InstructionDoc();
		indoc2.setTitle("ErsteHilfe");
		indoc2.setDescription("Dieses Dokument ist schon einem Fall hinzugefügt");
		indoc2.setFileType("txt");

		InstructionDoc indoc3 = new InstructionDoc();
		indoc3.setTitle("Beatmung");
		indoc3.setFileType("jpg");

		// InstructiondocDAO wird augerufen und die Objekt bei Hibernate erzeugt
		// Dabei wird gleichzeitig die FtpMethodenInDocs.uploadInstructionDoc()
		// Methode aufgerufen
		String feedbackIMD1 = InstructionDocDAO.createInstructionDoc(indoc1);
		Assert.assertEquals("success", feedbackIMD1);
		String feedbackIMD2 = InstructionDocDAO.createInstructionDoc(indoc2);
		Assert.assertEquals("success", feedbackIMD2);
		String feedbackIMD3 = InstructionDocDAO.createInstructionDoc(indoc3);
		Assert.assertEquals("success", feedbackIMD3);

		// Abrufen - direkt
		InstructionDoc test = InstructionDocDAO.getInstructionDoc(indoc1.getInstructionDocID());
		Assert.assertEquals(indoc1.getInstructionDocID(), test.getInstructionDocID());
		Assert.assertEquals(indoc1.getTitle(), test.getTitle());

		// Download der Files
		FtpMethodenInDocs.downloadInstructionDoc(indoc1);
		FtpMethodenInDocs.downloadInstructionDoc(indoc2);
		FtpMethodenInDocs.downloadInstructionDoc(indoc3);

		// Anzeigen aller Files auf dem Server
		FtpMethodenInDocs.showAllFiles(serveruser1, passwort1);
	}

}
