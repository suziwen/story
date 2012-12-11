/**
 * 
 */
package com.suziwen.weibo.base;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * ******************************************************
 * <p>Description: 认证请求状态</p>
 * <p>Create Time: 2012-4-9 下午12:20:05</p>
 * <p>Company: Copyright Bank</p>
 * @author xiongxzh
 * @version 1.0
 *******************************************************
 */
public class OAuthStatusConst {
	
	/***
	 * 认证成功
	 */
	public final static int SUCCESS=0;
	
	/****
	 * 认证请求失败
	 */
	public final static int REQUESTFAILURE=1;
	
	/***
	 * 认证访问失败
	 */
	public final static int ACCESSFAILURE=2;
}
