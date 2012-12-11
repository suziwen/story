package com.suziwen.weibo.base.sinaapi;

import com.suziwen.weibo.base.api.ISearch_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:搜索相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:24:22
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class Search_API extends SinaCheckExceptionRequestAPI implements ISearch_API {
	/**
	 * 搜索广播
	 * 新浪官方 API不提供搜索功能
	 * 
	 * @param oauth
	 * @param format
	 * @param keyword
	 * @param pagesize
	 *            每页大小
	 * @param page
	 *            页码
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean t(OAuth oauth,  String keyword, String pagesize, String page,String lastid)throws WeiBoException {
		WeiBoListBean list = new WeiBoListBean();
		return list;
	}

}
