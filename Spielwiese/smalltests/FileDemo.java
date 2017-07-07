package smalltests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import org.apache.log4j.BasicConfigurator;

import de.patientenpotal.ftpconnection.FtpMethoden;;

public class FileDemo {

	public static void main(String[] args) throws IOException {
		
		/*File path = new File("laber.txt");
    	System.out.println("We got a file: "+path);
    	
    	System.out.println("Does it exist?"+path.exists());
    	System.out.println("Wat? "+path.isDirectory());*/
    	
    	//Zum schreiben einer lokalenDatei
    /*	String contentstowrite = "hello du grausame Welt";
    	OutputStream outstream = new FileOutputStream(path);
    	outstream.write(contentstowrite.getBytes());
    	outstream.close();*/
    	
    	//Zum Lesen Lokale Datei
    	/*BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream(path)));
    	String firstLine = reader.readLine();
    	reader.close();
    	System.out.println("Read a Line "+ firstLine);*/
    	
    	
    	//LEsen der Dateien auf dem Server
    	/*URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
    	InputStream stream = url.openStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    	String line = reader.readLine();
    	while(line!= null){
    	System.out.println(line);
    	line = reader.readLine();
    	}
    	System.out.println("Done Reading!");*/
    	
		
		/*Path lokfile = Paths.get("C:/Users/Jani/Desktop/Filezilla/DasDokument.txt");
		File file = new File("C:/Users/Jani/Desktop/Filezilla/DasDokument.txt");
    	Path kopie = Paths.get("C:/Users/Jani/Desktop/Filezilla/Kopie.txt");
    	URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");   
    	OutputStream outstream = new URLOutputStream(path);
    	
    	FileUtils.copyFile(file, outstream);
    	
    	try{
    		Files.copy(lokfile, kopie, StandardCopyOption.REPLACE_EXISTING);
    		System.out.println("Kopie wurde erstellt");
    	} catch(IOException e) {
    		e.printStackTrace();
    	}*/
				
		//Abrufen des Servers
		/*BasicConfigurator.configure();
		URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
		String umgewandelt = url.toString();
		String Resp = FtpMethoden.downloadFile(umgewandelt);
		System.out.println(Resp);*/
		
		String ftpServer = new String("ftp://admin:12345@127.0.0.1/Server1/");
		String user = new String("admin");
		String password = new String("12345");
		String fileName = new String("JenesDokument");
		File file = new File("C://Users/Jani/Desktop/JenesDokument/");
		
		
		
   	}
    	
}



