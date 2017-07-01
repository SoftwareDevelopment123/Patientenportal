package de.patientenportal.persistence;

import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;
import org.junit.*;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

@SuppressWarnings("unused")
public class BasicCaseTest {
	
	@Test
	public void main() {
		
		// Case anlegen
		Case newcase = new Case();
			newcase.setTitle("Kreuzbandriss");
			newcase.setDescription("Hier könnte ihre Werbung stehen!");
			newcase.setStatus(true);
		
		String responseC = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", responseC);
						
		// Case abrufen
		Case getcase = CaseDAO.getCase(newcase.getCaseID());
			Assert.assertEquals("Kreuzbandriss", getcase.getTitle());
			Assert.assertEquals("Hier könnte ihre Werbung stehen!", getcase.getDescription());
			Assert.assertEquals(true, getcase.isStatus());		
								
		// Case updaten			
		Case casetoupdate = CaseDAO.getCase(newcase.getCaseID());
			casetoupdate.setTitle("Herzoperation");
			
			String responseU = CaseDAO.updateCase(casetoupdate);
				Assert.assertEquals("success", responseU);
			
			Case getcaseU = CaseDAO.getCase(newcase.getCaseID());
				Assert.assertEquals("Herzoperation", getcaseU.getTitle());
		
		// Case löschen
		String responseD = CaseDAO.deleteCase(newcase.getCaseID());
			Assert.assertEquals("success", responseD);
			
		Case deletedcase = CaseDAO.getCase(newcase.getCaseID());
			Assert.assertNull(deletedcase);
	}

}