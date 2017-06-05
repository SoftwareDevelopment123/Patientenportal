package de.patientenportal.persistence;

import org.junit.Test;

import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;


public class VitalDataTest {

	@Test
	public void addVitalDataTest() throws Exception{
		
		
		//Vitaldaten anlegen
		VitalData vitalData = new VitalData("12:30 Uhr,05.06.2015",82.5, VitalDataType.BLOODSUGAR);
		
		
		VitalDataDAO.add(vitalData);
		
}
}
