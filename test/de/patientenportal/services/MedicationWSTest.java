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
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.MedicationListResponse;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MedicationDAO;
import de.patientenportal.persistence.PatientDAO;

public class MedicationWSTest {

	private String token;
	
	@Before
	public void login() throws MalformedURLException{
		String username = "user10";
		String password = "pass10";
		
		URL url = new URL("http://localhost:8080/authentication?wsdl");
        QName qname = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
        Service service = Service.create(url, qname);
        AuthenticationWS authWS = service.getPort(AuthenticationWS.class);
        
        HTTPHeaderService.putUsernamePassword(username, password, authWS);
        authWS.authenticateUser(ActiveRole.Doctor);
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
	
		//TEst get MEdicationbyC
		int caseid =1;
		int patientid=1;
		URL url = new URL("http://localhost:8080/medication?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "MedicationWSImplService");
		Service service = Service.create(url, qname);
		MedicationWS med = service.getPort(MedicationWS.class);
		
		List<Medication> compareMedList = CaseDAO.getCase(caseid).getMedication();
		Accessor getMedList = new Accessor();
		
		getMedList.setObject(caseid);
		getMedList.setToken(token);
		MedicationListResponse medlistresp = med.getMedicationbyC(getMedList);
		List <Medication> ergebnis = medlistresp.getResponseList();
		Assert.assertEquals("success", medlistresp.getResponseCode());
		
		int i = 0;
		for (Medication m : compareMedList){
			Assert.assertEquals(m.getDosage()						, ergebnis.get(i).getDosage());
			Assert.assertEquals(m.getDuration()						, ergebnis.get(i).getDuration());
			Assert.assertEquals(m.getPrescribedBy().getDoctorID()	, ergebnis.get(i).getPrescribedBy().getDoctorID());
			i++;
		}
		//Get MedicationbyP
		List<Medication> compareMedList2 = PatientDAO.getPatient(patientid).getCases().get(1).getMedication();
		Accessor getMedListbyP = new Accessor();
		
		getMedListbyP.setObject(patientid);
		getMedListbyP.setToken(token);
		MedicationListResponse medlistrespfromcase = med.getMedicationbyP(getMedListbyP);
		List <Medication> ergebnis2 = medlistrespfromcase.getResponseList();
		Assert.assertEquals("success", medlistrespfromcase.getResponseCode());
		
		int a = 0;
		for (Medication m : compareMedList2){
			Assert.assertEquals(m.getDosage()						, ergebnis2.get(a).getDosage());
			Assert.assertEquals(m.getDuration()						, ergebnis2.get(a).getDuration());
			Assert.assertEquals(m.getPrescribedBy().getDoctorID()	, ergebnis2.get(a).getPrescribedBy().getDoctorID());
			a++;
		}

		
		
	}
}