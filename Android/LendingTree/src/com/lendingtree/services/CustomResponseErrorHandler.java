package com.lendingtree.services;

import java.io.IOException;

import org.androidannotations.annotations.EBean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

@EBean
public class CustomResponseErrorHandler implements ResponseErrorHandler {

	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response); }


	public void handleError(ClientHttpResponse response) throws IOException {
		
//		Integer StatusCode = Integer.parseInt(response.getStatusCode().toString());
//		Log.d("Status code",response.getStatusCode().toString());
		
//		Integer StatusCode = Integer.parseInt(response.getStatusCode().toString());
//		Log.d("Status code",""+StatusCode+application.isIsputordeleterequest());
//		if(application != null && application.isIsputordeleterequest() && StatusCode != 200)
//		{
//			Log.d("Status code",""+StatusCode);
//			application.setErrorResponse(true);
//			application.setIsputordeleterequest(false);
//			
//		}
		
		
				/*String theString = IOUtils.toString(response.getBody());
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("code", response.getStatusCode().toString());
		properties.put("body", theString);
		properties.put("header", response.getHeaders());*/
		 		//CustomException customException = new CustomException(properties);
		/*Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("code", response.getStatusCode().toString());
		properties.put("body", theString);
		properties.put("header", response.getHeaders());
		customException.setProperties(properties);*/
		//customException.setProperties(properties)



	}
}


 