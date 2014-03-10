package com.ms.learn.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.ms.learn.ShareData;

public class HttpMethod {

	/*
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair ("bookname", etBookName.getText().
	 * toString())); // 设置HTTP POST请求参数
	 */

	public static String doPost(String url, List<NameValuePair> params) {
		HttpResponse httpResponse = null;
		// 创建HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 超时限制
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		try {
			// 设置HTTP POST请求参数必须用NameValuePair对象
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 使用execute方法发送HTTP POST请求，并返回HttpResponse对象
			httpResponse = new DefaultHttpClient(httpParameters)
					.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				try {
					return EntityUtils.toString(httpResponse.getEntity());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (ConnectTimeoutException e) {
			return ShareData.REQUES_TIMEOUT;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
