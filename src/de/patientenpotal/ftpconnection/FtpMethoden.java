package de.patientenpotal.ftpconnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;



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
	
	private final static Logger log = Logger.getLogger( FtpMethoden.class );
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
	}

}
