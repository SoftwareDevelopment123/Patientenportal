package smalltests;

import java.util.List;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Rights;
import de.patientenportal.persistence.CaseDAO;
import de.patientenportal.persistence.RegistrationDAO;
import de.patientenportal.persistence.RightsDAO;

public class RechteMassenTest {

	public static void main(String[] args) {
		
		Case case1 = new Case("Impf-Pass"		,"Zusammenstellung aller Impfungen");
		Case case2 = new Case("Knie-Operation"	,"Nach einem bösen Unfall nötig");
		Case case3 = new Case("Herzinfarkt"		,"Oh oh oh ...");
		Case case4 = new Case("Knochenbruch"	,"Am Arm, Sturz bei schlechtem Wetter");
		Case case5 = new Case("Erkältung"		,"Was gibts da zu sagen?");

		Doctor D1 = new Doctor("Hausarzt");
			RegistrationDAO.createDoctor(D1);
		Doctor D2 = new Doctor("Kardiologe");
			RegistrationDAO.createDoctor(D2);

		Rights r1 = new Rights(case1, D1,null,true,true);
		Rights r2 = new Rights(case2, D1,null,true,false);
		Rights r3 = new Rights(case3, D2,null,true,false);
		Rights r4 = new Rights(case4, D1,null,true,false);
		Rights r5 = new Rights(case5, D1,null,true,true);
		Rights r6 = new Rights(case5, D2,null,true,false);
		
		CaseDAO.createCase(case1);
		CaseDAO.createCase(case2);
		CaseDAO.createCase(case3);
		CaseDAO.createCase(case4);
		CaseDAO.createCase(case5);
		
		RightsDAO.createRight(r1);
		RightsDAO.createRight(r2);
		RightsDAO.createRight(r3);
		RightsDAO.createRight(r4);
		RightsDAO.createRight(r5);
		RightsDAO.createRight(r6);
		
		List<Case> reading = RightsDAO.getDocRCases(1);
			for (Case cases : reading){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}
	}
	
}
