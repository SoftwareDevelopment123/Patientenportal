package smalltests;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import de.patientenportal.entities.*;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.entities.response.CaseListResponse;
import de.patientenportal.entities.response.MedicationListResponse;
import de.patientenportal.entities.response.RelativeListResponse;
import de.patientenportal.entities.response.RightsListResponse;
import de.patientenportal.persistence.*;
import de.patientenportal.services.RelativeWSImpl;


@SuppressWarnings("unused")
public class JaxBTest {

	public static void main(String[] args) throws Exception {

/*		JAXBContext jc = JAXBContext.newInstance(Relative.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
		Relative relative = RelativeDAO.getRelative(2);
		System.out.println(relative.getPatients().size());
        marshaller.marshal(relative, System.out);*/
		
		// Ergebnis: Über Relative werden Patienten mit gemapped

		/*JAXBContext jc = JAXBContext.newInstance(Patient.class);
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

		/* JAXBContext jc = JAXBContext.newInstance(RelativeListResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        Accessor access = new Accessor(1);
        RelativeWSImpl relws = new RelativeWSImpl();
        RelativeListResponse response = relws.getRelativesByP(access);       
        marshaller.marshal(response, System.out);		
			
			for(Relative r : response.getResponseList()){
				System.out.print(r.getRelativeID() + " - ");
				System.out.print(r.getUser().getFirstname() + " - ");
				System.out.println(r.getUser().getLastname());
			}*/
		
	/*	JAXBContext jc = JAXBContext.newInstance(Doctor.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Doctor doctor = DoctorDAO.getDoctor(2);
		
		System.out.println(doctor.getDoctorID());
		System.out.println(doctor.getSpecialization());
		System.out.println(doctor.getUser().getEmail());
		
        marshaller.marshal(doctor, System.out);
		*/
		

		/*JAXBContext jc = JAXBContext.newInstance(CaseListResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		List<Case> caselist = new ArrayList<Case>();
			caselist.add(CaseDAO.getCase(1));
			caselist.add(CaseDAO.getCase(2));
			caselist.add(CaseDAO.getCase(3));

		CaseListResponse response = new CaseListResponse();
			response.setResponseCode("TestString");
			response.setResponseList(caselist);
			
		marshaller.marshal(response, System.out);*/
		
		/*JAXBContext jc = JAXBContext.newInstance(RightsListResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<Rights> rightslist = new ArrayList<Rights>();
        	rightslist.addAll(RightsDAO.getRights(3));

		RightsListResponse response = new RightsListResponse();
			response.setResponseCode("TestString");
			response.setResponseList(rightslist);
			
		marshaller.marshal(response, System.out);*/
		
		JAXBContext jc = JAXBContext.newInstance(Medication.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Medication m = new Medication();
        	m.setDosage("viel");
        	m.setDuration("lange");
        	m.setMedicine(MedicineDAO.getMedicine(1));
        	m.setPcase(CaseDAO.getCase(1));
        	m.setPrescribedBy(DoctorDAO.getDoctor(1));
			
		marshaller.marshal(m, System.out);
        
		MedicationDAO.createMedication(m);
		
		/*JAXBContext jc = JAXBContext.newInstance(MedicationListResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		List<Medication> mlist =  CaseDAO.getCase(1).getMedication();
		MedicationListResponse med = new MedicationListResponse();
		med.setResponseList(mlist);
		
		marshaller.marshal(med, System.out);*/
		
		/*JAXBContext jc = JAXBContext.newInstance(Accessor.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
        Accessor createMedication = new Accessor("asaf322354253asd");
        
        User user = new User();
        	user.setFirstname("asd");
        	user.setPassword("3123");
        	user.setUsername("asd");
        
        Medicine medicine = new Medicine();
			medicine.setDrugmaker("Böser Pharmakonzern");
			medicine.setActiveIngredient("Krankium");
			medicine.setName("InnovativerName");

		Medication medication1 = new Medication();
		medication1.setDosage("212");
		medication1.setDuration("extremslange Stunden:");			
		medication1.setPcase(CaseDAO.getCase(1));
		medication1.setMedicine(medicine);
		medication1.setPrescribedBy(UserDAO.getUser(10).getDoctor());
			createMedication.setObject(user);
			
		marshaller.marshal(createMedication, System.out);*/
		
        System.exit(0);
        
	}

}
