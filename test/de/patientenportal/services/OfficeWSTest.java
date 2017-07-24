package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.OfficeDAO;

public class OfficeWSTest {

	@Test
	public void main() throws MalformedURLException {
	
		URL url = new URL("http://localhost:8080/office?wsdl");
		QName qname = new QName("http://services.patientenportal.de/", "OfficeWSImplService");
		Service service = Service.create(url, qname);
		OfficeWS off = service.getPort(OfficeWS.class);
				
		// Get Office
		Office compareOffice = OfficeDAO.getOffice(1);
		Accessor getOffice = new Accessor();
			getOffice.setObject(1);
		Office office = off.getOffice(getOffice);
			
			Assert.assertEquals(compareOffice.getOfficeID()				, office.getOfficeID());
			Assert.assertEquals(compareOffice.getName()					, office.getName());
			Assert.assertEquals(compareOffice.getContact().getPhone()	, office.getContact().getPhone());
			Assert.assertEquals(compareOffice.getAddress().getStreet()	, office.getAddress().getStreet());
		
		List<Doctor> compareList = compareOffice.getDoctors();
		int i = 0;
		for (Doctor d : compareList) {
			Assert.assertEquals(d.getDoctorID()				, office.getDoctors().get(i).getDoctorID());
			Assert.assertEquals(d.getSpecialization()		, office.getDoctors().get(i).getSpecialization());
			Assert.assertEquals(d.getUser().getLastname()	, office.getDoctors().get(i).getUser().getLastname());			
			i++;
		}
				
		/*
		 * Update Office
		 * Bitte bedenken, dass im DBCreator nur 3 Doktoren dem Office hinzugefügt werden
		 * falls man den Test mehr als 3-Mal ausführt, funktioniert er nicht mehr (sollte ohnehin nicht vorkommen)
		 */
		//TODO so anpassen, dass der Test beliebig oft ausgeführt werden kann
		
		office.setName("Testoffice 2.0");
		office.getDoctors().remove(0);
		Accessor updateOffice = new Accessor();
			updateOffice.setObject(office);
		String feedbackUO = off.updateOffice(updateOffice);
			Assert.assertEquals("success", feedbackUO);
		
		Office updatedOffice = off.getOffice(getOffice);
			Assert.assertEquals("Testoffice 2.0", updatedOffice.getName());
		
		int newsize = compareOffice.getDoctors().size()-1;
			Assert.assertEquals(newsize, updatedOffice.getDoctors().size());

		/*
		 *  Delete Office
		 * zum manuellen Testen vorgesehen
		 * Aktueller Stand: funktioniert
		 */
		/*Accessor deleteOffice = new Accessor(1);
		String feedbackDO = off.deleteOffice(deleteOffice);
			Assert.assertEquals("success", feedbackDO);*/	
	}
}
