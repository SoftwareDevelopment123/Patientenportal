package de.patientenportal.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.MedicalDoc;
import de.patientenportal.entities.Patient;

public class FTPDAO {

	//Datei upload
	public static String uploadmDoc(File file, Doctor doctor, Patient patient) throws IOException{
	
		MedicalDoc mdoc = new MedicalDoc();
			mdoc.setFile(file);
			String titleDatei = file.getName();
			mdoc.setmDocTitle(titleDatei);
			mdoc.setPatient(patient);
			mdoc.setCreatedBy(doctor);
			
			//anlegen bei Hibernate
			Session session = HibernateUtil.getSessionFactory().openSession();

			try {
				session.beginTransaction();
				session.save(mdoc);
				session.getTransaction().commit();
					
				
				
	
	//anlegen auf Deitserver

				URL url = new URL("ftp://admin:12345@127.0.0.1/");
				
				//<protokoll><benutzername>:<passwort>@<hostname>[/verzeichnis/] 
				int idmdoc = mdoc.getMedDocID();
				Integer meinInteger = new Integer(idmdoc);
				String idnamestring = meinInteger.toString();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		        bw.write("test");
		        bw.close();
		 
				boolean ress = file.renameTo(new File(url+idnamestring));
		        System.out.println(ress);
				
				} catch (MalformedURLException e1) {
				e1.printStackTrace();
				} catch (IOException e3) {
				e3.printStackTrace();
				}
				catch (PropertyValueException e2) {
				System.err.println("Error: " + e2);
				return "NotNullError";
				} catch (Exception e4) {
				System.err.println("Error: " + e4);
				return "error";
				} finally {
				session.close();
				}
			return "success";
	
	
	
	
	/*
	// Dateien die da sind anzeigen lassen
	//File[] FileList = new File( "C:/Users/Jani/Desktop/Filezilla/Dateien/" ).listFiles();
	//FileList.getName();
	private String myid;
	
	//neues File anlegen
	
	
	//File löschen
	File file = new File( "C:/Users/Jani/Desktop/Filezilla/Dateien/" );*/
}
}
