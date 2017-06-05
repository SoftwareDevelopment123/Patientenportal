package de.patientenportal.persistence;

import org.junit.Test;

import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;



public class VitalDataTest {

	@Test
	public void addVitalDataTest() throws Exception{
		
		
		//User anlegen
		VitalData vitalData = new VitalData("12:30 Uhr,05.06.2015",82.5, VitalDataType.BLOODSUGAR);
		
		System.out.println(vitalData.getTimestamp());
		
		VitalDataDAO.add(vitalData);
		

		
/*		User user = UserDAO.getUser(0);						
		
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
		System.out.println("Doctor " + doctor2.getSpecialization());}
			
}
}*/
}
}
