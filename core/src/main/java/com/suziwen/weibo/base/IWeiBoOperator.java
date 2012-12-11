/**
 * 
 */
package com.suziwen.weibo.base;


import com.suziwen.Exception.BaseException;
import com.suziwen.persistent.model.UserAuthOrganization;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.bean.WeiBoMessageBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 微博统一操作接口
 * </p>
 * <p>
 * Create Time: 2012-3-31 上午11:17:41
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IWeiBoOperator {

	/**
	 * 添加一条微博
	 * 
	 * @param userAuthOrganization
	 * @param content
	 * @return
	 * @throws BaseException
	 */
	public WeiBoBean add(UserAuthOrganization userAuthOrganization, String content) throws BaseException;

	/**
	 * 显示一条微博
	 * 
	 * @param userAuthOrganization
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public WeiBoBean show(UserAuthOrganization userAuthOrganization, String id) throws BaseException;

	/**
	 * 删除一条微博数据
	 * 
	 * @param userAuthOrganization
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public WeiBoBean del(UserAuthOrganization userAuthOrganization, String id) throws BaseException;

	/**
	 * 转播一条微博
	 * 
	 * @param userAuthOrganization
	 * @param content
	 * @param reid
	 * @return
	 * @throws BaseException
	 */
	public WeiBoBean re_add(UserAuthOrganization userAuthOrganization, String content, String reid) throws BaseException;

	/**
	 * 点评一条微博
	 * 
	 * @param userAuthOrganization
	 * @param content
	 * @param reid
	 * @return
	 * @throws BaseException
	 */
	public WeiBoBean comment(UserAuthOrganization userAuthOrganization, String content, String reid) throws BaseException;

	/**
	 * 搜索广播
	 * 
	 * @param userAuthOrganization
	 * @param keyword
	 * @param pagesize
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	public WeiBoListBean t(UserAuthOrganization userAuthOrganization, String keyword, String pagesize, String page, String lastid) throws BaseException;

	/**
	 * 主页时间线
	 * 
	 * @param userAuthOrganization
	 * @param pageflag
	 * @param reqnum
	 * @return
	 * @throws BaseException
	 */
	public WeiBoListBean home_timeline(UserAuthOrganization userAuthOrganization, String pageflag, String reqnum, String lastid) throws BaseException;

	/**
	 * 广播大厅时间线
	 * 
	 * @param userAuthOrganization
	 * @param pos
	 * @param reqnum
	 * @return
	 * @throws BaseException
	 */
	public WeiBoListBean public_timeline(UserAuthOrganization userAuthOrganization, String pos, String reqnum) throws BaseException;

	/**
	 * 其他用户发表时间线
	 * 
	 * @param userAuthOrganization
	 * @param pageflag
	 * @param reqnum
	 * @param name
	 * @return
	 * @throws BaseException
	 */
	public WeiBoListBean user_timeline(UserAuthOrganization userAuthOrganization, String pageflag, String reqnum, String name, String lastid) throws BaseException;

	/**
	 * 用户提及时间线
	 * 
	 * @param userAuthOrganization
	 * @param pageflag
	 * @param reqnum
	 * @param lastid
	 * @return
	 * @throws BaseException
	 */
	public WeiBoListBean mentions_timeline(UserAuthOrganization userAuthOrganization, String pageflag, String reqnum, String lastid) throws BaseException;

	/**
	 * 获取自己的资料
	 * 
	 * @param userAuthOrganization
	 * @return
	 * @throws BaseException
	 */
	public WeiBoAccount info(UserAuthOrganization userAuthOrganization) throws BaseException;

	/**
	 * 发送一条数据,首先发送私信,如果失败就发送评论,再如果失败就发送一条at微博
	 * 
	 * @param userAuthOrganization
	 * @param atUser
	 *            用户名称或昵称
	 * @param userId
	 *            用户唯一ID
	 * @param text
	 * @param orignId原微博ID
	 *            ,评论需要使用到
	 * @return
	 * @throws BaseException
	 */
	public boolean sendText(UserAuthOrganization userAuthOrganization, String atUser, String userId, String text, String orignId) throws BaseException;

	/**
	 * 发一条私信
	 * 
	 * @param userAuthOrganization
	 * @param format
	 * @param content
	 * @param clientip
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WeiBoMessageBean addMessage(UserAuthOrganization userAuthOrganization, String content, String name) throws WeiBoException;

}
