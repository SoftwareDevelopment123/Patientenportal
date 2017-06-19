package smalltests;

import de.patientenportal.entities.User;
import de.patientenportal.persistence.RegistrationDAO;

public class RegistrationTest {

	public static void main(String[] args) {
		
		User user = new User();
			user.setFirstname("Test");
		RegistrationDAO.createUser(user);

	}

}
