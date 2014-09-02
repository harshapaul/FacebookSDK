package com.lendingtree.services;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.lendingtree.model.PostResponse;
import com.lendingtree.model.PostalAddress;

@Rest(rootUrl = "https://www.lendingtree.com", converters={StringHttpMessageConverter.class, MappingJacksonHttpMessageConverter.class, FormHttpMessageConverter.class})
public interface RestClient {
	//for mortgage Negotiator OLD API
	/*@Get("/publishing-partner-api/getliveleads?requestJson={url}")
	Container getMortgageSearchRequestId(String url);*/
	
	//for loan explorer ip address of client
	@Get("/loan-explorer/ip")
	String getClientAddress();
	
	//for fetching the zip code status
	@Get("/geo/zip?postalcode={postalCode}")
	PostalAddress getPostalAddress(String postalCode);
	
	//for loan explorer OLD API
	/*@Post("/loan-explorer/postdatalead")
	@RequiresHeader("Content-Type")
	PostResponse getEmailResponse(String emailData);*/
	
	//for mortgage Negotiator
	/*@Post("/publishing-partner-api/submitdatalead")
	@RequiresHeader("Content-Type")
	void getContactForm(String data);*/
	
	
	void setHeader(String name, String value);
	  
	String getHeader(String name);
	
	RestTemplate getRestTemplate();
}
