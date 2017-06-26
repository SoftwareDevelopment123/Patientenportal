package de.patientenportal.persistence;

import de.patientenportal.entities.Insurance;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class InsuranceDAO {
	
	// Insurance über ID abrufen
	public static Insurance getInsurance(int InsuranceID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Insurance insurance = new Insurance();
		
		try{
		session.beginTransaction();		
		insurance = (Insurance)session.get(Insurance.class, InsuranceID);
			if (insurance != null){
				Hibernate.initialize(insurance.getPatients());			// LAZY-HIBERNATE-MAGIC
			}
		session.getTransaction().commit();
	
		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return null;
		} finally {
			session.close();
		}
			
		return insurance;
	}	

	// Insurance anlegen
	public static String createInsurance(Insurance insurance) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(insurance);
		session.getTransaction().commit();
			
		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";	
	}
	
	// Insurance Update
	public static String updateInsurance(Insurance updatedinsurance){
		int id = updatedinsurance.getInsuranceID();
		if(id!=0){
					
			String Name = updatedinsurance.getName();
			int InsuranceNr = updatedinsurance.getInsuranceNr();

			Session session = HibernateUtil.getSessionFactory().openSession();
			try{
			session.beginTransaction();				
			Insurance insurancetoupdate = session.get(Insurance.class, id);			
				insurancetoupdate.setName(Name);
				insurancetoupdate.setInsuranceNr(InsuranceNr);	
			session.getTransaction().commit();
			
			} catch (Exception e) {
				System.err.println("Flush-Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			return "success";
		}
		else {
			return "noID";
		}
	}
	
	// Insurance löschen 
	public static String deleteInsurance(int InsuranceID){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		Insurance insurance = (Insurance)session.get(Insurance.class, InsuranceID);
		session.delete(insurance);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return "error";
		} finally {
			session.close();
		}
		return "success";
	}
	
}
