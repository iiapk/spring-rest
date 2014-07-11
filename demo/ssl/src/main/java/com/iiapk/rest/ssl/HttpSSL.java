package com.iiapk.rest.ssl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpSSL {

	public void getHttpRequest() {
		URL myURL;
		try {
			myURL = new URL("https://mybank.nbcb.com.cn/payment/merCheck.do");
			URLConnection httpsConn = myURL.openConnection();
			InputStreamReader insr = new InputStreamReader(
					httpsConn.getInputStream());
			// 读取服务器的响应内容并显示
			int respInt = insr.read();
			while (respInt != -1) {
				System.out.print((char) respInt);
				respInt = insr.read();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getSSLHttpRequest() {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			// 创建URL对象
			URL myURL = new URL("https://localhost:8443/hello.jsp?test=qweqwrt");
			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
			httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			httpsConn.setSSLSocketFactory(ssf);
			// 取得该连接的输入流，以读取响应内容
			InputStreamReader insr = new InputStreamReader(
					httpsConn.getInputStream());
			// 读取服务器的响应内容并显示
			int respInt = insr.read();
			while (respInt != -1) {
				System.out.print((char) respInt);
				respInt = insr.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// fixed for:java.io.IOException: HTTPS hostname wrong: should be <localhost>
	public class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	public static void main(String[] args) {
		new HttpSSL().getHttpRequest();
		//new HttpSSL().getSSLHttpRequest();
	}

}