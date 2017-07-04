package smalltests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import de.patientenportal.entities.MedicalDoc;

public class Filetest {

	public static void main(String[] args) throws Exception {
		
		File File1 = new File( "C:/Users/Jani/Desktop/Filezilla/MedicalDoc123.txt" );
		boolean res1 = File1.exists();
		FileOutputStream fileOut = new FileOutputStream(File1);
		
		Path path = Paths.get("C:/Users/Jani/Desktop/Filezilla/MedicalDoc123.txt");
		byte[] data = Files.readAllBytes(path);
		fileOut.write(data);
		
		
		fileOut.close();
	}
	catch(Exception e1)
	{
		e1.printStackTrace();
	}
}
