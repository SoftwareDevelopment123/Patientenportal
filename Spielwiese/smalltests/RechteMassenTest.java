package smalltests;

import java.util.Arrays;
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
		
		List<Rights> rights1 = Arrays.asList(r1);
			case1.setRights(rights1);
		List<Rights> rights2 = Arrays.asList(r2);
			case2.setRights(rights2);
		List<Rights> rights3 = Arrays.asList(r3);
			case3.setRights(rights3);
		List<Rights> rights4 = Arrays.asList(r4);
			case4.setRights(rights4);
		List<Rights> rights5 = Arrays.asList(r5);
			case5.setRights(rights5);

		CaseDAO.createCase(case1);
		CaseDAO.createCase(case2);
		CaseDAO.createCase(case3);
		CaseDAO.createCase(case4);
		CaseDAO.createCase(case5);

		List<Case> reading = RightsDAO.getDocRCases(1);
			for (Case cases : reading){
				System.out.println("----------------------------------");
				System.out.println(cases.getCaseID());
				System.out.println(cases.getTitle());
				System.out.println(cases.getDescription());
			}
	}
	
}
