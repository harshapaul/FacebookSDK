package com.lendingtree.networkutils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.util.Log;

import com.lendingtree.model.OfferContainer;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class LoanExplorerOffersAdapter {

private static final String TAG = "LoanExplorerOffersAdapter";
	
	@Bean
	BfHttpClient connectionObj;

	public OfferContainer callLoanOffersRequestTask(String requestId)
	{
		OfferContainer offrerContainer = null;
		
		try 
		{
			offrerContainer = (OfferContainer) connectionObj.setClient2(Constants.LOAN_OFFERS, null, requestId);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		if(offrerContainer != null)
		{
			//Log.d("Container", offrerContainer.getOffers().get(0).getOfferGuid());
		}
		else
		{
			Log.d("Null", "Null");
		}
		return offrerContainer;
	}
}
