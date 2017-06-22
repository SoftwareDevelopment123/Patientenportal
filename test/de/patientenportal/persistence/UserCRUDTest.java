package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.*;

public class UserCRUDTest {

	@Test
	public void main() {
				
		//User anlegen (als Patient, Doktor und Relative)
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
			
		Doctor neuD = new Doctor();
			neuD.setSpecialization("Kardiologe");
			
		Relative neuR = new Relative();

			neu.setAddress(neuA);
			neu.setContact(neuC);
			neu.setPatient(neuP);
			neu.setDoctor(neuD);
			neu.setRelative(neuR);
			
		// User in der Datenbank speichern
		RegistrationDAO.createUser(neu);
		
		// Prüfen, ob der username nun (schon) vergeben ist
		boolean checkusername = RegistrationDAO.checkUsername("staps");
			Assert.assertTrue("Should be True",checkusername);
		
		// User aus der Datenbank abrufen
		User user = UserDAO.getUser(1);
			Assert.assertEquals("staps", user.getUsername());
			Assert.assertEquals("pass", user.getPassword());
			Assert.assertEquals("stap.staptp@mustermail.com", user.getEmail());
			Assert.assertEquals("Stupser", user.getLastname());
			Assert.assertEquals("Staps", user.getFirstname());
			Assert.assertEquals("01.01.1992", user.getBirthdate());
			Assert.assertEquals("male", user.getGender());
		
			Assert.assertEquals("Stapshausen", user.getAddress().getCity());
			Assert.assertEquals("1a", user.getAddress().getNumber());
			Assert.assertEquals("01234", user.getAddress().getPostalCode());
			Assert.assertEquals("Superstrasse", user.getAddress().getStreet());
		
			Assert.assertEquals("anderemail.als@oben.com", user.getContact().getEmail());
			Assert.assertEquals("01731234567", user.getContact().getMobile());
			Assert.assertEquals("03512646152", user.getContact().getPhone());
		
			Assert.assertEquals("ABC", user.getPatient().getBloodtype());
		
			Assert.assertEquals("Kardiologe", user.getDoctor().getSpecialization());
		
		//Bidirektionaler Zugriff-Test
			Assert.assertEquals(1, user.getRelative().getUser().getUserId());
			Assert.assertEquals(1, user.getDoctor().getUser().getUserId());
			Assert.assertEquals(1, user.getPatient().getUser().getUserId());
		
		//User-Update-Test
		User userupdate = UserDAO.getUser(1);
			userupdate.setLastname("Newname+");
		
		String feedbackUU = UserDAO.updateUser(userupdate);
			Assert.assertEquals("success", feedbackUU);
		
		User user2 = UserDAO.getUser(1);
			Assert.assertEquals("Newname+", user2.getLastname());
			
		//Update-Fehlermeldung-Test
		User userupdateF = new User();
			userupdateF.setBirthdate("01.01.0001"); //ID fehlt
		
		String feedbackF = UserDAO.updateUser(userupdateF);
			Assert.assertEquals("noID", feedbackF);
		
		//Address-Update-Test
		Address addressupdate = UserDAO.getUser(1).getAddress();
			addressupdate.setCity("NewCity");
			addressupdate.setPostalCode("464646");		
		AddressDAO.updateAddress(addressupdate);
		
		User user3 = UserDAO.getUser(1);
			Assert.assertEquals("NewCity", user3.getAddress().getCity());
			Assert.assertEquals("464646", user3.getAddress().getPostalCode());
		
		//Kontakt-Update-Test
		Contact contactupdate = UserDAO.getUser(1).getContact();
			contactupdate.setEmail("NeueMail@newnew.com");
			contactupdate.setMobile("01175/3737212");
		ContactDAO.updateContact(contactupdate);
		
		User user4 = UserDAO.getUser(1);
			Assert.assertEquals("NeueMail@newnew.com", user4.getContact().getEmail());
			Assert.assertEquals("01175/3737212", user4.getContact().getMobile());
		
		//Delete-Test
		String feedbackDU = UserDAO.deleteUser(1);
		User deletedU = UserDAO.getUser(1);
			Assert.assertEquals("success", feedbackDU);
			Assert.assertEquals(null, deletedU);	

		//Error-Feedback-Test
		String feedbackDUE = UserDAO.deleteUser(55);
			Assert.assertEquals("Error expected!","error", feedbackDUE);
			if (feedbackDUE == "error") {System.out.println("Dont worry, this Error was expected!");}
		
		//Cascade-Delete-Test
		//Hier vllt noch Abfragen in die anderen Tabellen (Doctor ...), ob die Einträge dort gelöscht sind
		//Test ist nicht zwingend erforderlicht ist manuell überprüfbar (funktioniert)

	}
}
