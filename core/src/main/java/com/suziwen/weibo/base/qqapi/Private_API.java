package com.suziwen.weibo.base.qqapi;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.BindTypeConst;
import com.suziwen.weibo.base.api.IPrivate_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoMessageBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:私信相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:25:48
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class Private_API extends QQCheckExceptionRequestAPI implements IPrivate_API {
	
	
	private String format = "xml";

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
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("content", content));
		parameters.add(new QParameter("clientip", "8.8.8.8"));
		parameters.add(new QParameter("jing", ""));
		parameters.add(new QParameter("wei", ""));
		parameters.add(new QParameter("name", name));
		String result = postContent("http://open.t.qq.com/api/private/add", parameters, oauth);
		return wrapQQWeiBoMessageBean(result);
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
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("id", id));
		String result = postContent("http://open.t.qq.com/api/private/del", parameters, oauth);
		return wrapQQWeiBoMessageBean(result);
	}
	
	private WeiBoMessageBean wrapQQWeiBoMessageBean(String oringStr) {
		SAXReader saxReader = new SAXReader();
		WeiBoMessageBean weiBoMessageBean = new WeiBoMessageBean();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(oringStr.getBytes("UTF-8")));

			String id = xml.selectSingleNode("root/data/id").getText();
			weiBoMessageBean.setId(id);
			weiBoMessageBean.setBindType(BindTypeConst.QQ);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}
		return weiBoMessageBean;
	}
}
