package de.patientenportal.services;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.services.UserWS;


public class UserTest {

	public static void main(String[] args) throws Exception {

		int user_id = 2;
		System.out.println("Test starting ... looking for User with ID = " + user_id);
		
		try{
		URL url = new URL("http://localhost:8080/user?wsdl");
		QName qname = new QName("http://services/", "UserWSImplService");

		Service service = Service.create(url, qname);
		UserWS userws = service.getPort(UserWS.class);
		

		}
		catch (Exception e) { 
			System.err.println("Zugriffsfehler");
		}
		
	}
}