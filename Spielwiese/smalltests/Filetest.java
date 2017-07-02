package smalltests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import de.patientenportal.entities.MedicalDoc;

public class Filetest {

	public static void main(String[] args) throws IOException {
		
		File Filetoname = new File( "C:/Users/Jani/Desktop/Filezilla/MedicalDoc123.txt" );
		boolean res1 = Filetoname.exists();
		System.out.println(res1);
		MedicalDoc mdoc1 = new MedicalDoc();
		mdoc1.setFile(Filetoname);
		int i = mdoc1.getMedDocID();
		
		Integer meinInteger = new Integer(i);
		String idnamestring = meinInteger.toString();

        BufferedWriter bw = new BufferedWriter(new FileWriter(Filetoname));
        bw.write("test");
        bw.close();
 
        boolean res = Filetoname.renameTo(new File("ftp://admin:12345@127.0.0.1/Server/"+idnamestring));
        System.out.println(res);
		
		
		
	}

}
