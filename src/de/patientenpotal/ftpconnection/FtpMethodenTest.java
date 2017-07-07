package de.patientenpotal.ftpconnection;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;




public class FtpMethodenTest {

	public static void main(String[] args) throws IOException {
		//Dateien auf dem Server mit der URL werden gelesen
		/*URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		FtpMethoden.readServerfiles(url);*/
		
		//Download aus Server
		/*BasicConfigurator.configure();
		URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		String umgewnadelt = url.toString();
		String Resp = FtpMethoden.downloadFile(umgewnadelt);
		System.out.println(Resp);*/
		
		//Uploaden eines Files auf einen Server
		//File filetoupload = new File("C:Users/Jani/Desktop/Filezilla/DokumentzumHochladen.txt");
		File filetoupload = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"leckmichfett.txt");
		FtpMethoden.uploadFile(filetoupload);
		
		//Download eines Files
		String remoteFile1 = "/leckmichfett.txt";
		File downloadFile1 = new File(
				"C:" 				+File.separator+
				"Users" 			+File.separator+ 
				"Jani"				+File.separator+
				"Desktop"			+File.separator+
				"Filezilla"			+File.separator+
				"Upload"			+File.separator+
				"leckmichfett.txt");
		FtpMethoden.downloadFile(remoteFile1, downloadFile1);
		
	}

}
