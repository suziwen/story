/**
 * 
 */
package com.suziwen.weibo.exception;

import com.suziwen.weibo.bean.WeiBoExceptionBean;

/********************************************************
 * <p>
 * Description: 第三方微博异常
 * </p>
 * <p>
 * Create Time: 2012-3-30 下午04:44:19
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoException extends RuntimeException {
	
	/**
	 * 发生错误时的上下文
	 */
	private WeiBoExceptionBean weiBoExceptionBean;

	public WeiBoException(WeiBoExceptionBean weiBoExceptionBean) {
		super();
		this.weiBoExceptionBean = weiBoExceptionBean;
	}

	/**
	 * 
	 */
	public WeiBoException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public WeiBoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public WeiBoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WeiBoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WeiBoExceptionBean getWeiBoExceptionBean() {
		return weiBoExceptionBean;
	}

	public void setWeiBoExceptionBean(WeiBoExceptionBean weiBoExceptionBean) {
		this.weiBoExceptionBean = weiBoExceptionBean;
	}

}
