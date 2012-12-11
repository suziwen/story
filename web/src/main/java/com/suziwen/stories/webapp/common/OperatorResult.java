/**
 * 
 */
package com.suziwen.stories.webapp.common;


/**
 * @author Administrator
 * @param <T>
 * 
 */
public class OperatorResult<T> {

	/***
	 * 操作结果消息
	 */
	private String message;
	/**
	 * 操作结果，成功或者失败，用常量值1，0来表示
	 */
	private String value;

	/**
	 * 操作的数据ID，返回当前进行操作的数据主键，如果是文件上传类型的，可能是文件名称也可以再进行新的文件上传结果定义
	 */
	private String dataId;

	/***
	 * 操作数据的父ID
	 */
	private String dataPId;

	/***
	 * 需要返回的数据内容，objprops必须是满足的简单对像
	 */
	private T dataList;
	/**
	 * 返回的页数
	 */
	private Page page =new Page();

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		// 消息编号，读取消息内容
		this.message = message;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the dataId
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * @param dataId
	 *            the dataId to set
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * @return the dataPId
	 */
	public String getDataPId() {
		return dataPId;
	}

	/**
	 * @param dataPId
	 *            the dataPId to set
	 */
	public void setDataPId(String dataPId) {
		this.dataPId = dataPId;
	}

	public OperatorResult() {
		this.message = "";
		this.value = "1";
		this.dataId = "";
		this.dataPId = "";
	}

	/***
	 * 指定操作结果，常量值，SyConst.True|| SysConst.False;
	 * 
	 * @param value
	 */
	public OperatorResult(String value) {
		this();
		this.value = value;
	}

	/***
	 * 指定操作结果，及返回的消息类型
	 * 
	 * @param value
	 * @param message
	 */
	public OperatorResult(String value, String message) {
		this(value);
		this.message = message;
	}

	/***
	 * 指定操作结果，消息，及操作结果ID
	 * 
	 * @param value
	 * @param message
	 * @param dataId
	 */
	public OperatorResult(String value, String message, String dataId) {
		this(value, message);
		this.dataId = dataId;
	}

	/***
	 * 指定操作结果，消息，及操作结果ID,及操作结果父ID
	 * 
	 * @param value
	 * @param message
	 * @param dataId
	 * @param dataPId
	 */
	public OperatorResult(String value, String message, String dataId,
			String dataPId) {
		this(value, message, dataId);
		this.dataPId = dataPId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public T getDataList() {
		return dataList;
	}

	public void setDataList(T dataList) {
		this.dataList = dataList;
	}

	/**
	 * ******************************************************
	 * <p>
	 * Description: 分页类
	 * </p>
	 * <p>
	 * Create Time: 2011-5-25 下午06:47:44
	 * </p>
	 * <p>
	 * Company: Copyright Bank
	 * </p>
	 * 
	 * @author suziwen
	 * @version 1.0
	 ******************************************************* 
	 */
	public class Page {

		private long recordCount;

		/**
		 * @return the recordCount
		 */
		public long getRecordCount() {
			return recordCount;
		}

		/**
		 * @param recordCount the recordCount to set
		 */
		public void setRecordCount(long recordCount) {
			this.recordCount = recordCount;
		}
 
	}

}
