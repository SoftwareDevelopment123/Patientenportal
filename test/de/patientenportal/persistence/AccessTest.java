package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.Rights;


public class AccessTest {

	@Test
	public void main() {
		
		//Fälle und Rechte (für Doktoren) anlegen
		Case case1 = new Case("Impf-Pass"		,"Zusammenstellung aller Impfungen");
		Case case2 = new Case("Knie-Operation"	,"Nach einem bösen Unfall nötig");
		Case case3 = new Case("Herzinfarkt"		,"Oh oh oh ...");
		Case case4 = new Case("Knochenbruch"	,"Am Arm, Sturz bei schlechtem Wetter");
		Case case5 = new Case("Erkältung"		,"Was gibts da zu sagen?");

		Doctor D1 = new Doctor("Hausarzt");
			RegistrationDAO.createDoctor(D1);
		Doctor D2 = new Doctor("Kardiologe");
			RegistrationDAO.createDoctor(D2);

		Rights r1 = new Rights(case1, D1,null,true,true);
		Rights r2 = new Rights(case2, D1,null,true,false);
		Rights r3 = new Rights(case3, D2,null,true,false);
		Rights r4 = new Rights(case4, D1,null,true,false);
		Rights r5 = new Rights(case5, D1,null,true,true);
		Rights r6 = new Rights(case5, D2,null,true,false);
		
		List<Case> d1CompareList = Arrays.asList(case1,case2,case4,case5);
		
		CaseDAO.createCase(case1);
		CaseDAO.createCase(case2);
		CaseDAO.createCase(case3);
		CaseDAO.createCase(case4);
		CaseDAO.createCase(case5);
		
		RightsDAO.createRight(r1);
		RightsDAO.createRight(r2);
		RightsDAO.createRight(r3);
		RightsDAO.createRight(r4);
		RightsDAO.createRight(r5);
		RightsDAO.createRight(r6);
		
		// Einsehbare Fälle zum Doktor abrufen
		List <Case> d1cases = RightsDAO.getDocRCases(1);
			
			int i = 0;
			for (Case cases : d1CompareList){				
				Assert.assertEquals(cases.getCaseID()		,	d1cases.get(i).getCaseID());
				Assert.assertEquals(cases.getTitle()		,	d1cases.get(i).getTitle());
				Assert.assertEquals(cases.getDescription()	,	d1cases.get(i).getDescription());
				i++;
			}
		
		// checkWRights-Test für Doktoren
			// Existierende Kombinationen (doctorID, caseID)
			Assert.assertTrue	(RightsDAO.checkDocWRight(1, 1));
			Assert.assertFalse	(RightsDAO.checkDocWRight(1, 2));
			Assert.assertFalse	(RightsDAO.checkDocWRight(1, 4));
			Assert.assertTrue	(RightsDAO.checkDocWRight(1, 5));
			Assert.assertFalse	(RightsDAO.checkDocWRight(2, 3));
			Assert.assertFalse	(RightsDAO.checkDocWRight(2, 5));

			// ungültige Kombinationen (sollen auch einfach False zurückgben)
			Assert.assertFalse	(RightsDAO.checkDocWRight(1, 3));
			Assert.assertFalse	(RightsDAO.checkDocWRight(5, 55));
			Assert.assertFalse	(RightsDAO.checkDocWRight(188, 23));
	
		// Ausgabe-Test
		/*System.out.println("Alle einsehbaren Fälle von Doktor 1");
		List<Case> readingD = RightsDAO.getDocRCases(1);
			for (Case cases : readingD){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}*/

		/*
		 *  Für die Relative-Entity ist das Vorgehen genau dasselbe, daher wird der Test hier stark verkürzt
		 */
		Relative R1 = new Relative();
		RegistrationDAO.createRelative(R1);
			
		Rights r7 = new Rights(case1, null,R1,true,true);
		Rights r8 = new Rights(case2, null,R1,true,false);
		RightsDAO.createRight(r7);
		RightsDAO.createRight(r8);
		
		List<Case> r1CompareList = Arrays.asList(case1,case2);
		
		List <Case> r1cases = RightsDAO.getRelRCases(1);
		
		i = 0;
		for (Case cases : r1CompareList){				
			Assert.assertEquals(cases.getCaseID()		,	r1cases.get(i).getCaseID());
			Assert.assertEquals(cases.getTitle()		,	r1cases.get(i).getTitle());
			Assert.assertEquals(cases.getDescription()	,	r1cases.get(i).getDescription());
			i++;
		}
		
		Assert.assertTrue	(RightsDAO.checkRelWRight(1, 1));
		Assert.assertFalse	(RightsDAO.checkRelWRight(1, 2));
		Assert.assertFalse	(RightsDAO.checkRelWRight(1, 3));
		
		// Ausgabe-Test
		/*System.out.println("/////////////////////////////////////");
		System.out.println("Alle einsehbaren Fälle von Relative 1");
		List<Case> readingR = RightsDAO.getRelRCases(1);
			for (Case cases : readingR){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}*/
		
	}

}
