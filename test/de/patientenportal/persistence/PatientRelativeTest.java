package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import de.patientenportal.entities.*;

public class PatientRelativeTest {

	@Test
	public void main(){
		
		//Patienten mit Verwandten anlegen (ManyToMany)
		Relative rel1 = new Relative();
		Relative rel2 = new Relative();
		Relative rel3 = new Relative();
		
		Relative rel4 = new Relative();
		Relative rel5 = new Relative();
		
		RegistrationDAO.createRelative(rel1);
		RegistrationDAO.createRelative(rel2);
		RegistrationDAO.createRelative(rel3);
		RegistrationDAO.createRelative(rel4);
		String feedbackCR = RegistrationDAO.createRelative(rel5);
			Assert.assertEquals("success", feedbackCR);
		
		
		Patient pat1 = new Patient();
		Patient pat2 = new Patient();
		
			pat1.setBloodtype("ABC");
			pat2.setBloodtype("XYZ");
	
			List<Relative> list1 = new ArrayList<Relative>();
				list1.add(rel1);
				list1.add(rel3);
				list1.add(rel4);
				list1.add(rel5);
			
			List<Relative> list2 = new ArrayList<Relative>();
				list2.add(rel2);
				list2.add(rel4);
			
			pat1.setRelatives(list1);
			pat2.setRelatives(list2);
		
		RegistrationDAO.createPatient(pat1);
		String feedbackCP = RegistrationDAO.createPatient(pat2);
			Assert.assertEquals("success", feedbackCP);
		
		//Bidirektionaler Zugriff
		//Patient
		List<Relative> patlist1 = PatientDAO.getPatient(1).getRelatives();
		List<Relative> patlist2 = PatientDAO.getPatient(2).getRelatives();
			
			Assert.assertEquals(1, patlist1.get(0).getRelativeID());
			Assert.assertEquals(3, patlist1.get(1).getRelativeID());
			Assert.assertEquals(4, patlist1.get(2).getRelativeID());
			Assert.assertEquals(5, patlist1.get(3).getRelativeID());
		
			Assert.assertEquals(2, patlist2.get(0).getRelativeID());
			Assert.assertEquals(4, patlist2.get(1).getRelativeID());
		
		//Relative
		List<Patient> rellist1 = RelativeDAO.getRelative(1).getPatients();
		List<Patient> rellist4 = RelativeDAO.getRelative(4).getPatients();
				
			Assert.assertEquals(1, rellist1.get(0).getPatientID());
		
			Assert.assertEquals(1, rellist4.get(0).getPatientID());
			Assert.assertEquals(2, rellist4.get(1).getPatientID());
			
			
			
	}
	
}
