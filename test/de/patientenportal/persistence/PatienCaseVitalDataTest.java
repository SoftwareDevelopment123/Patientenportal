package de.patientenportal.persistence;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.patientenportal.entities.PatientCase;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;



public class PatienCaseVitalDataTest {
	
	@Test
	public void addPatientTest() throws Exception{
		
		
		//Vitaldaten anlegen
		Set<VitalData> vitalDatas = new HashSet<VitalData>();
		vitalDatas.add(new VitalData("12:30 Uhr,05.06.2017",4.5, VitalDataType.BLOODSUGAR));
		//vitalDatas.add(new VitalData("13:30 Uhr,05.06.2017",6.5, VitalDataType.BLOODSUGAR));
		//vitalDatas.add(new VitalData("13:30 Uhr,05.06.2017",86.5, VitalDataType.WEIGHT));
		
		PatientCase patientcase = new PatientCase("Kreuzbandriss",true);
		PatientCase patientcase2 = new PatientCase("Mittelohrentzündung",true);
		
		patientcase.setVitaldatas(vitalDatas);
		patientcase2.setVitaldatas(vitalDatas);
		
		//VitalDataDAO.add(vitalData);
		PatientCaseDAO.add(patientcase);
		PatientCaseDAO.add(patientcase2);
}
}