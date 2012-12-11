package com.suziwen.weibo.base.sinaapi;

import com.suziwen.weibo.base.api.IPrivate_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoMessageBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description: 私信相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:24:37
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class Private_API extends SinaCheckExceptionRequestAPI implements IPrivate_API {
	/**
	 * 发一条私信
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param jing
	 * @param wei
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WeiBoMessageBean add(OAuth oauth, String content,  String name)throws WeiBoException {
		throw new WeiBoException("新浪不支持私信操作");

	}

	/**
	 * 删除一条私信
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoMessageBean del(OAuth oauth, String id)throws WeiBoException {
		throw new WeiBoException("新浪不支持私信操作");
	}
}
