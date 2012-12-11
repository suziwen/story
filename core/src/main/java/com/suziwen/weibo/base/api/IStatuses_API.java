/**
 * 
 */
package com.suziwen.weibo.base.api;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 时间线相关操作API
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午06:34:31
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IStatuses_API {

	/**
	 * 主页时间线
	 * 
	 * @param oauth
	 * @param pageflag  分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param reqnum
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean home_timeline(OAuth oauth,  String pageflag, String reqnum, String lastid)throws WeiBoException;

	/**
	 * 广播大厅时间线
	 * 
	 * @param oauth
	 * @param pos
	 * @param reqnum
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean public_timeline(OAuth oauth,  String pos, String reqnum, String lastid)throws WeiBoException;

	/**
	 * 其他用户发表时间线
	 * 
	 * @param oauth
	 * @param pageflag  分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param reqnum
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean user_timeline(OAuth oauth,  String pageflag, String reqnum, String lastid, String name)throws WeiBoException;

	/**
	 * 用户提及时间线
	 * 
	 * @param oauth
	 * @param pageflag  分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param reqnum
	 * @param lastid
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean mentions_timeline(OAuth oauth,  String pageflag, String reqnum, String lastid)throws WeiBoException;

}
