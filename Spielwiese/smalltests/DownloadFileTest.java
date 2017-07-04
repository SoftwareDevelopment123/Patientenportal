package smalltests;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mysql.jdbc.log.Log;

public class DownloadFileTest {

	private final static Logger log = Logger.getLogger( DownloadFileTest.class );
	public static String downloadFile(String url)
	{
		final String urlF = url;
		String returnThread;
		try
		{
			String[] urlPart = urlF.split("/");
			String fileName = urlPart[urlPart.length - 1];
			URL link = new URL(urlF);
			FileUtils.copyURLToFile(link, new File("tmp/" + fileName));
			log.info("Download Finished");
			returnThread = fileName;
		}
		catch (IOException e)
		{
			log.error("Download Error");
			returnThread = null;
		}
		return returnThread;
	}
}