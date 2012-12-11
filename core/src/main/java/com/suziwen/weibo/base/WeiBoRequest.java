/**
 * 
 */
package com.suziwen.weibo.base;

import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.base.api.IPrivate_API;
import com.suziwen.weibo.base.api.ISearch_API;
import com.suziwen.weibo.base.api.IStatuses_API;
import com.suziwen.weibo.base.api.IT_API;
import com.suziwen.weibo.base.api.IUser_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoAccount;

/********************************************************
 * <p>
 * Description: 微博请求接口
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午05:25:09
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface WeiBoRequest {
	
	public static final String SITE_SINA = "sina";
	public static final String SITE_SOHU = "sohu";
	public static final String SITE_QQ = "qq";
	
	/**
	 * 是否支持的微博请求
	 * @param type
	 * @return
	 */
	public boolean isSupport(String type);
	
	/**
	 * 获取对应的oauth实例，不包含accesstoken和accesstokensecurt
	 * @return
	 */
	public OAuth getOAuth();
	
	/**
	 * 根据userAuth取得已经拥有accesstoken,accesstokensecurt的oauth对像
	 * @param userAuthOrganization
	 * @return
	 */
	public OAuth getOAuth(UserAuthOrganization userAuthOrganization);
	
	/**
	 * 根据oauth取得对应的weiboaccount信息,oauth必须带有认证的accesstoken
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public WeiBoAccount getWeiBoAccount(OAuth oauth)  throws Exception;
	
	/**
	 * 取得私信息操作接口
	 * @return
	 */
	public IPrivate_API getPrivateAPI();
	
	/**
	 * 取得搜索操作接口
	 * @return
	 */
	public ISearch_API getSearchAPI();
	
	/**
	 * 取得时间线操作接口
	 * @return
	 */
	public IStatuses_API getStatusesAPI();
	
	/**
	 * 取得 微博操作接口
	 * @return
	 */
	public IT_API getTAPI();
	
	/**
	 * 取得用户操作接口
	 * @return
	 */
	public IUser_API getUserAPI();

}
