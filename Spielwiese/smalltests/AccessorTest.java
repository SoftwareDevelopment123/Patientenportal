package smalltests;

import de.patientenportal.entities.Gender;
import de.patientenportal.entities.User;
import de.patientenportal.entities.response.Accessor;

public class AccessorTest {

	public static void main(String[] args) throws Exception {
		
		/*User user = new User();
			user.setUsername("user");
			user.setPassword("pass");
			user.setLastname("lastname");
			user.setFirstname("firstname");
			user.setEmail("mail.address@mailprovider.com");
			user.setBirthdate("01.01.2001");
			user.setGender(Gender.MALE);
		
		Acessor tryme = new Acessor();
			tryme.setObject(user);
			
		System.out.println(tryme.getObject().getClass());

		User fromaccessor = (User) tryme.getObject();
			System.out.println(fromaccessor.getFirstname());*/
		
		int id = 1;
		
		Accessor tryme2 = new Accessor();
			tryme2.setObject(id);
		
		System.out.println(tryme2.getObject().getClass());
		
		int idfromaccess = (int) tryme2.getObject();
			System.out.println(idfromaccess);
	}
	
}
