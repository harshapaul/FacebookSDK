package com.lendingtree.networkutils;

import java.io.IOException;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.http.HttpStatus.Series;
import org.springframework.web.client.ResourceAccessException;

import android.util.Log;

import com.lendingtree.model.ContactForm;
import com.lendingtree.model.PostResponse;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class LoanExplorerContactFormAdapter {

	private static final String TAG = "LoanExplorerContactFormAdapter";

	@Bean
	BfHttpClient connectionObj;
	
	public PostResponse callEmailFormRequestTask(ContactForm emailForm)
	{
		PostResponse postResponse = null;
		
		try 
		{
			ObjectMapper mapper = new ObjectMapper();
			String jsonResponse = mapper.writeValueAsString(emailForm);
			//jsonResponse = jsonResponse.replace("test", "isTest");
			Log.i("Json Post", jsonResponse);
			postResponse = (PostResponse) connectionObj.setClient2(Constants.EMAIL_FORM, null, jsonResponse);
		} 
		catch (ResourceAccessException e) 
		{
			// TODO: handle exception
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return postResponse;
	}
}
