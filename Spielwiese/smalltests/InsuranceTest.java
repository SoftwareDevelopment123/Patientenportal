package smalltests;

import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.InsuranceDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RegistrationDAO;


public class InsuranceTest {

	public static void main(String[] args) {


		User neu = new User();
		neu.setUsername("staps");
		neu.setPassword("pass");
		neu.setEmail("stap.staptp@mustermail.com");
		neu.setLastname("Stupser");
		neu.setFirstname("Staps");
		neu.setBirthdate("01.01.1992");
		neu.setGender("male");
		
	
		Insurance insurance = new Insurance();
		insurance.setInsuranceNr(123);
		insurance.setName("Techniker");
		InsuranceDAO.createInsurance(insurance);
		
		Patient patient = new Patient();
		patient.setInsurance(insurance);
		neu.setPatient(patient);
		
		RegistrationDAO.createUser(neu);
		
Patient patientdb = PatientDAO.getPatient(1);						
		
		if (patientdb==null) {
		}
		else{	
		System.out.println("-------Test-Abfrage--------");
		System.out.println("----------User "+patientdb.getInsurance().getName()+"-----------");
		
		}
	}
}
