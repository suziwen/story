/**
 * 
 */
package com.suziwen.weibo.base;

/**
 * ******************************************************
 * <p>Description:第三方验证平台，返回类型常量定义 </p>
 * <p>Create Time: 2012-4-9 下午12:07:15</p>
 * <p>Company: Copyright Bank</p>
 * @author xiongxzh
 * @version 1.0
 *******************************************************
 */
public class CallTypeConst {
	
	/***
	 * 返回成功
	 */
	public final static String SUCCESS="0";
	
	/****
	 * 表示用户未登录且用户与绑定微博存在
	 */
	public final static String UNLOGIN_USERBINDEDTOAUTHSITE="1";
	
	/***
	 * 表示用户未登录且用户与绑定微博存在多对一的关系，需要用户选择PCB帐号登录
	 */
	public final static String UNLOGIN_USERBINDEDMANYAUTHSITE="2";
	
	/****
	 * 表示用户未登录且用户与绑定微博存在，但系统内已经找不到该PCB帐户（即存在垃圾数据）
	 */
	public final static String UNLOGIN_USERISOUTDATED="3";
	
	/****
	 * 表示用户未登录且用户与绑定微博不存在，需要用户先进行绑定操作
	 */
	public final static String UNLOGIN_USERNEEDBINDAUTHSITE="4";
	
	/****
	 * 表示用户已登录且用户与绑定微博存在，不进行任何操作
	 */
	public final static String LOGINED_USERBINDEDTOAUTHSITE="5";
	
	/***
	 * 表示用户已登录但用户与微博未进行绑定，系统自动进行绑定操作
	 */
	public final static String LOGINED_USERAUTOBINDTOAUTHSITE="6";
	
//	 * 1表示用户未登录且用户与绑定微博存在
//	 * 2.表示用户未登录且用户与绑定微博存在多对一的关系，需要用户选择PCB帐号登录
//	 * 3.表示用户未登录且用户与绑定微博存在，但系统内已经找不到该PCB帐户（即存在垃圾数据）
//	 * 4.表示用户未登录且用户与绑定微博不存在，需要用户先进行绑定操作
//	 * 5.表示用户已登录且用户与绑定微博存在，不进行任何操作
//	 * 6.表示用户已登录但用户与微博未进行绑定，系统自动进行绑定操作
}
