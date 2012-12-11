/**
 * 
 */
package com.suziwen.stories.webapp.action.bind;

import java.util.Map;

/********************************************************
 * <p>
 * Description: 获取第三方认证后系统返回的状态
 * callback格式说明
 * 
 * 1表示用户未登录且用户与绑定微博存在
 * 2.表示用户未登录且用户与绑定微博存在多对一的关系，需要用户选择PCB帐号登录
 * 3.表示用户未登录且用户与绑定微博存在，但系统内已经找不到该PCB帐户（即存在垃圾数据）
 * 4.表示用户未登录且用户与绑定微博不存在，需要用户先进行绑定操作
 * 5.表示用户已登录且用户与绑定微博存在，不进行任何操作
 * 6.表示用户已登录但用户与微博未进行绑定，系统自动进行绑定操作
 * </p>
 * <p>
 * Create Time: 2012-3-20 下午02:53:08
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class CallBack {
	private String id;
	private String href;
	private String type;
	private Map<String,String> context;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}
}