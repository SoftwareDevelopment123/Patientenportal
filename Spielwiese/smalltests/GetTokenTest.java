package smalltests;

import de.patientenportal.services.AuthenticationWSImpl;

public class GetTokenTest {

	public static void main(String[] args){
	/*String username = "Jonny"; //"Jonny";
	AuthenticationWSImpl autws = new AuthenticationWSImpl();
	 System.out.println(UserDAO.getUserByUsername(username).getContact());
	System.out.println(autws.getSessionToken(username));*/
		AuthenticationWSImpl autws = new AuthenticationWSImpl();
		autws.deleteExpiredWebSession();
	
}
}
