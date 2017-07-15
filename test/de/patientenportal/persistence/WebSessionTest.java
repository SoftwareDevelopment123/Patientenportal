package de.patientenportal.persistence;

import org.junit.Test;

import de.patientenportal.entities.Gender;
import de.patientenportal.entities.User;
import de.patientenportal.entities.WebSession;

public class WebSessionTest {

	
	
	@Test
	public void main(){
		WebSession websession1 = new WebSession();
		
		
		User neu = new User();
		neu.setUsername("staps");
		neu.setPassword("pass");
		neu.setEmail("stap.staptp@mustermail.com");
		neu.setLastname("Stupser");
		neu.setFirstname("Staps");
		//neu.setBirthdate("01.01.1992");
		neu.setGender(Gender.MALE);
		
		// User in der Datenbank speichern
				RegistrationDAO.createUser(neu);
				
		websession1.setUser(neu);
		websession1.setToken("abcdef");
		websession1.setValidTill(null);
		
		WebSessionDAO.createWebSession(websession1);
	}
}
