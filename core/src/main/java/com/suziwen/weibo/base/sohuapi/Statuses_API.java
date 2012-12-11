package com.suziwen.weibo.base.sohuapi;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.BindTypeConst;
import com.suziwen.weibo.base.api.IStatuses_API;
import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.bean.WeiBoAccount;
import com.suziwen.weibo.bean.WeiBoBean;
import com.suziwen.weibo.bean.WeiBoListBean;
import com.suziwen.weibo.exception.WeiBoException;

/**
 * ******************************************************
 * <p>
 * Description:时间线相关API
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午04:22:08
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */

public class Statuses_API extends SohuCheckExceptionRequestAPI implements IStatuses_API {
	private String format = "xml";
	/**
	 * 主页时间线,a
	 * 
	 * @param oauth
	 * @param format
	 * @param pageflag
	 * @param reqnum
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean home_timeline(OAuth oauth,String pageflag,  String reqnum, String lastid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		if("2".equals(pageflag)){
			parameters.add(new QParameter("since_id", lastid));
		} else if("1".equals(pageflag)){
			parameters.add(new QParameter("max_id", lastid));
		} else {
//			Date d = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("E M W HH:mm:ss Z yyyy",Locale.US);
//			String result = sdf.format(d);
			parameters.add(new QParameter("page", "0"));
		}
		parameters.add(new QParameter("count", reqnum));
		String result = getResource("http://api.t.sohu.com/statuses/friends_timeline." + format, parameters, oauth);
		return wrapSohuWeiBoBean(result);
	}

	/**
	 * 广播大厅时间线 sohuAPI没法控制取得的条数，只能是系统默认的，然后再在外面 进行处理
	 * 
	 * @param oauth
	 * @param format
	 * @param pos
	 * @param reqnum
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean public_timeline(OAuth oauth,String pos, String reqnum, String lastid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		String result = getResource("http://api.t.sohu.com/statuses/public_timeline.xml", parameters, oauth);
		return wrapSohuWeiBoBean(result);
	}

	/**
	 * 其他用户发表时间线
	 * 
	 * @param oauth
	 * @param format
	 * @param pageflag
	 * @param reqnum
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean user_timeline(OAuth oauth,String pageflag,  String reqnum, String lastid, String name)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		if("2".equals(pageflag)){
			parameters.add(new QParameter("since_id", lastid));
		} else if("1".equals(pageflag)){
			parameters.add(new QParameter("max_id", lastid));
		} else {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("E M W HH:mm:ss Z yyyy",Locale.US);
			String result = sdf.format(d);
			parameters.add(new QParameter("since", result));
		}
		parameters.add(new QParameter("count", reqnum));
		String result = getResource("http://api.t.sohu.com/statuses/user_timeline/" + name + "." + format, parameters, oauth);
		return wrapSohuWeiBoBean(result);
	}

	/**
	 * 用户提及时间线
	 * 
	 * @param oauth
	 * @param format
	 * @param pageflag
	 * @param reqnum
	 * @param lastid
	 * @return
	 * @throws Exception
	 */
	public WeiBoListBean mentions_timeline(OAuth oauth,String pageflag,  String reqnum, String lastid)throws WeiBoException {
		List<QParameter> parameters = new ArrayList<QParameter>();
		if("2".equals(pageflag)){
			parameters.add(new QParameter("since_id", lastid));
		} else if("1".equals(pageflag)){
			parameters.add(new QParameter("max_id", lastid));
		} else {
			
		}
		parameters.add(new QParameter("count", reqnum));
		String result = getResource("http://api.t.sohu.com/statuses/mentions_timeline."+format, parameters, oauth);
		return wrapSohuWeiBoBean(result);
	}
	
	
	private WeiBoListBean wrapSohuWeiBoBean(String orign)throws WeiBoException {
		WeiBoListBean list = new WeiBoListBean();
		
		
		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orign.getBytes("UTF-8")));
			List<WeiBoBean> weiBoBeans = new ArrayList<WeiBoBean>();
			List<Node> nodes = xml.selectNodes("statuses/status");
			for(Node node : nodes) {
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
		account.setName(StringUtils.isBlank(name)? id : name	);
		account.setNick(StringUtils.isBlank(screen_name)? id : screen_name);
		account.setSex("0".equals(gender) ? "male" : "female");
		account.setBindType(BindTypeConst.SOHU);
		return account;
	}
}
