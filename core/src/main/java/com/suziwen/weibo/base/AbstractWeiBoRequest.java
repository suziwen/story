/**
 * 
 */
package com.suziwen.weibo.base;

import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.bean.OAuth;

/********************************************************
 * <p>Description: </p>
 * <p>Create Time: 2012-3-28 下午04:40:59</p>
 * <p>Company: Copyright Bank</p>
 * @author suziwen
 * @version 1.0
 ********************************************************/
public abstract class AbstractWeiBoRequest implements WeiBoRequest {
	
	public OAuth getOAuth(UserAuthOrganization userAuthOrganization) {
		
		OAuth oauth = this.getOAuth();
		oauth.setOauth_token(userAuthOrganization.getBindAccessToken());
		oauth.setOauth_token_secret(userAuthOrganization.getBindAccessSecret());
		
		return oauth;
	}

}
