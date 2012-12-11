package com.suziwen.weibo.base.api;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:搜索相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午03:53:00
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public interface ISearch_API {


	/**
	 * 搜索广播
	 * 
	 * @param oauth
	 * @param keyword
	 * @param pagesize
	 *            每页大小
	 * @param page
	 *            
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean t(OAuth oauth, String keyword, String pagesize, String pageflag,String lastid)throws WeiBoException;

}
