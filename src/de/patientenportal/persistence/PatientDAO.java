package de.patientenportal.persistence;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.Relative;
import de.patientenportal.entities.User;

public class PatientDAO {

	// Patient abrufen
	public static Patient getPatient(int patientID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Patient patient = new Patient();
				
		session.beginTransaction();		
		patient = (Patient)session.get(Patient.class, patientID);
		
		if (patient != null){
			Hibernate.initialize(patient.getRelatives());			// LAZY-HIBERNATE-MAGIC
		}
		
		session.getTransaction().commit();

		session.close();
			
		return patient;
	}
	
	// Patient ändern
	public static String updatePatient (Patient updatedpatient){
		int id = updatedpatient.getPatientID();
		if(id!=0){
					
		User user = updatedpatient.getUser();
		String bloodtype = updatedpatient.getBloodtype();
		List <Relative> relatives = updatedpatient.getRelatives();
		
		/*Insurance insurance = updatedpatient.getInsurance();		Hinzugefügt wenn die Entitity Patient fertig ist	
		List <Case> cases = updatedpatient.getCases;	
		List <MDoc> Mdoc = updatedpatient.getMdoc;*/
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();				
		Patient patienttoupdate = session.get(Patient.class, id);
				
		patienttoupdate.setUser(user);
		patienttoupdate.setBloodtype(bloodtype);
		patienttoupdate.setRelatives(relatives);

/*		patienttoupdate.setInsurance(insurance);					Hinzugefügt wenn die Entitity Patient fertig ist
		patienttoupdate.setCases(cases);
		patienttoupdate.setMdoc(Mdoc);*/
		
		session.getTransaction().commit();
		session.close();
		return "success";
		}
		else {
			return "noID";
	}
	}
	
	//Patient löschen
	public static String deletePatient (int patientID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Patient patient = (Patient)session.get(Patient.class, patientID);
		session.delete(patient);
		session.getTransaction().commit();
			
			} 
		catch (Exception e) {
				System.err.println("Flush-Error: " + e);
				return "error";
			} 
		finally {
				session.close();
			}
			return "success";
		}	

}