package smalltests;

import de.patientenportal.services.AuthenticationWSImpl;

public class DeleteWSTest {
	public static void main(String[] args){
		
		
		//AuthenticationWSImpl.deleteInvalidTokens();
		AuthenticationWSImpl aws = new AuthenticationWSImpl();
		 aws.authenticateToken("lur1nf95iki2sje8irua2ik99a");
		 System.out.println(aws.logout("lur1nf95iki2sje8irua2ik99a"));
	}
}
