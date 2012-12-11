/**
 * 
 */
package com.suziwen.weibo.base.qqapi;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.base.RequestAPI;
import com.suziwen.weibo.bean.WeiBoExceptionBean;
import com.suziwen.weibo.exception.WeiBoAuthException;
import com.suziwen.weibo.exception.WeiBoConnLimmitException;
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
public abstract class QQCheckExceptionRequestAPI extends RequestAPI {
	private static final Logger logger = Logger.getLogger(QQCheckExceptionRequestAPI.class);

	public void checkException(String orignStr) throws WeiBoException {
		SAXReader saxReader = new SAXReader();
		try {
			Document xml = saxReader.read(new ByteArrayInputStream(orignStr.getBytes("UTF-8")));
			int ret = Integer.parseInt(xml.selectSingleNode("root/ret").getText());
			String msg = xml.selectSingleNode("root/msg").getText();
			String errcode = xml.selectSingleNode("root/errcode").getText();
			WeiBoExceptionBean wbean = new WeiBoExceptionBean();
			wbean.setOrignMsg(msg);
			wbean.setOrignReturn(orignStr);
			wbean.setErrorCode(errcode);
			switch (ret) {

			case 0:
				// arr[1] = "操作成功";
				break;
			case 1:
				// arr[1] = "参数错误";
				wbean.setValue(WeiBoExceptionBean.REQUESTERROR);
				throw new WeiBoAuthException(wbean);
			case 2:
				// arr[1] = "频率受限";
				wbean.setValue(WeiBoExceptionBean.CONNLIMMIT);
				throw new WeiBoConnLimmitException(wbean);
			case 3:
				// arr[1] = "鉴权失败";
				wbean.setValue(WeiBoExceptionBean.UNAUTH);
				throw new WeiBoAuthException(wbean);
			case 4:
				// arr[1] = "服务器内部错误";
				wbean.setValue(WeiBoExceptionBean.SERVERERROR);
				throw new WeiBoAuthException(wbean);

			}

		} catch (WeiBoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw new WeiBoException("返回了无法解析的数据", e);
		}

	}
}
