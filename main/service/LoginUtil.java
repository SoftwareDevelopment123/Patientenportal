package service;

import java.util.HashMap;
import java.util.Map;

public class LoginUtil {

	private Map<String, String> combination;
	
	public LoginUtil() {
		combination = new HashMap<String, String>();
		
		String user1 = "Nutzer";
		String pw1 = "password";
		String user2 = "Admin";
		String pw2 = "passpass";
		String user3 = "Root";
		String pw3 = "root";
		
		combination.put(user1, pw1);
		combination.put(user2, pw2);
		combination.put(user3, pw3);
	}
	
	public Map<String, String> getCombination() {
		return combination;
	}
}