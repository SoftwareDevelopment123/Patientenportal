package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.entities.VitalData;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class VitalDataTest {

	@Test
	public void main(){
		
	//drei Vitaldaten anlegen
		VitalData vitaldata1 = new VitalData();
		vitaldata1.setTimestamp("8:00");
		vitaldata1.setValue(2.37);
		//vitaldata1.setVitalDataType(HEARTRATE);
		
		VitalData vitaldata2 = new VitalData();
		vitaldata2.setTimestamp("9:00");
		vitaldata2.setValue(3.77);
		
		VitalData vitaldata3 = new VitalData();
		vitaldata3.setTimestamp("9:01");
		vitaldata3.setValue(100.0);
		
		VitalDataDAO.add(vitaldata1);
		VitalDataDAO.add(vitaldata2);
		VitalDataDAO.add(vitaldata3);
		
		
		List<VitalData> vitaldatalist1 = Arrays.asList(vitaldata1,vitaldata2,vitaldata3);
		List<VitalData> vitaldatalist2 = Arrays.asList(vitaldata1,vitaldata2);
		List<VitalData> vitaldatalist3 = Arrays.asList(vitaldata3);
		
		Patient pat = new Patient();
		pat.setBloodtype("AB+");
		RegistrationDAO.createPatient(pat);
	
		Case pcase1 = new Case("Erster Fall"	,"Wichtige Info zum ersten Fall");
		Case pcase2 = new Case("Impfpass"		,"Alle wichtigen Impfungen");
		Case pcase3 = new Case("Knochenbruch"	,"nach Verkehrsunfall");
		
		//Zuordnungen
		pcase1.setVitaldata(vitaldatalist1);
		pcase2.setVitaldata(vitaldatalist2);
		pcase3.setVitaldata(vitaldatalist3);
		
		vitaldata1.setPcase(pcase1);
		vitaldata1.setPcase(pcase2);
		vitaldata2.setPcase(pcase1);
		vitaldata2.setPcase(pcase2);
		vitaldata3.setPcase(pcase3);
		
		pcase1.setPatient(pat);
		pcase2.setPatient(pat);
		pcase3.setPatient(pat);
	
		String feedbackCC1 = CaseDAO.createCase(pcase1);
			Assert.assertEquals("success", feedbackCC1);
		String feedbackCC2 = CaseDAO.createCase(pcase2);
			Assert.assertEquals("success", feedbackCC2);
		String feedbackCC3 = CaseDAO.createCase(pcase3);
			Assert.assertEquals("success", feedbackCC3);
			
		// Vitaldaten aus der Datenbank abrufen
		VitalData vital = VitalDataDAO.getVitalData(2);
		Assert.assertEquals("9:00", vital.getTimestamp());
		assertEquals ( 3.77 , vital.getValue(), 0);
		
		
		//Aufrufen der Vitaldaten über den Case
		Case case1 = CaseDAO.getCase(1);
		List<VitalData> compareme = Arrays.asList(vitaldata1,vitaldata2,vitaldata3);
		//Assert.assertEquals(case1.getVitaldata(), compareme);
		System.out.println(compareme.get(1).getValue());
		System.out.println(case1.getTitle());
		System.out.println(case1.getVitaldata().get(1).getValue());
		
		/*// Fälle vom Patient abrufen
		List<VitalData> compareme = Arrays.asList(vitaldata1,vitaldata2,vitaldata3);
		List<VitalData> vitaldatacase1 = case1.getVitaldata();
				
				int i = 0;
				for (VitalData v : compareme){
					Assert.assertEquals(v.getTimestamp()	, vitaldatacase1.get(i).getTimestamp());
					Assert.assertEquals(v.getValue()		, vitaldatacase1.get(i).getValue());
					i++;
				}*/
		
		
		
	}
}
