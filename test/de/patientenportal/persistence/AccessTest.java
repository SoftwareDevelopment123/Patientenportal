package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.patientenportal.entities.Access;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.Rights;

public class AccessTest {

	@Test
	public void main() {

		// Fälle und Rechte (für Doktoren) anlegen
		Case case1 = new Case("Impf-Pass", "Zusammenstellung aller Impfungen");
		Case case2 = new Case("Knie-Operation", "Nach einem bösen Unfall nötig");
		Case case3 = new Case("Herzinfarkt", "Oh oh oh ...");
		Case case4 = new Case("Knochenbruch", "Am Arm, Sturz bei schlechtem Wetter");
		Case case5 = new Case("Erkältung", "Was gibts da zu sagen?");

		// Test zum Massen-Anlegen
		List<Case> d1CompareList = Arrays.asList(case1, case2, case4, case5);
		List<Case> cCList = new ArrayList<Case>();
		cCList.addAll(d1CompareList);
		cCList.add(case3);

		Doctor D1 = new Doctor("Hausarzt");
		RegistrationDAO.createDoctor(D1);
		Doctor D2 = new Doctor("Kardiologe");
		RegistrationDAO.createDoctor(D2);

		Rights r1 = new Rights(case1, D1, null, true, true);
		Rights r2 = new Rights(case2, D1, null, true, false);
		Rights r3 = new Rights(case3, D2, null, true, false);
		Rights r4 = new Rights(case4, D1, null, true, false);
		Rights r5 = new Rights(case5, D1, null, true, true);
		Rights r6 = new Rights(case5, D2, null, true, false);

		List<Rights> cRList = Arrays.asList(r1, r2, r3, r4, r5, r6);

		for (Case c : cCList) {
			CaseDAO.createCase(c);
		}

		for (Rights r : cRList) {
			RightsDAO.createRight(r);
		}

		// Einsehbare Fälle zum Doktor abrufen
		List<Case> d1cases = RightsDAO.getDocRCases(D1.getDoctorID());

		int i = 0;
		for (Case cases : d1CompareList) {
			Assert.assertEquals(cases.getCaseID(), d1cases.get(i).getCaseID());
			Assert.assertEquals(cases.getTitle(), d1cases.get(i).getTitle());
			Assert.assertEquals(cases.getDescription(), d1cases.get(i).getDescription());
			i++;
		}

		// checkWRights-Test für Doktoren
		// Existierende Kombinationen (doctorID, caseID)
		Assert.assertTrue(RightsDAO.checkDocRight(D1.getDoctorID(), case1.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(D1.getDoctorID(), case2.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(D1.getDoctorID(), case4.getCaseID(), Access.WriteCase));
		Assert.assertTrue(RightsDAO.checkDocRight(D1.getDoctorID(), case5.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(D2.getDoctorID(), case3.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(D2.getDoctorID(), case5.getCaseID(), Access.WriteCase));

		// ungültige Kombinationen (sollen auch einfach False zurückgben)
		Assert.assertFalse(RightsDAO.checkDocRight(D1.getDoctorID(), case3.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(5, 55, Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkDocRight(188, 23, Access.WriteCase));

		// Ausgabe-Test
		/*
		 * System.out.println("Alle einsehbaren Fälle von Doktor 1"); List<Case>
		 * readingD = RightsDAO.getDocRCases(1); for (Case cases : readingD){
		 * System.out.println("----------------------------------");
		 * System.out.println(cases.getCaseID());
		 * System.out.println(cases.getTitle());
		 * System.out.println(cases.getDescription()); }
		 */

		/*
		 * Für die Relative-Entity ist das Vorgehen genau dasselbe, daher wird
		 * der Test hier stark verkürzt
		 */

		Relative R1 = new Relative();
		RegistrationDAO.createRelative(R1);

		Rights r7 = new Rights(case1, null, R1, true, true);
		Rights r8 = new Rights(case2, null, R1, true, false);
		RightsDAO.createRight(r7);
		RightsDAO.createRight(r8);

		List<Case> r1CompareList = Arrays.asList(case1, case2);

		List<Case> r1cases = RightsDAO.getRelRCases(R1.getRelativeID());

		i = 0;
		for (Case cases : r1CompareList) {
			Assert.assertEquals(cases.getCaseID(), r1cases.get(i).getCaseID());
			Assert.assertEquals(cases.getTitle(), r1cases.get(i).getTitle());
			Assert.assertEquals(cases.getDescription(), r1cases.get(i).getDescription());
			i++;
		}

		Assert.assertTrue(RightsDAO.checkRelRight(R1.getRelativeID(), case1.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkRelRight(R1.getRelativeID(), case2.getCaseID(), Access.WriteCase));
		Assert.assertFalse(RightsDAO.checkRelRight(R1.getRelativeID(), case3.getCaseID(), Access.WriteCase));

		// Ausgabe-Test
		/*
		 * System.out.println("/////////////////////////////////////");
		 * System.out.println("Alle einsehbaren Fälle von Relative 1");
		 * List<Case> readingR = RightsDAO.getRelRCases(1); for (Case cases :
		 * readingR){ System.out.println("----------------------------------");
		 * System.out.println(cases.getCaseID());
		 * System.out.println(cases.getTitle());
		 * System.out.println(cases.getDescription()); }
		 */

	}

}
