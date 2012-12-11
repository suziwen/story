/**
 * 
 */
package com.suziwen.weibo.base;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.bean.WeiBoMessageBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>Description: </p>
 * <p>Create Time: 2012-3-31 上午11:27:43</p>
 * <p>Company: Copyright Bank</p>
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoOperatorImpl implements IWeiBoOperator {
	
	private static final Logger logger = Logger.getLogger(WeiBoOperatorImpl.class);
	/**
	 * 第三方微博操作管理类
	 */
	private IWeiBoRequestManager weiBoRequestManager;
	
	private  WeiBoRequest getWeiBoRequest(UserAuthOrganization userAuthOrganization){
		if(userAuthOrganization == null ||StringUtils.isBlank(userAuthOrganization.getSiteId())){
			throw new BaseException("not bind exception");
		}
		WeiBoRequest weiboRequest = this.getWeiBoRequestManager().getWeiBoRequest(userAuthOrganization.getSiteId());
		return weiboRequest;
	}
	
	private OAuth getOAuth(UserAuthOrganization userAuthOrganization){
		OAuth oauth = getWeiBoRequest(userAuthOrganization).getOAuth(userAuthOrganization);
		return oauth;
	}
	

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#add(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String)
	 */
	@Override
	public WeiBoBean add(UserAuthOrganization userAuthOrganization, String content) throws BaseException {
			WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
			OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
			return weiboRequest.getTAPI().add(oauth, content);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#show(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String)
	 */
	@Override
	public WeiBoBean show(UserAuthOrganization userAuthOrganization, String id) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getTAPI().show(oauth, id);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#del(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String)
	 */
	@Override
	public WeiBoBean del(UserAuthOrganization userAuthOrganization, String id) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getTAPI().del(oauth, id);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#re_add(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoBean re_add(UserAuthOrganization userAuthOrganization, String content, String reid) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getTAPI().re_add(oauth, content, reid);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#comment(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoBean comment(UserAuthOrganization userAuthOrganization, String content, String reid) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getTAPI().comment(oauth, content, reid);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#t(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoListBean t(UserAuthOrganization userAuthOrganization, String keyword, String pagesize, String page,String lastid) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getSearchAPI().t(oauth, keyword, pagesize, page,lastid);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#home_timeline(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoListBean home_timeline(UserAuthOrganization userAuthOrganization, String pageflag,String lastid, String reqnum) throws BaseException {
		if("0".equals(pageflag)){
			lastid = "0";
		}
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getStatusesAPI().home_timeline(oauth, pageflag, reqnum,lastid);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#public_timeline(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoListBean public_timeline(UserAuthOrganization userAuthOrganization, String pos, String reqnum) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getStatusesAPI().public_timeline(oauth, pos, reqnum,"");
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#user_timeline(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoListBean user_timeline(UserAuthOrganization userAuthOrganization, String pageflag, String reqnum,String lastid, String name) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getStatusesAPI().user_timeline(oauth, pageflag, reqnum,lastid, name);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#mentions_timeline(com.suziwen.persistent.model.UserAuthOrganization, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public WeiBoListBean mentions_timeline(UserAuthOrganization userAuthOrganization, String pageflag, String reqnum, String lastid) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getStatusesAPI().mentions_timeline(oauth, pageflag, reqnum, lastid);
	}

	/* (non-Javadoc)
	 * @see com.suziwen.weibo.base.IWeiBoOperator#info(com.suziwen.persistent.model.UserAuthOrganization)
	 */
	@Override
	public WeiBoAccount info(UserAuthOrganization userAuthOrganization) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return null;
	}


	public IWeiBoRequestManager getWeiBoRequestManager() {
		return weiBoRequestManager;
	}


	public void setWeiBoRequestManager(IWeiBoRequestManager weiBoRequestManager) {
		this.weiBoRequestManager = weiBoRequestManager;
	}



	@Override
	public boolean sendText(UserAuthOrganization userAuthOrganization, String atUser,String userId, String text, String orignId) throws BaseException {
		try {
			this.addMessage(userAuthOrganization, text, userId);
		} catch (WeiBoException e) {
			logger.warn("发送私信失败,将采用发送评论");
			try {
				this.comment(userAuthOrganization, text, orignId);
			} catch (WeiBoException e1) {
				logger.warn("发送评论失败,将采用AT微博");
				String content = "@"+atUser + "  " + text;
				try {
					this.add(userAuthOrganization, content);
				} catch (WeiBoException e2) {
					logger.warn("发送微博失败");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public WeiBoMessageBean addMessage(UserAuthOrganization userAuthOrganization, String content, String name) throws BaseException {
		WeiBoRequest weiboRequest =getWeiBoRequest(userAuthOrganization);
		OAuth oauth = weiboRequest.getOAuth(userAuthOrganization);
		return weiboRequest.getPrivateAPI().add(oauth, content, name);
	}

}
