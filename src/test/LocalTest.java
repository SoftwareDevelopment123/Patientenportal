package test;

import dao.UserDAO;
import entities.User;

public class LocalTest {

	public static void main(String[] args) throws Exception {
	
		/*//User anlegen
		User neu = new User();
			neu.setUsername("staps");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Muhu");
			neu.setFirstname("Staps");
		UserDAO.add(neu);*/
		
		//User löschen
		/*int user_del = 3;
		UserDAO.delete(user_del);*/
		
		//User aufrufen
		System.out.println("Test starting...");

			for (int i=1; i<15; i++){
		User user = UserDAO.getUser(i);						
		//User user = (User)UserDAO.getUser(1);  			// (User)-Deklaration offenbar nicht benötigt, geht auch so
		if (user==null) {
		}
		else{
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		String lastname = user.getLastname();
		String firstname = user.getFirstname();
				
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Email:    " + email);
		System.out.println("Lastname: " + lastname);
		System.out.println("Firstname " + firstname);}
			}
	}
}