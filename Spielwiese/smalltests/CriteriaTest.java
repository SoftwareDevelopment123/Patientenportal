package smalltests;

import java.util.List;

import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

public class CriteriaTest {

	public static void main(String[] args){
		
		User newuser = new User();
			newuser.setUsername("NewUser");
			newuser.setPassword("pass");
			newuser.setLastname("MusterUser");
			newuser.setFirstname("Max");
			newuser.setEmail("muster.mail@mustermail.com");
		
		RegistrationDAO.createUser(newuser);
	
		
		User neu = new User();
		
			neu.setUsername("staps12");
			neu.setPassword("pass");
			neu.setEmail("stap.staptp@mustermail.com");
			neu.setLastname("Stupser");
			neu.setFirstname("Staps1");
			
		RegistrationDAO.createUser(neu);
		
		
		String checkme = "NewUser";
		boolean check =  RegistrationDAO.checkUsername(checkme);
		System.out.println("User exists: " + check);
		
		
		List<User> ulist = UserDAO.getAllUsers();
		System.out.println(ulist);
		int i = 0;
		for (User u : ulist){
			System.out.println(u.getUsername());
			i++;
	
	}
	
	
}
}
