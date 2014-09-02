package com.lendingtree.networkutils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import android.util.Log;

import com.lendingtree.model.ReviewContainer;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class LoanExplorerReviewAdapter {

private static final String TAG = "LoanExplorerReviewAdapter";
	
	@Bean
	BfHttpClient connectionObj;

	public ReviewContainer callLoanOffersReview(String lenderId)
	{
		ReviewContainer reviewContainer = null;
		
		try 
		{
			reviewContainer = (ReviewContainer) connectionObj.setClient2(Constants.LOAN_REVIEWS, null, lenderId);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		if(reviewContainer != null)
		{
			//Log.d("Container", offrerContainer.getOffers().get(0).getOfferGuid());
		}
		else
		{
			Log.d("Null", "Null");
		}
		return reviewContainer;
	}
}
