package loginservice.old;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService													// mögliches Attribut: serviceName
@SOAPBinding(style = Style.RPC)
public interface Login {
	@WebMethod String authenticateUser(						// mögliches Attribut: operationName
		@WebParam (name ="name") String name,
		@WebParam (name ="password") String password );
	
	@WebMethod String ConnectionTest();
}