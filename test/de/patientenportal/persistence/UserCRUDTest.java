package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.*;

public class UserCRUDTest {

	@Test
	public void main() {
				
		//User anlegen (Patient)
		User neu = new User();
			neu.setUsername("staps");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Stupser");
			neu.setFirstname("Staps");
			neu.setBirthdate("01.01.1992");
			neu.setGender("male");
			
		Address neuA = new Address();
			neuA.setCity("Stapshausen");
			neuA.setNumber("1a");
			neuA.setPostalCode(01234);
			neuA.setStreet("Superstrasse");
			
		Contact neuC = new Contact();
			neuC.setEmail("anderemail.als@oben.com");
			neuC.setMobile("01731234567");
			neuC.setPhone("03512646152");
			
		Patient neuP = new Patient();
			neuP.setBloodtype("ABC");
			//neuP.setUser(neu);
			
		Doctor neuD = new Doctor();
			neuD.setSpecialization("Kardiologe");
			//neuD.setUser(neu);
			
		Relative neuR = new Relative();
			neuR.setUser(neu);

			neu.setAddress(neuA);
			neu.setContact(neuC);
			neu.setPatient(neuP);
			neu.setDoctor(neuD);
			neu.setRelative(neuR);
				
		//User in der Datenbank speichern
		UserDAO.add(neu);
		
		//User aus der Datenbank abrufen
		User user = UserDAO.getUser(1);
		
		//Werte-Abgleich
		Assert.assertEquals("staps", user.getUsername());
		Assert.assertEquals("pass", user.getPassword());
		Assert.assertEquals("stap.staptp@mustermail.com", user.getEmail());
		Assert.assertEquals("Stupser", user.getLastname());
		Assert.assertEquals("Staps", user.getFirstname());
		Assert.assertEquals("01.01.1992", user.getBirthdate());
		Assert.assertEquals("male", user.getGender());
		
		Assert.assertEquals("Stapshausen", user.getAddress().getCity());
		Assert.assertEquals("1a", user.getAddress().getNumber());
		Assert.assertEquals(01234, user.getAddress().getPostalCode());
		Assert.assertEquals("Superstrasse", user.getAddress().getStreet());
		
		Assert.assertEquals("anderemail.als@oben.com", user.getContact().getEmail());
		Assert.assertEquals("01731234567", user.getContact().getMobile());
		Assert.assertEquals("03512646152", user.getContact().getPhone());
		
		Assert.assertEquals("ABC", user.getPatient().getBloodtype());
		
		Assert.assertEquals("Kardiologe", user.getDoctor().getSpecialization());
			
		//Update-Test
		User userupdate = new User();
		userupdate.setUserId(1);
		userupdate.setLastname("Newname+");
		
		String feedback = UserDAO.update(userupdate);
		Assert.assertEquals("success", feedback);
		
		User user2 = UserDAO.getUser(1);
		Assert.assertEquals("Newname+", user2.getLastname());
		
		System.out.println("Relative ist Bidirektional mit UserID " + user.getRelative().getUser().getUserId());
		
		//Delete fehlt noch
		
		
		
	}

}
