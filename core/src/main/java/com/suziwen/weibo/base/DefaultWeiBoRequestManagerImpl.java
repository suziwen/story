/**
 * 
 */
package com.suziwen.weibo.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/********************************************************
 * <p>
 * Description:
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午05:36:54
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class DefaultWeiBoRequestManagerImpl implements IWeiBoRequestManager {

	private List<WeiBoRequest> weiBoRequests = new ArrayList<WeiBoRequest>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.suziwen.weibo.base.WeiBoRequestFactory#getWeiBoRequest()
	 */
	@Override
	public WeiBoRequest getWeiBoRequest(String type) {
		for (WeiBoRequest weiBoRequest : weiBoRequests) {
			if (weiBoRequest.isSupport(type)) {
				return weiBoRequest;
			}
		}
		return null;
	}

	public List<WeiBoRequest> getWeiBoRequests() {
		return weiBoRequests;
	}

	public void setWeiBoRequests(List<WeiBoRequest> weiBoRequests) {
		this.weiBoRequests = weiBoRequests;
	}

}
