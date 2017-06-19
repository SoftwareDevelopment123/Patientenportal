package smalltests;

import org.junit.Assert;
import org.junit.Test;

import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.InsuranceDAO;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;


public class InsuranceTest {

	@Test
	public static void main(String[] args) {


	
		Insurance insurance1 = new Insurance();
		insurance1.setInsuranceNr(123);
		insurance1.setName("Techniker");
		InsuranceDAO.createInsurance(insurance1);
		
		Insurance insurance2 = new Insurance();
		insurance2.setInsuranceNr(234);
		insurance2.setName("ReifEisen");
		InsuranceDAO.createInsurance(insurance2); 
		
		Patient pat1 = new Patient();
		pat1.setInsurance(insurance1);
		
		
		Patient pat2 = new Patient();
		pat2.setInsurance(insurance2);
		
		Patient pat3 = new Patient();
		pat3.setInsurance(insurance1);
		
		RegistrationDAO.createPatient(pat1);
		RegistrationDAO.createPatient(pat2);
		RegistrationDAO.createPatient(pat3);
		
		Patient patient1 = PatientDAO.getPatient(1);
		Patient patient2 = PatientDAO.getPatient(2);
		Patient patient3 = PatientDAO.getPatient(3);
		
		//Insurance in Datenbank
		Insurance insurance = InsuranceDAO.getInsurance(1);
		Assert.assertEquals("Techniker",insurance.getName());
		Assert.assertEquals(123 ,insurance.getInsuranceNr());
		
		
	
			
		System.out.println("-------Test-Abfrage--------");
		System.out.println("----------User1 "+patient1.getInsurance().getName()+"-----------");
		System.out.println("----------User2 "+patient2.getInsurance().getName()+"-----------");
		System.out.println("----------User3 "+patient3.getInsurance().getName()+"-----------");
		
		//Patient-Update-Test
				Patient patientupdate = PatientDAO.getPatient(2);
					patientupdate.setInsurance(insurance1);
				
			String feedbackUP = PatientDAO.updatePatient(patientupdate);
					//Assert.assertEquals("success", feedbackUP);
		
		Patient patientxy = PatientDAO.getPatient(2);	
			
		System.out.println("----------User2 Insurance von "+patient2.getInsurance().getName()+" zu "+patientxy.getInsurance().getName()+"-----------");
					 
				Insurance update1 = InsuranceDAO.getInsurance(2);
				update1.setName("SIEG");
				InsuranceDAO.updateInsurance(update1);
				
					
		System.out.println("----------User2 "+patient2.getInsurance().getName()+"-----------");
		
		}
	}

