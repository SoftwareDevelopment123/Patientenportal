package smalltests;

import de.patientenportal.persistence.UserDAO;

public class UserDAOTest {
	
	public static void main(String[] args){
		System.out.println(UserDAO.getUserByUsername("Jonny"));
	}
}
