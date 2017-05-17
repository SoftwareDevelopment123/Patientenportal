package loginservice.old;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class LoginClient {

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:8080/login?wsdl");
		QName qname = new QName("http://old.loginservice/", "LoginImplService");

		Service service = Service.create(url, qname);
		
		Login login = service.getPort(Login.class);
		
		System.out.println("Testing Connection... ");
		System.out.println(login.ConnectionTest());
		
		System.out.println("Hier müsste correct credentials stehen: " + login.authenticateUser("Root", "root"));
		System.out.println("Hier müsste Password is missing stehen: "+ login.authenticateUser("Root", ""));
			}
}	