package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;


public class RightsTest {

	@Test
	public void main(){
		
		// Case anlegen (inkl. Rechte)
		Case newcase = new Case();
			newcase.setTitle("Impfliste");
			newcase.setDescription("Zusammenstellung aller Impfungen");
			newcase.setStatus(true);
		
		Rights r1 = new Rights(1,0,true,true);
		Rights r2 = new Rights(2,0,true,false);
		Rights r3 = new Rights(0,1,true,false);
		Rights r4 = new Rights(0,2,true,false);
		List<Rights> rights = new ArrayList<Rights>(Arrays.asList(r1,r2,r3,r4));
			newcase.setRights(rights);
			
		String feedbackCC = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", feedbackCC);
			
		// Case-Rechte ändern
		Case rUpdate = CaseDAO.getCase(1);
			rUpdate.getRights().get(0).setDoctorID(5);
				
		String feedbackUC1 = CaseDAO.updateCase(rUpdate);
			Assert.assertEquals("success", feedbackUC1);
			
		// Case-Rechte-Zuordnung entfernen (Eintrag bleibt bestehen und muss manuell gelöscht werden)
		// Der Test kann eigentlich weg, bzw. muss zusammen mit der RightsDAO getestet werden
		Case rRemove = CaseDAO.getCase(1);
			rRemove.getRights().remove(2);
	
		String feedbackUC2 = CaseDAO.updateCase(rRemove);
			Assert.assertEquals("success", feedbackUC2);
	}

}
