package org.netarchivesuite.bitrepositoryrestclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientWrapper {

	protected HttpClient httpClient;

	protected String baseUrl;

	protected HttpClientWrapper() {
	}

	public static HttpClientWrapper getInstance(String hostname, int port, String userName, String password) {
		HttpClientWrapper hcw = new HttpClientWrapper();
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		if (userName != null && userName.length() > 0 && password != null) {
			credsProvider.setCredentials(new AuthScope(hostname, port), new UsernamePasswordCredentials(userName, password));
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		//httpClientBuilder.setSSLSocketFactory(sslSocketFactory).setHostnameVerifier(hostnameVerifier);
		hcw.httpClient = httpClientBuilder.setDefaultCredentialsProvider(credsProvider).build();
		hcw.baseUrl = "http://" + hostname + ":" + Integer.toString(port);
		return hcw;
	}

	public static HttpClientWrapper getInstance(String hostname, int port, File keystoreFile, String keyStorePassword, String userName, String password) {
		HttpClientWrapper hcw = new HttpClientWrapper();
		InputStream instream = null;
		KeyManager[] keyManagers = null;
		try {
			if (keystoreFile != null) {
				if (keyStorePassword == null) {
					keyStorePassword = "";
				}
				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				instream = new FileInputStream(keystoreFile);
				ks.load(instream, keyStorePassword.toCharArray());

				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(ks, keyStorePassword.toCharArray());
				keyManagers = kmf.getKeyManagers();
			}

			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(keyManagers, new TrustManager[] {tm}, null);

			X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			//SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
			//socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			//Scheme sch = new Scheme("https", socketFactory, port);

			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			if (userName != null && userName.length() > 0 && password != null) {
				credsProvider.setCredentials(new AuthScope(hostname, port), new UsernamePasswordCredentials(userName, password));
			}

			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			//httpClientBuilder.setSSLSocketFactory(sslSocketFactory).setHostnameVerifier(hostnameVerifier);
			httpClientBuilder.setSslcontext(sslcontext);
			httpClientBuilder.setHostnameVerifier(hostnameVerifier);
			hcw.httpClient = httpClientBuilder.setDefaultCredentialsProvider(credsProvider).build();
			//hcw.httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			hcw.baseUrl = "https://" + hostname + ":" + Integer.toString(port);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (KeyStoreException e) {
			e.printStackTrace();
		}
		catch (KeyManagementException e) {
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (CertificateException e) {
			e.printStackTrace();
		}
		catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		finally {
			if (instream != null) {
				try {
					instream.close();
				}
				catch (IOException e) {
				}
			}
		}
		return hcw;
	}

	public HttpResult get(String resource) {
		HttpGet request = new HttpGet(baseUrl + resource);
		HttpResult httpResult = new HttpResult();
		try {
			HttpResponse response = httpClient.execute(request);
			if (response != null) {
				httpResult.responseCode = response.getStatusLine().getStatusCode();
				HttpEntity responseEntity = response.getEntity();
				long contentLength = responseEntity.getContentLength();
				if (contentLength < 0) {
					contentLength = 0;
				}
				ByteArrayOutputStream bOut = new ByteArrayOutputStream((int) contentLength);
				InputStream in = responseEntity.getContent();
				int read;
				byte[] tmpBuf = new byte[8192];
				while ((read = in.read(tmpBuf)) != -1) {
					bOut.write(tmpBuf, 0, read);
				}
				in.close();
				bOut.close();
				httpResult.response = bOut.toByteArray();
				switch (httpResult.responseCode) {
				case 200:
					httpResult.status = HttpResultStatus.OK;
					break;
				case 404:
					httpResult.status = HttpResultStatus.NOT_FOUND;
					break;
				case 500:
					httpResult.status = HttpResultStatus.INTERNAL_ERROR;
					break;
				default:
					httpResult.status = HttpResultStatus.NO_RESPONSE;
					break;
				}
			} else {
				httpResult.status = HttpResultStatus.NO_RESPONSE;
			}
		}
		catch (NoHttpResponseException e) {
			httpResult.status = HttpResultStatus.OFFLINE;
			httpResult.t = e;
		}
		catch (ClientProtocolException e) {
			httpResult.status = HttpResultStatus.RESPONSE_EXCEPTION;
			httpResult.t = e;
		}
		catch (IOException e) {
			httpResult.status = HttpResultStatus.RESPONSE_EXCEPTION;
			httpResult.t = e;
		}
		catch (Exception e) {
			httpResult.status = HttpResultStatus.EXCEPTION;
			httpResult.t = e;
		}
		catch (Throwable t) {
			httpResult.status = HttpResultStatus.THROWABLE;
			httpResult.t = t;
		}
		return httpResult;
	}

}
