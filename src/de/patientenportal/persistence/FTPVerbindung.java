package de.patientenportal.persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class FTPVerbindung {

	
	 public static void main(String[] args) {
	        try {
	            URL url = new URL("ftp://admin:12345@127.0.0.1/Ordner1/");
	            //<protokoll><benutzername>:<passwort>@<hostname>[/verzeichnis/] 
	            show(url);
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
}
	

