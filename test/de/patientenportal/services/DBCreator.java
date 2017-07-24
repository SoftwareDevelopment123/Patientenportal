package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;

import de.patientenportal.clientHelper.ClientHelper;
import de.patientenportal.entities.*;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.*;


public class DBCreator {

	@Test
	public void createDatabase() throws ParseException, MalformedURLException{// main(String[] args) throws MalformedURLException, ParseException {

		
		System.out.println("Creating DB-Entries ...");
		
		System.err.println("Setting up actors ...");
		Integer i = 0;
		List <Relative> relatives = new ArrayList<Relative>();
		List <Doctor> doctors = new ArrayList<Doctor>();
		
		Insurance insurance = new Insurance();
			insurance.setInsuranceNr(4641831);
			insurance.setName("Techniker");
		InsuranceDAO.createInsurance(insurance);
		
		
		for (int s = 12; s>=1; s--){
			i++;
			i.toString();
						
			User user = new User();
				user.setUsername("user" + i);
				user.setPassword("pass" + i);
				user.setLastname("lastname" + i);
				user.setFirstname("firstname" + i);
				user.setEmail("mail.address" + i + "@mailprovider.com");
				
				Date geburtstag = ClientHelper.parseStringtoDate(i + ".1.2001");
				user.setBirthdate(geburtstag);
				user.setGender(Gender.MALE);
				
			Address address = new Address();
				address.setCity("City " + i);
				address.setPostalCode("XY00" + i);
				address.setStreet("Testing Yard " + i);
				address.setNumber(i + "a");
			
			Contact contact = new Contact();
				contact.setEmail("contact.mail" + i + "@mail.com");
				contact.setMobile("0123/456 800" + i);
				contact.setPhone("0578/222 00" + i);
				
				user.setContact(contact);
				user.setAddress(address);
			
			// User created = WS.createUser
				
			if (s >= 10) {
				Relative relative = new Relative();
				// int userID = created.getID
				// string feedback = registrationWS.createRelative(relative, id)
				
				
				RegistrationDAO.createRelative(relative);		//kommt weg
				user.setRelative(relative);					//kommt weg
				
				relatives.add(relative);
				System.out.println("User-ID " + i + " - Relative created");
			}
			
			else if (s <= 3) {
				Doctor doctor = new Doctor();
				doctor.setSpecialization("Specialization " + i);
				RegistrationDAO.createDoctor(doctor);
				user.setDoctor(doctor);
				doctors.add(doctor);
				System.out.println("User-ID " + i + " - Doctor created");
			}
			
			else {
				Patient patient = new Patient();
				patient.setBloodtype("A" + i);
				patient.setInsurance(insurance);
				RegistrationDAO.createPatient(patient);
				user.setPatient(patient);
				System.out.println("User-ID " + i + " - Patient created");
	
			}	
			RegistrationDAO.createUser(user);					//kommt weg
		}
		
		System.err.println("Creating Patient-Relative-Relations ...");
		Patient pat1 = PatientDAO.getPatient(1);
			pat1.setRelatives(relatives);
		PatientDAO.updatePatient(pat1);
		System.out.println("Patient 1 updated");
			
		relatives.remove(0);
		Patient pat2 = PatientDAO.getPatient(3);
			pat2.setRelatives(relatives);
		PatientDAO.updatePatient(pat2);
		System.out.println("Patient 3 updated");
			
		relatives.remove(0);
		Patient pat3 = PatientDAO.getPatient(6);
			pat3.setRelatives(relatives);
		PatientDAO.updatePatient(pat3);
		System.out.println("Patient 6 updated");
		
		/*
		 * Office
		 */
		
		System.err.println("Opening Office ...");
		Office office = new Office();
			office.setName("Testoffice");
			office.setDoctors(doctors);
			
			Address address = new Address();
				address.setCity("Office-city");
				address.setPostalCode("0f1cc3");
				address.setStreet("Office-Street");
				address.setNumber("123");
			office.setAddress(address);
		
			Contact contact = new Contact();
				contact.setEmail("office.contact@mail.com");
				contact.setMobile("112");
				contact.setPhone("110");
			office.setContact(contact);
		
		URL urlO = new URL("http://localhost:8080/office?wsdl");
		QName qnameO = new QName("http://services.patientenportal.de/", "OfficeWSImplService");
		Service serviceO = Service.create(urlO, qnameO);
		OfficeWS off = serviceO.getPort(OfficeWS.class);
		Accessor createOffice = new Accessor();
			createOffice.setObject(office);
		String feedbackCO = off.createOffice(createOffice);
		if (feedbackCO != null){
		System.out.println("Doctors added: " + doctors.size());
		System.out.println("Office created");}
		
		
		
		
		
		// Authentifizierung
		System.out.println("Authentication ...");
		String username = "user10";
		String password = "pass10";
		
		URL urlA = new URL("http://localhost:8080/authentication?wsdl");
		QName qnameA = new QName("http://services.patientenportal.de/", "AuthenticationWSImplService");
		Service serviceA = Service.create(urlA, qnameA);
		AuthenticationWS auth = serviceA.getPort(AuthenticationWS.class);
		
		ClientHelper.putUsernamePassword(username, password, auth);
        auth.authenticateUser(ActiveRole.Doctor);
        String token = auth.getSessionToken(username);
		System.out.println("Success!");
				
		// Fälle anlegen
		URL urlC = new URL("http://localhost:8080/case?wsdl");
		QName qnameC = new QName("http://services.patientenportal.de/", "CaseWSImplService");
		Service serviceC = Service.create(urlC, qnameC);
		CaseWS casews = serviceC.getPort(CaseWS.class);
		
		System.err.println("Creating Cases ...");
		i = 0;
		for (int s = 6; s>=1; s--){
			i++;
			i.toString();
			Case pcase = new Case();
				pcase.setTitle("Case " + i);
				pcase.setDescription("Description " + i);
				pcase.setStatus(true);
			
				Patient pat = PatientDAO.getPatient(i);
				pcase.setPatient(pat);
			
			if (i <= 2){
				Doctor doc = DoctorDAO.getDoctor(3);
				List <Doctor> doclist = new ArrayList<Doctor>();
					doclist.add(doc);
				pcase.setDoctors(doclist);
			}
			
			else {
				Doctor doc1 = DoctorDAO.getDoctor(1);
				Doctor doc2 = DoctorDAO.getDoctor(2);
				List <Doctor> doclist = new ArrayList<Doctor>();
					doclist.add(doc1);
					doclist.add(doc2);
				pcase.setDoctors(doclist);
			}
			
			Accessor accessor = new Accessor();
				accessor.setToken(token);
				accessor.setObject(pcase);
			casews.createCase(accessor);

			System.out.println("Case for Patient " + pat.getPatientID() + " created");
		}
		
		//XXX
		//Medikamente + Medikation
			URL urlMedicine = new URL("http://localhost:8080/medicine?wsdl");
			QName qnameMed = new QName("http://services.patientenportal.de/", "MedicineWSImplService");
			Service serviceMed = Service.create(urlMedicine, qnameMed);
			MedicineWS medws = serviceMed.getPort(MedicineWS.class);
			
			URL urlMedication = new URL("http://localhost:8080/medication?wsdl");
			QName qnameMedica = new QName("http://services.patientenportal.de/", "MedicationWSImplService");
			Service serviceMedica = Service.create(urlMedication, qnameMedica);
			MedicationWS medicaws = serviceMedica.getPort(MedicationWS.class);
			
			Accessor createMedication = new Accessor(token);
			Accessor createMedicine = new Accessor(token);
			System.err.println("Develop Medicine ...");
			
			for(int zahl = 1 ; zahl<=6 ; zahl++){
			Medicine medicine = new Medicine();
				medicine.setDrugmaker("Böser Pharmakonzern"+zahl);
				medicine.setActiveIngredient("Krankium"+zahl);
				medicine.setName("InnovativerName"+zahl);
				createMedicine.setObject(medicine);
				medws.createMedicine(createMedicine);
				System.out.println("Medicines added: " + medicine.getName());
				
				
			//	for (int zahli = 6; zahli>=1 ; zahli--){
					Medication medication1 = new Medication();
					medication1.setDosage("212"+zahl);
					medication1.setDuration("extremslange Stunden:"+zahl);			
					medication1.setPcase(CaseDAO.getCase(zahl));
					medication1.setMedicine(MedicineDAO.getMedicine(zahl));
					//medication1.setPrescribedBy(UserDAO.getUser(10).getDoctor());
					createMedication.setObject(medication1);
					
					medicaws.createMedication(createMedication);
					System.out.println("Medicationes added: " + medication1.getDosage());
			//	}
				
			}
				
		//Vitaldaten
			URL urlVitalData = new URL("http://localhost:8080/vitaldata?wsdl");
			QName qnameVD = new QName("http://services.patientenportal.de/", "VitalDataWSImplService");
			Service serviceVD = Service.create(urlVitalData, qnameVD);
			VitalDataWS vitaldataws = serviceVD.getPort(VitalDataWS.class);
			
			System.err.println("Creating Vitaldata ...");
			
			Accessor createVitalData = new Accessor(token);
			Date timestamp = new Date();
			
			for(int j = 1 ; j<=6 ; j++){
				VitalData vitaldata = new VitalData();
				
				vitaldata.setPcase(CaseDAO.getCase(j));
				
				for(int j2 = 1; j2<=6; j2++){
				timestamp = ClientHelper.parseStringtoTimeStamp("07."+j2+".2017 1"+j2+":05");
				vitaldata.setTimestamp(timestamp);
				vitaldata.setValue(80.2+j);
				vitaldata.setVitalDataType(VitalDataType.WEIGHT);
				
				createVitalData.setObject(vitaldata);
				vitaldataws.createVitalData(createVitalData);
				System.out.println("Vitaldata added: " +vitaldata.getTimestamp());
				}
			}
			//XXX	
		
		
		
		
		System.exit(0);
	}	
}
