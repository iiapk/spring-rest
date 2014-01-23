package com.iiapk.rest.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("rawtypes")
public class WebHttpClient {
	
	private final static int SOCKETTIMEOUT = 5000;
	private final static int CONNECTTIMEOUT = 5000;

	public static String postRequest(String url,Map<String, String> data) {
		return postRequest(url,data,HttpEncoding.UTF8);
	}

	public static String postRequest(String url,String data) {
		return postRequest(url,data,HttpEncoding.UTF8);
	}

	public static String postRequest(String url,String data,
			HttpEncoding encoding) {
		HttpPost httppost = new HttpPost(url);
		//设置请求和响应超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				SOCKETTIMEOUT).setConnectTimeout(CONNECTTIMEOUT).build();
		httppost.setConfig(requestConfig);
		StringEntity reqEntity;
		try {
			reqEntity = new StringEntity(data);
			httppost.setEntity(reqEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return postRequest(httppost);
	}

	public static String postRequest(String url,List<NameValuePair> nvps, 
			HttpEncoding encoding) {
		HttpPost httppost = new HttpPost(url);
		//设置请求和响应超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				SOCKETTIMEOUT).setConnectTimeout(CONNECTTIMEOUT).build();
		httppost.setConfig(requestConfig);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nvps, encoding.getCode()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return postRequest(httppost);
	}

	public static String postRequest(String url,Map<String, String> data, 
			HttpEncoding encoding) {
		return postRequest(url,convertMapToNameValuePair(data),encoding);
	}

	public static String postRequest(HttpPost httppost) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
		        long len = entity.getContentLength();
		        if (len != -1 && len < 2048) {
		        	body = EntityUtils.toString(entity);
		        } else {
		            // Stream content out
		        }
		    }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	public static String getRequest(String url,List<NameValuePair> nvps) throws Exception {
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		return getRequest(url,str);
	}
	
	public static String getRequest(String url,Map<String, String> data) throws Exception {
		return getRequest(url,convertMapToNameValuePair(data));
	}
	
	public static String getRequest(String url,String data) throws Exception {
		HttpGet httpget = new HttpGet(url);
		//设置请求和响应超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				SOCKETTIMEOUT).setConnectTimeout(CONNECTTIMEOUT).build();
		httpget.setConfig(requestConfig);
		httpget.setURI(new URI(httpget.getURI().toString() + "?" + data));
		return getRequest(httpget);
	}
	
	public static String getRequest(HttpGet httpget) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				body = EntityUtils.toString(httpEntity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}
	
	public static List<NameValuePair> convertMapToNameValuePair(
			Map<String, String> data) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			NameValuePair nvp = new BasicNameValuePair(key, value);
			nvps.add(nvp);
		}
		return nvps;
	}

}
