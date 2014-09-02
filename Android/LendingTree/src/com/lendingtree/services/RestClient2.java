package com.lendingtree.services;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.PostResponse;
import com.lendingtree.model.ReviewContainer;
import com.lendingtree.util.Constants;

//OLD API LOAN EXPLORER
/*@Rest(rootUrl = "https://smartoffers.lendingtree.com", converters={MappingJacksonHttpMessageConverter.class, FormHttpMessageConverter.class, StringHttpMessageConverter.class})
public interface RestClient2 {

	//for loan explorer
	@Get("/api/offers/v2/retrieveoffers?RequestedLoanTypeId={requestedLoanTypeId}&Esource=4801390&CustomerIPAddress={ipAddress}&PropertyTypeId={propertyTypeId}&PropertyZipCode={propertyZipCode}&EstimatedPropertyValue={estimatedPropertyValue}&CurrentMortgageBalance={currentMortgageBalance}&EstimatedCreditScore={estimatedCreditScore}&PropertyUseId=1&VeteranStatusTypeId=1&ForeclosureDischargedId=9&BankruptcyDischargedId=9&SecondLienMortgageBalance=0&RequestedLoanProgramIds=1,3,6&RequestedCashoutAmount=0&EstimatedPurchasePrice={estimatedPurchasePrice}&EstimatedDownPayment={estimatedDownPayment}&PageNumber=1&PageSize=10&ReturnByTimeoutMilliseconds=1000&SlimResponse=true&FirstOfferCountLimit=10&HideSimilarOffers=true&IgnoreCachedOffers=false&ShouldSortDescending=true&SortByOfferField=SortRelevanceScore")
	OfferContainer getRequestStatus(String requestedLoanTypeId, String ipAddress, String propertyTypeId, String propertyZipCode, String estimatedPropertyValue, String currentMortgageBalance, String estimatedCreditScore, String estimatedPurchasePrice, String estimatedDownPayment);
	
	//for loan explorer
	@Get("/api/offers/v2/retrieveoffersbyrequestid?requestId={requestId}&returnByTimeoutMilliseconds=1000")
	OfferContainer getOffers(String requestId);
	
	RestTemplate getRestTemplate();
}*/


//staging
//@Rest(rootUrl = "http://publishingpartners.staging.lendingtree.com", converters={MappingJacksonHttpMessageConverter.class, FormHttpMessageConverter.class, StringHttpMessageConverter.class	})

//NEW API LOAN EXPLORER
//production URL & Also change API_KEY in Constants
@Rest(rootUrl = "https://publishingpartners.lendingtree.com", converters={MappingJacksonHttpMessageConverter.class, FormHttpMessageConverter.class, StringHttpMessageConverter.class	})
public interface RestClient2 {

	//for loan explorer &ESourceId=4801390&PhoneNumberKey=14349
	@Get("/quotes/v1/lenderoffers?$expand=Links&$select=RatePercentage,APRPercentage,LoanProductName,LoanProgramId,RelevanceSortScore,TotalMonthlyPayment,TotalFees,Points,Name,"
			+ "AverageOverallRating,TotalRatingsAndReviews,OfferId,LenderId,RequestedLoanTypeId,ShowTelephoneNumber,IsFHALoan,TelephoneNumber,IsTelephoneLeadEnabled,NMLSID,Links&ApiKey="+Constants.API_KEY
			+ "&ESourceId="+Constants.ESOURCEID_LE+"&PhoneNumberKey="+Constants.PHONEKEY_LE+"&RequestedLoanTypeId={requestedLoanTypeId}&CustomerIpAddress={ipAddress}&PropertyTypeId={propertyTypeId}&PropertyZipCode={propertyZipCode}"
			+ "&VeteranStatusTypeId={veteranStatusTypeId}&PropertyUseId={propertyUseId}&RequestedLoanProgramIds={requestedLoanProgramIds}&PrepaymentPenaltyAccepted={prepaymentPenaltyAccepted}&BankruptcyDischargedId={bankruptcyDischargedId}"
			+ "&ForeclosureDischargedId={foreclosureDischargedId}&SecondLienMortgageBalance={secondLienMortgageBalance}&EstimatedPurchasePrice={estimatedPurchasePrice}&EstimatedDownPayment={estimatedDownPayment}&EstimatedPropertyValue={estimatedPropertyValue}&CurrentMortgageBalance={currentMortgageBalance}&EstimatedCreditScoreBandId={estimatedCreditScoreBandId}")
	OfferContainer getRequestStatus(String requestedLoanTypeId,
			String ipAddress, String propertyTypeId, String propertyZipCode,
			String veteranStatusTypeId, String propertyUseId,
			String requestedLoanProgramIds, String prepaymentPenaltyAccepted,
			String bankruptcyDischargedId, String foreclosureDischargedId,
			String secondLienMortgageBalance, String estimatedPurchasePrice,
			String estimatedDownPayment, String estimatedPropertyValue,
			String currentMortgageBalance, String estimatedCreditScoreBandId);

	// for loan explorer & mortgage negotiator subsequent call
	@Get("/quotes/v1/{quotesId}/lenderoffers?$expand=Links&$select=RatePercentage,FixedRatePeriodMonths,APRPercentage,LoanProductName,LoanProgramId,RelevanceSortScore,TotalMonthlyPayment,TotalFees,Points,Name,"
			+ "AverageOverallRating,TotalRatingsAndReviews,OfferId,LenderId,RequestedLoanTypeId,ShowTelephoneNumber,IsFHALoan,TelephoneNumber,IsTelephoneLeadEnabled,NMLSID,Links")
	OfferContainer getOffers(String quotesId);
	
	//for loan explorer Reviews
	@Get("/lenders/v1/{lenderId}/reviews?$select=CustomerServiceRating,ReviewTitle,ReviewText,UserLocation,UserNickname")
	ReviewContainer getReviews(String lenderId);
	
	//NEW API MORTGAGE NEGOTIATOR
	//for mortgage negotiator
	@Get("/quotes/v1/lenderoffers?$expand=Links&$select=RatePercentage,FixedRatePeriodMonths,APRPercentage,LoanProductName,LoanProgramId,RelevanceSortScore,TotalMonthlyPayment,TotalFees,Points,Name,"
			+ "AverageOverallRating,TotalRatingsAndReviews,OfferId,LenderId,RequestedLoanTypeId,ShowTelephoneNumber,TelephoneNumber,IsFHALoan,IsTelephoneLeadEnabled,NMLSID,Links&ApiKey="+Constants.API_KEY
			+ "&ESourceId="+Constants.ESOURCEID_MN+"&PhoneNumberKey="+Constants.PHONEKEY_MN+"&RequestedLoanTypeId={requestedLoanTypeId}&CustomerIpAddress={ipAddress}&PropertyTypeId={propertyTypeId}&PropertyZipCode={propertyZipCode}"
			+ "&VeteranStatusTypeId={veteranStatusTypeId}&PropertyUseId={propertyUseId}&RequestedLoanProgramIds={requestedLoanProgramIds}&PrepaymentPenaltyAccepted={prepaymentPenaltyAccepted}&BankruptcyDischargedId={bankruptcyDischargedId}"
			+ "&ForeclosureDischargedId={foreclosureDischargedId}&SecondLienMortgageBalance={secondLienMortgageBalance}&EstimatedPurchasePrice={estimatedPurchasePrice}&EstimatedDownPayment={estimatedDownPayment}&EstimatedPropertyValue={estimatedPropertyValue}&CurrentMortgageBalance={currentMortgageBalance}&EstimatedCreditScoreBandId={estimatedCreditScoreBandId}&CurrentMonthlyPayment={currentMonthlyPayment}&CurrentMortgageInterestRatePercent={currentMortgageInterestRatePercent}")
	OfferContainer getNegotiatorOffersData(String requestedLoanTypeId,
			String ipAddress, String propertyTypeId, String propertyZipCode,
			String veteranStatusTypeId, String propertyUseId,
			String requestedLoanProgramIds, String prepaymentPenaltyAccepted,
			String bankruptcyDischargedId, String foreclosureDischargedId,
			String secondLienMortgageBalance, String estimatedPurchasePrice,
			String estimatedDownPayment, String estimatedPropertyValue,
			String currentMortgageBalance, String estimatedCreditScoreBandId,
			String currentMonthlyPayment,
			String currentMortgageInterestRatePercent);
	
	//for email response NEW API
	@Post("/DataLeads/v1")
	@RequiresHeader("Content-Type")
	PostResponse getEmailResponse(String emailData);
	
	void setHeader(String name, String value);
	  
	String getHeader(String name);

	RestTemplate getRestTemplate();
}
