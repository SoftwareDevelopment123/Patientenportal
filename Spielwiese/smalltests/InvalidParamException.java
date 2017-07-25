package smalltests;

import javax.xml.ws.WebFault;

@WebFault(name="WebServiceFault")
public class InvalidParamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6818177939168541553L;

	public InvalidParamException() {
		super();
	}

	public InvalidParamException(String message) {
		super(message);
	}

}
