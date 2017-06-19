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
		
				VitalData vitaldata1 = new VitalData("früh",4.5,VitalDataType.BLOODSUGAR);
				VitalData vitaldata2 = new VitalData("mittag",5.0,VitalDataType.BLOODSUGAR);
				VitalData vitaldata3 = new VitalData("abend",6.5,VitalDataType.BLOODSUGAR);
				VitalData vitaldata4 = new VitalData("früh",86.0,VitalDataType.WEIGHT);
				VitalData vitaldata5 = new VitalData("abend",70.0,VitalDataType.HEARTRATE);
					
				List<VitalData> vitaldatas = Arrays.asList(vitaldata1, vitaldata2, vitaldata3, vitaldata4, vitaldata5);
			newcase.setVitaldata(vitaldatas);
	
			String responseC = CaseDAO.createCase(newcase);
				Assert.assertEquals("success", responseC);
				
		// Case abrufen
		Case getcase = CaseDAO.getCase(1);
			Assert.assertEquals("Kreuzbandriss", getcase.getTitle());
			Assert.assertEquals("Hier könnte ihre Werbung stehen!", getcase.getDescription());
			Assert.assertEquals(true, getcase.isStatus());		

			List<VitalData> getvitaldatas= getcase.getVitaldata();
			
			/*for ( ListIterator<VitalData> it = getvitaldatas.listIterator(); it.hasNext(); ){
				VitalData vd = it.next();			
				System.out.println(vd.getVitalDataID() + " " + vd.getTimestamp() + " " + vd.getVitalDataType() + ": " + vd.getValue());
				System.out.println();
			}*/
				
			VitalData getvdata1 = getvitaldatas.get(0);
				Assert.assertEquals("früh", getvdata1.getTimestamp());
				Assert.assertEquals(4.5, getvdata1.getValue(), 0);
				Assert.assertEquals(VitalDataType.BLOODSUGAR, getvdata1.getVitalDataType());	
				
		// Case updaten			
		Case casetoupdate = CaseDAO.getCase(1);
			casetoupdate.setTitle("Herzoperation");
			
			String responseU = CaseDAO.updateCase(casetoupdate);
				Assert.assertEquals("success", responseU);
			
			Case getcaseU = CaseDAO.getCase(1);
				Assert.assertEquals("Herzoperation", getcaseU.getTitle());
			
		// Case updaten (Vitaldata ändern) soll ja wsl eh in ein eigenes DAO/WS
		Case vdchange = CaseDAO.getCase(1);
			vdchange.getVitaldata().get(3).setTimestamp("nachts");
			//vdchange.getVitaldata().remove(4);							//macht keinen Sinn da nur die Zuordnung entfernt wird
			
			String responseU2 = CaseDAO.updateCase(vdchange);
				Assert.assertEquals("success", responseU2);
			
		// Case löschen
		/*String responseD = CaseDAO.deleteCase(1);
			Assert.assertEquals("success", responseD);
			
		Case deletedcase = CaseDAO.getCase(1);
			Assert.assertNull(deletedcase);*/
	}

}