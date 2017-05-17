package loginservice.old;

import javax.xml.ws.Endpoint;

import loginservice.old.LoginImpl;

public class LoginPublisher {

	public static void main(String[] args) {
                //Erstes Argument ist die URL
                //Zweites Argument ist eine Instanz des SIB
		Endpoint.publish("http://localhost:8080/login", new LoginImpl());

		System.out.println("Web-Server running on http://localhost:8080/login" + "?wsdl");
	}
}