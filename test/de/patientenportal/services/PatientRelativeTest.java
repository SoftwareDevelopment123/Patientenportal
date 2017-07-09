package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import de.patientenportal.entities.*;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.PatientListResponse;
import de.patientenportal.persistence.PatientDAO;

@SuppressWarnings("unused")
public class PatientRelativeTest {

	/*@Before
	public void setUp(){
		DBCreator.main(null);
	}*/
	
	@Test
	public void main() throws MalformedURLException{
		System.out.print("Testing Patient and Relative WebServices ...");
		
		// Zu Testzwecken Auskommentierung entfernen
		/*System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");*/
		
		/*URL url = new URL("http://localhost:8080/relative?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "RelativeWSImplService");
		Service service = Service.create(url, qname);
		RelativeWS rel = service.getPort(RelativeWS.class);*/
				
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

		URL url2 = new URL("http://localhost:8080/patient?wsdl");
		QName qname2 = new QName("http://services.patientenportal.de/", "PatientWSImplService");
		Service service2 = Service.create(url2, qname2);
		PatientWS pat = service2.getPort(PatientWS.class);
		
		// Get Patient
		Patient comparepat = PatientDAO.getPatient(1);
		Accessor getpat = new Accessor(1);
		Patient patient = pat.getPatient(getpat);
			Assert.assertEquals(comparepat.getPatientID() 			, patient.getPatientID());
			Assert.assertEquals(comparepat.getBloodtype() 			, patient.getBloodtype());
			Assert.assertEquals(comparepat.getInsurance().getName() , patient.getInsurance().getName());
			Assert.assertEquals(comparepat.getUser().getLastname() 	, patient.getUser().getLastname());

		
		// Get PatientList by Relative
		Accessor getPatList = new Accessor(3);
		PatientListResponse PatList = pat.getPatientsByR(getPatList);
		
			
		System.out.println("Success!");	
	}	
}
