/**
 * 
 */
package com.suziwen.weibo.bean;

import java.util.Date;
import java.util.Map;

/********************************************************
 * <p>
 * Description: 微博记录
 * </p>
 * <p>
 * Create Time: 2012-3-28 下午05:29:53
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoBean {
	
	/**
	 * 微博唯一ID
	 */
	private String id;
	/**
	 * 微博标题,(如果有的话)
	 */
	private String title;
	/**
	 * 微博文本内容
	 */
	private String text;
	/**
	 * 微博发布源
	 */
	private String source;
	/**
	 * 微博发布时间 
	 */
	private Date createTime;
	/**
	 * 该微博的发布者
	 */
	private WeiBoAccount user;
	
	/**
	 * 上下文变量,用于解决不同微博需要的特殊值
	 */
	private Map<String,String> context;
	
	/**
	 * 绑定类型
	 */
	private String bindType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public WeiBoAccount getUser() {
		return user;
	}

	public void setUser(WeiBoAccount user) {
		this.user = user;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
