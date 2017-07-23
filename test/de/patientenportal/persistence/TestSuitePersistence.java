package de.patientenportal.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		UserActorTest.class,				// x
		PatientRelativeTest.class,			// x
		DoctorOfficeTest.class,				// x
		BasicCaseTest.class,				// x
		PatientCaseTest.class,				// x
		MedicineMedicationTest.class,		// x
		VitalDataTest.class,				// x
		
		//MDocTest.class,						// x
		//InDocTest.class,					// x
		/*DocumentsTest.class,*/			// fehlt noch
		RightsTest.class,					// x
		AccessTest.class})					// x				

public class TestSuitePersistence {

}
