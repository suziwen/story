package com.suziwen.weibo.base.sohuapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.suziwen.weibo.base.api.IUser_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:帐户相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:22:57
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class User_API extends SohuCheckExceptionRequestAPI implements IUser_API {

	/**
	 * 获取自己的资料
	 * 
	 * @param oauth
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public String info(OAuth oauth, String format)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		format = StringUtils.isBlank(format) ? "xml" : format;
		return getResource("http://api.t.sohu.com/users/show." + format, parameters, oauth);
	}

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
			throws Exception {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("nick", nick));
		parameters.add(new QParameter("sex", sex));
		parameters.add(new QParameter("year", year));
		parameters.add(new QParameter("month", month));
		parameters.add(new QParameter("day", day));
		parameters.add(new QParameter("countrycode", countrycode));
		parameters.add(new QParameter("provincecode", provincecode));
		parameters.add(new QParameter("citycode", citycode));
		parameters.add(new QParameter("introduction", introduction));
		return postContent("http://open.t.qq.com/api/user/update", parameters, oauth);
	}

	/**
	 * 修改/更新用户本人头像
	 * 
	 * @param oauth
	 * @param format
	 * @param picpath
	 * @return
	 * @throws Exception
	 */
	public String update_head(OAuth oauth, String format, String picpath)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));

		List<QParameter> pic = new ArrayList<QParameter>();
		pic.add(new QParameter("pic", picpath));
		return postFile("http://open.t.qq.com/api/user/update_head", parameters, pic, oauth);
	}

	/**
	 * 获取其他用户个人资料
	 * 
	 * @param oauth
	 * @param format
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String other_info(OAuth oauth, String format, String name)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("name", name));
		return getResource("http://open.t.qq.com/api/user/other_info", parameters, oauth);
	}
}
