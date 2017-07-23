package smalltests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthPasswordFieldUI;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patienportal.demo.ClientHelper;
import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.services.AuthenticationWS;
import de.patientenportal.services.PatientWS;
import de.patientenportal.services.RelativeWS;

@SuppressWarnings("unused")
public class TestFehlerbehebung {

	public static void main(String[] args) throws MalformedURLException {
		
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "1000000");
		
		String username = "user4";
		String password = "pass4";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
 
        ClientHelper.putUsernamePassword(username, password, authWS);
        System.out.println(authWS.authenticateUser(ActiveRole.Patient));
        System.out.println(authWS.getSessionToken(username));
        
        String token = authWS.getSessionToken(username);
        System.out.println(authWS.authenticateToken(token));
        
        URL url2 = new URL("http://localhost:8080/patient?wsdl");
		QName qname2 = new QName("http://services.patientenportal.de/", "PatientWSImplService");
		Service service2 = Service.create(url2, qname2);
		PatientWS pat = service2.getPort(PatientWS.class);
		
		// Get Patient
		Patient comparepat = PatientDAO.getPatient(1);

		Accessor getpat = new Accessor();
			getpat.setToken(token);
			getpat.setObject(1);
				
		Patient patient = pat.getPatient(getpat);
		
		System.out.println(patient.getBloodtype());
		
		

	}

}
