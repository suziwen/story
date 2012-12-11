/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.appfuse.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;
import com.suziwen.stories.webapp.common.AccountTypeConst;

/********************************************************
 * <p>
 * Description:绑定登录
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
public class BindLoginAction extends BaseBindAction {
	
	String isRemember;
	String rememberDate;
	
	public String cblinkexecute() throws Exception {
		HttpSession session = request.getSession();
		
		List<UserAuth > userAuths = (List<UserAuth>) session.getAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		session.removeAttribute(DefaultBindAction.REGISTER_USERAUTHS);
		if(CollectionUtils.isEmpty(userAuths)){
			throw new BaseException("绑定失败，你太长时间没进行操作，请重新绑定");
		}

		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String loginName = request.getParameter("loginName");
		User u = this.userManager.getUserByUsername(loginName);

		//this.getUser().logout(session);
		SecurityContextHolder.clearContext();
		if(!CollectionUtils.isEmpty(userAuths)){
			for(UserAuth userAuth : userAuths){
				userAuth.setUserId(String.valueOf(u.getId()));
				this.getUserAuth().add(userAuth);
			}
			UserAuth currentUserAuth = userAuths.get(userAuths.size()-1);
			UserAuth sessionUserAuth = new UserAuth();
			BeanUtils.copyProperties(currentUserAuth, sessionUserAuth);
			session.setAttribute(DefaultBindAction.CURRENT_USERAUTH, sessionUserAuth);
		}

		// try{
		// Authentication request = new UsernamePasswordAuthenticationToken(
		// uid, upwd);
		// Authentication result = authenticationManager.authenticate(request);
		// SecurityContextHolder.getContext().setAuthentication(result);
		// } catch(AuthenticationException e) {
		// System.out.println("Authentication failed: " + e.getMessage());
		// }
		//RequestDispatcher rd = request.getRequestDispatcher("/j_spring_security_check?j_username=" + uid + "&j_password=" + upwd);
		//rd.forward(request, response);
		return null;
	}


}
