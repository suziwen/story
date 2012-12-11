package com.suziwen.weibo.bean;

/**
 * 个人账户实体类
 * 
 * @author <a href="http://blog.javawind.net">Xuefang Xu</a> 
 * @Data 2010-01-21
 * @Version 1.0.0
 */

public class WeiBoAccount {
	private String id = "";
	private String name = ""; // 帐号
	private String nick = ""; // 昵称
	private String head = ""; // 头像
	private String isvip = ""; // 是否认证
	private String sex = ""; // 性别
	private String fansnum = ""; // 粉丝数量
	private String idolnum = ""; // 偶像数量
	private String tweetnum = ""; // 微博数量
	
	/**
	 * 绑定类型
	 */
	private String bindType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getIsvip() {
		return isvip;
	}

	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFansnum() {
		return fansnum;
	}

	public void setFansnum(String fansnum) {
		this.fansnum = fansnum;
	}

	public String getIdolnum() {
		return idolnum;
	}

	public void setIdolnum(String idolnum) {
		this.idolnum = idolnum;
	}

	public String getTweetnum() {
		return tweetnum;
	}

	public void setTweetnum(String tweetnum) {
		this.tweetnum = tweetnum;
	}

	@Override
	public String toString() {
		return "{id:\""+id+"\",name:\"" + name + "\", nick:\"" + nick + "\", head:\"" + head
				+ "\", isvip:\"" + isvip + "\", sex:\"" + sex + "\", fansnum:\"" + fansnum
				+ "\", idolnum:\"" + idolnum + "\", tweetnum:\"" + tweetnum + "\"}";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}
}
