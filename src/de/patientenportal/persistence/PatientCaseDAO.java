package de.patientenportal.persistence;

import java.util.Set;

import org.hibernate.Session;

import de.patientenportal.entities.PatientCase;
import de.patientenportal.entities.VitalData;


public class PatientCaseDAO {
	// Fall hinzufügen
	public static void add(PatientCase patientCase) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		session.save(patientCase);
		session.getTransaction().commit();
		
		session.close();
	}
	
	public static PatientCase getCase(int caseID) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		PatientCase patientcase = new PatientCase();
		
		session.beginTransaction();
		
		patientcase = (PatientCase)session.get(PatientCase.class, caseID);
				
		
		session.getTransaction().commit();
			
		session.close();
		
		return patientcase;
	}

	public static void updateVitalData(PatientCase casetoupdate) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		int caseID = casetoupdate.getCaseID();
		Set <VitalData> vdata = casetoupdate.getVitaldatas();
		
		PatientCase updatedcase = session.get(PatientCase.class, caseID);
		
		updatedcase.setVitaldatas(vdata);	
		
		session.saveOrUpdate(casetoupdate);
		
		session.getTransaction().commit();
			
		session.close();
		
	}


	
}

