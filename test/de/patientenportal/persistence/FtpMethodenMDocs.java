package de.patientenportal.persistence;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


import de.patientenportal.entities.MedicalDoc;
import de.patientenpotal.ftpconnection.FtpMethoden;

public class FtpMethodenMDocs {
	

		public static void uploadMDoc(MedicalDoc mdoc)
		//in Beta muss Logik eingeführt werden die zwei gleiche Dateinamen zulässt oder Ids vergibt
		//über mdoc file und id geben dann setid name und get name den Namen in Hibernate speichern
		{
			String server = "127.0.0.1";
	        int port = 21;
	        String user = "admin";
	        String pass = "12345";
	        File filetoupload = mdoc.getFile();
	 
	        FTPClient ftpClient = new FTPClient();
	        try {
	 
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	 
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	            //File firstLocalFile = new File("C:/Users/Jani/Desktop/Filezilla/DasDokument.txt");
	 
	            String firstRemoteFile = mdoc.getMedDocID()+".txt";
	            InputStream inputStream = new FileInputStream(filetoupload);
	 
	            System.out.println("Start uploading "+mdoc.getmDocTitle());
	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
	            inputStream.close();
	            if (done) {
	                System.out.println("The Medicaldocument : "+mdoc.getmDocTitle()+" is uploaded successfully.");
	            }
	        } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                    
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
		}
		
		public static void downloadFile(MedicalDoc mdoctodownload) {
			//String nameOfFile, File downloadtofile
			String remoteFile1 = File.separator+mdoctodownload.getmDocTitle();
			String server = "127.0.0.1";
	        int port = 21;
	        String user = "admin";
	        String pass = "12345";
	        File downloadFile = new File(
					"C:" 				+File.separator+
					"Users" 			+File.separator+ 
					"Jani"				+File.separator+
					"Desktop"			+File.separator+
					"Filezilla"			+File.separator+
					"Download"			+File.separator+
					mdoctodownload.getmDocTitle()+".txt");
	 
	        FTPClient ftpClient = new FTPClient();
	        try {
	        	
	        	System.out.println("Starting Download of the Medicaldocument"+ mdoctodownload.getmDocTitle());
	        	
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	 
	            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile));
	            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
	            outputStream1.close();
	 
	            if (success) {
	                System.out.println("File #1 has been downloaded successfully.");
	            }
	        } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	        

}


