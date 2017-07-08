package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.Relative;

public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		URL url = new URL("http://localhost:8080/relative?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RelativeWSImplService");

		Service service = Service.create(url, qname);
		RelativeWS rel = service.getPort(RelativeWS.class);
			
		// Methode 1
		Relative relative = rel.getRelative(1);
		System.out.println(relative.getRelativeID());
		
		// Methode 2
			/*List<Relative> relList = rel.getRelativesByP(1);
			
			for(Relative r : relList){
				System.out.println(r.getRelativeID());
			}*/

	}

}
