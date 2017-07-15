package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.plaf.synth.SynthPasswordFieldUI;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;

import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.persistence.PatientDAO;

public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		String username = "user6";
		String password = "pass6";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
       /* HTTPHeaderService.putUsernamePassword(username, password, authWS);
        authWS.authenticateUser(ActiveRole.Patient);*/
        String token = authWS.getSessionToken(username);
		
        System.out.println(token);
        
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");
		
		URL urlC = new URL("http://localhost:8080/case?wsdl");
		QName qnameC = new QName("http://services.patientenportal.de/", "CaseWSImplService");
		Service serviceC = Service.create(urlC, qnameC);
		CaseWS casews = serviceC.getPort(CaseWS.class);

		List<Case> compareme = PatientDAO.getPatient(3).getCases();
		Accessor getCases = new Accessor(token);
			boolean status = true;
			getCases.setObject(status);
		
		CaseListResponse response = casews.getCases(getCases);
		
		for (Case c : response.getResponseList()){
			System.out.println(c.getCaseID());
		}

	}

}
