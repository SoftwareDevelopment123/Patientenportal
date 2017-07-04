package de.patientenpotal.ftpconnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.BasicConfigurator;


public class FtpMethodenTest {

	public static void main(String[] args) throws IOException {
		//Dateien auf dem Server mit der URL werden gelesen
		/*URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		FtpMethoden.readServerfiles(url);*/
		
		//Download aus Server
		BasicConfigurator.configure();
		URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		String umgewnadelt = url.toString();
		String Resp = FtpMethoden.downloadFile(umgewnadelt);
		System.out.println(Resp);
	}

}
