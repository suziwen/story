/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.appfuse.model.User;
import org.springframework.beans.BeanUtils;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuth;
import com.suziwen.stories.webapp.common.AccountTypeConst;
import com.suziwen.stories.webapp.common.OperatorResult;
import com.suziwen.stories.webapp.util.UserDetailsUtil;
import com.suziwen.weibo.base.CallTypeConst;
import com.suziwen.weibo.base.OAuthStatusConst;
import com.suziwen.weibo.base.WeiBoRequest;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoAccount;

/********************************************************
 * <p>
 * 用户与微博绑定操作 Description:callback格式说明
 * 0表示获取requestTOken成功下一步需要用户通过href去获取authotoken 1表示用户未登录且用户与绑定微博存在
 * 2.表示用户未登录且用户与绑定微博存在多对一的关系，需要用户选择PCB帐号登录
 * 3.表示用户未登录且用户与绑定微博存在，但系统内已经找不到该PCB帐户（即存在垃圾数据） 4.表示用户未登录且用户与绑定微博不存在，需要用户先进行绑定操作
 * 5. 表示用户已登录且用户与绑定微博存在，绑定的微博是当前用户所拥有的，则只进行token的更新6.表示用户已登录但用户与微博未进行绑定，
 * 系统自动进行绑定操作
 * 7表示用户已经登录且用户与绑定微博存在，但绑定的微博并非当前登录的用户，（需要确认是否将原有的绑定删除，重新绑定到新的ecb用户身上,
 * 目前是直接删除现在的绑定重新绑定到新用户上）
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
public class DefaultBindAction extends BaseBindAction {

	private static final Logger logger = Logger.getLogger(DefaultBindAction.class);
	/**
	 * 已经注册认证通过的ACCESSTOKEN但未被绑定到系统pcb,ecb
	 */
	public static final String REGISTER_USERAUTHS = "register_userauths";
	/**
	 * 当前登录身份
	 */
	public static final String CURRENT_USERAUTH = "current_userauth";
	/**
	 * 将要以某个第三方账号身份登录,(存放列表数据,当只有一条数据时,自动以该身份登录,否则让用户选择身份登录)
	 */
	public static final String WILL_AUTOLOGIN_USERAUTH = "will_autologin_userauth";
	/**
	 * pcb,ecb里的系统用户id，多个用户uid以逗号隔开
	 */
	public static final String CURRENT_UID = "current_uid";
	/**
	 * 绑定类型,qq,sina,sohu
	 */
	private String bindType;

	private OperatorResult returnResult;

	public OperatorResult getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(OperatorResult returnResult) {
		this.returnResult = returnResult;
	}

	public String execute() throws Exception {

		CallBack callback = bindSuccess();
		returnResult.setDataList(callback);
		returnResult.setValue("1");
		return "success";
	}

	public CallBack bindSuccess() throws Exception {

		HttpSession session = request.getSession();
		OAuth oauth = (OAuth) session.getAttribute(DefaultAuthBindAction.CURRENT_OAUTH);
		// session.removeAttribute(DefaultAuthBindAction.CURRENT_OAUTH);
		if (oauth == null || oauth.getStatus() == OAuthStatusConst.ACCESSFAILURE) {
			throw new BaseException("获取accesstoken失败");
		} else {
			WeiBoRequest weiBoRequest = this.getWeiBoRequestManager().getWeiBoRequest(this.bindType);
			// 成功获得ACCESSTOKEN，开始系统的自动绑定操作,将微博号与系统用户绑定（主要存储access_token，refresh_token）
			WeiBoAccount qqaccount = weiBoRequest.getWeiBoAccount(oauth);
			User user = UserDetailsUtil.getCurrUser();

			if ( user == null) {
				// 直接跳转到登录页面
				String name = qqaccount.getId();
				List<UserAuth> userAuths = this.getUserAuth().getListByAuthSiteAndBindUid(this.bindType, name);
				if (CollectionUtils.isNotEmpty(userAuths)) {
					if (userAuths.size() == 1) {
						// 直接登录
						String uid = "";
						User appuser = this.userManager.get(Long.parseLong(userAuths.get(0).getUserId()));
						if (appuser != null) {
							// 更新token
							UserAuth userAuth = userAuths.get(0);
							userAuth.setBindAccessToken(oauth.getOauth_token());
							userAuth.setBindAccessSecret(oauth.getOauth_token_secret());
							this.getUserAuth().modify(userAuth);
							uid = String.valueOf(appuser.getId());
							bindCurrentSession(oauth, qqaccount, uid, userAuths);
							// return
							CallBack callback = new CallBack();
							callback.setHref("自动登录,存在用户");
							callback.setType("1");
							Map<String, String> contextPara = new HashMap<String, String>();
							contextPara.put("uid", uid);
							callback.setContext(contextPara);
							return callback;
							// response.sendRedirect("./sns/autologin.html?uid="+uid);
						} else {
							// 不存在该用户,因此直接跳出让其登录绑定
							System.out.println("非登录用户且存在绑定用户但系统里已经找不到系统用户，则跳转到登录绑定页面");
							// 删除这条垃圾数据绑定
							this.getUserAuth().remove(userAuths.get(0).getId());
							bindRegisterSession(oauth, qqaccount, this.bindType);
							CallBack callback = new CallBack();
							callback.setHref("非登录用户且存在绑定用户但系统里已经找不到系统用户，则跳转到登录绑定页面");
							callback.setType("3");
							return callback;
						}

					} else {
						// TODO 跳转页面让用户选择身份登录
						System.out.println("非登录用户但存在多个绑定用户，则跳转到选择身份登录登录页面，或重新绑定一个用户登录页面");
						List<String> uid = new ArrayList<String>();
						for (UserAuth userAuth : userAuths) {
							uid.add(userAuth.getUserId());
						}
						// StringUtils.join(uid, ",");
						// String uid = "";
						// response.sendRedirect("./sns/autologin.html?uid="+StringUtils.join(uid,
						// ","));
						bindCurrentSession(oauth, qqaccount, StringUtils.join(uid, ","), userAuths);
						CallBack callback = new CallBack();
						callback.setHref("非登录用户但存在多个绑定用户，则跳转到选择身份登录登录页面，或重新绑定一个用户登录页面");
						callback.setType("2");
						return callback;
					}
				} else {
					// TODO 跳转到登录绑定页面
					bindRegisterSession(oauth, qqaccount, this.bindType);
					CallBack callback = new CallBack();
					callback.setHref("非登录用户且不存在绑定用户，直接跳转到登录绑定页面");
					// 需要转到登录绑定页面
					callback.setType(CallTypeConst.UNLOGIN_USERNEEDBINDAUTHSITE);
					return callback;
				}
			} else {
				// 用户已经登录，则进行绑定操作,并跳转到指定页面，如果没有指定，则跳转到首页
				List<UserAuth> bindUserAuths = this.getUserAuth().getListByAuthSiteAndBindUid(this.bindType, qqaccount.getId());
				if (CollectionUtils.isNotEmpty(bindUserAuths)) {
					// 已经绑定通过，则直接进行页面间的跳转
					// response.sendRedirect("./pcb/index.html");
					if (bindUserAuths.size() > 1) {
						// TODO 这里假设一个微博只能被一个用户绑定
					}

					UserAuth tempUserAuth = bindUserAuths.get(0);
					CallBack callback = new CallBack();
					if (user.getId().equals(tempUserAuth.getUserId())) {
						tempUserAuth.setBindAccessSecret(oauth.getOauth_token_secret());
						tempUserAuth.setBindAccessToken(oauth.getOauth_token());
						tempUserAuth.setBindLoginName(qqaccount.getName());
						this.getUserAuth().modify(tempUserAuth);
						callback.setHref("已经存在绑定，且绑定的用户与当前用户一致，则进行token的更新");
						callback.setType("5");
					} else {
						tempUserAuth.setBindAccessSecret(oauth.getOauth_token_secret());
						tempUserAuth.setBindAccessToken(oauth.getOauth_token());
						tempUserAuth.setBindLoginName(qqaccount.getName());
						tempUserAuth.setBindDate(new Date());
						tempUserAuth.setUserId(String.valueOf(user.getId()));
						this.getUserAuth().modify(tempUserAuth);
						callback.setHref("已经存在绑定，但绑定的用户与当前用户不一致,则删除原有的绑定，添加现在新的绑定（即更换userId）");
						callback.setType("7");
					}
					UserAuth sessionUserAuth = new UserAuth();
					BeanUtils.copyProperties(tempUserAuth, sessionUserAuth);
					session.setAttribute(DefaultBindAction.CURRENT_USERAUTH, sessionUserAuth);
					return callback;
				} else {
					// 进行绑定操作
					// 该绑定操作会读取出所有在session里的绑定内容
					UserAuth userAuth = new UserAuth();
					userAuth.setUserId(String.valueOf(user.getId()));
					userAuth.setBindDate(new Date());
					userAuth.setBindLoginName(qqaccount.getNick());
					userAuth.setBindUid(qqaccount.getId());
					userAuth.setSiteId(this.bindType);
					userAuth.setBindAccessToken(oauth.getOauth_token());
					userAuth.setBindAccessSecret(oauth.getOauth_token_secret());
					this.getUserAuth().add(userAuth);
					UserAuth sessionUserAuth = new UserAuth();
					BeanUtils.copyProperties(userAuth, sessionUserAuth);
					session.setAttribute(DefaultBindAction.CURRENT_USERAUTH, sessionUserAuth);
					CallBack callback = new CallBack();
					callback.setHref("绑定成功");
					callback.setType("6");
					return callback;
				}
				// 返回绑定成功后的操作
			}
		}
	}

	/**
	 * 绑定自动登录session操作，用以表示当前登录微博身份
	 * 
	 * @param oauth
	 * @param qqaccount
	 */
	private void bindCurrentSession(OAuth oauth, WeiBoAccount qqaccount, String uid, List<UserAuth> userAuths) {
		HttpSession session = request.getSession();
		session.setAttribute(WILL_AUTOLOGIN_USERAUTH, userAuths);
		session.setAttribute(CURRENT_UID, uid);
	}

	/**
	 * 绑定需要注册的注册微博信息，在进行注册操作时需要进行删除操作 最后一个为当前最近获得认证的一个accesstoken
	 */
	private void bindRegisterSession(OAuth oauth, WeiBoAccount qqaccount, String bindType) {
		HttpSession session = request.getSession();
		List<UserAuth> userAuths = (List<UserAuth>) (session.getAttribute(REGISTER_USERAUTHS));
		if (userAuths == null) {
			userAuths = new ArrayList<UserAuth>();
		}
		UserAuth userAuth = new UserAuth();
		userAuth.setBindAccessToken(oauth.getOauth_token());
		userAuth.setBindAccessSecret(oauth.getOauth_token_secret());
		userAuth.setBindDate(new Date());
		userAuth.setBindLoginName(qqaccount.getName());
		userAuth.setBindUid(qqaccount.getId());
		userAuth.setSiteId(bindType);
		UserAuth tempUserAuth = checkHasUserAuth(userAuths, userAuth);
		if (tempUserAuth != null) {
			// 不需要重新注册
			session.setAttribute(CURRENT_USERAUTH, tempUserAuth);
		} else {
			userAuths.add(userAuth);
			session.setAttribute(REGISTER_USERAUTHS, userAuths);
			session.setAttribute(CURRENT_USERAUTH, userAuth);
		}

	}

	private UserAuth checkHasUserAuth(List<UserAuth> userAuths, UserAuth userAuth) {
		for (UserAuth tempuserAuth : userAuths) {
			if (tempuserAuth.getSiteId().equalsIgnoreCase(userAuth.getSiteId()) && tempuserAuth.getBindUid().equalsIgnoreCase(userAuth.getBindUid())) {
				return tempuserAuth;
			}
		}
		return null;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

}
