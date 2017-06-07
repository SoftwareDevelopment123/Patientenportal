package smalltests;

import org.junit.Test;
import de.patientenportal.entities.*;
//import de.patientenportal.persistence.*;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

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
			neuA.setPostalCode("01234");
			neuA.setStreet("Superstrasse");
			
		Contact neuC = new Contact();
			neuC.setEmail("anderemail.als@oben.com");
			neuC.setMobile("01731234567");
			neuC.setPhone("03512646152");
			
		Patient neuP = new Patient();
			neuP.setBloodtype("ABC");
			
			neu.setAddress(neuA);
			neu.setContact(neuC);
			neu.setPatient(neuP);
		
		//User in der Datenbank speichern
		RegistrationDAO.createUser(neu);
		
		//User abrufen
		User user = UserDAO.getUser(1);						
		
		if (user==null) {
		}
		else{	
		System.out.println("-------Test-Abfrage--------");
		System.out.println("----------User "+user.getUserId()+"-----------");
		System.out.println("---------------------------");
		System.out.println("Username  " + user.getUsername());
		System.out.println("Password  " + user.getPassword());
		System.out.println("Email     " + user.getEmail());
		System.out.println("Lastname  " + user.getLastname());
		System.out.println("Firstname " + user.getFirstname());
		System.out.println("Birthdate " + user.getBirthdate());
		System.out.println("Gender    " + user.getGender());
		System.out.println("----------Contact----------");
		System.out.println("ContactID " + user.getContact().getContactID());
		
		// Hier kommt nichts weil LAZY eingestellt ist
		System.out.println("E-Mail    " + user.getContact().getEmail());
		System.out.println("Mobile    " + user.getContact().getMobile());
		System.out.println("Phone     " + user.getContact().getPhone());
		
		System.out.println("----------Address----------");
		System.out.println("AddressID " + user.getAddress().getAddressID());

		
		//Hier wird das Objekt befüllt, weil EAGER eingestellt ist
		System.out.println("City      " + user.getAddress().getPostalCode() + " " + user.getAddress().getCity());
		System.out.println("Address   " + user.getAddress().getStreet() + " " + user.getAddress().getNumber());
		
		System.out.println("----------Patient----------");
		System.out.println("PatientID " + user.getPatient().getPatientID());
		
		// Hier kommt nichts weil LAZY eingestellt ist	
		System.out.println("Bloodtype " + user.getPatient().getBloodtype());
		}
	}
}