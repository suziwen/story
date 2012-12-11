/**
 * 
 */
package com.suziwen.weibo.base;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

import com.suziwen.weibo.base.api.IPrivate_API;
import com.suziwen.weibo.base.api.ISearch_API;
import com.suziwen.weibo.base.api.IStatuses_API;
import com.suziwen.weibo.base.api.IT_API;
import com.suziwen.weibo.base.api.IUser_API;
import com.suziwen.weibo.base.sohuapi.Private_API;
import com.suziwen.weibo.base.sohuapi.Search_API;
import com.suziwen.weibo.base.sohuapi.Statuses_API;
import com.suziwen.weibo.base.sohuapi.T_API;
import com.suziwen.weibo.base.sohuapi.User_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoAccount;


/********************************************************
 * <p>
 * Description:
 * </p>
 * Description: 搜狐微博请求操作，包括获取 OAUTH和根据USERAUTH获取OAUTH等操作
 * 及微博相关接口获取操作
 * <p>
 * Create Time: 2012-3-27 下午05:26:46
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class SohuWeiBoRequest extends AbstractWeiBoRequest implements WeiBoRequest {
	
//	private static final String CONSUMER_KEY="DFgB9lBPgaiR1qmXkF5v";
//	
//	private static final String CONSUMER_SECRET ="ejmzNJS)^%paIyG!x%hbl(#X^e27rXcw1H5rgvZ8";
	
	private static final String REQUEST_TOKEN_URL = "http://api.t.sohu.com/oauth/request_token";
	
	private static final String OAUTH_TOKEN_URL="http://api.t.sohu.com/oauth/authorize";
	private static final String ACCESS_TOKEN_URL="http://api.t.sohu.com/oauth/access_token";
	
	private String consumerKey ;
	private String consumerSecret;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.suziwen.weibo.base.WeiBoRequest#getOAuth()
	 */
	@Override
	public OAuth getOAuth() {
		Assert.notNull(consumerKey);
		Assert.notNull(consumerSecret);
		OAuth o = new OAuth();
		o.setOauth_consumer_key(consumerKey);
		o.setOauth_consumer_secret(consumerSecret);
		o.setRequestTokenUrl(REQUEST_TOKEN_URL);
		o.setOauthTokenUrl(OAUTH_TOKEN_URL);
		o.setAccessTokenUrl(ACCESS_TOKEN_URL);
		return o;
	}

	@Override
	public boolean isSupport(String type) {
		return SITE_SOHU.equalsIgnoreCase(type);
	}

	@Override
	public WeiBoAccount getWeiBoAccount(OAuth oauth)  throws Exception{
		WeiBoAccount account = new WeiBoAccount();
		String myInfo = this.getUserAPI().info(oauth, "xml");

		SAXReader saxReader = new SAXReader();
		Document xml = saxReader.read(new ByteArrayInputStream(myInfo.getBytes("UTF-8")));

		String id = xml.selectSingleNode("user/id")!=null?xml.selectSingleNode("user/id").getText():"";
		//String name = xml.selectSingleNode("user/name")!=null?xml.selectSingleNode("user/name").getText():"";
		String name = xml.selectSingleNode("user/screen_name")!=null?xml.selectSingleNode("user/screen_name").getText():"";
		String nick = xml.selectSingleNode("user/screen_name")!=null?xml.selectSingleNode("user/screen_name").getText():"";
		String head =xml.selectSingleNode("user/profile_image_url")!=null? xml.selectSingleNode("user/profile_image_url").getText() : "";
		String isvip =xml.selectSingleNode("user/profile_image_url")!=null? xml.selectSingleNode("user/verified").getText():"";
		name = StringUtils.isBlank(name)? id : name;
		nick = StringUtils.isBlank(nick)? name : nick;
		account.setId(id);
		account.setName(name);
		account.setNick(nick);
		account.setHead(head);
		account.setIsvip(isvip);

		return account;
	}

	@Override
	public IPrivate_API getPrivateAPI() {
		Private_API a = new Private_API();
		return a;
	}

	@Override
	public ISearch_API getSearchAPI() {
		Search_API a = new Search_API();
		return a;
	}

	@Override
	public IStatuses_API getStatusesAPI() {
		Statuses_API a = new Statuses_API();
		return a;
	}

	@Override
	public IT_API getTAPI() {
		T_API a = new T_API();
		return a;
	}

	@Override
	public IUser_API getUserAPI() {
		User_API a = new User_API();
		return a;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

}
