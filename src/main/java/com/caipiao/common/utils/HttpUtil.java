package com.caipiao.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class HttpUtil {
	/**
	 * 模拟chrome发送请求
	 */
	static public String getUrlByChrome(String urlString, String charsetCode) {
		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			//log.info("http tools: 非法请求(" + urlString + ")");
			return "";
		}
		String charset = charsetCode;
		try {
			url = new URL(urlString);
			connection = getProxyConnection(urlString);

			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			//connection.setRequestProperty("Cache-Control:", "max-age=0");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			connection.setRequestProperty("Referer", urlString);
			
			String encoding = "";
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection http = (HttpURLConnection) connection;
				encoding = http.getContentEncoding();

			}

			
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]|\\\"", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					//log.info(e.getMessage() + "");
				}
			}
			if (("gzip").equals(encoding)) {
				in = new GZIPInputStream(connection.getInputStream());
			} else {
				in = connection.getInputStream();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}

	
	static private URLConnection getProxyConnection(String urlString) throws IOException {

		URLConnection connection = null;
		URL url = new URL(urlString);

		CookieManager manager = new CookieManager();
		CookieHandler.setDefault(manager);

		// 匹配url为500的 就用代理
		Pattern pattern500 = Pattern.compile("\\.(500|500wan)\\.");
		Matcher matcher500 = pattern500.matcher(urlString);

		// 匹配url为ydniu的 就用代理
		Pattern patternYdniu = Pattern.compile("\\.(ydniu)\\.");
		Matcher matcherYdniu = patternYdniu.matcher(urlString);

		// 匹配url为竞彩官网sporttery.cn的 就用代理
		Pattern patternSporttery = Pattern.compile("\\.(sporttery)\\.");
		Matcher matcherSporttery = patternSporttery.matcher(urlString);

		// 匹配url为kai168的 就用代理
		Pattern patternKai168 = Pattern.compile("\\.(api68)\\.");
		Matcher matcherKai168 = patternKai168.matcher(urlString);

		// 匹配url为icaile的 就用代理
		Pattern patternIcaile = Pattern.compile("\\.(icaile)\\.");
		Matcher matcherIcaile = patternIcaile.matcher(urlString);

		if (matcher500.find()) {
			SocketAddress addr = new InetSocketAddress("117.177.250.152", 80);// 代理地址
			Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
			connection = url.openConnection(typeProxy);
		} else if (matcherYdniu.find()) {
			SocketAddress addr = new InetSocketAddress("14.153.53.139", 3128);// 代理地址
			Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
			connection = url.openConnection(typeProxy);
		} else if (matcherSporttery.find()) {
			SocketAddress addr = new InetSocketAddress("58.216.202.149", 8118);// 代理地址
			Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
			connection = url.openConnection(typeProxy);
		} else if (matcherKai168.find()) {
			SocketAddress addr = new InetSocketAddress("60.205.125.201", 8888);// 代理地址
			Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
			connection = url.openConnection(typeProxy);
		} else if (matcherIcaile.find()) {
			SocketAddress addr = new InetSocketAddress("221.231.109.40", 3128);// 代理地址
			Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
			connection = url.openConnection(typeProxy);
		} else {
			connection = url.openConnection();
		}
		return connection;
	}
	
	public static void main(String[] args) {
		String content = getUrlByChrome("http://info.sporttery.cn/football/hhad_list.php","utf-8");
		System.out.println(content);
	}

}
