package com.suziwen.weibo.base;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.exception.WeiBoException;
import com.suziwen.weibo.utils.OAuthClient;
import com.suziwen.weibo.utils.QHttpClient;


 

/**
 * Request Remote Server API
 * 
 * @author <a href="http://blog.javawind.net">Xuefang Xu</a> 
 * @Data 2010-01-21
 * @Version 1.0.0
 */

public abstract class RequestAPI {
	QHttpClient http = new QHttpClient();// 使用同步HTTP方式
	private static Log log = LogFactory.getLog(RequestAPI.class);
	// QHttpClient http = new AsyncHttpClient();//使用异步HTTP方式
//	AsyncCallBack callBack = new AsyncCallBack();

	/**
	 * get json or xml resource from remote api
	 * 
	 * @param url
	 * @param params
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public String getResource(String url, List<QParameter> parameters,
			OAuth oauth) {

		parameters.addAll(oauth.getTokenParams());

		OAuthClient oac = new OAuthClient();
		String queryString = oac.getOauthParams(url, "GET",
				oauth.getOauth_consumer_secret(),
				oauth.getOauth_token_secret(), parameters);
		String result = http.httpGet(url, queryString);
		checkException(result);
		return result;
	}
	
	
	/**
	 * 
	 * 检测数据是否有异常
	 * @param result
	 * @throws WeiBoException
	 */
	public void checkException(String result) throws WeiBoException {
		//子函数可以继承该方法用于对异常的处理
	}
	

	/**
	 * submit a post request to remote api
	 * 
	 * @param url
	 * @param parameters
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public String postContent(String url, List<QParameter> parameters,
			OAuth oauth) {

		parameters.addAll(oauth.getTokenParams());

		OAuthClient oac = new OAuthClient();
		String queryString = oac.getOauthParams(url, "POST",
				oauth.getOauth_consumer_secret(),
				oauth.getOauth_token_secret(), parameters);
		
		log.info("RequestAPI postContent queryString = "+queryString);
		String result =  http.httpPost(url, queryString);
		checkException(result);
		return result;
	}

	/**
	 * submit a post request with a file to remote api
	 * 
	 * @param url
	 * @param parameters
	 * @param filePath
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public String postFile(String url, List<QParameter> parameters,
			List<QParameter> files, OAuth oauth) {
		parameters.addAll(oauth.getTokenParams());

		OAuthClient oac = new OAuthClient();
		String queryString = oac.getOauthParams(url, "POST",
				oauth.getOauth_consumer_secret(),
				oauth.getOauth_token_secret(), parameters);

		String result = http.httpPostWithFile(url, queryString, files);
		checkException(result);
		return result;
	}

	public String[] message(String result) {
		String[] arr = new String[2];

		try {
			SAXReader saxReader = new SAXReader();
			Document xml = saxReader.read(new ByteArrayInputStream(result
					.getBytes("UTF-8")));
			arr[0] = xml.selectSingleNode("root/ret").getText();
			// arr[1] = xml.selectSingleNode("root/msg").getText();
			switch (Integer.parseInt(arr[0])) {
			case 0:
				arr[1] = "操作成功";
				break;
			case 1:
				arr[1] = "参数错误";
				break;
			case 2:
				arr[1] = "频率受限";
				break;
			case 3:
				arr[1] = "鉴权失败";
				break;
			case 4:
				arr[1] = "服务器内部错误";
				break;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			arr[0] = "4";
			arr[1] = e.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
			arr[0] = "4";
			arr[1] = e.toString();
		}

		return arr;
	}
}
