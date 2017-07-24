package de.patientenportal.entities.response;

public class Accessor {

	private String token;
	private Object object;
	private int id;
	public Accessor(){
	}
	
	public Accessor(String token){
		this.token = token;
	}
	
	public Accessor(String token, Object object){
		this.token = token;
		this.object = object;
	}
	
	public Accessor(String token, Object object, int id){
		this.token = token;
		this.object = object;
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
