package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.patientenportal.entities.ActiveRole;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.VitalDataListResponse;
import de.patientenportal.persistence.CaseDAO;


public class VitalDataWSTest {
private String token;
	
	@Before
	public void login() throws MalformedURLException{
		String username = "user6";
		String password = "pass6";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        HTTPHeaderService.putUsernamePassword(username, password, authWS);
        authWS.authenticateUser(ActiveRole.Patient);
        token = authWS.getSessionToken(username);
	}
	

	@After
	public void logout() throws MalformedURLException{
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
		
        authWS.logout(token);
	}
	@Test
	public void main() throws MalformedURLException {
	
		//Test getVitalDatabyC
		int caseid =1;
		URL url = new URL("http://localhost:8080/vitaldata?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "VitalDataWSImplService");
		Service service = Service.create(url, qname);
		VitalDataWS vdws = service.getPort(VitalDataWS.class);
		
		List<VitalData> compareVDList = CaseDAO.getCase(caseid).getVitaldata();
		Accessor getVDList = new Accessor();
		
		getVDList.setObject(caseid);
		getVDList.setToken(token);
		VitalDataListResponse vdlistresp = vdws.getVitalDatabyC(getVDList, VitalDataType.WEIGHT);
		List <VitalData> ergebnis = vdlistresp.getResponseList();
		Assert.assertEquals("success", vdlistresp.getResponseCode());
		
		int i = 0;
		for (VitalData vd : compareVDList){
			System.out.println(compareVDList.size());
			System.out.println(vd.getVitalDataID());
			System.out.println(ergebnis.get(i).getVitalDataID());
			System.out.println(vd.getVitalDataType());
			
			Assert.assertEquals(vd.getVitalDataID()		, ergebnis.get(i).getVitalDataID());
			Assert.assertEquals(vd.getValue()			, ergebnis.get(i).getValue());
			Assert.assertEquals(vd.getVitalDataType()	, ergebnis.get(i).getVitalDataType());
			
			i++;
		}				
	}
}
