package de.patientenportal.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * Funktioniert gerade nicht, alle Tests müssen vollständig aufräumen, damit das klappt
 */

@RunWith(Suite.class)
@SuiteClasses({
		UserActorTest.class,				// x
		PatientRelativeTest.class,			// x
		DoctorOfficeTest.class,				// x
		BasicCaseTest.class,				// x
		PatientCaseTest.class,				// x
		MedicineMedicationTest.class,		// x
		VitalDataTest.class,				// x
		/*DocumentsTest.class,*/			// fehlt noch
		RightsTest.class,					// x
		AccessTest.class})					// x				

public class TestSuite {

}
