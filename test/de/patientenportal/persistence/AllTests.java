package de.patientenportal.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		CaseCRUDTest.class,
		UserCRUDTest.class,
		DoctorOfficeCRUDTest.class,
		PatientRelativeCRUDTest.class,
		RightsTest.class})

public class AllTests {

}
