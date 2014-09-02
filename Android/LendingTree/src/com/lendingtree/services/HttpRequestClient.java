package com.lendingtree.services;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.apache.http.client.HttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.lendingtree.util.Constants;
import com.lendingtree.util.HttpUtils;

@EBean
public class HttpRequestClient {

	private static final String TAG = "HttpClient";

	@RestService
	RestClient restClient;
	
	@RestService
	RestClient2 restClient2;
	
	@Bean
	CustomResponseErrorHandler Errhandler;

	public Object setClient(int iRequestType, MultiValueMap<String, Object> map, String... params)
	{

		HttpClient httpClient = HttpUtils.getNewHttpClient();
		RestTemplate restTemplate = restClient.getRestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		//restClient.getRestTemplate().setRequestFactory(requestFactory)
		//CustomResponseErrorHandler Errhandler = new CustomResponseErrorHandler();
		restTemplate.setErrorHandler(Errhandler);

		// server not responding added the connection Time out.
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		if (factory instanceof HttpComponentsClientHttpRequestFactory) {
			HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
			advancedFactory.setConnectTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
			advancedFactory.setReadTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
		} else if (factory instanceof SimpleClientHttpRequestFactory) {
			SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
			advancedFactory.setConnectTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
			advancedFactory.setReadTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
		}
		Log.i(TAG,"end of setClient()");

		return httpClient;

	}
	
	public Object setClient2(int iRequestType, MultiValueMap<String, Object> map, String... params)
	{

		HttpClient httpClient = HttpUtils.getNewHttpClient();
		RestTemplate restTemplate = restClient2.getRestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		//restClient.getRestTemplate().setRequestFactory(requestFactory)
		//CustomResponseErrorHandler Errhandler = new CustomResponseErrorHandler();
		restTemplate.setErrorHandler(Errhandler);

		// server not responding added the connection Time out.
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		if (factory instanceof HttpComponentsClientHttpRequestFactory) {
			HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
			advancedFactory.setConnectTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
			advancedFactory.setReadTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
		} else if (factory instanceof SimpleClientHttpRequestFactory) {
			SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
			advancedFactory.setConnectTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
			advancedFactory.setReadTimeout(Constants.ONE_TWO_ZERO_ZERO_ZERO_ZERO);
		}
		Log.i(TAG,"end of setClient()");

		return httpClient;

	}
}
