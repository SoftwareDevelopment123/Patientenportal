package de.patientenpotal.ftpconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import de.patientenportal.entities.Medication;

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

}
