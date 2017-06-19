package de.patientenportal.persistence;

import org.hibernate.cfg.Configuration;

import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Insurance;
import de.patientenportal.entities.Office;
import de.patientenportal.entities.User;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InsuranceDAO {
	
	public static String createInsurance(Insurance insurance) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(insurance);
		session.getTransaction().commit();
			
		session.close();
		return "success";	
}
	
	//Insurance Update Update der Patienten ist von hier nicht möglich
	public static String updateInsurance(Insurance updatedinsurance){
		int id = updatedinsurance.getInsuranceID();
		if(id!=0){
					
			String Name = updatedinsurance.getName();
			int InsuranceNr = updatedinsurance.getInsuranceNr();

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();				
			Insurance insurancetoupdate = session.get(Insurance.class, id);
						
				insurancetoupdate.setName(Name);
				insurancetoupdate.setInsuranceNr(InsuranceNr);
				
			session.getTransaction().commit();
			session.close();
			return "success";
		}
		else {
			return "noID";
		}
	}
	
	//INsurance löschen 
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
