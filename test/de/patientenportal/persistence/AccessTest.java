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
		
		//F�lle und Rechte (f�r Doktoren) anlegen
		Case case1 = new Case("Impf-Pass"		,"Zusammenstellung aller Impfungen");
		Case case2 = new Case("Knie-Operation"	,"Nach einem b�sen Unfall n�tig");
		Case case3 = new Case("Herzinfarkt"		,"Oh oh oh ...");
		Case case4 = new Case("Knochenbruch"	,"Am Arm, Sturz bei schlechtem Wetter");
		Case case5 = new Case("Erk�ltung"		,"Was gibts da zu sagen?");

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
		
		// Einsehbare F�lle zum Doktor abrufen
		List <Case> d1cases = RightsDAO.getDocRCases(D1.getDoctorID());
			
			int i = 0;
			for (Case cases : d1CompareList){				
				Assert.assertEquals(cases.getCaseID()		,	d1cases.get(i).getCaseID());
				Assert.assertEquals(cases.getTitle()		,	d1cases.get(i).getTitle());
				Assert.assertEquals(cases.getDescription()	,	d1cases.get(i).getDescription());
				i++;
			}
		
		// checkWRights-Test f�r Doktoren
			// Existierende Kombinationen (doctorID, caseID)
			Assert.assertTrue	(RightsDAO.checkDocWRight(D1.getDoctorID() , case1.getCaseID()));
			Assert.assertFalse	(RightsDAO.checkDocWRight(D1.getDoctorID() , case2.getCaseID()));
			Assert.assertFalse	(RightsDAO.checkDocWRight(D1.getDoctorID() , case4.getCaseID()));
			Assert.assertTrue	(RightsDAO.checkDocWRight(D1.getDoctorID() , case5.getCaseID()));
			Assert.assertFalse	(RightsDAO.checkDocWRight(D2.getDoctorID() , case3.getCaseID()));
			Assert.assertFalse	(RightsDAO.checkDocWRight(D2.getDoctorID() , case5.getCaseID()));

			// ung�ltige Kombinationen (sollen auch einfach False zur�ckgben)
			Assert.assertFalse	(RightsDAO.checkDocWRight(D1.getDoctorID()	, case3.getCaseID()));
			Assert.assertFalse	(RightsDAO.checkDocWRight(5					, 55));
			Assert.assertFalse	(RightsDAO.checkDocWRight(188				, 23));
	
		// Ausgabe-Test
		/*System.out.println("Alle einsehbaren F�lle von Doktor 1");
		List<Case> readingD = RightsDAO.getDocRCases(1);
			for (Case cases : readingD){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}*/

		/*
		 *  F�r die Relative-Entity ist das Vorgehen genau dasselbe, daher wird der Test hier stark verk�rzt
		 */
			
		Relative R1 = new Relative();
		RegistrationDAO.createRelative(R1);
			
		Rights r7 = new Rights(case1, null,R1,true,true);
		Rights r8 = new Rights(case2, null,R1,true,false);
		RightsDAO.createRight(r7);
		RightsDAO.createRight(r8);
		
		List<Case> r1CompareList = Arrays.asList(case1,case2);
		
		List <Case> r1cases = RightsDAO.getRelRCases(R1.getRelativeID());
		
		i = 0;
		for (Case cases : r1CompareList){				
			Assert.assertEquals(cases.getCaseID()		,	r1cases.get(i).getCaseID());
			Assert.assertEquals(cases.getTitle()		,	r1cases.get(i).getTitle());
			Assert.assertEquals(cases.getDescription()	,	r1cases.get(i).getDescription());
			i++;
		}
		
		Assert.assertTrue	(RightsDAO.checkRelWRight(R1.getRelativeID() , case1.getCaseID()));
		Assert.assertFalse	(RightsDAO.checkRelWRight(R1.getRelativeID() , case2.getCaseID()));
		Assert.assertFalse	(RightsDAO.checkRelWRight(R1.getRelativeID() , case3.getCaseID()));
		
		// Ausgabe-Test
		/*System.out.println("/////////////////////////////////////");
		System.out.println("Alle einsehbaren F�lle von Relative 1");
		List<Case> readingR = RightsDAO.getRelRCases(1);
			for (Case cases : readingR){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}*/
		
	}

}
