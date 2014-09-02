package com.lendingtree.networkutils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.web.client.ResourceAccessException;

import com.lendingtree.model.PostalAddress;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class PostalAddressAdapter {

private static final String TAG = "PostalAddressAdapter";
	
	@Bean
	BfHttpClient connectionObj;

	public PostalAddress callPostalAddressRequestTask(String postalCode)
	{
		PostalAddress postalAddress = null;
		
		try 
		{
			postalAddress = (PostalAddress) connectionObj.setClient(Constants.POSTAL_ADRESS, null, postalCode);
		} 
		catch (ResourceAccessException e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return postalAddress;
	}
}
