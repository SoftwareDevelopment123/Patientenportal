package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import de.patientenportal.entities.Case;
//import de.patientenportal.entities.Case;
import de.patientenportal.entities.Rights;

public class RightsDAO {

	//Hibernate-Initialize für doctor und relative nicht vergessen!
	
	// Prüfen, bei welchen Fällen ich (als Doktor) Leserechte habe
	public static List<Case> getDocRCases(int doctorID){
			
		Session session = HibernateUtil.getSessionFactory().openSession();
						
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery <Rights> query = builder.createQuery(Rights.class);
			
		Root<Rights> right = 	query.from(Rights.class);
									Predicate idP = builder.equal(right.get("doctor"), doctorID);
								query.select(right).where(idP).distinct(true);
			
		List <Rights> result;					
		try {
		result = session.createQuery(query).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
		
		// Als Alternative hierzu könnte man auch die Rechte zurückgeben
		// (dann stekt auch das WRight gleich mit drin)
		
		List<Case> cases = new ArrayList<Case>();
		for (Rights it : result){
			cases.add(it.getPcase());
		}
		return cases;	
	}	
	
	
}
