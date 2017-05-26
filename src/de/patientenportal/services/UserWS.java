// Hier nur Testweise drin
// Siehe Youtube-Video:
// https://www.youtube.com/watch?v=dEO0_1vvD80



package de.patientenportal.services;

//import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.User;

@WebService
@SOAPBinding(style = Style.RPC)
public interface UserWS {
	
//	@WebMethod
//	public List<User> getAllusers();
	
	@WebMethod
	public User getUser(@WebParam (name="User-ID")int user_id);
	
//	@WebMethod
//	public boolean checkUsername(String username);
	
	@WebMethod
	public void add (User user);
	
	@WebMethod
	public void delete (@WebParam (name="User-ID")int user_id);
	
	@WebMethod
	public void update (User user);
}

