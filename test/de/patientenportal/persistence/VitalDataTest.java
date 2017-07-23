package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VitalDataTest {

	@Test
	public void main() throws ParseException{
		
	//Vitaldaten anlegen
		Case newcase = new Case("Erster Fall"	, "Wichtige Info zum ersten Fall");
		
		String feedbackCC1 = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", feedbackCC1);
			Date timestamp1 = ClientHelper.parseStringtoTimeStamp("01.12.2016 14:05");
			Date timestamp2 = ClientHelper.parseStringtoTimeStamp("03.12.2016 16:25");
			Date timestamp3 = ClientHelper.parseStringtoTimeStamp("05.12.2016 18:09");
			
			VitalData vitaldata1 = new VitalData(timestamp1	,4.5, VitalDataType.BLOODSUGAR	, newcase);
			VitalData vitaldata2 = new VitalData(timestamp2	,5.0, VitalDataType.BLOODSUGAR	, newcase);
			VitalData vitaldata3 = new VitalData(timestamp3	,6.5, VitalDataType.BLOODSUGAR	, newcase);
			VitalData vitaldata4 = new VitalData(timestamp3	,86.0,VitalDataType.WEIGHT		, newcase);
			VitalData vitaldata5 = new VitalData(timestamp3	,70.0,VitalDataType.HEARTRATE	, newcase);
		
			List<VitalData> compareme = Arrays.asList(vitaldata1, vitaldata2, vitaldata3, vitaldata4, vitaldata5);

		String feedbackCV1 = VitalDataDAO.add(vitaldata1);
			Assert.assertEquals("success", feedbackCV1);
		String feedbackCV2 = VitalDataDAO.add(vitaldata2);
			Assert.assertEquals("success", feedbackCV2);
		String feedbackCV3 = VitalDataDAO.add(vitaldata3);	
			Assert.assertEquals("success", feedbackCV3);
		String feedbackCV4 = VitalDataDAO.add(vitaldata4);	
			Assert.assertEquals("success", feedbackCV4);
		String feedbackCV5 = VitalDataDAO.add(vitaldata5);	
			Assert.assertEquals("success", feedbackCV5);
		
	// Vitaldaten abrufen
	VitalData vital = VitalDataDAO.getVitalData(vitaldata2.getVitalDataID());
		Assert.assertEquals(vitaldata2.getVitalDataID()		 	, vital.getVitalDataID());
		Assert.assertEquals(vitaldata2.getTimestamp()		 	, vital.getTimestamp());
		Assert.assertEquals(vitaldata2.getValue()		 		, vital.getValue());
		Assert.assertEquals(vitaldata2.getVitalDataType()		, vital.getVitalDataType());
		Assert.assertEquals(vitaldata2.getPcase().getCaseID()	, vital.getPcase().getCaseID());
		Assert.assertEquals(vitaldata2.getPcase().getTitle()	, vital.getPcase().getTitle());
	
	//Aufrufen der Vitaldaten über den Case
	Case getcase = CaseDAO.getCase(newcase.getCaseID());
				
	//Vitaldaten überprüfen vom Patient abrufen
		List<VitalData> vitaldatas = getcase.getVitaldata();
	
		int i = 0;
		for (VitalData v : compareme){
			Assert.assertEquals(v.getVitalDataID()	, vitaldatas.get(i).getVitalDataID());
			Assert.assertEquals(v.getTimestamp()	, vitaldatas.get(i).getTimestamp());
			Assert.assertEquals(v.getValue()		, vitaldatas.get(i).getValue());
			Assert.assertEquals(v.getVitalDataType(), vitaldatas.get(i).getVitalDataType());
			i++;
		}
	
	// Vitaldaten Updaten (inkl. Abruf über den Case)
	VitalData vitaldatatoupdate = VitalDataDAO.getVitalData(vitaldata1.getVitalDataID());
		vitaldatatoupdate.setVitalDataType(VitalDataType.WEIGHT);
		vitaldatatoupdate.setTimestamp(timestamp2);
		vitaldatatoupdate.setValue(1.00001);
			
	String responseU = VitalDataDAO.updateVitalData(vitaldatatoupdate);
		Assert.assertEquals("success", responseU);
			
	VitalData updatedvitaldata = VitalDataDAO.getVitalData(vitaldata1.getVitalDataID());
		Assert.assertEquals(vitaldatatoupdate.getVitalDataType(), updatedvitaldata.getVitalDataType());
		Assert.assertEquals(vitaldatatoupdate.getTimestamp()	, updatedvitaldata.getTimestamp());
		Assert.assertEquals(vitaldatatoupdate.getValue()		, updatedvitaldata.getValue());

	VitalData changeme = compareme.get(0);
		changeme.setVitalDataType(VitalDataType.WEIGHT);
		changeme.setTimestamp(timestamp2);
		changeme.setValue(1.00001);
			
		List <VitalData> newlist = CaseDAO.getCase(newcase.getCaseID()).getVitaldata();
			
		i = 0;
		for (VitalData v : compareme){
			Assert.assertEquals(v.getVitalDataID()	, newlist.get(i).getVitalDataID());
			Assert.assertEquals(v.getTimestamp()	, newlist.get(i).getTimestamp());
			Assert.assertEquals(v.getValue()		, newlist.get(i).getValue());
			Assert.assertEquals(v.getVitalDataType(), newlist.get(i).getVitalDataType());
			i++;
		}
	
	//Vitaldaten Löschen
	String responseD = VitalDataDAO.deleteVitalData(vitaldata1.getVitalDataID());
		Assert.assertEquals("success", responseD);
		
	VitalData deletedvitaldata = VitalDataDAO.getVitalData(vitaldata1.getVitalDataID());
		Assert.assertNull(deletedvitaldata);	
	}
}
