/**
 * 
 */
package com.suziwen.weibo.base.api;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 用户信息相关操作API
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午06:33:32
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public interface IUser_API {

	/**
	 * 获取自己的资料
	 * 
	 * @param oauth
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public String info(OAuth oauth, String format)throws WeiBoException;

	/**
	 * 修改/更新用户本人信息
	 * 
	 * @param oauth
	 * @param format
	 * @param nick
	 * @param sex
	 * @param year
	 * @param month
	 * @param day
	 * @param countrycode
	 * @param provincecode
	 * @param citycode
	 * @param introduction
	 * @return
	 * @throws Exception
	 */
	public String update(OAuth oauth, String format, String nick, String sex, String year, String month, String day, String countrycode, String provincecode, String citycode, String introduction)
			throws Exception;

	/**
	 * 修改/更新用户本人头像
	 * 
	 * @param oauth
	 * @param format
	 * @param picpath
	 * @return
	 * @throws Exception
	 */
	public String update_head(OAuth oauth, String format, String picpath)throws WeiBoException;

	/**
	 * 获取其他用户个人资料
	 * 
	 * @param oauth
	 * @param format
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String other_info(OAuth oauth, String format, String name)throws WeiBoException;

}
