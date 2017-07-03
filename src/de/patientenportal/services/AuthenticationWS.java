package de.patientenportal.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AuthenticationWS {
	
	@WebMethod String authenticateUser(); //(@WebParam (name="username")String username, @WebParam (name="password")String password);
}