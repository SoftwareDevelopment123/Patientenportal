package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;


public class RightsTest {

	@Test
	public void main(){
		
		// Case anlegen (inkl. Rechte)
		Case newcase = new Case();
			newcase.setTitle("Impf-Pass");
			newcase.setDescription("Zusammenstellung aller Impfungen");
			newcase.setStatus(true);
		
		// kleiner Zusatztest für unten
				VitalData vitaldata1 = new VitalData("früh",4.5,VitalDataType.BLOODSUGAR);
				List<VitalData> vitaldatas = Arrays.asList(vitaldata1);
			newcase.setVitaldata(vitaldatas);
			
		Doctor D1 = new Doctor("Hausarzt");
		Doctor D2 = new Doctor("Zahnarzt");
		Relative R1 = new Relative();
		Relative R2 = new Relative();
			RegistrationDAO.createDoctor(D1);
			RegistrationDAO.createDoctor(D2);
			RegistrationDAO.createRelative(R1);
			RegistrationDAO.createRelative(R2);
		
		Rights r1 = new Rights(newcase, D1,null,true,true);
		Rights r2 = new Rights(newcase, D2,null,true,false);
		Rights r3 = new Rights(newcase, null,R1,true,false);
		Rights r4 = new Rights(newcase, null,R2,true,false);
		List<Rights> rights = new ArrayList<Rights>(Arrays.asList(r1,r2,r3,r4));
			newcase.setRights(rights);
			
		String feedbackCC = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", feedbackCC);
			
		// Case-Rechte ändern (neuer Doktor kommt dazu)
		Doctor D3 = new Doctor("Notarzt");
			RegistrationDAO.createDoctor(D3);
			
		Case rUpdate = CaseDAO.getCase(1);
		Rights r5 = new Rights(newcase, D3,null,true,true);
			rUpdate.getRights().add(r5);
				
		String feedbackUC1 = CaseDAO.updateCase(rUpdate);
			Assert.assertEquals("success", feedbackUC1);
			
		// Case abrufen (inkl. Rechte)
		Case changedCase = CaseDAO.getCase(1);
		List<Rights> rlist = changedCase.getRights();
		
			Assert.assertEquals(1,rlist.get(0).getDoctor().getDoctorID());
			Assert.assertEquals(2,rlist.get(1).getDoctor().getDoctorID());
			Assert.assertEquals(1,rlist.get(2).getRelative().getRelativeID());
			Assert.assertEquals(2,rlist.get(3).getRelative().getRelativeID());
			Assert.assertEquals(3,rlist.get(4).getDoctor().getDoctorID());
			
			Assert.assertEquals("Hausarzt", rlist.get(0).getDoctor().getSpecialization());
			
		/*// Case-Rechte-Zuordnung entfernen (Eintrag bleibt bestehen und muss manuell gelöscht werden)
		// Der Test kann eigentlich weg, bzw. muss zusammen mit der RightsDAO getestet werden
		Case rRemove = CaseDAO.getCase(1);
			rRemove.getRights().remove(2);
			
		String feedbackUC2 = CaseDAO.updateCase(rRemove);
			Assert.assertEquals("success", feedbackUC2);*/
		
		// Rechte-Abfrage (für den Doktor)
		List<Case> reading = RightsDAO.getDocRCases(2);
			for (Case cases : reading){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
				System.out.println(CaseDAO.getCase(cases.getCaseID()).getVitaldata().get(0).getTimestamp());
			}
	}

}
