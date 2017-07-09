package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.DoctorDAO;

public class DoctorTest {

	@Test
	public void main () throws MalformedURLException{
	
	System.out.print("Testing Doctor WebService ...");
	
	URL url = new URL("http://localhost:8080/doctor?wsdl");
	QName qname = new QName("http://services.patientenportal.de/", "DoctorWSImplService");
	Service service = Service.create(url, qname);
	DoctorWS doc = service.getPort(DoctorWS.class);
	
	// Get Doctor
	Doctor comparedoc = DoctorDAO.getDoctor(1);
	Accessor getdoc = new Accessor(1);
	Doctor doctor = doc.getDoctor(getdoc);

		Assert.assertEquals(comparedoc.getDoctorID() 			, doctor.getDoctorID());
		Assert.assertEquals(comparedoc.getSpecialization() 		, doctor.getSpecialization());
		Assert.assertEquals(comparedoc.getUser().getLastname() 	, doctor.getUser().getLastname());
	
	/*// Get PatientList by Relative
	List<Patient> comparePatList = RelativeDAO.getRelative(3).getPatients();
	Accessor getPatList = new Accessor(3);
	PatientListResponse patListResponse = pat.getPatientsByR(getPatList);
	List<Patient> patList = patListResponse.getResponseList();
	
	int i = 0;
	for (Patient p : comparePatList){
		Assert.assertEquals(p.getPatientID() 			, patList.get(i).getPatientID());
		Assert.assertEquals(p.getBloodtype() 			, patList.get(i).getBloodtype());
		Assert.assertEquals(p.getUser().getLastname() 	, patList.get(i).getUser().getLastname());
		i++;
	}*/
		
	System.out.println("Success!");	
	}
}
