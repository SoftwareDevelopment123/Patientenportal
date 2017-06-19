package de.patientenportal.persistence;

import org.hibernate.cfg.Configuration;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.Patient;
import de.patientenportal.entities.User;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InsuranceDAO {
	
	// Insurance über ID abrufen
	public static Insurance getInsurance(int InsuranceID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Insurance insurance = new Insurance();
				
		session.beginTransaction();		
		insurance = (Insurance)session.get(Insurance.class, InsuranceID);
		
		if (insurance != null){
			Hibernate.initialize(insurance.getPatients());			// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();
	
		session.close();
			
		return insurance;
	}	

	// Insurance anlegen
	public static String createInsurance(Insurance insurance) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(insurance);
		session.getTransaction().commit();
			
		session.close();
		return "success";	
	}
	
	// Insurance Update (Verknüpfte Patienten können hier nicht geändert werden)
	public static String updateInsurance(Insurance updatedinsurance){
		int id = updatedinsurance.getInsuranceID();
		if(id!=0){
					
			String Name = updatedinsurance.getName();
			int InsuranceNr = updatedinsurance.getInsuranceNr();
			List <Patient> patlist = updatedinsurance.getPatients();

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Insurance insurancetoupdate = session.get(Insurance.class, id);
						
				insurancetoupdate.setName(Name);
				insurancetoupdate.setInsuranceNr(InsuranceNr);
				insurancetoupdate.setPatients(patlist);
				
			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
	}
	
	// Insurance löschen 
	public static String deleteInsurance(int insurance_id){
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Insurance insurance = (Insurance)session.get(Insurance.class, insurance_id);
		session.delete(insurance);
		session.getTransaction().commit();
		
		session.close();
		return "success";
	}
	
}
