/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.appfuse.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;

/********************************************************
 * <p>
 * Description:自动登录已经绑定的用户
 * bindAutoLogin和bindLogin的区别是
 * bindAutoLogin使用的登录身份是第三方账号，并同时绑定已经register的userauths,当前登录的userauth做为currentuserauth
 * bindlogin使用的是系统账号，并同时绑定已经register的userauths,同时使用最后一个userauth做为当前userauth
 * 
 * bindlogin里的registeruserauths不能为空，但bindautologin里的registeruserauths可以为空
 * </p>
 * <p>
 * Create Time: 2012-3-19 上午11:11:33
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class BindAutoLoginAction extends BaseBindAction {
	
	String isRemember;
	String rememberDate;

	public String cblinkexecute() throws Exception {
		HttpSession session = request.getSession();
		
		List<UserAuth > userAuths = (List<UserAuth>) session.getAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		session.removeAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		List<UserAuth> willUserAuths = (List<UserAuth>) session.getAttribute(DefaultBindAction.WILL_AUTOLOGIN_USERAUTH);
		session.removeAttribute(DefaultBindAction.WILL_AUTOLOGIN_USERAUTH);
		String current_uid = ObjectUtils.toString(session.getAttribute(DefaultBindAction.CURRENT_UID));
		current_uid = "," + current_uid +",";
		if (CollectionUtils.isEmpty(willUserAuths) ) {
			throw new BaseException("绑定失败，你太长时间没进行操作，请重新绑定");
		}
		String uid = request.getParameter("uid");
		if(StringUtils.isBlank(uid)){
			throw new BaseException("未选择身份登录");
		}
		if(current_uid.indexOf(","+uid+",") <0){
			throw new BaseException("请勿进行欺骗登录");
		}
		UserAuth currentUserAuth = getUserAuthByUID(willUserAuths,uid);
		if(currentUserAuth == null ){
			throw new BaseException("请勿进行欺骗登录");
		}
		User user = this.userManager.get(Long.parseLong(uid));
		String upwd = user.getPassword();
		rememberUser(uid);
		//this.getUser().logout(session);
		SecurityContextHolder.clearContext();
		//进行绑定操作
		if(!CollectionUtils.isEmpty(userAuths)){
			for(UserAuth userAuth : userAuths){
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
		// try{
		// Authentication request = new UsernamePasswordAuthenticationToken(
		// uid, upwd);
		// Authentication result = authenticationManager.authenticate(request);
		// SecurityContextHolder.getContext().setAuthentication(result);
		// } catch(AuthenticationException e) {
		// System.out.println("Authentication failed: " + e.getMessage());
		// }
		return null;
	}
	
	
	private UserAuth getUserAuthByUID(List<UserAuth> userAuths,String uid	){
		for(UserAuth userAuth : userAuths){
			if(uid.equalsIgnoreCase(userAuth.getUserId())){
				return userAuth	;
			}
		}
		return null;
	}

	//记录当前用户的会话信息
	private boolean rememberUser(String userId) {
		if (!StringUtils.isBlank(this.isRemember) && "1".equals(this.isRemember)) {
			int dateNumber = NumberUtils.stringToInt(this.rememberDate, 30);
			if (dateNumber < 1)
				dateNumber = 1;
			Cookie cookie = new Cookie("USERREMEMBERCOOKIENAME", userId);
			cookie.setMaxAge(60 * 60 * 24 * dateNumber);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return true;
	}
	/**
	 * @return the isRemember
	 */
	public String getIsRemember() {
		return isRemember;
	}

	/**
	 * @param isRemember
	 *            the isRemember to set
	 */
	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}

	/**
	 * @return the rememberDate
	 */
	public String getRememberDate() {
		return rememberDate;
	}

	/**
	 * @param rememberDate
	 *            the rememberDate to set
	 */
	public void setRememberDate(String rememberDate) {
		this.rememberDate = rememberDate;
	}

}
