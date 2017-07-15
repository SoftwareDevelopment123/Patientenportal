package smalltests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.patienportal.demo.ClientHelper;
import de.patientenportal.entities.Gender;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;

public class CriteriaTest {

	public static void main(String[] args) throws ParseException{
		
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
		Date geburtstag = ClientHelper.parseStringtoDate("04.12.1991");
		neu.setBirthdate(geburtstag);
		neu2.setGender(Gender.MALE);
		
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
	
		User u = UserDAO.getUserByUsername ("staps12");
			System.out.println(u.getUsername());
			System.out.println(u.getUserId());
			System.out.println(u.getFirstname());
}
}
