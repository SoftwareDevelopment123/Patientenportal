package de.patientenportal.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/*
 * Eine WebSession wird erzeugt, wenn sich ein User mit gültigem username und password anmeldet. Sie enthält die User, einen Zeitstempel
 * und einen Token, der an den User zurückgegeben wird.
 * 
 */


@Entity
//@PrimaryKeyJoinColumn //(name="baseclass_id")
@Table(name = "Websession", catalog = "patientenportal")
public class WebSession  {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "webSessionID", unique = true, nullable = false)
	private int webSessionID;
	
	@Column
	private String token;
	
	public int getWebSessionID() {
		return webSessionID;
	}

	public void setWebSessionID(int webSessionID) {
		this.webSessionID = webSessionID;
	}

	@OneToOne
	private User user;
	
	@Column(name="validtill", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validtill;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public WebSession() {
		
	}

	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getValidTill() {
		return validtill;
	}
	
	public void setValidTill(Date validtill) {
		this.validtill = validtill;
	}
}
