package smalltests;

import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import de.patientenportal.entities.*;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.persistence.*;
import de.patientenportal.services.RelativeWSImpl;


public class JaxBTest {

	public static void main(String[] args) throws Exception {

/*		JAXBContext jc = JAXBContext.newInstance(Relative.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
		Relative relative = RelativeDAO.getRelative(2);
		System.out.println(relative.getPatients().size());
        marshaller.marshal(relative, System.out);*/
		
		// Ergebnis: Über Relative werden Patienten mit gemapped

/*		JAXBContext jc = JAXBContext.newInstance(Patient.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        Patient patient = PatientDAO.getPatient(3);
        marshaller.marshal(patient, System.out);
        System.out.println("------------------------------");
        System.out.println("--- " + patient.getRelatives().size() + " Relatives registered ---");
        for (Relative r : patient.getRelatives()){
        	System.out.print(r.getRelativeID());
        	System.out.print(" - " + r.getUser().getFirstname());
           	System.out.println(" - " + r.getUser().getLastname());
        }*/
               
        // Ergebnis: Über Patient werden Relatives nicht direkt gemapped, nur bei explizitem Abruf

        JAXBContext jc = JAXBContext.newInstance(RelativeListResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        RelativeWSImpl relws = new RelativeWSImpl();
        RelativeListResponse response = relws.getRelativesByP(1);       
        marshaller.marshal(response, System.out);		
			
			/*for(Relative r : response.getResponseList()){
				System.out.print(r.getRelativeID() + " - ");
				System.out.print(r.getUser().getFirstname() + " - ");
				System.out.println(r.getUser().getLastname());
			}*/
		
		
        System.exit(0);
        
	}

}
