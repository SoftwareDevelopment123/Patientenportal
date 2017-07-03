package smalltests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class FileDemo {

	public static void main(String[] args) throws IOException {
		File path = new File("laber.txt");
    	System.out.println("We got a file: "+path);
    	
    	System.out.println("Does it exist?"+path.exists());
    	System.out.println("Wat? "+path.isDirectory());
    	
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
    	
    	URL url = new URL("ftp://admin:12345@127.0.0.1/Server1/");
    	InputStream stream = url.openStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    	String line = reader.readLine();
    	while(line!= null){
    	System.out.println(line);
    	}
    	
    }
}


