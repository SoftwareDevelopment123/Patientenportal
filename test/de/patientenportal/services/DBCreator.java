package de.patientenportal.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.patientenportal.entities.*;
import de.patientenportal.entities.response.Accessor;
import de.patientenportal.persistence.*;

public class DBCreator {

	public static void main(String[] args) throws MalformedURLException {

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
				user.setBirthdate(i + ".1.2001");
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
			
			if (s >= 10) {
				Relative relative = new Relative();
				RegistrationDAO.createRelative(relative);
				user.setRelative(relative);
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
			RegistrationDAO.createUser(user);
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
		Accessor createOffice = new Accessor(office);
		String feedbackCO = off.createOffice(createOffice);
		if (feedbackCO != null){
		System.out.println("Doctors added: " + doctors.size());
		System.out.println("Office created");}
		
		
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
			CaseDAO.createCase(pcase);
			System.out.println("Case-ID " + pcase.getCaseID() + " for Patient " + pat.getPatientID());
		}

		
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
			CaseDAO.createCase(pcase);
			System.out.println("Case-ID " + pcase.getCaseID() + " for Patient " + pat.getPatientID());
		}

		
		System.exit(0);
	}	
}
