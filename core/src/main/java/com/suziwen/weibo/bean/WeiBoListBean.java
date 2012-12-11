/**
 * 
 */
package com.suziwen.weibo.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/********************************************************
 * <p>
 * Description:
 * </p>
 * <p>
 * Create Time: 2012-3-29 下午12:03:01
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoListBean {

	/**
	 * 返回的总结果集
	 */
	private long total;
	/**
	 * 每页显示数
	 */
	private long pagecount = 0;
	/**
	 * 起始页码
	 */
	private long pageindex = 0;
	/**
	 * 上下文，用于不提供分页的第三方微博平台数据间的传递
	 */
	private Map<String,String> context = new HashMap<String,String>();
	/**
	 * 结果集
	 */
	private List<WeiBoBean> weiBoBeans = new ArrayList<WeiBoBean>();

	public Map<String, String> getContext() {
		return context;
	}
	public void setContext(Map<String, String> context) {
		this.context = context;
	}
	public List<WeiBoBean> getWeiBoBeans() {
		return weiBoBeans;
	}
	public void setWeiBoBeans(List<WeiBoBean> weiBoBeans) {
		this.weiBoBeans = weiBoBeans;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPagecount() {
		return pagecount;
	}
	public void setPagecount(long pagecount) {
		this.pagecount = pagecount;
	}
	public long getPageindex() {
		return pageindex;
	}
	public void setPageindex(long pageindex) {
		this.pageindex = pageindex;
	}

}
