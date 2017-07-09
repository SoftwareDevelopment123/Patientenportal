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
import de.patientenportal.entities.response.DoctorListResponse;
import de.patientenportal.persistence.CaseDAO;
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
	
	// Get DoctorList by Case
	System.out.println(CaseDAO.getCase(3).getDoctors().size());
		
	/*List<Doctor> compareDocList = CaseDAO.getCase(3).getDoctors();
	Accessor getDocList = new Accessor(3);
	DoctorListResponse docListResponse = doc.getDoctorsByC(getDocList);
	List<Doctor> docList = docListResponse.getResponseList();
	
	int i = 0;
	for (Doctor d : compareDocList){
		Assert.assertEquals(d.getDoctorID()				, docList.get(i).getDoctorID());
		Assert.assertEquals(d.getSpecialization()		, docList.get(i).getSpecialization());
		Assert.assertEquals(d.getUser().getFirstname()	, docList.get(i).getUser().getFirstname());
		i++;
	}*/
		
	System.out.println("Success!");	
	}
}
