package de.patientenportal.persistence;

import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;
import org.junit.*;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

@SuppressWarnings("unused")
public class CaseCRUDTest {
	
	@Test
	public void main() {
		
		// Case anlegen (inkl. Vitaldata)
		Case newcase = new Case();
			newcase.setTitle("Kreuzbandriss");
			newcase.setDescription("Hier könnte ihre Werbung stehen!");
			newcase.setStatus(true);
		
		String responseC = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", responseC);
			
		VitalData vitaldata1 = new VitalData("früh",4.5,VitalDataType.BLOODSUGAR,newcase);
		VitalData vitaldata2 = new VitalData("mittag",5.0,VitalDataType.BLOODSUGAR,newcase);
		VitalData vitaldata3 = new VitalData("abend",6.5,VitalDataType.BLOODSUGAR,newcase);
		VitalData vitaldata4 = new VitalData("früh",86.0,VitalDataType.WEIGHT,newcase);
		VitalData vitaldata5 = new VitalData("abend",70.0,VitalDataType.HEARTRATE,newcase);
					
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

		// Case abrufen
		Case getcase = CaseDAO.getCase(1);
			Assert.assertEquals("Kreuzbandriss", getcase.getTitle());
			Assert.assertEquals("Hier könnte ihre Werbung stehen!", getcase.getDescription());
			Assert.assertEquals(true, getcase.isStatus());		

			List<VitalData> vitaldatas= getcase.getVitaldata();
			
			int i = 0;
			for (VitalData v : compareme){
				Assert.assertEquals(v.getVitalDataID()	, vitaldatas.get(i).getVitalDataID());
				Assert.assertEquals(v.getTimestamp()	, vitaldatas.get(i).getTimestamp());
				Assert.assertEquals(v.getValue()		, vitaldatas.get(i).getValue());
				Assert.assertEquals(v.getVitalDataType(), vitaldatas.get(i).getVitalDataType());
				i++;
			}
								
		// Case updaten			
		Case casetoupdate = CaseDAO.getCase(1);
			casetoupdate.setTitle("Herzoperation");
			
			String responseU = CaseDAO.updateCase(casetoupdate);
				Assert.assertEquals("success", responseU);
			
			Case getcaseU = CaseDAO.getCase(1);
				Assert.assertEquals("Herzoperation", getcaseU.getTitle());
			
		// Case updaten (Vitaldata ändern) -- geändert, jetzt über VitalDataDAO
		VitalData v3 = VitalDataDAO.getVitalData(3);
			v3.setTimestamp("nachts");
		String feedbackUV1 = VitalDataDAO.updateVitalData(v3);	
			Assert.assertEquals("success", feedbackUV1);
		
		VitalData v4 = VitalDataDAO.getVitalData(4);
			v4.setValue(90.0);
		String feedbackUV2 = VitalDataDAO.updateVitalData(v4);	
			Assert.assertEquals("success", feedbackUV2);
		
		compareme.get(2).setTimestamp("nachts");		
		compareme.get(3).setValue(90.0);
		
		List <VitalData> newlist = CaseDAO.getCase(1).getVitaldata();
		
			i = 0;
			for (VitalData v : compareme){
				Assert.assertEquals(v.getVitalDataID()	, newlist.get(i).getVitalDataID());
				Assert.assertEquals(v.getTimestamp()	, newlist.get(i).getTimestamp());
				Assert.assertEquals(v.getValue()		, newlist.get(i).getValue());
				Assert.assertEquals(v.getVitalDataType(), newlist.get(i).getVitalDataType());
				i++;
			}
		
		// Case löschen
		String responseD = CaseDAO.deleteCase(1);
			Assert.assertEquals("success", responseD);
			
		Case deletedcase = CaseDAO.getCase(1);
			Assert.assertNull(deletedcase);

	}

}