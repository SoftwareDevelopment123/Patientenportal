package de.patienportal.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHelper {

	/**Wandelt ein als String übergebenes Datum in ein Datum vom Typ date um.
	 * 
	 * @param dateAsString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringtoDate(String dateAsString) throws ParseException{
		String pattern = "MM.dd.yyyy";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	      Date date = format.parse(dateAsString);
	      return date;	
	}
	
	public static Date parseStringtoTimeStamp(String timeStampAsString) throws ParseException{
		String pattern = "MM.dd.yyyy HH:mm";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	      Date timeStamp = format.parse(timeStampAsString);
	      return timeStamp;	
	}
}
