package de.patientenportal.persistence;

import org.junit.Test;
import org.junit.Assert;
import de.patientenportal.entities.Case;
import de.patientenportal.entities.Doctor;
import de.patientenportal.entities.Medication;
import de.patientenportal.entities.Medicine;

public class MedicineMedicationTest {

	@Test
	public void main(){
		
		// Setup Case/Doctor/Medicine/Medication
		Case newcase = new Case("Neuer Fall","Beschreibung eines Falles mit viel Medikation");
			CaseDAO.createCase(newcase);
		
		Doctor newdoctor = new Doctor("Hausarzt");
			RegistrationDAO.createDoctor(newdoctor);
		
		Medicine med1 = new Medicine("Supergel 5000","Superfirma THX");
			String feedbackCM1 = MedicineDAO.createMedicine(med1);
				Assert.assertEquals("success", feedbackCM1);
		Medicine med2 = new Medicine("Aspirin XXL"	,"Superfirma THX");
			String feedbackCM2 = MedicineDAO.createMedicine(med2);
				Assert.assertEquals("success", feedbackCM2);
		Medicine med3 = new Medicine("Fibrizin"		,"Future-Meds Inc.");
			String feedbackCM3 = MedicineDAO.createMedicine(med3);
				Assert.assertEquals("success", feedbackCM3);
		
		Medication m1 = new Medication(med1, "2ml"			, "2 Wochen", newdoctor, newcase);
			String feedbackCMed1 = MedicationDAO.createMedication(m1);
				Assert.assertEquals("success", feedbackCMed1);
		Medication m2 = new Medication(med2, "10 Tabletten"	, "10 Tage"	, newdoctor, newcase);
			String feedbackCMed2 = MedicationDAO.createMedication(m2);
				Assert.assertEquals("success", feedbackCMed2);
		Medication m3 = new Medication(med3, "100mg"		, "einmalig", newdoctor, newcase);
			String feedbackCMed3 = MedicationDAO.createMedication(m3);
				Assert.assertEquals("success", feedbackCMed3);

		// Medizin und Medikation einzeln abrufen (Test beim Anlegen)
		Medicine getmedicine1 = MedicineDAO.getMedicine(med1.getMedicineID());
			Assert.assertEquals(med1.getMedicineID(), getmedicine1.getMedicineID());
			Assert.assertEquals(med1.getName()		, getmedicine1.getName());
			Assert.assertEquals(med1.getDrugmaker()	, getmedicine1.getDrugmaker());
			Assert.assertEquals(1					, getmedicine1.getMedication().size());
			
		Medication getmedication1 = MedicationDAO.getMedication(m1.getMedicationID());
			Assert.assertEquals(m1.getMedicationID()					, getmedication1.getMedicationID());
			Assert.assertEquals(m1.getMedicine().getMedicineID()		, getmedication1.getMedicine().getMedicineID());
			Assert.assertEquals(m1.getMedicine().getName()				, getmedication1.getMedicine().getName());
			Assert.assertEquals(m1.getDosage()							, getmedication1.getDosage());
			Assert.assertEquals(m1.getDuration()						, getmedication1.getDuration());
			Assert.assertEquals(m1.getPrescribedBy().getDoctorID()		, getmedication1.getPrescribedBy().getDoctorID());
			Assert.assertEquals(m1.getPrescribedBy().getSpecialization(), getmedication1.getPrescribedBy().getSpecialization());
			Assert.assertEquals(m1.getPcase().getCaseID()				, getmedication1.getPcase().getCaseID());
			Assert.assertEquals(m1.getPcase().getTitle()				, getmedication1.getPcase().getTitle());
	
		// Medication/Medicine über Case abrufen
		Case getcase = CaseDAO.getCase(newcase.getCaseID());
			Assert.assertEquals(3							, getcase.getMedication().size());
			Assert.assertEquals(m1.getMedicationID()		, getcase.getMedication().get(0).getMedicationID());
			Assert.assertEquals(m1.getDosage()				, getcase.getMedication().get(0).getDosage());
			
			;
			Assert.assertEquals(m1.getMedicine().getName()	, MedicineDAO.getMedicine(getcase.getMedication().get(0)
																.getMedicine().getMedicineID()).getName());
			
		// Medicine Update
		Medicine newmedicine = MedicineDAO.getMedicine(med2.getMedicineID());
			newmedicine.setDrugmaker("MEDIMAX");
		String feedbackUM = MedicineDAO.updateMedicine(newmedicine);
			Assert.assertEquals("success", feedbackUM);
		
		Medicine medicineUTest = MedicineDAO.getMedicine(newmedicine.getMedicineID());
			Assert.assertEquals(newmedicine.getDrugmaker(), medicineUTest.getDrugmaker());
		
		// Medication Update
		Medication newmedication = MedicationDAO.getMedication(m3.getMedicationID());
			newmedication.setDosage("20mg");
				Doctor d2 = new Doctor("Hautarzt");
				RegistrationDAO.createDoctor(d2);
			newmedication.setPrescribedBy(d2);
		String feedbackUMed = MedicationDAO.updateMedication(newmedication);
			Assert.assertEquals("success", feedbackUMed);
		
		Medication medicationUtest = MedicationDAO.getMedication(newmedication.getMedicationID());
			Assert.assertEquals(newmedication.getDosage()							, medicationUtest.getDosage());
			Assert.assertEquals(newmedication.getPrescribedBy().getDoctorID()		, medicationUtest.getPrescribedBy().getDoctorID());
			Assert.assertEquals(newmedication.getPrescribedBy().getSpecialization()	, medicationUtest.getPrescribedBy().getSpecialization());
		
		// Medicine/Medication - Delete
		String feedbackDMedi = MedicationDAO.deleteMedication(m1.getMedicationID());
			Assert.assertEquals("success", feedbackDMedi);
			
		String feedbackDMed = MedicineDAO.deleteMedicine(med1.getMedicineID());
			Assert.assertEquals("success", feedbackDMed);
			
		Medication deletedMedi = MedicationDAO.getMedication(m1.getMedicationID());
			Assert.assertNull(deletedMedi);
		
		Medicine deletedMed	= MedicineDAO.getMedicine(med1.getMedicineID());
			Assert.assertNull(deletedMed);
	}
	
}
