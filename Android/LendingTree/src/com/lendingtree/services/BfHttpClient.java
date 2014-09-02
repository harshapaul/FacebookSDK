package com.lendingtree.services;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.util.MultiValueMap;

import android.util.Log;

import com.lendingtree.application.LendingTreeApplication;
import com.lendingtree.util.CheckNetwork;
import com.lendingtree.util.Constants;

/**
 * @author harshapaul.pinto This is the implementation class of HTTPClient.
 *         Calls specific to requests are made from this class.
 */
@EBean
public class BfHttpClient extends HttpRequestClient {

	private static final String TAG = "BfHttpClient";

	@Bean
	CheckNetwork checkNetworkBean;

	@App
	LendingTreeApplication application;

	/**
	 * Check if the super method is successful and make calls for the request
	 * specified in the param.
	 * 
	 * @Param The request id from the constants file to represent a specific
	 *        request.
	 * @Return The model class of the specific request calls. We dont know which
	 *         request is called and hence which model will be returned. Hence
	 *         Object is returned as a generic type.
	 * */
	@Override
	public Object setClient(int iRequestType, MultiValueMap<String, Object> map, String... params) {
		Object data = null;
		Log.i(TAG, "inside setClient");
		if (super.setClient(iRequestType, map) != null) {
			if (checkNetworkBean.checkInternetConnection()) {
				application.checkForNetwork = 0;
				if (iRequestType == Constants.CLIENT_IP_ADDRESS) {
					Log.i(TAG,
							"inside if(iRequestType==Constants.CLIENT_IP_ADDRESS)");

					data = restClient.getClientAddress();

					if (data == null) {
						Log.i(TAG, "data = null");
					}
				} 
				else if (iRequestType == Constants.POSTAL_ADRESS) 
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.POSTAL_ADRESS)");
					data = restClient.getPostalAddress(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				/*else if(iRequestType == Constants.CONTAINER)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.CONTAINER)");
					data = restClient.getMortgageSearchRequestId(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}*/
				/*else if(iRequestType == Constants.EMAIL_FORM)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.EMAIL_FORM)");
					restClient.setHeader("Content-Type", "application/json");
					data = restClient.getEmailResponse(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}*/
				/*else if (iRequestType == Constants.CONTACT_FORM) 
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.CONTACT_FORM)");
					restClient.setHeader("Content-Type", "application/x-www-form-urlencoded");
					restClient.getContactForm(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}*/
					
			} 
			else 
			{

				Log.i(TAG, "NetworkError");
				application.checkForNetwork = 1;

			}

		}
		else {
			Log.i(TAG, "Error");
		}
		return data;
	}

	@Override
	public Object setClient2(int iRequestType,
			MultiValueMap<String, Object> map, String... params) {
		// TODO Auto-generated method stub
		Object data = null;
		Log.i(TAG, "inside setClient");
		if(super.setClient2(iRequestType, map) != null)
		{
			if (checkNetworkBean.checkInternetConnection()) {
				application.checkForNetwork = 0;
				if(iRequestType == Constants.REQUEST_STATUS)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.REQUEST_STATUS)");
					data = restClient2.getRequestStatus(params[0], params[1], params[Constants.TWO], params[Constants.THREE], params[Constants.FOUR], params[Constants.FIVE], params[Constants.SIX], params[Constants.SEVEN], params[Constants.EIGHT], params[Constants.NINE], params[Constants.TEN], params[Constants.ELEVEN], params[Constants.TWELVE], params[Constants.THIRTEEN], params[Constants.FOURTEEN], params[Constants.FIFTEEN]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				else if(iRequestType == Constants.LOAN_OFFERS)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.LOAN_OFFERS)");
					data = restClient2.getOffers(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				
				else if(iRequestType == Constants.LOAN_REVIEWS)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.LOAN_REVIEWS)");
					data = restClient2.getReviews(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				else if(iRequestType == Constants.CONTAINER)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.CONTAINER)");
					data = restClient2.getNegotiatorOffersData(params[0], params[1], params[Constants.TWO], params[Constants.THREE], params[Constants.FOUR], params[Constants.FIVE], params[Constants.SIX], params[Constants.SEVEN], params[Constants.EIGHT], params[Constants.NINE], params[Constants.TEN], params[Constants.ELEVEN], params[Constants.TWELVE], params[Constants.THIRTEEN], params[Constants.FOURTEEN], params[Constants.FIFTEEN], params[Constants.SIXTEEN], params[Constants.SEVENTEEN]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				
				else if(iRequestType == Constants.EMAIL_FORM)
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.EMAIL_FORM)");
					restClient2.setHeader("Content-Type", "application/json");
					data = restClient2.getEmailResponse(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
				else if (iRequestType == Constants.CONTACT_FORM) 
				{
					Log.i(TAG,
							"inside if(iRequestType==Constants.CONTACT_FORM)");
					restClient2.setHeader("Content-Type", "text/xml");
					restClient2.getEmailResponse(params[0]);
					if (data == null) {
						Log.i(TAG, "data = null");
					}
				}
			}
			else
			{
				Log.i(TAG, "NetworkError");
				application.checkForNetwork = 1;
			}
		}
		else {
			Log.i(TAG, "Error");
		}
		return data;
	}

}
