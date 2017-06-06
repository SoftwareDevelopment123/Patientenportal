package de.patientenportal.persistence;

import org.junit.Test;

import de.patientenportal.entities.*;
//import de.patientenportal.persistence.*;


public class TestOnetoOne {

	@Test
	public void test() throws Exception {
		
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
			neuC.setMobile(01731234567);
			neuC.setPhone(03512646152);
			
		Patient neuP = new Patient();
			neuP.setBloodtype("ABC");
			
			neu.setAddress(neuA);
			neu.setContact(neuC);
			neu.setPatient(neuP);
		
		//User in der Datenbank speichern
		UserDAO.add(neu);
					
		/*User user = UserDAO.getUser(0);						
		
		if (user==null) {
		}
		else{
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		String lastname = user.getLastname();
		String firstname = user.getFirstname();
		Doctor doctor2 = user.getDoctor();
		
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Email:    " + email);
		System.out.println("Lastname: " + lastname);
		System.out.println("Firstname " + firstname);
		System.out.println("Doctor " + doctor2.getSpecialization());}*/
			
	}
}