/**
 * 
 */
package com.suziwen.weibo.base.api;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 微博相关API
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午06:35:21
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IT_API {

	/**
	 * 获取一条微博数据
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean show(OAuth oauth,String id)throws WeiBoException;

	/**
	 * 发表一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean add(OAuth oauth,String content)throws WeiBoException;

	/**
	 * 删除一条微博数据
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean del(OAuth oauth,String id)throws WeiBoException;

	/**
	 * 转播一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param reid
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean re_add(OAuth oauth,String content, String reid)throws WeiBoException;

	/**
	 * 点评一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param reid
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean comment(OAuth oauth,String content, String reid)throws WeiBoException;
}
