/**
 * 
 */
package com.suziwen.weibo.exception;

import com.suziwen.weibo.bean.WeiBoExceptionBean;

/********************************************************
 * <p>
 * Description: 第三方微博连接限制异常
 * </p>
 * <p>
 * Create Time: 2012-3-30 下午04:45:57
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class WeiBoConnLimmitException extends WeiBoException {

	/**
	 * 
	 */
	public WeiBoConnLimmitException() {
		// TODO Auto-generated constructor stub
	}

	public WeiBoConnLimmitException(WeiBoExceptionBean weiBoExceptionBean) {
		super(weiBoExceptionBean);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public WeiBoConnLimmitException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public WeiBoConnLimmitException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WeiBoConnLimmitException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
