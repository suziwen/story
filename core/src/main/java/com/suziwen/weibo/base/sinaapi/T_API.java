package com.suziwen.weibo.base.sinaapi;

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
 * ******************************************************
 * <p>
 * Description:微博相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:23:55
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class T_API extends SinaCheckExceptionRequestAPI implements IT_API {
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
	public WeiBoBean show(OAuth oauth, String id)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		String result = getResource("http://api.t.sina.com.cn/statuses/show/" + id + "." + format, parameters, oauth);
		return wrapSinaWeiBoBean(result);
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
	public WeiBoBean add(OAuth oauth, String content)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("status", content));
		String result = postContent("http://api.t.sina.com.cn/statuses/update." + format, parameters, oauth);
		return wrapSinaWeiBoBean(result);
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
		String result = postContent("http://api.t.sina.com.cn/statuses/destroy/" + id + "." + format, parameters, oauth);
		return wrapSinaWeiBoBean(result);
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
	public WeiBoBean re_add(OAuth oauth, String content, String reid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("status", content));
		parameters.add(new QParameter("id", reid));
		String result = postContent("http://api.t.sina.com.cn/statuses/repost." + format, parameters, oauth);
		return wrapSinaWeiBoBean(result);
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
	public WeiBoBean comment(OAuth oauth, String content, String reid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("comment", content));
		parameters.add(new QParameter("id", reid));
		String result = postContent("http://api.t.sina.com.cn/statuses/comment." + format, parameters, oauth);
		return wrapSinaWeiBoBean(result);
	}
	
	private WeiBoBean wrapSinaCommentWeiBoBean(String orign)throws WeiBoException {

		SAXReader saxReader = new SAXReader();
		WeiBoBean weiBoBean = new WeiBoBean();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			String id = xml.selectSingleNode("comment/id").getText();
			String text = xml.selectSingleNode("comment/text").getText();
			String source = xml.selectSingleNode("comment/source").getText();
			String created_at = xml.selectSingleNode("comment/created_at").getText();
			Node userNode = xml.selectSingleNode("comment/user");
			WeiBoAccount account = wrapSohuWeiBoAccount(userNode);

			weiBoBean.setId(id);
			weiBoBean.setCreateTime(new Date());
			weiBoBean.setSource(source);
			weiBoBean.setText(text);
			weiBoBean.setBindType(BindTypeConst.SINA);
			weiBoBean.setUser(account);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}
		return weiBoBean;
	}

	private WeiBoBean wrapSinaWeiBoBean(String orign)throws WeiBoException {

		SAXReader saxReader = new SAXReader();
		WeiBoBean weiBoBean = new WeiBoBean();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			String id = xml.selectSingleNode("status/id").getText();
			String text = xml.selectSingleNode("status/text").getText();
			String source = xml.selectSingleNode("status/source").getText();
			String created_at = xml.selectSingleNode("status/created_at").getText();
			Node userNode = xml.selectSingleNode("status/user");
			WeiBoAccount account = wrapSohuWeiBoAccount(userNode);

			weiBoBean.setId(id);
			weiBoBean.setCreateTime(new Date());
			weiBoBean.setSource(source);
			weiBoBean.setText(text);
			weiBoBean.setBindType(BindTypeConst.SINA);
			weiBoBean.setUser(account);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}
		return weiBoBean;
	}

	private WeiBoAccount wrapSohuWeiBoAccount(Node node)throws WeiBoException {
		WeiBoAccount account = new WeiBoAccount();
		String id = node.selectSingleNode("id").getText();
		String screen_name = node.selectSingleNode("screen_name").getText();
		String name = node.selectSingleNode("name").getText();
		String profile_image_url = node.selectSingleNode("profile_image_url").getText();
		String verified = node.selectSingleNode("verified").getText();
		String gender = node.selectSingleNode("gender").getText();

		account.setId(id);
		account.setHead(profile_image_url);
		account.setIsvip(verified);
		account.setName(StringUtils.isBlank(name) ? id : name);
		account.setNick(StringUtils.isBlank(screen_name) ? id : screen_name);
		account.setSex("0".equals(gender) ? "male" : "female");
		account.setBindType(BindTypeConst.SINA);
		return account;
	}

}
