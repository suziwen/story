/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import com.suziwen.Exception.BaseException;
import com.suziwen.stories.webapp.common.OperatorResult;
import com.suziwen.weibo.base.OAuthStatusConst;
import com.suziwen.weibo.base.WeiBoRequest;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.utils.OAuthClient;
import com.suziwen.weibo.utils.QStr;

/********************************************************
 * <p>
 * 用户与微博认证操作
 * Description:callback格式说明 0表示获取requestTOken成功下一步需要用户通过href去获取authotoken
 * </p>
 * <p>
 * Create Time: 2012-3-15 下午05:09:39
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class DefaultAuthBindAction extends BaseBindAction {

	private static final Logger logger = Logger.getLogger(DefaultAuthBindAction.class);

	/**
	 * 登录成功后的跳转地址
	 */
	public static final String BACK_URL = "back_url";

	/**
	 * 用户request_token
	 */
	public static final String CURRENT_TOKEN_AUTH = "current_token_auth";
	/**
	 * 用户request_token_secret
	 */
	public static final String CURRENT_TOKEN_SECRET = "current_token_secret";
	
	/**
	 * 认证成功后包含accesstoken和secrettoken的oauth
	 */
	public static final String CURRENT_OAUTH = "current_oauth";
	
	public static final String SENDREDIRECT_URL = "sendredirect_url";

	/**
	 * 第三方微博authtoke认证通过后的回调地址
	 */
	private String callBack;
	/**
	 * 认证成功后重定向的页面地址
	 */
	private String sendRedirectUrl;
	/**
	 * 是否第三方返回的页面
	 */
	private String isCallBack;
	/**
	 * 确认码
	 */
	private String oauth_verifier;
	/**
	 * 绑定类型,qq,sina,sohu
	 */
	private String bindType;

	public String cblinkexecute() throws Exception {
		// this.request.getParameterMap()
		if (SysConst.TRUE.equals(isCallBack)) {
			CallBack callback = callback();
			OperatorResult<CallBack> result = new OperatorResult<CallBack>();
			result.setDataList(callback);
			result.setValue(SysConst.TRUE);
			return returnContent(result);
		} else {
			CallBack callback = bind();
			OperatorResult<CallBack> result = new OperatorResult<CallBack>();
			result.setDataList(callback);
			result.setValue(SysConst.TRUE);
			return returnContent(result);
		}
	}

	/**
	 * 发出auth_token请求
	 */
	private CallBack bind() throws Exception {

		WeiBoRequest weiBoRequest = this.getWeiBoRequestManager().getWeiBoRequest(this.bindType);
		OAuth oauth = weiBoRequest.getOAuth();

		oauth.setOauth_callback(this.callBack);
		OAuthClient auth = new OAuthClient();
		// 获取request token
		oauth = auth.requestToken(oauth);
		if (oauth.getStatus() != OAuthStatusConst.SUCCESS) {
			throw new BaseException("获取认证失败...");
		} else {
			String oauth_token = oauth.getOauth_token();
			// auth.a
			String url = oauth.getOauthTokenUrl() + "?oauth_token=" + oauth_token;
			if ("sohu".equalsIgnoreCase(this.bindType)) {
				url = url + "&oauth_callback=" + QStr.encode(this.callBack);
			}
			// sohu需要在这里进行回调地址的处理
			HttpSession session = request.getSession();
			session.setAttribute(CURRENT_TOKEN_SECRET, oauth.getOauth_token_secret());
			session.setAttribute(CURRENT_TOKEN_AUTH, oauth.getOauth_token());
			session.setAttribute(SENDREDIRECT_URL, this.sendRedirectUrl);
			CallBack callback = new CallBack();
			callback.setType("0");
			callback.setHref(url);
			return callback;
		}
	}

	/**
	 * 如果用户已经登录,则直接将当前用户绑定到该微博下,如果用户未登录,则跳转到用户登录绑定页面,如果用户已经登录,则直接绑定,
	 * 如果在系统里查询到已经有这个被绑定的用户,则直接以当前用户身份登录 通过auth_token后进行绑定操作
	 */
	private CallBack callback() throws Exception {

		WeiBoRequest weiBoRequest = this.getWeiBoRequestManager().getWeiBoRequest(this.bindType);
		OAuth oauth = weiBoRequest.getOAuth();
		HttpSession session = request.getSession();
		String token_auth = ObjectUtils.toString(session.getAttribute(CURRENT_TOKEN_AUTH));
		String token_secret = ObjectUtils.toString(session.getAttribute(CURRENT_TOKEN_SECRET));
		String sendRedirectUrl = ObjectUtils.toString(session.getAttribute(SENDREDIRECT_URL));
		oauth.setOauth_token(token_auth);
		oauth.setOauth_token_secret(token_secret);
		// oauth.setOauth_callback("http://localhost:8080/cblink/guest?method=guest.account.bind&callback="+SysConst.TRUE);
		OAuthClient auth = new OAuthClient();
		oauth.setOauth_verifier(this.oauth_verifier);
		// auth.requestToken(oauth);
		oauth = auth.accessToken(oauth);
		logger.info("微博端返回数据：：");
		//session.removeAttribute(CURRENT_TOKEN_AUTH);
		//session.removeAttribute(CURRENT_TOKEN_SECRET);
		session.setAttribute(CURRENT_OAUTH, oauth);
		response.sendRedirect(sendRedirectUrl);
		return null;
	}

	public String getOauth_verifier() {
		return oauth_verifier;
	}

	public void setOauth_verifier(String oauth_verifier) {
		this.oauth_verifier = oauth_verifier;
	}

	public String getIsCallBack() {
		return isCallBack;
	}

	public void setIsCallBack(String isCallBack) {
		this.isCallBack = isCallBack;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

	public String getSendRedirectUrl() {
		return sendRedirectUrl;
	}

	public void setSendRedirectUrl(String sendRedirectUrl) {
		this.sendRedirectUrl = sendRedirectUrl;
	}
}
