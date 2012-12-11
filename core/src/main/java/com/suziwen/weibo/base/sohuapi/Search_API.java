package com.suziwen.weibo.base.sohuapi;

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
 * Create Time: 2012-3-28 下午04:23:11
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class Search_API extends SohuCheckExceptionRequestAPI implements ISearch_API {
	private String format = "xml";

	/**
	 * 搜索广播
	 * 搜狐查询后只能向下翻页
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
	public WeiBoListBean t(OAuth oauth, String keyword, String pagesize, String pageflag,String lastid) throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		parameters.add(new QParameter("q", keyword));
		parameters.add(new QParameter("rpp", pagesize));
		parameters.add(new QParameter("page", pageflag));
//		if("2".equals(pageflag)){
//			parameters.add(new QParameter("since_id", lastid));
//		} else if("1".equals(pageflag)){
//			parameters.add(new QParameter("max_id", lastid));
//		} else {
//			
//		}
		String result = getResource("http://api.t.sohu.com/statuses/search." + format, parameters, oauth);
		return wrapSohuWeiBoBean(result);
	}

	private WeiBoListBean wrapSohuWeiBoBean(String orign) throws WeiBoException {
		WeiBoListBean list = new WeiBoListBean();

		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			List<WeiBoBean> weiBoBeans = new ArrayList<WeiBoBean>();
			String total = xml.selectSingleNode("searchResult/current_total_count").getText();
			list.setTotal(Long.parseLong(total));
			List<Node> nodes = xml.selectNodes("searchResult/statuses/status");
			for (Node node : nodes) {
				WeiBoBean weiBoBean = new WeiBoBean();
				String id = node.selectSingleNode("id").getText();
				String text = node.selectSingleNode("text").getText();
				String source = node.selectSingleNode("source").getText();
				String created_at = node.selectSingleNode("created_at").getText();
				Node userNode = node.selectSingleNode("user");
				WeiBoAccount account = wrapSohuWeiBoAccount(userNode);

				weiBoBean.setId(id);
				weiBoBean.setCreateTime(new Date());
				weiBoBean.setSource(source);
				weiBoBean.setText(text);
				weiBoBean.setBindType(BindTypeConst.SOHU);
				weiBoBean.setUser(account);
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

	private WeiBoAccount wrapSohuWeiBoAccount(Node node) throws WeiBoException {
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
		account.setBindType(BindTypeConst.SOHU);
		return account;
	}
}
