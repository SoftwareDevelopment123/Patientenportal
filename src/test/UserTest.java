package test;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


import services.UserWS;


public class UserTest {

	public static void main(String[] args) throws Exception {

		int user_id = 2;
		System.out.println("Test starting ... looking for User with ID = " + user_id);
		
		try{
		URL url = new URL("http://localhost:8080/user?wsdl");
		QName qname = new QName("http://services/", "UserWSImplService");

		Service service = Service.create(url, qname);
		
		UserWS userws = service.getPort(UserWS.class);
				
		System.out.println("User-ID:  " + userws.getUser(user_id).getUserId());
		System.out.println("Username: " + userws.getUser(user_id).getUsername());
		System.out.println("Password: " + userws.getUser(user_id).getPassword());
		System.out.println("Email:    " + userws.getUser(user_id).getEmail());
		System.out.println("Lastname: " + userws.getUser(user_id).getLastname());
		System.out.println("Firstname " + userws.getUser(user_id).getFirstname());
		}
		catch (Exception e) { 
			System.out.println("Zugriffsfehler");
		}
		
	}
}