package de.patientenportal.services;

//import java.util.List;
import javax.jws.WebService;
import javax.transaction.Transactional;

import de.patientenportal.DAO.UserDAO;
import de.patientenportal.entities.User;

@WebService (endpointInterface = "services.UserWS")
public class UserWSImpl implements UserWS {

//	private UserDAO userdao;

//	@Transactional
//	public List<User> getAllusers() {
//		return UserDAO.getAllUsers();
//	}

	@Transactional
	public User getUser(int user_id) {
		return UserDAO.getUser(user_id);
	}

	@Transactional
	public void add(User user) {
//		UserDAO.add(user);

	}

	@Transactional
	public void delete(int user_id) {
//		UserDAO.delete(user_id);

	}

	@Transactional
	public void update(User user) {
//		UserDAO.update(user);

	}

}
