/**
 * 
 */
package com.suziwen.weibo.bean;

import java.util.Date;
import java.util.Map;

/********************************************************
 * <p>
 * Description:微博私信BEAN
 * </p>
 * <p>
 * Create Time: 2012-3-31 下午12:12:35
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoMessageBean {
	
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 发送文本内容
	 */
	private String text;
	/**
	 * 创建时间 
	 */
	private Date createDate;
	
	/**
	 * 绑定类型
	 */
	private String bindType;
	/**
	 * 发送者
	 */
	private WeiBoAccount senderUser;
	/**
	 * 接收者
	 */
	private WeiBoAccount receiverUser;
	
	/**
	 * 上下文,用于存储特殊微博需要的数据
	 */
	private Map<String,String> context;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public WeiBoAccount getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(WeiBoAccount senderUser) {
		this.senderUser = senderUser;
	}

	public WeiBoAccount getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(WeiBoAccount receiverUser) {
		this.receiverUser = receiverUser;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

}
