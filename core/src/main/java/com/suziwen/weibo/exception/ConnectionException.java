/**
 * 
 */
package com.suziwen.weibo.exception;

/********************************************************
 * <p>
 * Description: 连接时出现的异常
 * </p>
 * <p>
 * Create Time: 2012-3-30 下午04:37:10
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public class ConnectionException extends RuntimeException {

	/**
	 * 
	 */
	public ConnectionException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ConnectionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
