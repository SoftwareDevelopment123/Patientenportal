package de.patientenportal.entities.response;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import de.patientenportal.entities.User;

@XmlRootElement (name="userListResponse")
public class UserListResponse extends ListResponse{

	private List<User> responseList;

	public UserListResponse(){
	}
	
	public UserListResponse(List<User> userlist){
		this.responseList = userlist;
	}
		
	@XmlElement(name="user")
	public List<User> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<User> responseList) {
		this.responseList = responseList;
	}
	
}
