package smalltests;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;

public class JaxBTest {

	public static void main(String[] args) throws Exception {

		JAXBContext jc = JAXBContext.newInstance(Relative.class);
		 
		Patient p1 = new Patient();
			p1.setBloodtype("AB+");
		Patient p2 = new Patient();
			p2.setBloodtype("O-");
		
		List <Patient> patients = Arrays.asList(p1,p2);
		
        Relative relative = new Relative();
        	relative.setPatients(patients);
        
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(relative, System.out);

	}

}
