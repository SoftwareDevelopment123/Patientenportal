package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.plaf.synth.SynthPasswordFieldUI;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;

public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");
		
		URL url = new URL("http://localhost:8080/relative?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RelativeWSImplService");

		Service service = Service.create(url, qname);
		RelativeWS rel = service.getPort(RelativeWS.class);
				
		// Methode 1
	//	Relative relative = rel.getRelative(2);
	//	System.out.println(relative.getRelativeID());
				

		/*for (Patient p : relative.getPatients()){
			System.out.print("ID: " + p.getUser().getUserId() + " - ");
			System.out.print(p.getUser().getFirstname() + " - ");
			System.out.print(p.getUser().getLastname()  + " - ");
			System.out.println(p.getBloodtype());
		}*/


		
		// Methode 2
			/*List<Relative> relList = rel.getRelativesByP(1);
			
			for(Relative r : relList){
				System.out.println(r.getRelativeID());
			}*/

	}

}
