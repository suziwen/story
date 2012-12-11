package com.suziwen.weibo.base.qqapi;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.BindTypeConst;
import com.suziwen.weibo.base.api.ISearch_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:搜索相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:25:33
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
// 注意：搜索相关API仅对腾讯合作方开放
public class Search_API extends QQCheckExceptionRequestAPI implements ISearch_API {
	private static final Logger logger = Logger.getLogger(Search_API.class);
	private String format = "xml";
	/**
	 * 搜索广播  ,QQ查询支持正常的分页~~~
	 * 
	 * @param oauth
	 * @param keyword
	 * @param pagesize
	 *            每页大小
	 * @param page
	 *            页码
	 * @return
	 * @throws Exception
	 */
	public  WeiBoListBean t(OAuth oauth,  String keyword, String pagesize, String page,String lastid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("format", format));
		parameters.add(new QParameter("keyword", keyword));
		parameters.add(new QParameter("pagesize", pagesize));
		parameters.add(new QParameter("page", page));
		String result = getResource("http://open.t.qq.com/api/search/t", parameters, oauth);
		return wrapQQWeiBoBean(result);
	}
	
private WeiBoListBean wrapQQWeiBoBean(String orign)throws WeiBoException {
		
	WeiBoListBean list = new WeiBoListBean();
		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			List<WeiBoBean> weiBoBeans = new ArrayList<WeiBoBean>();
			String total = xml.selectSingleNode("root/data/totalnum").getText();
			list.setTotal(Long.parseLong(total));
			List<Node> nodes = xml.selectNodes("root/data/info");
			for(Node node : nodes) {
				WeiBoBean weiBoBean = new WeiBoBean();
				String id = node.selectSingleNode("id").getText();
				String text = node.selectSingleNode("text").getText();
				String source = node.selectSingleNode("from").getText();
				String created_at = node.selectSingleNode("timestamp").getText();
				WeiBoAccount account = wrapQQWeiBoAccount(node);
				
				weiBoBean.setId(id);
				weiBoBean.setCreateTime(new Date());
				weiBoBean.setSource(source);
				weiBoBean.setText(text);
				weiBoBean.setUser(account);
				weiBoBean.setBindType(BindTypeConst.QQ	);
				weiBoBeans.add(weiBoBean);
			}
			list.setWeiBoBeans(weiBoBeans);
		} catch (UnsupportedEncodingException e) {
			throw new WeiBoException("不支持的格式转换", e);
		} catch (DocumentException e) {
			throw new WeiBoException(e);
		}
		return list;
	}
	
	private WeiBoAccount wrapQQWeiBoAccount(Node node)throws WeiBoException {
		WeiBoAccount account = new WeiBoAccount();
		String id = node.selectSingleNode("openid").getText();
		String screen_name = node.selectSingleNode("nick").getText();
		String name = node.selectSingleNode("name").getText();
		String profile_image_url = node.selectSingleNode("head").getText();
		String verified = node.selectSingleNode("isvip").getText();
		//String gender = node.selectSingleNode("gender").getText();
		
		
		account.setId(id);
		account.setHead(profile_image_url+"/50");
		account.setIsvip(verified == "0" ? "false": "true");
		account.setName(StringUtils.isBlank(name)? id : name	);
		account.setNick(StringUtils.isBlank(screen_name)? id : screen_name);
		account.setBindType(BindTypeConst.QQ);
		//account.setSex("0".equals(gender) ? "male" : "female");
		
		return account;
	}
}
