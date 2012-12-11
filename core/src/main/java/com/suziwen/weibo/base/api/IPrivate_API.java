/**
 * 
 */
package com.suziwen.weibo.base.api;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoMessageBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 私信相关API
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午06:35:01
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IPrivate_API {
	/**
	 * 发一条私信
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WeiBoMessageBean add(OAuth oauth,  String content,String name)throws WeiBoException;

	/**
	 * 删除一条私信
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoMessageBean del(OAuth oauth,  String id)throws WeiBoException;

}
