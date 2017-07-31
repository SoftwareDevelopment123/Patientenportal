package de.patientenportal.clientHelper;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import de.patientenportal.services.AuthenticationWS;

public class ClientHelper {

	/**
	 * Wandelt ein als <code>String</code> übergebenes Datum in ein Datum vom
	 * Typ <code>Date</code> um.
	 * 
	 * @param dateAsString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringtoDate(String dateAsString) throws ParseException {
		String pattern = "MM.dd.yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = format.parse(dateAsString);
		return date;
	}

	/**
	 * Wandelt einen als <code>String</code> übergebenen Timestamp in ein
	 * Timestamp vom Typ <code>Date</code> um.
	 * 
	 * @param timeStampAsString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringtoTimeStamp(String timeStampAsString) throws ParseException {
		String pattern = "MM.dd.yyyy HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date timeStamp = format.parse(timeStampAsString);
		return timeStamp;
	}

	/** The Constant WS_URL. */
	private static final String WS_URL = "http://localhost:8080/authentication?wsdl";

	/**
	 * Schreibt Username und Passwort in den HTTP-Header
	 *
	 * @param username
	 * @param password
	 * @param authWS
	 *            Authentifizierungs-Webservice
	 * @throws MalformedURLException
	 *             the malformed URL exception
	 */
	public static void putUsernamePassword(String username, String password, AuthenticationWS authWS)
			throws MalformedURLException {

		Map<String, Object> req_ctx = ((BindingProvider) authWS).getRequestContext();
		req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		headers.put("Username", Collections.singletonList(username));
		headers.put("Password", Collections.singletonList(password));
		req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	}
}
