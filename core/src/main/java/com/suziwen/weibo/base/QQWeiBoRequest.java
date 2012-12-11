/**
 * 
 */
package com.suziwen.weibo.base;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

import com.suziwen.weibo.base.api.IPrivate_API;
import com.suziwen.weibo.base.api.ISearch_API;
import com.suziwen.weibo.base.api.IStatuses_API;
import com.suziwen.weibo.base.api.IT_API;
import com.suziwen.weibo.base.api.IUser_API;
import com.suziwen.weibo.base.qqapi.Private_API;
import com.suziwen.weibo.base.qqapi.Search_API;
import com.suziwen.weibo.base.qqapi.Statuses_API;
import com.suziwen.weibo.base.qqapi.T_API;
import com.suziwen.weibo.base.qqapi.User_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoAccount;


/********************************************************
 * <p>
 * Description:
 * </p>
 * Description: QQ微博请求操作，包括获取 OAUTH和根据USERAUTH获取OAUTH等操作
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
public class QQWeiBoRequest extends AbstractWeiBoRequest implements WeiBoRequest {
	
//	private static final String CONSUMER_KEY="801121744";
//	
//	private static final String CONSUMER_SECRET ="92a967e40a2d2119a8b339382aecfc31";
	
	private static final String REQUEST_TOKEN_URL = "https://open.t.qq.com/cgi-bin/request_token";
	
	private static final String OAUTH_TOKEN_URL="http://open.t.qq.com/cgi-bin/authorize";
	private static final String ACCESS_TOKEN_URL="https://open.t.qq.com/cgi-bin/access_token";

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
		o.setOauth_consumer_key(consumerKey); //应用id
		o.setOauth_consumer_secret(consumerSecret);//应用的key
		o.setRequestTokenUrl(REQUEST_TOKEN_URL);
		o.setOauthTokenUrl(OAUTH_TOKEN_URL);
		o.setAccessTokenUrl(ACCESS_TOKEN_URL);
		return o;
	}
	
	private String consumerKey ;
	private String consumerSecret;

	@Override
	public boolean isSupport(String type) {
		return SITE_QQ.equalsIgnoreCase(type);
	}

	@Override
	public WeiBoAccount getWeiBoAccount(OAuth oauth)  throws Exception{
		WeiBoAccount account = new WeiBoAccount();
		String myInfo = this.getUserAPI().info(oauth, "xml");

		SAXReader saxReader = new SAXReader();
		Document xml = saxReader.read(new ByteArrayInputStream(myInfo.getBytes("UTF-8")));

		int ret = Integer.parseInt(xml.selectSingleNode("root/ret").getText());
		if (ret != 0) {
			return account;
		}

		String id = xml.selectSingleNode("root/data/openid").getText();
		String name = xml.selectSingleNode("root/data/name").getText();
		String nick = xml.selectSingleNode("root/data/nick").getText();
		String head = xml.selectSingleNode("root/data/head").getText();
		String isvip = xml.selectSingleNode("root/data/isvip").getText();
		String sex = xml.selectSingleNode("root/data/sex").getText();
		String fansnum = xml.selectSingleNode("root/data/fansnum").getText();
		String idolnum = xml.selectSingleNode("root/data/idolnum").getText();
		String tweetnum = xml.selectSingleNode("root/data/tweetnum").getText();

		account.setId(id);
		account.setName(name);
		account.setNick(nick);
		account.setHead(head);
		account.setIsvip(isvip);
		account.setSex(sex);
		account.setFansnum(fansnum);
		account.setIdolnum(idolnum);
		account.setTweetnum(tweetnum);

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
