package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
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

@SuppressWarnings("unused")
public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		String username = "user6";
		String password = "pass6";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        //HTTPHeaderService.putUsernamePassword(username, password, authWS);
        //authWS.authenticateUser(ActiveRole.Patient);
        String token = authWS.getSessionToken(username);
		
        System.out.println(token);
        
		/*System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");*/
		
		// insert Code here
		
        System.out.println(AuthenticationWSImpl.getActiveRole(token));
        
	}

}
