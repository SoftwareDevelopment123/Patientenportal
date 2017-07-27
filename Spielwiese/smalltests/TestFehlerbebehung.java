package smalltests;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Medicine;
import de.patientenportal.entities.exceptions.AccessException;
import de.patientenportal.entities.exceptions.AccessorException;
import de.patientenportal.entities.exceptions.AuthenticationException;
import de.patientenportal.entities.exceptions.AuthorizationException;
import de.patientenportal.entities.exceptions.InvalidParamException;
import de.patientenportal.entities.exceptions.PersistenceException;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.MedicineDAO;
import de.patientenportal.persistence.UserDAO;
import de.patientenportal.services.AuthenticationWS;
import de.patientenportal.services.MedicationWS;
import de.patientenportal.services.MedicineWS;

public class TestFehlerbebehung {

	public static void main(String[] args) throws MalformedURLException, InvalidParamException, AccessorException, PersistenceException, AuthenticationException, AccessException, AuthorizationException {
		
		URL urlA = new URL("http://localhost:8080/authentication?wsdl");
		QName qnameA = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
		Service serviceA = Service.create(urlA, qnameA);
		AuthenticationWS auth = serviceA.getPort(AuthenticationWS.class);
		
		String token = auth.getSessionToken("user10");
		
		URL urlMedicine = new URL("http://localhost:8080/medicine?wsdl");
		QName qnameMed = new QName("http://services.patientenportal.de/", "MedicineWSImplService");
		Service serviceMed = Service.create(urlMedicine, qnameMed);
		MedicineWS medws = serviceMed.getPort(MedicineWS.class);
		
		URL urlMedication = new URL("http://localhost:8080/medication?wsdl");
		QName qnameMedica = new QName("http://services.patientenportal.de/", "MedicationWSImplService");
		Service serviceMedica = Service.create(urlMedication, qnameMedica);
		MedicationWS medicaws = serviceMedica.getPort(MedicationWS.class);
		
		Accessor createMedication = new Accessor(token);
		Accessor createMedicine = new Accessor(token);
		
		Medicine medicine = new Medicine();
			medicine.setDrugmaker("Böser Pharmakonzern");
			medicine.setActiveIngredient("Krankium");
			medicine.setName("InnovativerName");
			createMedicine.setObject(medicine);
		
		medws.createMedicine(createMedicine);

			
		Medication medication1 = new Medication();
			medication1.setDosage("212");
			medication1.setDuration("extremslange Stunden:");			
			medication1.setPcase(CaseDAO.getCase(1));
			medication1.setMedicine(MedicineDAO.getMedicine(2));
			medication1.setPrescribedBy(UserDAO.getUser(10).getDoctor());
			createMedication.setObject(medication1);
				
			String response = medicaws.createMedication(createMedication);
			System.out.println("----------");
			System.out.println(response);
			
		}
	
}
