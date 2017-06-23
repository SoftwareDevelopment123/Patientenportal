package de.patientenportal.persistence;

import org.hibernate.Session;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

public class VitalDataDAO {
	
	// VitalData abrufen
	public static VitalData getVitalData(int vitalDataID){
		Session session = HibernateUtil.getSessionFactory().openSession();
		VitalData vitalData = new VitalData();
		
		try{
		session.beginTransaction();		
		vitalData = (VitalData)session.get(VitalData.class, vitalDataID);		
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Flush-Error: " + e);
			return null;
		} finally {
			session.close();
		}
		return vitalData;
	}

	// VitalData hinzufügen
	public static String add(VitalData vitalData) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		session.save(vitalData);
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return "error";
		} finally {
			session.close();
		}	
		return "success";
	}
	
	// VitalData ändern
	public static String updateVitalData (VitalData updatedVitalData){
		int id = updatedVitalData.getVitalDataID();
		if(id!=0){
				
		String timestamp = updatedVitalData.getTimestamp();
		Double value = updatedVitalData.getValue();
		VitalDataType vitalDataType = updatedVitalData.getVitalDataType();
		Case pcase = updatedVitalData.getPcase();
				
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
		session.beginTransaction();				
		VitalData vitaldDataToUpdate = session.get(VitalData.class, id);
				
		vitaldDataToUpdate.setTimestamp(timestamp);
		vitaldDataToUpdate.setValue(value);
		vitaldDataToUpdate.setVitalDataType(vitalDataType);
		vitaldDataToUpdate.setPcase(pcase);
		
		session.getTransaction().commit();
		
		} catch (Exception e) {
			System.err.println("Error: " + e);
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
	
	//VitalData löschen
	public static String deleteVitalData (int vitalDataID) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
		session.beginTransaction();
		VitalData vitalData = (VitalData)session.get(VitalData.class, vitalDataID);
		session.delete(vitalData);
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

