package de.patientenportal.entities;

import java.io.File;
import java.io.IOException;

public class FTPVerbindungTest {

	public static void main(String[] args){ 
		File file = new File("ftp://admin:12345@127.0.0.1/Ordner1/Beispielobjekt.docx/", "Beispielobjekt");
		  if(!file.exists()){
		  try{
			  boolean wurdeErstellt = file.createNewFile();
			  
			  if(wurdeErstellt){
				  System.out.println("Beispiel wurde erfolgreich erstellt");
				  }
			  else{
				  System.out.println("wurde nicht erstellt");
			  }
		  }
		  catch(IOException ex) {
		  
				  ex.printStackTrace();
			  }
		

	}
	}
}



