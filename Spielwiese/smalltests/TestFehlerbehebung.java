package smalltests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthPasswordFieldUI;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.services.RelativeWS;

public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		/*System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");*/
		
		URL url = new URL("http://localhost:8080/relative?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RelativeWSImplService");

		Service service = Service.create(url, qname);
		RelativeWS rel = service.getPort(RelativeWS.class);
				
		// Methode 1
		/*Accessor accessor = new Accessor(2);
		Relative relative = rel.getRelative(accessor);
		System.out.println(relative.getRelativeID());
		for (Patient p : relative.getPatients()){
			System.out.print("ID: " + p.getUser().getUserId() + " - ");
			System.out.print(p.getUser().getFirstname() + " - ");
			System.out.print(p.getUser().getLastname()  + " - ");
			System.out.println(p.getBloodtype());
		}*/

		// Methode 2
		/*Accessor accessor = new Accessor(1);
		RelativeListResponse response = rel.getRelativesByP(accessor);
		System.out.println(response.getResponseCode());
		System.out.println(response.getResponseList().size());
						
			for(Relative r : response.getResponseList()){
				System.out.print(r.getRelativeID() + " - ");
				System.out.print(r.getUser().getFirstname() + " - ");
				System.out.println(r.getUser().getLastname());
			}*/

	}

}
