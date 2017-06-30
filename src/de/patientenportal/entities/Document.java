package de.patientenportal.entities;

import javax.persistence.Column;

public class Document {

	public String title;
	public String description;
	//private Document
	
	public Document(){
		
	}
	
	@Column(name = "TITLE", length = 20)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DESCRIPTION", length = 20)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
