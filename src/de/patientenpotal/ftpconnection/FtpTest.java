package de.patientenpotal.ftpconnection;
import de.patientenpotal.ftpconnection.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class FtpTest {

	public static void main(String[] args) throws IOException {
		//Dateien auf dem Server mit der URL werden gelesen
		URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		FtpMethoden.readServerfiles(url);
	}

}
