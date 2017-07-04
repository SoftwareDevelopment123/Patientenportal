package smalltests;

import java.util.ArrayList;
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
		
		User neu2 = new User();
		
		neu2.setUsername("Jon");
		neu2.setPassword("123Jon");
		neu2.setEmail("stap.staptp@mustermail.com");
		neu2.setLastname("Stupser");
		neu2.setFirstname("Staps");
		neu2.setBirthdate("01.01.1952");
		neu2.setGender("male");
		
		RegistrationDAO.createUser(neu2);
		
		String checkme = "NewUser";
		boolean check =  RegistrationDAO.checkUsername(checkme);
		System.out.println("User exists: " + check);
		
		
		
		List<User> ulist = UserDAO.getAllUsers();
		System.out.println(ulist);
		
		for (User u : ulist){
			System.out.println(u.getUsername());
			System.out.println(u.getUserId());
			System.out.println(u.getFirstname());
			System.out.println(u.getUsername());
		}
		
	//	ulist.stream().forEach(elm -> System.out.println(elm.username));
	
		User u = UserDAO.getUserByUsername2 ("staps12");
			System.out.println(u.getUsername());
			System.out.println(u.getUserId());
			System.out.println(u.getFirstname());
}
}
