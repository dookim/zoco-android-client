package com.zoco.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class ZocoNetwork {

	public enum Method {
		GET, POST
	}

	private String url;
	private String data;
	private Method method;

    public final static String SERVER_URL_4_WRITE = "http://14.49.36.193:55555/zoco/client/";
    public final static String SERVER_URL_4_READ = "http://14.49.36.193:33333/zoco/client/";
    public final static String SUFFIX_4_REGISTER_BOOK = "register_book";
    public final static String SUFFIX_4_LOGIN = "login";
    public final static String SUFFIX_4_QUERY_BOOK = "query_book/?query=";
    public final static String URL_4_REGISTER_BOOK = SERVER_URL_4_WRITE + SUFFIX_4_REGISTER_BOOK;
    public final static String URL_4_LOGIN = SERVER_URL_4_WRITE + SUFFIX_4_LOGIN;
    public final static String URL_4_QUERY_BOOK = SERVER_URL_4_READ + SUFFIX_4_QUERY_BOOK;

	public ZocoNetwork setPostOption(String url, String data) {
		setNetworkOption(url, data, Method.POST);
		return this;
	}

	public ZocoNetwork setGetOption(String url) {
		setNetworkOption(url, null, Method.GET);
		return this;
	}

	private void setNetworkOption(String url, String data, Method method) {
		this.url = url;
		this.data = data;
		this.method = method;
	}

	public  String execute() throws IllegalStateException, IOException {
		String returnValue = null;
		if (method.equals(Method.GET)) {
			returnValue = sendGetMethod();
		} else if (method.equals(Method.POST)) {
			returnValue = sendPostMethod();
		}
		return returnValue;
	}

	private String sendGetMethod() throws IllegalStateException, IOException {

		HttpClient client = new DefaultHttpClient();
		setHttpParams(client.getParams());

		HttpGet get = new HttpGet(url);
		HttpResponse responseGet = client.execute(get);
		return convertResponseToString(responseGet);

	}

	private String sendPostMethod() throws IllegalStateException, IOException {

		HttpClient client = new DefaultHttpClient();
		setHttpParams(client.getParams());

		HttpPost post = new HttpPost(url);

		post.setHeader("Content-Type", "application/json; charset=utf-8");
		post.addHeader("version", "1.0.0");

		StringEntity input = new StringEntity(data, "UTF-8");
		post.setEntity(input);
		HttpResponse responsePOST = client.execute(post);
		return convertResponseToString(responsePOST);

	}

	private String convertResponseToString(HttpResponse response)
			throws IllegalStateException, IOException {

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private void setHttpParams(HttpParams params) {
		params.setParameter("http.protocol.expect-continue", false);
		params.setParameter("http.connection.timeout", 5000);
		params.setParameter("http.socket.timeout", 5000);
	}

}
