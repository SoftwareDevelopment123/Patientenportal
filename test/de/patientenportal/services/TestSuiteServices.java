package de.patientenportal.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
		AccessWSTest.class,				
		AuthenticationTest.class,
		CaseWSTest.class,
		OfficeWSTest.class,
		PatientRelativeTest.class,
		VitalDataWSTest.class,
		DoctorTest.class,
		RightsWSTest.class,
		//MedicationWSTest.class,
		})								


public class TestSuiteServices {

}
