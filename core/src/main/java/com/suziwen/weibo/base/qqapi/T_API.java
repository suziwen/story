package com.suziwen.weibo.base.qqapi;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.BindTypeConst;
import com.suziwen.weibo.base.api.IT_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * \******************************************************
 * <p>
 * Description:微博相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:25:04
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class T_API extends QQCheckExceptionRequestAPI implements IT_API {

	private String format = "xml";

	/**
	 * 获取一条微博数据
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean show(OAuth oauth, String id)throws WeiBoException  {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("id", id));
		// return getResource("http://open.t.qq.com/api/t/show", parameters,
		// oauth);
		String result = getResource("http://open.t.qq.com/api/t/show", parameters, oauth);
		return wrapQQWeiBoBean(result);
	}

	/**
	 * 发表一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param jing
	 * @param wei
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean add(OAuth oauth, String content) throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("content", content));
		parameters.add(new QParameter("clientip", "8.8.8.8"));
		parameters.add(new QParameter("jing", ""));
		parameters.add(new QParameter("wei", ""));
		// return postContent("http://open.t.qq.com/api/t/add", parameters,
		// oauth);
		String result = postContent("http://open.t.qq.com/api/t/add", parameters, oauth);
		return wrapQQWeiBoBeanById(oauth, result);
	}

	/**
	 * 删除一条微博数据
	 * 
	 * @param oauth
	 * @param format
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean del(OAuth oauth, String id)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("id", id));
		// return postContent("http://open.t.qq.com/api/t/del", parameters,
		// oauth);
		String result = postContent("http://open.t.qq.com/api/t/del", parameters, oauth);
		return wrapQQWeiBoBeanById(oauth, result);
	}

	/**
	 * 转播一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param jing
	 * @param wei
	 * @param reid
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean re_add(OAuth oauth, String content, String reid)throws WeiBoException  {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("content", content));
		parameters.add(new QParameter("clientip", "8.8.8.8"));
		parameters.add(new QParameter("jing", ""));
		parameters.add(new QParameter("wei", ""));
		parameters.add(new QParameter("reid", reid));
		// return postContent("http://open.t.qq.com/api/t/re_add", parameters,
		// oauth);
		String result = postContent("http://open.t.qq.com/api/t/re_add", parameters, oauth);
		return wrapQQWeiBoBeanById(oauth, result);
	}

	/**
	 * 点评一条微博
	 * 
	 * @param oauth
	 * @param format
	 * @param content
	 * @param clientip
	 * @param jing
	 * @param wei
	 * @param reid
	 * @return
	 * @throws Exception
	 */
	public WeiBoBean comment(OAuth oauth, String content, String reid) throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("content", content));
		parameters.add(new QParameter("clientip", "8.8.8.8"));
		parameters.add(new QParameter("jing", ""));
		parameters.add(new QParameter("wei", ""));
		parameters.add(new QParameter("reid", reid));
		// return postContent("http://open.t.qq.com/api/t/comment", parameters,
		// oauth);
		String result = postContent("http://open.t.qq.com/api/t/comment", parameters, oauth);
		return wrapQQWeiBoBeanById(oauth, result);
	}

	private WeiBoBean wrapQQWeiBoBeanById(OAuth oauth, String orign) {
		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			String id = xml.selectSingleNode("root/data/id").getText();
			return this.show(oauth, id);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}
	}

	private WeiBoBean wrapQQWeiBoBean(String orign) {
		SAXReader saxReader = new SAXReader();
		WeiBoBean weiBoBean = new WeiBoBean();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));

			String id = xml.selectSingleNode("root/data/id").getText();
			String text = xml.selectSingleNode("root/data/text").getText();
			String source = xml.selectSingleNode("root/data/from").getText();
			String created_at = xml.selectSingleNode("root/data/timestamp").getText();
			WeiBoAccount account = wrapQQWeiBoAccount(xml);

			weiBoBean.setId(id);
			weiBoBean.setCreateTime(new Date());
			weiBoBean.setSource(source);
			weiBoBean.setText(text);
			weiBoBean.setBindType(BindTypeConst.QQ);
			weiBoBean.setUser(account);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}

		return weiBoBean;
	}

	private WeiBoAccount wrapQQWeiBoAccount(Node node) {
		WeiBoAccount account = new WeiBoAccount();
		String id = node.selectSingleNode("root/data/openid").getText();
		String screen_name = node.selectSingleNode("root/data/nick").getText();
		String name = node.selectSingleNode("root/data/name").getText();
		String profile_image_url = node.selectSingleNode("root/data/head").getText();
		String verified = node.selectSingleNode("root/data/isvip").getText();
		// String gender = node.selectSingleNode("gender").getText();

		account.setId(id);
		account.setHead(profile_image_url +"/50");
		account.setIsvip(verified == "0" ? "false" : "true");
		account.setName(StringUtils.isBlank(name) ? id : name);
		account.setNick(StringUtils.isBlank(screen_name) ? id : screen_name);
		account.setBindType(BindTypeConst.QQ);
		// account.setSex("0".equals(gender) ? "male" : "female");

		return account;
	}

}
