package de.patientenportal.persistence;

import org.junit.Test;
import java.util.List;
import org.junit.Assert;
import de.patientenportal.entities.*;

public class DoctorOfficeCRUDTest {

	/*
	 * Der Test ist noch nicht vollständig 
	 */
	
	@Test
	public void main(){
		
		// Office anlegen
		Office neuO = new Office();
			neuO.setName("Zahnarztpraxis");
	
		Contact officeContact = new Contact();
			officeContact.setEmail("officemail.dentist@googlemail.com");
			officeContact.setPhone("0123456789");
			officeContact.setMobile("112");
		
			neuO.setContact(officeContact);
			
		String feedbackCO = OfficeDAO.createOffice(neuO);
			Assert.assertEquals("success", feedbackCO);
		
		// Office abrufen
		Office newoffice = OfficeDAO.getOffice(1);
			Assert.assertEquals("Zahnarztpraxis", newoffice.getName());
			Assert.assertEquals("officemail.dentist@googlemail.com", newoffice.getContact().getEmail());
			Assert.assertEquals("0123456789", newoffice.getContact().getPhone());
			Assert.assertEquals("112", newoffice.getContact().getMobile());
		
		// Doktoren anlegen Office hinterlegen
		Doctor neuD1 = new Doctor();
			neuD1.setSpecialization("Zahnarzt");
			neuD1.setOffice(newoffice);
		Doctor neuD2 = new Doctor();
			neuD2.setSpecialization("Kieferorthopäde");
			neuD2.setOffice(newoffice);
		
		RegistrationDAO.createDoctor(neuD1);
		RegistrationDAO.createDoctor(neuD2);
			
		//Doktor und Office über Doktor (bidirektional) abrufen
		Doctor D1 = DoctorDAO.getDoctor(1);
			Assert.assertEquals("Zahnarzt", D1.getSpecialization());
			Assert.assertEquals("Zahnarztpraxis", D1.getOffice().getName());
			Assert.assertEquals("0123456789", D1.getOffice().getContact().getPhone());
		
		// Dem Office die Doktoren hinzufügen (andere Zugriffsrichtung) // Office-Update
		Doctor D2 = DoctorDAO.getDoctor(2);
		
		Office emptyoffice = OfficeDAO.getOffice(1);
			List<Doctor> doctors = emptyoffice.getDoctors();
				doctors.add(D1);
				doctors.add(D2);
			emptyoffice.setName("Zaaahnarztpraxis");
			//emptyoffice.getContact().setMobile("911");									// Flush-Fehler!!
		String feedbackUO = OfficeDAO.updateOffice(emptyoffice);
			Assert.assertEquals("success", feedbackUO);
					
		//Doktoren und geänderte Daten abrufen
		Office fulloffice = OfficeDAO.getOffice(1);
				Assert.assertEquals("Zaaahnarztpraxis", fulloffice.getName());
				//Assert.assertEquals("991", fulloffice.getContact().getMobile());			// Flush-Fehler!!
			List<Doctor> fulldoctors = fulloffice.getDoctors();
				Assert.assertEquals("Zahnarzt", fulldoctors.get(0).getSpecialization());
				Assert.assertEquals("Kieferorthopäde", fulldoctors.get(1).getSpecialization());
		
		//Zusätzlicher Test - Über Office Arzt ändern (wie reagiert die DB)	
		fulldoctors.get(1).setSpecialization("Test");
		OfficeDAO.updateOffice(fulloffice);
			Assert.assertEquals("Test", DoctorDAO.getDoctor(2).getSpecialization());

		//Nachträglicher Userinput-Test (unabhängige Doktor-Entity bis zur Registrierung)
		/*User newuser = new User();
			newuser.setUsername("newuser");
			newuser.setPassword("newpass");
			newuser.setFirstname("New");
			newuser.setLastname("User");
			newuser.setDoctor(D1);
		String feedbackCU = RegistrationDAO.createUser(newuser);
			Assert.assertEquals("success", feedbackCU);*/
		
	}	
}
