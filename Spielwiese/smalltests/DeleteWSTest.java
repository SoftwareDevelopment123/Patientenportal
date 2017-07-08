package smalltests;

import de.patientenportal.services.AuthenticationWSImpl;

public class DeleteWSTest {
	public static void main(String[] args){
		
		AuthenticationWSImpl.deleteInvalidTokens();
	}
}
