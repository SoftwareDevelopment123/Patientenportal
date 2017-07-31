package de.patientenportal.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserActorTest.class, // x
		PatientRelativeTest.class, // x
		DoctorOfficeTest.class, // x
		BasicCaseTest.class, // x
		PatientCaseTest.class, // x
		MedicineMedicationTest.class, // x
		VitalDataTest.class, // x

		// MDocTest.class, funktionieren nur mit den nötigen Dateien auf dem
		// PC// x
		// InDocTest.class, // x
		RightsTest.class, // x
		AccessTest.class }) // x

public class TestSuitePersistence {

}
