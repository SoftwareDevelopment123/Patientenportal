package de.patientenportal.persistence;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import de.patientenportal.entities.*;

public class DoctorCRUDTest {

	/*
	 * Der Test ist noch nicht vollständig 
	 */
	
	@Test
	public void main(){
		
		Doctor neuD1 = new Doctor();
			neuD1.setSpecialization("Zahnarzt");
		Doctor neuD2 = new Doctor();
			neuD2.setSpecialization("Hautarzt");
		
		List<Doctor> doctors = new ArrayList<Doctor>();
			doctors.add(neuD1);
			doctors.add(neuD2);
		
			Office neuO = new Office();
				neuO.setName("Zahnarztpraxis");
				neuO.setDoctor(doctors);
		
				Contact officeContact = new Contact();
					officeContact.setEmail("officemail.dentist@googlemail.com");
					officeContact.setPhone("0123456789");
			
				neuO.setContact(officeContact);
			
			neuD1.setOffice(neuO);
			neuD2.setOffice(neuO);

		RegistrationDAO.createDoctor(neuD1);
		RegistrationDAO.createDoctor(neuD2);
	}	
}
