package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class VitalDataTest {

	@Test
	public void main(){
		
	//drei Vitaldaten anlegen
		Case newcase = new Case("Erster Fall"	,"Wichtige Info zum ersten Fall");
		Case newcase2 = new Case("Impfpass"		,"Alle wichtigen Impfungen");
		
		String feedbackCC1 = CaseDAO.createCase(newcase);
		Assert.assertEquals("success", feedbackCC1);
		String feedbackCC2 = CaseDAO.createCase(newcase2);
		Assert.assertEquals("success", feedbackCC2);
	
		
		
		VitalData vitaldata1 = new VitalData("früh",4.5,VitalDataType.BLOODSUGAR,newcase);
		VitalData vitaldata2 = new VitalData("mittag",5.0,VitalDataType.BLOODSUGAR,newcase);
		VitalData vitaldata3 = new VitalData("abend",6.5,VitalDataType.BLOODSUGAR,newcase2);
		
		String feedbackCV1 = VitalDataDAO.add(vitaldata1);
			Assert.assertEquals("success", feedbackCV1);
		String feedbackCV2 = VitalDataDAO.add(vitaldata2);
			Assert.assertEquals("success", feedbackCV2);
		String feedbackCV3 = VitalDataDAO.add(vitaldata3);	
			Assert.assertEquals("success", feedbackCV3);
		
		Patient pat = new Patient();
		pat.setBloodtype("AB+");
		RegistrationDAO.createPatient(pat);
	
		Patient pat2 = new Patient();
		pat.setBloodtype("AB²");
		RegistrationDAO.createPatient(pat);
		
		
		//Zuordnungen
		newcase.setPatient(pat);
		newcase2.setPatient(pat2);
		
			
		// Vitaldaten aus der Datenbank abrufen
		VitalData vital = VitalDataDAO.getVitalData(2);
		Assert.assertEquals("mittag", vital.getTimestamp());
		assertEquals ( 5.0 , vital.getValue(), 0);
		
		
		//Aufrufen der Vitaldaten über den Case
		Case case1 = CaseDAO.getCase(1);
				
		//Vitaldaten überprüfen vom Patient abrufen
		List<VitalData> compareme = Arrays.asList(vitaldata1,vitaldata2);
		List<VitalData> vitaldatacase1 = case1.getVitaldata();
				
				int i = 0;
				for (VitalData v : compareme){
					Assert.assertEquals(v.getTimestamp()	, vitaldatacase1.get(i).getTimestamp());
					Assert.assertEquals(v.getValue()		, vitaldatacase1.get(i).getValue());
					i++;
				}
		
		//Vitaldaten Updaten
		VitalData vitaldatatoupdate = VitalDataDAO.getVitalData(1);
			vitaldatatoupdate.setVitalDataType(VitalDataType.WEIGHT);
			vitaldatatoupdate.setTimestamp("9:31");
			vitaldatatoupdate.setValue(1.00001);
				
		String responseU = VitalDataDAO.updateVitalData(vitaldatatoupdate);
			Assert.assertEquals("success", responseU);
				
		VitalData updatedvitaldata = VitalDataDAO.getVitalData(1);
			assertEquals(1.00001,updatedvitaldata.getValue(), 0);
		
		//Vitaldaten Löschen
		String responseD = VitalDataDAO.deleteVitalData(1);
			Assert.assertEquals("success", responseD);
			
		VitalData deletedvitaldata = VitalDataDAO.getVitalData(1);
			Assert.assertNull(deletedvitaldata);			
		
		
	}
}
