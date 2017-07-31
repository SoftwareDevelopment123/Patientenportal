package de.patientenportal.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Websession", catalog = "patientenportal")
public class WebSession {

	@Id
	@Column(name = "webSessionID")
	private int webSessionID;

	@Column
	private String token;

	@Column(name = "validtill", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validtill;

	@MapsId
	@OneToOne(mappedBy = "webSession")
	@JoinColumn(name = "webSessionID")
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "ActiveRole")
	private ActiveRole activeRole;

	public WebSession() {
	}

	public ActiveRole getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(ActiveRole activeRole) {
		this.activeRole = activeRole;
	}

	public int getWebSessionID() {
		return webSessionID;
	}

	public void setWebSessionID(int webSessionID) {
		this.webSessionID = webSessionID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
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
