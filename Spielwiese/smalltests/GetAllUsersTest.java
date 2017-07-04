package smalltests;

import java.util.List;

import org.junit.Test;

import de.patientenportal.entities.Address;
import de.patientenportal.entities.Contact;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.UserDAO;



public class GetAllUsersTest {
	
	
	@Test
	public void main(){
		//User anlegen (Patient)
					User neu = new User();
				
					neu.setUsername("staps");
					neu.setPassword("pass");
					neu.setEmail("stap.staptp@mustermail.com");
					neu.setLastname("Stupser");
					neu.setFirstname("Staps");
					neu.setBirthdate("01.01.1992");
					neu.setGender("male");
					
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
					
					
					/*	User neu3 = new User();
					
					neu3.setUsername("Erika");
					neu3.setPassword("123");
					neu3.setEmail("stap.staptp@mustermail.com");
					neu3.setLastname("Stupser");
					neu3.setFirstname("Staps");
					neu3.setBirthdate("01.01.1952");
					neu3.setGender("male");
				*/
				//User in der Datenbank speichern
				
				//
				//RegistrationDAO.createUser(neu3);
		
				List<User> ulist = UserDAO.getAllUsers();
				System.out.println(ulist);
				int i = 0;
				for (User u : ulist){
					System.out.println(u.getUsername());
					i++;
				}
}
}
