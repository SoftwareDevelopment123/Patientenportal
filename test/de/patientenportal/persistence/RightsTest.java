package de.patientenportal.persistence;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;


public class RightsTest {

	@Test
	public void main(){
		
		// Case und Rechte anlegen
		Case newcase = new Case();
			newcase.setTitle("Impf-Pass");
			newcase.setDescription("Zusammenstellung aller Impfungen");
			newcase.setStatus(true);
			
		Doctor D1 = new Doctor("Hausarzt");
		Doctor D2 = new Doctor("Zahnarzt");
		Relative R1 = new Relative();
		Relative R2 = new Relative();
			String feedbackCD1 = RegistrationDAO.createDoctor(D1);
				Assert.assertEquals("success", feedbackCD1);
			String feedbackCD2 = RegistrationDAO.createDoctor(D2);
				Assert.assertEquals("success", feedbackCD2);
			String feedbackCRe1 = RegistrationDAO.createRelative(R1);
				Assert.assertEquals("success", feedbackCRe1);
			String feedbackCRe2 = RegistrationDAO.createRelative(R2);
				Assert.assertEquals("success", feedbackCRe2);
		
		String feedbackCC = CaseDAO.createCase(newcase);
			Assert.assertEquals("success", feedbackCC);	
		
		Rights r1 = new Rights(newcase, D1,null,true,true);
		Rights r2 = new Rights(newcase, D2,null,true,false);
		Rights r3 = new Rights(newcase, null,R1,true,false);
		Rights r4 = new Rights(newcase, null,R2,true,false);

		String feedbackCR1 = RightsDAO.createRight(r1);
			Assert.assertEquals("success", feedbackCR1);
		String feedbackCR2 = RightsDAO.createRight(r2);
			Assert.assertEquals("success", feedbackCR2);
		String feedbackCR3 = RightsDAO.createRight(r3);
			Assert.assertEquals("success", feedbackCR3);
		String feedbackCR4 = RightsDAO.createRight(r4);
			Assert.assertEquals("success", feedbackCR4);

		// Case-Rechte ändern (neuer Doktor kommt dazu)
		Doctor D3 = new Doctor("Notarzt");
			String feedbackCD3 = RegistrationDAO.createDoctor(D3);
				Assert.assertEquals("success", feedbackCD3);
		
				Case rUpdate = CaseDAO.getCase(1);
		Rights r5 = new Rights(rUpdate, D3,null,true,true);
			String feedbackCR5 = RightsDAO.createRight(r5);
				Assert.assertEquals("success", feedbackCR5);

		List<Rights> compareme = Arrays.asList(r1,r2,r3,r4,r5);
		int listsize = compareme.size();
				
		// Rechte zum Fall abrufen
		Case changedCase = CaseDAO.getCase(1);
		List<Rights> rlist = RightsDAO.getRights(changedCase.getCaseID());
		
		int i = 0;
		for (Rights r : compareme){
			Assert.assertEquals(r.getRightID()						,	rlist.get(i).getRightID());
			Assert.assertEquals(r.getPcase()	.getCaseID()		,	rlist.get(i).getPcase()		.getCaseID());
			
			if (r.getDoctor() != null){
			Assert.assertEquals(r.getDoctor()	.getDoctorID()		,	rlist.get(i).getDoctor()	.getDoctorID());
			Assert.assertEquals(r.getDoctor()	.getSpecialization(),	rlist.get(i).getDoctor()	.getSpecialization());}
			
			if (r.getRelative() != null){
			Assert.assertEquals(r.getRelative()	.getRelativeID()	,	rlist.get(i).getRelative()	.getRelativeID());}
			i++;
		}
		
		// Ausgabe-Test
		/*System.out.println("Zugewiesene Rechte - Fall " + changedCase.getCaseID());
			for (Rights right : rlist){
				System.out.print("Recht-ID: " + right.getRightID());
				System.out.print(" / Fall-ID: " + right.getPcase().getCaseID());
				if (right.getDoctor() 	!= null) 	{System.out.print(" / Doctor-ID   " + right.getDoctor().getDoctorID());}
				if (right.getRelative() != null) 	{System.out.print(" / Relative-ID " + right.getRelative().getRelativeID());}
				System.out.print(" / Leserecht: " + right.isrRight());
				System.out.println(" / Schreibrecht: " + right.iswRight());
			}*/
		
		// Recht ändern (wRight setzen)
		Rights changeme = rlist.get(2);
			changeme.setwRight(true);
		
		String feedbackUR = RightsDAO.updateRight(changeme);
			Assert.assertEquals("success", feedbackUR);
		
		List<Rights> changedList = RightsDAO.getRights(1);
			Assert.assertTrue(changedList.get(2).iswRight());
		
		// Recht entfernen
		String feedbackDR = RightsDAO.removeRight(1);
			Assert.assertEquals("success", feedbackDR);
		listsize--;
		
		List<Rights> smallerList = RightsDAO.getRights(1);
			Assert.assertEquals(listsize, smallerList.size());

			
		// Clearing Up DB
		DoctorDAO.deleteDoctor(1);
		DoctorDAO.deleteDoctor(2);
		DoctorDAO.deleteDoctor(3);
		RelativeDAO.deleteRelative(1);
		RelativeDAO.deleteRelative(2);
		
	}

}
