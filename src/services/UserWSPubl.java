package services;

import javax.xml.ws.Endpoint;
import services.UserWSImpl;

public class UserWSPubl {

	public static void main(String[] args) {

		Endpoint.publish("http://localhost:8080/user", new UserWSImpl());

		System.out.println("Web-Server running on http://localhost:8080/user" + "?wsdl");
	}
}