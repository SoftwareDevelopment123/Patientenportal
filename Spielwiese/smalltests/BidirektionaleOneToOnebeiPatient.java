package smalltests;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.*;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;


public class BidirektionaleOneToOnebeiPatient {

	@Test
	public void main() throws ParseException {
		
		//User anlegen (Patient)
		User neu = new User();
			neu.setUsername("staps");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Stupser");
			neu.setFirstname("Staps");
			Date geburtstag = ClientHelper.parseStringtoDate("04.12.1991");
			neu.setBirthdate(geburtstag);
			neu.setGender(Gender.MALE);
			
		Address neuA = new Address();
			neuA.setCity("Stapshausen");
			neuA.setNumber("1a");
			neuA.setPostalCode("01234");
			neuA.setStreet("Superstrasse");
			
		Contact neuC = new Contact();
			neuC.setEmail("anderemail.als@oben.com");
			neuC.setMobile("01731234567");
			neuC.setPhone("03512646152");
			
		Patient neuP = new Patient();
			neuP.setBloodtype("ABC");
			neuP.setUser(neu);
			
		Doctor neuD = new Doctor();
			neuD.setSpecialization("Kardiologe");
			neuD.setUser(neu);
			
		Relative neuR = new Relative();
			neuR.setUser(neu);
			
			neu.setAddress(neuA);
			neu.setContact(neuC);
			neu.setPatient(neuP);
			neu.setDoctor(neuD);
			neu.setRelative(neuR);
			
		//User in der Datenbank speichern
		RegistrationDAO.createUser(neu);
		//User aus der Datenbank abrufen
				User user = UserDAO.getUser(1);
				
				//Werte-Abgleich
				
				
				Assert.assertEquals("Staps", user.getPatient().getUser().getFirstname());
				Assert.assertEquals("Stupser", user.getDoctor().getUser().getLastname());
				
	}
}
