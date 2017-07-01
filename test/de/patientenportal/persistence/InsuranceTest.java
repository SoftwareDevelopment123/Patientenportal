package de.patientenportal.persistence;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Patient;

public class InsuranceTest {

	@Test
	public void main() {
	
	//Insurances anlegen
		Insurance insurance1 = new Insurance();
			insurance1.setInsuranceNr(123);
			insurance1.setName("Techniker");
	
		Insurance insurance2 = new Insurance();
			insurance2.setInsuranceNr(234);
			insurance2.setName("ReifEisen");
	
		InsuranceDAO.createInsurance(insurance1);
		InsuranceDAO.createInsurance(insurance2); 
	
		//Patienten und Insurances zuweisen anlegen
		Patient pat1 = new Patient();
			pat1.setBloodtype("A-");
			pat1.setInsurance(insurance1);
		Patient pat2 = new Patient();
			pat2.setBloodtype("B+");
			pat2.setInsurance(insurance2);
		Patient pat3 = new Patient();
			pat3.setBloodtype("AB²");
			pat3.setInsurance(insurance1);
		
		
		String feedbackpat1 = RegistrationDAO.createPatient(pat1);
			Assert.assertEquals("success", feedbackpat1);
		String feedbackpat2 = RegistrationDAO.createPatient(pat2);
			Assert.assertEquals("success", feedbackpat2);
		String feedbackpat3 = RegistrationDAO.createPatient(pat3);
			Assert.assertEquals("success", feedbackpat3);
		
		
		//Patients abrufen und testen
		Patient patient1 = PatientDAO.getPatient(1);
		Patient patient2 = PatientDAO.getPatient(2);
		Patient patient3 = PatientDAO.getPatient(3);
		
			Assert.assertEquals("Techniker",patient1.getInsurance().getName());
			Assert.assertEquals("ReifEisen",patient2.getInsurance().getName());
			Assert.assertEquals("AB²",		patient3.getBloodtype());
		
		//Insurance abgleichen
		Insurance insurance = InsuranceDAO.getInsurance(1);
			Assert.assertEquals("Techniker",insurance.getName());
			Assert.assertEquals(123 ,insurance.getInsuranceNr());
		
		//Patienten über die Insurance abrufen
		Insurance ins = InsuranceDAO.getInsurance(1);
		List <Patient> patients = ins.getPatients();
		List<Patient> vergleich = InsuranceDAO.getInsurance(1).getPatients();
		int u=0;
		for( Patient p : patients){
				
		Assert.assertEquals(p.getBloodtype()					, vergleich.get(u).getBloodtype()) ;
		Assert.assertEquals(p.getInsurance().getInsuranceID()	, vergleich.get(u).getInsurance().getInsuranceID());
		Assert.assertEquals(p.getInsurance().getInsuranceNr()	, vergleich.get(u).getInsurance().getInsuranceNr());
		Assert.assertEquals(p.getInsurance().getName()			, vergleich.get(u).getInsurance().getName());
		u++;
		}
				
		//Patient-Update-Test
		Patient patientupdate = PatientDAO.getPatient(2);
			patientupdate.setInsurance(insurance1);
				
		String feedbackUP = PatientDAO.updatePatient(patientupdate);
			Assert.assertEquals("success", feedbackUP);
		
		patients.get(1).setInsurance(insurance1);
		List<Patient> compareme = InsuranceDAO.getInsurance(1).getPatients();
		
		//Liste muss nach Update neu definiert werden
		//Vergleich der Liste der PAtienten mit der Liste der Patienten aufgerufen über die VErsicherung
		Insurance ins1 = InsuranceDAO.getInsurance(1);
		List <Patient> patients1 = ins1.getPatients();
		
		int i = 0;
		for (Patient p : patients1){
			
			Assert.assertEquals(p.getBloodtype()					, compareme.get(i).getBloodtype()) ;
			Assert.assertEquals(p.getInsurance().getInsuranceID()	, compareme.get(i).getInsurance().getInsuranceID());
			Assert.assertEquals(p.getInsurance().getInsuranceNr()	, compareme.get(i).getInsurance().getInsuranceNr());
			Assert.assertEquals(p.getInsurance().getName()			, compareme.get(i).getInsurance().getName());
			i++;
		}
	
		//Patient wird abgeglichen
		Patient patientxy = PatientDAO.getPatient(2);	
		Assert.assertEquals( 123 , patientxy.getInsurance().getInsuranceNr());
		
		//Insurance wird upgedated
		Insurance update1 = InsuranceDAO.getInsurance(1);
		update1.setName("Schwarzwald Krankenkasse");
		InsuranceDAO.updateInsurance(update1);
		
		//Abgleich ob update der einzelnen Krankenkasse funktioniert hat
		Patient patientSchwarzwald = PatientDAO.getPatient(1);	
		Assert.assertEquals("Schwarzwald Krankenkasse", patientSchwarzwald.getInsurance().getName());
		Assert.assertEquals( 123 , patientSchwarzwald.getInsurance().getInsuranceNr());
	
		//Löschen der Insurance
		String responseD = InsuranceDAO.deleteInsurance(1);
		Assert.assertEquals("success", responseD);
		
		Insurance deletedinsurance = InsuranceDAO.getInsurance(1);
		Assert.assertNull(deletedinsurance);
	}
	
	}

