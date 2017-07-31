package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Medicine", catalog = "patientenportal")
@XmlRootElement(name = "medicine")
public class Medicine {

	private int medicineID;
	private String name;
	private String drugmaker;
	private String activeIngredient;
	private List<Medication> medication;

	public Medicine() {
	}

	public Medicine(String name, String drugmaker) {
		this.name = name;
		this.drugmaker = drugmaker;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MEDICINE_ID", unique = true, nullable = false)
	public int getMedicineID() {
		return medicineID;
	}

	public void setMedicineID(int medicineID) {
		this.medicineID = medicineID;
	}

	@Column(name = "NAME", length = 60, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DRUGMAKER", length = 45, nullable = false)
	public String getDrugmaker() {
		return drugmaker;
	}

	public void setDrugmaker(String drugmaker) {
		this.drugmaker = drugmaker;
	}

	@Column(name = "ACTIVEINGREDIENT", length = 90)
	public String getActiveIngredient() {
		return activeIngredient;
	}

	public void setActiveIngredient(String activeIngredient) {
		this.activeIngredient = activeIngredient;
	}

	@OneToMany(mappedBy = "medicine")
	@XmlTransient
	public List<Medication> getMedication() {
		return medication;
	}

	public void setMedication(List<Medication> medication) {
		this.medication = medication;
	}
}