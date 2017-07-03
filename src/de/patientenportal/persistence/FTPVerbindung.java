package de.patientenportal.persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class FTPVerbindung {
	
	public static void main(String[] args) throws IOException { 
/*        try { 
            URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/"); 
            show(url);
            System.out.println(url.getProtocol());
            System.out.println(url.getHost());
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 

    private static void show(URL url) throws IOException { 
        InputStream in = url.openStream(); 
        BufferedReader buff = new BufferedReader(new InputStreamReader(in)); 
        String s;
        while ((s = buff.readLine()) != null) { 
            System.out.println(s); 
        }
        
    }

    
    private static void outputbla(URL url) throws IOException { */
    	File path = new File("laber.txt");
    	System.out.println("We got a file: "+path);
    	
    	System.out.println("Does it exist?"+path.exists());
    	System.out.println("Wat? "+path.isDirectory());
    	
    	String contentstowrite = "hello du grausame Welt";
    	OutputStream outstream = new FileOutputStream(path);
    	outstream.write(contentstowrite.getBytes());
    	outstream.close();
    }
    
    
}   	
	

	 


/*package de.patientenportal.persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

//import klären

public class FTPVerbindung {

	public static void main(String[] args) throws IOException {
	
	int temp,number;
	ServerSocket s1 = new ServerSocket(1342);
	Socket ss = s1.accept();
	Scanner sc = new Scanner(ss.getInputStream());
	number=sc.nextInt();
	temp = number*2;
	
	PrintStream p = new PrintStream(ss.getOutputStream());
	
	
	
	
	
	}
	
	
	 FTPClient ftpClient = new FTPClient();
	 String server = "127.0.0.1";
     int port = 14147;
     String user = "admin";
     String pass = "12345";
	 
	 try {

         ftpClient.connect(server, port);
         ftpClient.login(user, pass);
         ftpClient.enterLocalPassiveMode();

         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

         // APPROACH #1: uploads first file using an InputStream
         File firstLocalFile = new File("C:/Users/Jani/Desktop/BeispielDoc");

         String firstRemoteFile = "BeispielDoc";
         InputStream inputStream = new FileInputStream(firstLocalFile);

         System.out.println("Start uploading first file");
         boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
         inputStream.close();
         if (done) {
             System.out.println("The first file is uploaded successfully.");
         }

         // APPROACH #2: uploads second file using an OutputStream
         File secondLocalFile = new File("E:/Test/Report.doc");
         String secondRemoteFile = "test/Report.doc";
         inputStream = new FileInputStream(secondLocalFile);

         System.out.println("Start uploading second file");
         OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
         byte[] bytesIn = new byte[4096];
         int read = 0;

         while ((read = inputStream.read(bytesIn)) != -1) {
             outputStream.write(bytesIn, 0, read);
         }
         inputStream.close();
         outputStream.close();

         boolean completed = ftpClient.completePendingCommand();
         if (completed) {
             System.out.println("The second file is uploaded successfully.");
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
package de.patientenportal.persistence;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FTPServer {

	public static void main(String[] args) throws UnknownHostException, IOException {
		int number, temp;
		Scanner sc = new Scanner(System.in);
		Socket s= new Socket("127.0.0.1", 1342);
		Scanner sc1 = new Scanner(s.getInputStream());
		System.out.println("Enter any number");
		number = sc.nextInt();
		PrintStream p = new PrintStream(s.getOutputStream());
		p.println(number);
		temp= sc1.nextInt();
		System.out.println(temp);
	}

}

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
	File file = new File( "C:/Users/Jani/Desktop/Filezilla/Dateien/" );


	
*/