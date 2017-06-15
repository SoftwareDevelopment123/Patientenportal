package smalltests;

import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;

public class CriteriaTest {

	public static void main(String[] args){
		
		User newuser = new User();
			newuser.setUsername("NewUser");
			newuser.setPassword("pass");
			newuser.setLastname("MusterUser");
			newuser.setFirstname("Max");
			newuser.setEmail("muster.mail@mustermail.com");
		
		RegistrationDAO.createUser(newuser);
	
		String checkme = "NewUser";
		boolean check =  RegistrationDAO.checkUsername(checkme);
		System.out.println("User exists: " + check);
		
	
	}
	
	
}
