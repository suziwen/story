package com.suziwen.weibo.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.suziwen.weibo.bean.OAuth;
import com.suziwen.weibo.bean.QParameter;



/**
 * ******************************************************
 * <p>
 * Description:OAuth认证授权以及签名相关
 * </p>
 * <p>
 * Create Time: 2012-3-27 下午05:06:04
 * </p>
 * <p>
 * Company: Copyright Bank
 * </p>
 * 
 * @author suziwen
 * @version 1.0
 ******************************************************* 
 */
public class OAuthClient {
	private static final String hashAlgorithmName = "HmacSHA1";
	private static Log log = LogFactory.getLog(OAuthClient.class);

	/**
	 * 获取未授权的Request Token
	 * 
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public OAuth requestToken(OAuth oauth) throws Exception {
		//String url = "https://open.t.qq.com/cgi-bin/request_token";
		String url = oauth.getRequestTokenUrl();
		QHttpClient http = new QHttpClient();

		String queryString = getOauthParams(url, "GET", oauth.getOauth_consumer_secret(), "", oauth.getParams());

		log.info("requestToken queryString = " + queryString);
		System.out.println("queryString:" + queryString);
		String responseData = http.httpGet(url, queryString);
		log.info("requestToken responseData = " + responseData);
		System.out.println("responseData:" + responseData);
		if (!parseToken(responseData, oauth)) {// Request Token 授权不通过
			oauth.setStatus(1);
			log.info("requestToken past !");
		}

		return oauth;
	}

	/**
	 * 使用授权后的Request Token换取Access Token
	 * 
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public OAuth accessToken(OAuth oauth) throws Exception {

		log.info("accessToken oauth.getOauth_token() = " + oauth.getOauth_token() + "oauth.getOauth_verifier() = " + oauth.getOauth_verifier());

		//String url = "https://open.t.qq.com/cgi-bin/access_token";
		String url = oauth.getAccessTokenUrl();
		QHttpClient http = new QHttpClient();

		String queryString = getOauthParams(url, "GET", oauth.getOauth_consumer_secret(), oauth.getOauth_token_secret(), oauth.getAccessParams());

		log.info("accessToken queryString = " + queryString);
		log.info("accessToken url = " + url);

		String responseData = http.httpGet(url, queryString);

		log.info("accessToken responseData = " + responseData);

		if (!parseToken(responseData, oauth)) {// Access Token 授权不通过
			oauth.setStatus(2);
		}
		return oauth;
	}

	/**
	 * 处理请求参数 和 生成签名
	 * 
	 * @param url
	 * @param httpMethod
	 * @param consumerSecret
	 * @param tokenSecrect
	 * @param parameters
	 * @return
	 */
	public String getOauthParams(String url, String httpMethod, String consumerSecret, String tokenSecrect, List<QParameter> parameters) {
		Collections.sort(parameters);

		String urlWithParameter = url;

		String parameterString = encodeParams(parameters);
		if (parameterString != null && !parameterString.equals("")) {
			urlWithParameter += "?" + parameterString;
		}

		URL aUrl = null;
		try {
			aUrl = new URL(urlWithParameter);
		} catch (MalformedURLException e) {
			System.err.println("URL parse error:" + e.getLocalizedMessage());
		}

		String signature = this.generateSignature(aUrl, consumerSecret, tokenSecrect, httpMethod, parameters);
		parameterString += "&oauth_signature=";
		parameterString += QStr.encode(signature);

		return parameterString;
	}

	/**
	 * 验证Token返回结果
	 * 
	 * @param response
	 * @param oauth
	 * @return
	 * @throws Exception
	 */
	public boolean parseToken(String response, OAuth oauth) throws Exception {
		if (response == null || response.equals("")) {
			return false;
		}

		oauth.setMsg(response);
		String[] tokenArray = response.split("&");
		if (tokenArray.length < 2) {
			return false;
		}

		String strTokenKey = tokenArray[0];
		String strTokenSecrect = tokenArray[1];

		String[] token = strTokenKey.split("=");
		if (token.length < 2) {
			return false;
		}
		oauth.setOauth_token(token[1]);

		String[] tokenSecrect = strTokenSecrect.split("=");
		if (tokenSecrect.length < 2) {
			return false;
		}
		oauth.setOauth_token_secret(tokenSecrect[1]);

		log.info("parseToken response=>> tokenArray.length = " + tokenArray.length);
		if (tokenArray.length == 3) {
			String[] params = tokenArray[2].split("=");
			if ("name".equals(params[0]) && params.length == 2) {
				// oauth.setAccount(this.getAccount(oauth));// 获取当前登录用户的账户信息
			} else if(("user_id").equals(params[0])&&params.length == 2 ){
				oauth.setUser_id(params[1]);
			}
		}

		return true;
	}

	/**
	 * 生成签名值
	 * 
	 * @param url
	 * @param consumerSecret
	 * @param accessTokenSecret
	 * @param httpMethod
	 * @param parameters
	 * @return
	 */
	private String generateSignature(URL url, String consumerSecret, String accessTokenSecret, String httpMethod, List<QParameter> parameters) {
		String base = this.generateSignatureBase(url, httpMethod, parameters);
		return this.generateSignature(base, consumerSecret, accessTokenSecret);
	}

	/**
	 * 生成签名值
	 * 
	 * @param base
	 * @param consumerSecret
	 * @param accessTokenSecret
	 * @return
	 */
	private String generateSignature(String base, String consumerSecret, String accessTokenSecret) {
		try {
			Mac mac = Mac.getInstance(hashAlgorithmName);
			String oauthSignature = QStr.encode(consumerSecret) + "&" + ((accessTokenSecret == null) ? "" : QStr.encode(accessTokenSecret));
			SecretKeySpec spec = new SecretKeySpec(oauthSignature.getBytes(), hashAlgorithmName);
			mac.init(spec);
			byte[] bytes = mac.doFinal(base.getBytes());
			return new String(Base64Encoder.encode(bytes));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 处理签名请求和参数
	 * 
	 * @param url
	 * @param httpMethod
	 * @param parameters
	 * @return
	 */
	private String generateSignatureBase(URL url, String httpMethod, List<QParameter> parameters) {

		StringBuilder base = new StringBuilder();
		base.append(httpMethod.toUpperCase());
		base.append("&");
		base.append(QStr.encode(getNormalizedUrl(url)));
		base.append("&");
		base.append(QStr.encode(encodeParams(parameters)));

		return base.toString();
	}

	/**
	 * 处理请求URL
	 * 
	 * @param url
	 * @return
	 */
	private static String getNormalizedUrl(URL url) {
		try {
			StringBuilder buf = new StringBuilder();
			buf.append(url.getProtocol());
			buf.append("://");
			buf.append(url.getHost());
			if ((url.getProtocol().equals("http") || url.getProtocol().equals("https")) && url.getPort() != -1) {
				buf.append(":");
				buf.append(url.getPort());
			}
			buf.append(url.getPath());
			return buf.toString();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 编码请求参数
	 * 
	 * @param params
	 * @return
	 */
	private static String encodeParams(List<QParameter> params) {
		StringBuilder result = new StringBuilder();
		for (QParameter param : params) {
			if (result.length() != 0) {
				result.append("&");
			}
			result.append(QStr.encode(param.getName()));
			result.append("=");
			result.append(QStr.encode(param.getValue()));
		}
		return result.toString();
	}

}
