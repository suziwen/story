package com.suziwen.persistent.model;


import java.util.Date;

/**
 * UserAuthOrganization entity. @author MyEclipse Persistence Tools
 */

public class UserAuthOrganization implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String siteId;
	private String bindLoginName;
	private String binPwd;
	private Date bindDate;
	private String bindUid;
	private String bindAccessToken;
	private String bindAccessSecret;

	// Constructors

	/** default constructor */
	public UserAuthOrganization() {
	}

	/** minimal constructor */
	public UserAuthOrganization(String id) {
		this.id = id;
	}

	/** full constructor */
	public UserAuthOrganization(String id, String userId, String siteId,
			String bindLoginName, String binPwd, Date bindDate, String bindUid,
			String bindAccessToken, String bindAccessSecret) {
		this.id = id;
		this.userId = userId;
		this.siteId = siteId;
		this.bindLoginName = bindLoginName;
		this.binPwd = binPwd;
		this.bindDate = bindDate;
		this.bindUid = bindUid;
		this.bindAccessToken = bindAccessToken;
		this.bindAccessSecret = bindAccessSecret;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getBindLoginName() {
		return this.bindLoginName;
	}

	public void setBindLoginName(String bindLoginName) {
		this.bindLoginName = bindLoginName;
	}

	public String getBinPwd() {
		return this.binPwd;
	}

	public void setBinPwd(String binPwd) {
		this.binPwd = binPwd;
	}

	public Date getBindDate() {
		return this.bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public String getBindUid() {
		return this.bindUid;
	}

	public void setBindUid(String bindUid) {
		this.bindUid = bindUid;
	}

	public String getBindAccessToken() {
		return this.bindAccessToken;
	}

	public void setBindAccessToken(String bindAccessToken) {
		this.bindAccessToken = bindAccessToken;
	}

	public String getBindAccessSecret() {
		return this.bindAccessSecret;
	}

	public void setBindAccessSecret(String bindAccessSecret) {
		this.bindAccessSecret = bindAccessSecret;
	}

}