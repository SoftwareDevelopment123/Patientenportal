package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import de.patientenportal.entities.User;

@WebService
@SOAPBinding(style = Style.RPC)
public interface UserWS {
	
	@WebMethod
	public User getUser(@WebParam (name="User-ID")int user_id);
		
	@WebMethod
	public void deleteUser(@WebParam (name="User-ID")int user_id);
	
	@WebMethod
	public void updateUser(User user);
}

