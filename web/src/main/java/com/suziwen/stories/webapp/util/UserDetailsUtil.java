/**
 * 
 */
package com.suziwen.stories.webapp.util;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author suziwen
 * 
 */
public class UserDetailsUtil {

	private static UserManager userManager;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * 获得当前登录帐号
	 * 
	 * @return
	 */
	public static String getCurrAccount() {
		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static User getCurrUser() {
		String username = UserDetailsUtil.getCurrAccount();
		try {
			User user = userManager.getUserByUsername(username);
			return user;
		} catch (UsernameNotFoundException e) {
			return null;
		}

	}
}