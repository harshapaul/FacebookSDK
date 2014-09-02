package com.lendingtree.networkutils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.web.client.ResourceAccessException;

import android.util.Log;

import com.lendingtree.model.OfferContainer;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class LoanExplorerAdapter {

	private static final String TAG = "LoanExplorerAdapter";

	@Bean
	BfHttpClient connectionObj;

	public OfferContainer callRequestStatusRequestTask(
			String requestedLoanTypeId, String ipAddress,
			String propertyTypeId, String propertyZipCode,
			String veteranStatusTypeId, String propertyUseId,
			String requestedLoanProgramIds, String prepaymentPenaltyAccepted,
			String bankruptcyDischargedId, String foreclosureDischargedId,
			String secondLienMortgageBalance, String estimatedPurchasePrice,
			String estimatedDownPayment, String estimatedPropertyValue,
			String currentMortgageBalance,
			String estimatedCreditScoreBandId) {
		OfferContainer offerContainer = null;
		try {
			offerContainer = (OfferContainer) connectionObj.setClient2(
					Constants.REQUEST_STATUS, null, requestedLoanTypeId,
					ipAddress, propertyTypeId, propertyZipCode,
					veteranStatusTypeId, propertyUseId,
					requestedLoanProgramIds, prepaymentPenaltyAccepted,
					bankruptcyDischargedId, foreclosureDischargedId,
					secondLienMortgageBalance, estimatedPurchasePrice,
					estimatedDownPayment, estimatedPropertyValue,
					currentMortgageBalance,estimatedCreditScoreBandId);

		} catch (ResourceAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (offerContainer != null) {
			// /Log.d("Container",
			// offerContainer.getOffers().get(0).getOfferGuid());
		} else {
			Log.d("Null", "Null");
		}
		return offerContainer;
	}
	
	public OfferContainer callNegotiatorRequestTask(
			String requestedLoanTypeId, String ipAddress,
			String propertyTypeId, String propertyZipCode,
			String veteranStatusTypeId, String propertyUseId,
			String requestedLoanProgramIds, String prepaymentPenaltyAccepted,
			String bankruptcyDischargedId, String foreclosureDischargedId,
			String secondLienMortgageBalance, String estimatedPurchasePrice,
			String estimatedDownPayment, String estimatedPropertyValue,
			String currentMortgageBalance,
			String estimatedCreditScoreBandId,String currentMonthlyPayment,
			String currentMortgageInterestRatePercent) {
		OfferContainer offerContainer = null;
		try {
			offerContainer = (OfferContainer) connectionObj.setClient2(
					Constants.CONTAINER, null, requestedLoanTypeId,
					ipAddress, propertyTypeId, propertyZipCode,
					veteranStatusTypeId, propertyUseId,
					requestedLoanProgramIds, prepaymentPenaltyAccepted,
					bankruptcyDischargedId, foreclosureDischargedId,
					secondLienMortgageBalance, estimatedPurchasePrice,
					estimatedDownPayment, estimatedPropertyValue,
					currentMortgageBalance,estimatedCreditScoreBandId,currentMonthlyPayment,currentMortgageInterestRatePercent);

		} catch (ResourceAccessException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (offerContainer != null) {
			// /Log.d("Container",
			// offerContainer.getOffers().get(0).getOfferGuid());
		} else {
			Log.d("Null", "Null");
		}
		return offerContainer;
	}
}
