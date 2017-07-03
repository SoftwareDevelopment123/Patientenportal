/*package de.patientenportal.persistence;
import org.apache.commons.net.ftp.FTPClient;

//import klären

public class FTPVerbindung {

	
	 public static void main(String[] args) {
		
	FTPClient client = new FTPClient(); 
	client.connect("127.0.0.1");
    client.enterLocalPassiveMode();
    client.login("admin", "12345");
    FTPFile[] files = client.listFiles("/pub");
    for (FTPFile file : files) {
        System.out.println(file.getName());
    }	 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		   try {
	            URL url = new URL("ftp://admin:12345@127.0.0.1/Dateien/");
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
	        boolean InstructionDoc = new File( "C:/Users/Jani/Desktop/Filezilla/Dateien/InstructionDoc.txt/" ).delete();
	        System.out.println( InstructionDoc );  
} 
}
}
	

*/