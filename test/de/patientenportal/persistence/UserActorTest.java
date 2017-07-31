package de.patientenportal.persistence;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import org.junit.Assert;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.*;

public class UserActorTest {

	@Test
	public void main() throws ParseException {

		// User anlegen (als Patient, Doktor und Relative)
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
		Assert.assertTrue("Should be True", checkusername);

		// User aus der Datenbank abrufen
		User user = UserDAO.getUserByUsername("staps");
		Assert.assertEquals("staps", user.getUsername());
		Assert.assertEquals("pass", user.getPassword());
		Assert.assertEquals("stap.staptp@mustermail.com", user.getEmail());
		Assert.assertEquals("Stupser", user.getLastname());
		Assert.assertEquals("Staps", user.getFirstname());
		Assert.assertEquals(geburtstag, user.getBirthdate());
		Assert.assertEquals(Gender.MALE, user.getGender());

		Assert.assertEquals("Stapshausen", user.getAddress().getCity());
		Assert.assertEquals("1a", user.getAddress().getNumber());
		Assert.assertEquals("01234", user.getAddress().getPostalCode());
		Assert.assertEquals("Superstrasse", user.getAddress().getStreet());

		Assert.assertEquals("anderemail.als@oben.com", user.getContact().getEmail());
		Assert.assertEquals("01731234567", user.getContact().getMobile());
		Assert.assertEquals("03512646152", user.getContact().getPhone());

		// Bidirektionaler Zugriff-Test // ist jetzt Transient
		// Assert.assertEquals(neu.getUserId() ,
		// user.getRelative().getUser().getUserId());
		// Assert.assertEquals(neu.getUserId() , user.getDoctor()
		// .getUser().getUserId());
		// Assert.assertEquals(neu.getUserId() , user.getPatient()
		// .getUser().getUserId());

		// User-Update-Test
		System.out.println(neu.getUserId());
		User userupdate = UserDAO.getUser(neu.getUserId());
		userupdate.setLastname("Newname+");

		String feedbackUU = UserDAO.updateUser(userupdate);
		Assert.assertEquals("success", feedbackUU);

		User user2 = UserDAO.getUser(neu.getUserId());
		Assert.assertEquals("Newname+", user2.getLastname());

		// Update-Fehlermeldung-Test
		User userupdateF = new User();
		// userupdateF.setBirthdate("01.01.0001"); // ID fehlt

		String feedbackF = UserDAO.updateUser(userupdateF);
		Assert.assertEquals("noID", feedbackF);

		// Address-Update-Test
		Address addressupdate = UserDAO.getUser(neu.getUserId()).getAddress();
		addressupdate.setCity("NewCity");
		addressupdate.setPostalCode("464646");
		AddressDAO.updateAddress(addressupdate);

		User user3 = UserDAO.getUser(neu.getUserId());
		Assert.assertEquals("NewCity", user3.getAddress().getCity());
		Assert.assertEquals("464646", user3.getAddress().getPostalCode());

		// Kontakt-Update-Test
		Contact contactupdate = UserDAO.getUser(neu.getUserId()).getContact();
		contactupdate.setEmail("NeueMail@newnew.com");
		contactupdate.setMobile("01175/3737212");
		ContactDAO.updateContact(contactupdate);

		User user4 = UserDAO.getUser(neu.getUserId());
		Assert.assertEquals("NeueMail@newnew.com", user4.getContact().getEmail());
		Assert.assertEquals("01175/3737212", user4.getContact().getMobile());

		// Delete-Test
		String feedbackDU = UserDAO.deleteUser(neu.getUserId());
		User deletedU = UserDAO.getUser(neu.getUserId());
		Assert.assertEquals("success", feedbackDU);
		Assert.assertEquals(null, deletedU);

		// Error-Feedback-Test
		String feedbackDUE = UserDAO.deleteUser(55);
		Assert.assertEquals("Error expected!", "error", feedbackDUE);
		if (feedbackDUE == "error") {
			System.out.println("Dont worry, this Error was expected!");
		}

		// Cascade-Delete-Test
		// Hier vllt noch Abfragen in die anderen Tabellen (Doctor ...), ob die
		// Einträge dort gelöscht sind
		// Test ist nicht zwingend erforderlicht ist manuell überprüfbar
		// (funktioniert)
	}
}
