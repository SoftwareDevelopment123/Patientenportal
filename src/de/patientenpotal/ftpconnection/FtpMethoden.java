package de.patientenpotal.ftpconnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;




public class FtpMethoden {

	//Methode zum Lesen der auf dem Server vorhandenen 
	public static void readServerfiles(URL url) throws IOException{
		InputStream stream = url.openStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    	String line = reader.readLine();
    	while(line!= null){
    	System.out.println(line);
    	line = reader.readLine();
    	}
    	System.out.println("Done Reading!");
		

	}
	public static void uploadFile(File filetoupload)
	//in Beta muss Logik eingeführt werden die zwei gleiche Dateinamen zulässt oder Ids vergibt
	//über mdoc file und id geben dann setid name und get name den Namen in Hibernate speichern
	{
		String server = "127.0.0.1";
        int port = 21;
        String user = "admin";
        String pass = "12345";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //File firstLocalFile = new File("C:/Users/Jani/Desktop/Filezilla/DasDokument.txt");
 
            String firstRemoteFile = "leckmichfett.txt";
            InputStream inputStream = new FileInputStream(filetoupload);
 
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
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
	
	public static void downloadFile(String nameOfFile, File downloadtofile) {
		String server = "127.0.0.1";
        int port = 21;
        String user = "admin";
        String pass = "12345";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            
            String remoteFile1 = nameOfFile;
            File downloadFile1 = downloadtofile;
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
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
	
			

//Download Methode für den gesamten Server, alte Version
//Logger muss über maven dependicies einführen	
/*private final static Logger log = Logger.getLogger( FtpMethoden.class );
	public static String downloadFile(String url)
	{
		final String urlF = url;
		String returnThread;
		try
		{
			String[] urlPart = urlF.split("/");
			String fileName = urlPart[urlPart.length - 1];
			URL link = new URL(urlF);
			FileUtils.copyURLToFile(link, new File("tmp/" + fileName));
			log.info("Download Finished");
			returnThread = fileName;
		}
		catch (IOException e)
		{
			log.error("Download Error");
			returnThread = null;
		}
		return returnThread;
	}*/
	

