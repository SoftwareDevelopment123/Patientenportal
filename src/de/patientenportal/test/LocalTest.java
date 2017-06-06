package de.patientenportal.test;

import java.util.List;
import java.util.ListIterator;

import de.patientenportal.persistence.*;
import de.patientenportal.entities.*;

@SuppressWarnings("unused")

public class LocalTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Test starting...");
		//User anlegen
		/*User neu = new User();
			neu.setUsername("staps4");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Muhu");
			neu.setFirstname("Staps");
		UserDAO.add(neu);*/
		
		//User update
		/*User updateduser = new User();
		updateduser.setUserId(1);
		updateduser.setUsername("Wursti");
		updateduser.setPassword("hackerpass1337");
		updateduser.setEmail("hanswurst@mustermail.com");
		updateduser.setLastname("Wurst");
		updateduser.setFirstname("Hans");
		UserDAO.update(updateduser);*/
		
		//User löschen
		/*int user_del = 3;
		UserDAO.delete(user_del);*/
		
		//User aufrufen		
		/*
		 User user = UserDAO.getUser(3);						
		//User user = (User)UserDAO.getUser(1);  			// (User)-Deklaration offenbar nicht benötigt, geht auch so
		
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		String lastname = user.getLastname();
		String firstname = user.getFirstname();
				
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Email:    " + email);
		System.out.println("Lastname: " + lastname);
		System.out.println("Firstname " + firstname);
		*/
		
		//User-Liste 
		/*List<User> userliste = UserDAO.getAllUsers();
		for(ListIterator<User> iter = userliste.listIterator(); iter.hasNext();){
			User user = iter.next();
			System.out.println("ID   : " + user.getUserId());
			System.out.println("User : " + user.getUsername());
			System.out.println("Pass : " + user.getPassword());
			System.out.println("Mail : " + user.getEmail());
			System.out.println("FName: " + user.getFirstname());
			System.out.println("Name : " + user.getLastname());
			System.out.println("----------------------------");
		}*/

		System.out.println("...Test finished!");		
	}
}
