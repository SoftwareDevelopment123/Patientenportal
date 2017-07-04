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
	 
	            String firstRemoteFile = mdoc.getMedDocID()+".txt";
	            InputStream inputStream = new FileInputStream(filetoupload);
	 
	            System.out.println("Start uploading "+mdoc.getmDocTitle());
	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
	            inputStream.close();
	            if (done) {
	                System.out.println("The Medicaldocument : "+mdoc.getmDocTitle()+" has been uploaded sucessfully..");
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
			String filetodownload = File.separator+mdoctodownload.getMedDocID()+".txt";
			String server = "127.0.0.1";
	        int port = 21;
	        String user = "admin";
	        String pass = "12345";
	        File downloadfileto = new File(
					"C:" 				+File.separator+
					"Users" 			+File.separator+ 
					"Jani"				+File.separator+
					"Desktop"			+File.separator+
					"Filezilla"			+File.separator+
					"Download"			+File.separator+
					mdoctodownload.getmDocTitle()+".txt");
	 
	        FTPClient ftpClient = new FTPClient();
	        try {
	        	
	        	System.out.println("Starting Download of the Medicaldocument: "+ mdoctodownload.getmDocTitle());
	        	
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	 
	            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadfileto));
	            boolean success = ftpClient.retrieveFile(filetodownload, outputStream1);
	            outputStream1.close();
	 
	            if (success) {
	                System.out.println("The medicaldocument: "+mdoctodownload.getmDocTitle()+" has been downloaded sucessfully.");
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


