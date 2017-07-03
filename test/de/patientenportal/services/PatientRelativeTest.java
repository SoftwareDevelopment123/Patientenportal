package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Response;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.*;

public class PatientRelativeTest {

	// Funktioniert noch nicht richtig
	
	@Test
	public void main() throws MalformedURLException{
		

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
