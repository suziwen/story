/**
 * 
 */
package com.suziwen.weibo.base.sinaapi;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.RequestAPI;
import com.suziwen.weibo.bean.WeiBoExceptionBean;
import com.suziwen.weibo.exception.WeiBoException;

/********************************************************
 * <p>
 * Description: 基础错误检测
 * </p>
 * <p>
 * Create Time: 2012-3-30 下午05:14:13
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ********************************************************/
public abstract class SinaCheckExceptionRequestAPI extends RequestAPI {
	private static final Logger logger = Logger.getLogger(SinaCheckExceptionRequestAPI.class);

	public void checkException(String orignStr) throws WeiBoException {
		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orignStr.getBytes("UTF-8")));Node node = xml.selectSingleNode("hash");
			if(node != null ){
				String msg = xml.selectSingleNode("hash/error").getText();
				String errcode = xml.selectSingleNode("hash/code").getText();
				WeiBoExceptionBean wbean = new WeiBoExceptionBean();
				wbean.setOrignMsg(msg);
				wbean.setOrignReturn(orignStr);
				wbean.setErrorCode(errcode);
				wbean.setValue(WeiBoExceptionBean.SERVERERROR);
				throw new WeiBoException(wbean);
			}
		} catch (WeiBoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new WeiBoException("返回了无法解析的数据", e);
		}

	}
}
