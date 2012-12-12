/**
 * 
 */
package com.suziwen.stories.webapp.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;
import com.suziwen.stories.webapp.action.bind.DefaultBindAction;
import com.suziwen.weibo.base.IUserAuth;

/**
 * @author suziwen
 * 
 */
public class BindAutoLoginFilter extends OncePerRequestFilter {
	
    /**
     * The UserManager
     */
    private UserManager userManager;
    
	/**
	 * 用户与第三方微博关系
	 */
	private IUserAuth userAuth;


	public UserManager getUserManager() {
		return userManager;
	}


	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}


	public IUserAuth getUserAuth() {
		return userAuth;
	}


	public void setUserAuth(IUserAuth userAuth) {
		this.userAuth = userAuth;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException {
		HttpSession session = request.getSession();

		List<UserAuth> userAuths = (List<UserAuth>) session.getAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		session.removeAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		List<UserAuth> willUserAuths = (List<UserAuth>) session.getAttribute(DefaultBindAction.WILL_AUTOLOGIN_USERAUTH);
		session.removeAttribute(DefaultBindAction.WILL_AUTOLOGIN_USERAUTH);
		String current_uid = ObjectUtils.toString(session.getAttribute(DefaultBindAction.CURRENT_UID));
		current_uid = "," + current_uid + ",";
		if (CollectionUtils.isEmpty(willUserAuths)) {
			throw new BaseException("绑定失败，你太长时间没进行操作，请重新绑定");
		}
		String uid = request.getParameter("uid");
		if (StringUtils.isBlank(uid)) {
			throw new BaseException("未选择身份登录");
		}
		if (current_uid.indexOf("," + uid + ",") < 0) {
			throw new BaseException("请勿进行欺骗登录");
		}
		UserAuth currentUserAuth = getUserAuthByUID(willUserAuths, uid);
		if (currentUserAuth == null) {
			throw new BaseException("请勿进行欺骗登录");
		}
		User user = this.userManager.get(Long.parseLong(uid));
		String upwd = user.getPassword();

		// this.getUser().logout(session);
		SecurityContextHolder.clearContext();
		// 进行绑定操作
		if (!CollectionUtils.isEmpty(userAuths)) {
			for (UserAuth userAuth : userAuths) {
				userAuth.setUserId(uid);
				this.getUserAuth().add(userAuth);
			}
		}

		//
		UserAuth sessionUserAuth = new UserAuth();
		BeanUtils.copyProperties(currentUserAuth, sessionUserAuth);
		session.setAttribute(DefaultBindAction.CURRENT_USERAUTH, sessionUserAuth);
		RequestDispatcher rd = request.getRequestDispatcher("/j_spring_security_check?j_username=" + uid + "&j_password=" + upwd);
		rd.forward(request, response);
		filterChain.doFilter(request, response);
	}
	
	private UserAuth getUserAuthByUID(List<UserAuth> userAuths,String uid){
		for(UserAuth userAuth : userAuths){
			if(uid.equalsIgnoreCase(userAuth.getUserId())){
				return userAuth	;
			}
		}
		return null;
	}
}
