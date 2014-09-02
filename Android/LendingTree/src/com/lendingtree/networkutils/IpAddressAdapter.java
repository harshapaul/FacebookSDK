package com.lendingtree.networkutils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.web.client.ResourceAccessException;

import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class IpAddressAdapter {

	private static final String TAG = "IpAddressAdapter";
	
	@Bean
	BfHttpClient connectionObj;
	
	public String callAddressRequestTask()
	{
		String response = null;
		//CustomerAddress address = null;
		try
		{
			response = (String) connectionObj.setClient(Constants.CLIENT_IP_ADDRESS, null);
			response = response.replaceAll("[();]", "");

						/*JSONObject obj = new JSONObject(response);
						address = new CustomerAddress(obj.getString("AreaCode"), obj.getString("City"), obj.getString("CountryCode"), obj.getString("CountryName"), obj.getString("DMACode"), obj.getString("Latitude"), obj.getString("Longitude"), obj.getString("MetroCode"), obj.getString("PostalCode"), obj.getString("Region"), obj.getString("RegionName"), obj.getString("ipAddress"));
						Log.i(TAG, address.getAreaCode());
						Log.i(TAG, address.getCity());
						Log.i(TAG, address.getCountryCode());
						Log.i(TAG, address.getCountryName());
						Log.i(TAG, address.getDmaCode());
						Log.i(TAG, address.getLatitude());
						Log.i(TAG, address.getLongitude());
						Log.i(TAG, address.getMetroCode());
						Log.i(TAG, address.getPostalCode());
						Log.i(TAG, address.getRegion());
						Log.i(TAG, address.getRegionName());
						Log.i(TAG, address.getIpAddress());*/
		}
		catch(ResourceAccessException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return response;
	}
	
}
