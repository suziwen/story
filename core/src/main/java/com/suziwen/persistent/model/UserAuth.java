package com.suziwen.persistent.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bind_userauth")
public class UserAuth implements java.io.Serializable {

	// Fields
	@Id
   @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
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
	public UserAuth() {
	}

	/** minimal constructor */
	public UserAuth(Long id) {
		this.id = id;
	}

	/** full constructor */
	public UserAuth(Long id, String userId, String siteId,
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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