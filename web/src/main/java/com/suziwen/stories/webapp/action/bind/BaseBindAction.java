/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import org.springframework.security.authentication.AuthenticationManager;

import com.suziwen.stories.webapp.action.BaseAction;
import com.suziwen.weibo.base.IUserAuth;
import com.suziwen.weibo.base.IWeiBoRequestManager;

/********************************************************
 * <p>
 * Description: 
 * </p>
 * <p>
 * Create Time: 2012-3-16 下午04:22:22
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class BaseBindAction extends BaseAction {
	
	protected AuthenticationManager authenticationManager;

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 用户与第三方微博关系
	 */
	private IUserAuth userAuth;

	/**
	 * 微博请求处理类
	 */
	private IWeiBoRequestManager weiBoRequestManager;

	public IUserAuth getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(IUserAuth userAuth) {
		this.userAuth = userAuth;
	}

	public IWeiBoRequestManager getWeiBoRequestManager() {
		return weiBoRequestManager;
	}

	public void setWeiBoRequestManager(IWeiBoRequestManager weiBoRequestManager) {
		this.weiBoRequestManager = weiBoRequestManager;
	}

}
