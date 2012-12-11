package com.suziwen.weibo.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.suziwen.weibo.bean.QParameter;
import com.suziwen.weibo.exception.ConnectionException;

public class QHttpClient {

	private static final int CONNECTION_TIMEOUT = 60 * 1000;// 一分钟的等待时间
	private static Log log = LogFactory.getLog(QHttpClient.class);

	public QHttpClient() {

	}

	/**
	 * Using GET method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpGet(String url, String queryString) throws ConnectionException {
		String responseData = null;

		try {
			if (queryString != null && !queryString.equals("")) {
				url += "?" + queryString;
			}

			log.info("httpGet [1]. url = " + url);

			HttpClient httpClient = new DefaultHttpClient();
			HttpUriRequest request = new HttpGet(url);
			request.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));

			// consumer.
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				log.info("HttpGet [2] Method failed: " + statusCode);
			}
			responseData = IOUtils.toString(entity.getContent(), "utf-8");
			log.info(" httpGet [3] getResponseBodyAsString() = " + responseData);
			//保证实体内容被完全消耗而且低层的流被关闭
			EntityUtils.consume(entity);
			httpClient = null;
		} catch (Exception e) {
			log.error("", e);
			throw new ConnectionException(e);
		}
		return responseData;
	}

	/**
	 * Using POST method.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPost(String url, String queryString) throws ConnectionException {
		String responseData = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			log.info("QHttpClient httpPost [1] url = " + url);
			HttpPost httpPost = new HttpPost(url);

			httpPost.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded"));
			httpPost.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
			if (queryString != null && !queryString.equals("")) {
				ByteArrayEntity reqEntity = new ByteArrayEntity(queryString.getBytes("utf-8"));
				httpPost.setEntity(reqEntity);
			}
			// httpPost.getEntity().
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: " + statusLine);
			}
			responseData = IOUtils.toString(entity.getContent(), "utf-8");
			log.info("QHttpClient httpPost [2] responseData = " + responseData);
			EntityUtils.consume(entity);
			httpClient = null;
		} catch (Exception e) {
			log.error("", e);
			throw new ConnectionException(e);
		}

		return responseData;
	}

	/**
	 * Using POST method with multiParts.
	 * 
	 * @param url
	 *            The remote URL.
	 * @param queryString
	 *            The query string containing parameters
	 * @param files
	 *            The list of image files
	 * @return Response string.
	 * @throws Exception
	 */
	public String httpPostWithFile(String url, String queryString, List<QParameter> files) throws ConnectionException {

		String responseData = null;
		try {
			url += '?' + queryString;
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			List<QParameter> listParams = QHttpUtil.getQueryParameters(queryString);
			int length = listParams.size() + (files == null ? 0 : files.size());

			MultipartEntity reqEntity = new MultipartEntity();
			int i = 0;
			for (QParameter param : listParams) {
				reqEntity.addPart(param.getName(), new StringBody(QHttpUtil.formParamDecode(param.getValue()), Charset.forName("UTF-8")));
			}

			for (QParameter param : files) {
				String filePath = param.getValue();
				File file = new File(filePath);
				// String fileName = file.getName();
				// String type = QHttpUtil.getContentType(file);

				// image/jpeg - image/png
				reqEntity.addPart(param.getName(), new FileBody(file, "image/jpeg", "utf-8"));
			}
			// reqEntity.getContentType()
			httpPost.setEntity(reqEntity);
			// httpPost.setEntity(new
			// MultipartEntity(parts,httpPost.getParams()));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: " + statusLine);
			}
			responseData = IOUtils.toString(entity.getContent(), "utf-8");

			EntityUtils.consume(entity);
			httpClient = null;
		} catch (Exception e) {
			log.error("", e);
			throw new ConnectionException(e);
		}

		return responseData;
	}

}
