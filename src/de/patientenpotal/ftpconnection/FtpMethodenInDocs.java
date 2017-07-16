package de.patientenpotal.ftpconnection;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import de.patientenportal.entities.InstructionDoc;

public class FtpMethodenInDocs {
	public static void uploadInstructionDoc(InstructionDoc indoc)
	//in Beta muss Logik eingeführt werden die zwei gleiche Dateinamen zulässt oder Ids vergibt
	//über mdoc file und id geben dann setid name und get name den Namen in Hibernate speichern
	{
		String server = "127.0.0.1";
        int port = 21;
        String user = "admin";
        String pass = "12345";
        File filetoupload = new File(
        		"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				indoc.getTitle()+"."+indoc.getFileType());
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            String firstRemoteFile = "InstructionDoc"+indoc.getInstructionDocID()+"."+indoc.getFileType();
            InputStream inputStream = new FileInputStream(filetoupload);
 
            System.out.println("Start uploading "+indoc.getTitle());
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The Medicaldocument : "+indoc.getTitle()+" has been uploaded sucessfully..");
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
	
	public static void downloadInstructionDoc(InstructionDoc indoctodownload) {
		
		String filetodownload = File.separator+"InstructionDoc"+indoctodownload.getInstructionDocID()+"."+indoctodownload.getFileType();
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
				indoctodownload.getTitle()+
				indoctodownload.getInstructionDocID()+"."+
				indoctodownload.getFileType());
 
        FTPClient ftpClient = new FTPClient();
        try {
        	
        	System.out.println("Starting Download of the Medicaldocument: "+ indoctodownload.getTitle()+indoctodownload.getFileType());
        	
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadfileto));
            boolean success = ftpClient.retrieveFile(filetodownload, outputStream1);
            outputStream1.close();
 
            if (success) {
                System.out.println("The Instruction Document: "+indoctodownload.getTitle()+indoctodownload.getFileType()+" has been downloaded sucessfully.");
            }
            if (!success){
            	System.out.println("The Instruction Document: "+indoctodownload.getTitle()+indoctodownload.getFileType()+" has not been found on the server.");
            	downloadfileto.delete();
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
	public static void showAllFiles(String user, String password) throws IOException {
		URL url = new URL("ftp://"+user+":"+password+"@127.0.0.1/");
		InputStream stream = url.openStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    	String line = reader.readLine();
    	while(line!= null){
    	System.out.println(line);
    	line = reader.readLine();
    	}
    	System.out.println("Done Reading!");
		

	}
		
}

