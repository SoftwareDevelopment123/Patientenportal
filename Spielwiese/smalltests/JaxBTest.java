package smalltests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.persistence.PatientDAO;
import de.patientenportal.persistence.RelativeDAO;

public class JaxBTest {

	public static void main(String[] args) throws Exception {

	/*	JAXBContext jc = JAXBContext.newInstance(Relative.class);        
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
		Relative relative = RelativeDAO.getRelative(2);				
        marshaller.marshal(relative, System.out);*/

 
        JAXBContext jc = JAXBContext.newInstance(Patient.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        Patient patient = PatientDAO.getPatient(3);        
        marshaller.marshal(patient, System.out);
        
        System.exit(0);
        
	}

}