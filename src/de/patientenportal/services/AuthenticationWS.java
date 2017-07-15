package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import de.patientenportal.entities.ActiveRole;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AuthenticationWS {
	
	@WebMethod String authenticateUser		(@WebParam (name="ActiveRole")ActiveRole activeRole); //(@WebParam (name="username")String username, @WebParam (name="password")String password);

	@WebMethod boolean authenticateToken	(@WebParam (name="Token")String token);

	@WebMethod String getSessionToken		(@WebParam (name="Username")String username);
	
	@WebMethod public String logout(String token);
	
}