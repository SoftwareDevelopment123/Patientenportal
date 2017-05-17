package loginservice.old;

import javax.jws.WebService;

@WebService(endpointInterface = "loginservice.old.Login")
public class LoginImpl implements Login {
	
	@Override
	public String authenticateUser(String name, String password) {
		
		LoginUtil utility = new LoginUtil();
		
		if (name.equals("")) {
			return "Name is missing";
		}
		
		if (password.equals("")) {
			return "Password is missing";
		}
		
		String pass = utility.getCombination().get(name);
		
		if (password.equals(pass)) {
			return "Correct credentials";
		}
		
		if (pass == null) {
			return "User not found. Please register here";
		}
		
		return "Invalid password";
	}
	
	public String ConnectionTest(){
		return "Connection Test sucessful.";
	}
}