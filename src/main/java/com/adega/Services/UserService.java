package com.adega.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.adega.DAO.UserDAO;
import com.adega.domains.User;

@Service
public class UserService {

	
	@Autowired
	UserDAO userDAO;
	
	public User findUser(String userId) {
		User user = userDAO.findUser(userId);
		return user;
	}

	public List<User> getListUser() {
		List<User> listUser = userDAO.getListUser();
		return listUser;
	}

	public void save(User user) {
		String salt = BCrypt.gensalt(12);
		user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
		if(user.getId() == 0) {
			userDAO.save(user);
		}else {
			userDAO.update(user);
		}
		
	}

	public void deleteUser(String userId) {
		 userDAO.deleteUser(userId);
	}

}
