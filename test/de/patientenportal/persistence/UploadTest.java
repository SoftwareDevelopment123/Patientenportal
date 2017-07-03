/*package de.patientenportal.persistence;

import de.patientenportal.entities.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class UploadTest {

	@Test
	public void main() throws IOException{
		
		File filetoupload = new File( "C:/Users/Jani/Desktop/Filezilla/MedicalDoc123.txt" );
		boolean res = filetoupload.exists();
		System.out.println(res);
		
				
		Doctor doc = new Doctor();
		doc.setSpecialization("ein wirklich echter Arzt");
		RegistrationDAO.createDoctor(doc);
		
		Patient pat = new Patient();
		pat.setBloodtype("AA");
		RegistrationDAO.createPatient(pat);
		
		FTPDAO.uploadmDoc(filetoupload, doc, pat);
		
		
		
		
	}
	
}*/
