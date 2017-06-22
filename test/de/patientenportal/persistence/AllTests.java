package de.patientenportal.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * Funktioniert gerade nicht, alle Tests müssen vollständig aufräumen, damit das klappt
 */


@RunWith(Suite.class)
@SuiteClasses({
		UserCRUDTest.class,
		CaseCRUDTest.class,
		PatientRelativeTest.class,
		DoctorOfficeCRUDTest.class,
		RightsTest.class,
		AccessTest.class})

public class AllTests {

}
