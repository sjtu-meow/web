package me.sjtumeow.meow.authorization.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.sjtumeow.meow.dao.UserRepository;
import me.sjtumeow.meow.model.User;

@Component
public class WebAuthUtility {
	
	private String CURRENT_USER_ATTR_FIELD = "user";
	
	@Autowired
    private UserRepository userRepository;
	
	public boolean checkAuth(HttpSession session) {
		Long id = (Long) session.getAttribute(CURRENT_USER_ATTR_FIELD);
		return id != null && userRepository.existsActive(id);
	}
	
	public boolean checkAdmin(HttpSession session) {
		Long id = (Long) session.getAttribute(CURRENT_USER_ATTR_FIELD);
		if (id == null)
			return false;
		User user = userRepository.findOneActive(id);
		return user != null && user.getIsAdmin();
	}
	
	public User getCurrentUser(HttpSession session) {
		Long id = (Long) session.getAttribute(CURRENT_USER_ATTR_FIELD);
		return id == null ? null : userRepository.findOneActive(id);
	}
	
	public void login(HttpSession session, Long userId){
		session.setAttribute(CURRENT_USER_ATTR_FIELD, userId);
	}
	
	public void logout(HttpSession session) {
		session.removeAttribute(CURRENT_USER_ATTR_FIELD);
	}
}
