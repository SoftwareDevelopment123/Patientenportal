package de.patientenportal.test;

import de.patientenportal.DAO.DoctorDAO;
import de.patientenportal.DAO.UserDAO;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.User;

public class TestOnetoOne {

	public static void main(String[] args) throws Exception {
		
		//User anlegen
		Doctor doctor = new Doctor("Hautarzt");
			DoctorDAO.add(doctor);
		
		User neu = new User();
			neu.setUsername("staps");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Muhu");
			neu.setFirstname("Staps");
			neu.setDoctor(doctor);
		UserDAO.add(neu);
}
}