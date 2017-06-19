package de.patientenportal.services;

import javax.jws.WebService;
import javax.transaction.Transactional;
import de.patientenportal.entities.User;
import de.patientenportal.persistence.UserDAO;

@WebService (endpointInterface = "de.patientenportal.services.UserWS")
public class UserWSImpl implements UserWS {

	@Transactional
	public User getUser(int user_id) {
		return UserDAO.getUser(user_id);
	}

	@Transactional
	public void deleteUser(int user_id) {
		UserDAO.deleteUser(user_id);
	}

	@Transactional
	public void updateUser(User user) {
		UserDAO.updateUser(user);
	}

}
