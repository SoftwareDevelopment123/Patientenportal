package service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class LoginClient {

	public static void main(String[] args) throws Exception {
				//URL des Web Service.
                //Beachte, dass auf der Link auf die WSDL-Datei verweist.
		URL url = new URL("http://localhost:8080/login?wsdl");
                //Qualifizierter Name des Service
                //Erstes Argument ist die Service URL
                //Zweites Argument ist der Service-Name, der in der WSDL-Datei angegeben wird
		QName qname = new QName("http://service/", "LoginImplService");
                // Erstelle einen Service und extrahiere das Endpoint-Interface, bzw. den Service-Port
		Service service = Service.create(url, qname);
		
		Login login = service.getPort(Login.class);
		
		System.out.println("Hier müsste correct credentials stehen: " + login.authenticateUser("Root", "root"));
		System.out.println("Hier müsste Password is missing stehen: "+ login.authenticateUser("Root", ""));
			}
}	