package smalltests;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import de.patientenportal.entities.PatientCase;
import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;
import de.patientenportal.persistence.PatientCaseDAO;



public class PatientCaseVitalDataTest {
	
	@Test
	public void addPatientTest() throws Exception{
		
		
		//Vitaldaten anlegen
		Set<VitalData> vitalDatas = new HashSet<VitalData>();
		vitalDatas.add(new VitalData("12:30 Uhr,05.06.2017",4.5, VitalDataType.BLOODSUGAR));
		vitalDatas.add(new VitalData("13:30 Uhr,05.06.2017",6.5, VitalDataType.BLOODSUGAR));
		vitalDatas.add(new VitalData("13:30 Uhr,05.06.2017",86.5, VitalDataType.WEIGHT));
		
		PatientCase patientcase = new PatientCase("Kreuzbandriss",true);
		patientcase.setVitaldatas(vitalDatas);
		
		Set<VitalData> vitalDatas2 = new HashSet<VitalData>();
		vitalDatas2.add(new VitalData("12:30 Uhr,05.06.2017",4.5, VitalDataType.BLOODSUGAR));
		vitalDatas2.add(new VitalData("13:30 Uhr,05.06.2017",6.5, VitalDataType.BLOODSUGAR));
		vitalDatas2.add(new VitalData("13:30 Uhr,05.06.2017",86.5, VitalDataType.WEIGHT));
		
		PatientCase patientcase2 = new PatientCase("Mittelohrentzündung",true);
		patientcase2.setVitaldatas(vitalDatas2);
		
		
		//VitalDataDAO.add(vitalData);
		PatientCaseDAO.add(patientcase);
		PatientCaseDAO.add(patientcase2);
		
		Set <VitalData> vitaldata = PatientCaseDAO.getCase(2).getVitaldatas();		
		
		Iterator<VitalData> it = vitaldata.iterator();
		while(it.hasNext()){
			VitalData element = it.next();


			System.out.println(element.getVitalDataID());
			System.out.println(element.getTimestamp());
			
			if(element.getVitalDataID() == 4){
				it.remove();
				System.out.println("Eintrag " + 4 + " wird gelöscht");

			}			
		}
		/*int caseID = 2;
		PatientCase casetoupdate = new PatientCase();
		
		casetoupdate.setCaseID(caseID);
		casetoupdate.setVitaldatas(vitaldata);
		
		PatientCaseDAO.updateVitalData(casetoupdate);*/
	
				
	
		}
}