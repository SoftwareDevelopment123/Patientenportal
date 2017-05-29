package de.patientenportal.test;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.DoctorDAO;
import de.patientenportal.persistence.UserDAO;

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
		
		
		User user = UserDAO.getUser(0);						
		
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
}