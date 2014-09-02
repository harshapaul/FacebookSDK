package com.lendingtree.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("Offers")
public class Offers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "IsTelephoneLeadEnabled")
	private boolean IsTelephoneLeadEnabled;
	
	@JsonProperty(value = "TelephoneNumber")
	private String TelephoneNumber;
	
	@JsonProperty(value = "IsFHALoan")
	private boolean IsFHALoan;
	
	@JsonProperty(value = "ShowTelephoneNumber")
	private boolean ShowTelephoneNumber;
	
	@JsonProperty(value = "RequestedLoanTypeId")
	private int RequestedLoanTypeId;
	
	@JsonProperty(value = "LenderId")
	private int LenderId;
	
	@JsonProperty(value = "OfferId")
	private String OfferId;
	
	@JsonProperty(value = "TotalRatingsAndReviews")
	private int TotalRatingsAndReviews;
	
	@JsonProperty(value = "AverageOverallRating")
	private float AverageOverallRating;
	
	@JsonProperty(value = "Name")
	private String Name;
	
	@JsonProperty(value = "Points")
	private float Points;
	
	@JsonProperty(value = "TotalFees")
	private int TotalFees;
	
	@JsonProperty(value= "TotalMonthlyPayment")
	private int TotalMonthlyPayment;
	
	@JsonProperty(value = "RelevanceSortScore")
	private int RelevanceSortScore;
	
	@JsonProperty(value = "LoanProgramId")
	private int LoanProgramId;
	
	@JsonProperty(value = "LoanProductName")
	private String LoanProductName;
	
	@JsonProperty(value = "APRPercentage")
	private double APRPercentage;
	
	@JsonProperty(value = "RatePercentage")
	private double RatePercentage;
	
	@JsonProperty(value = "FixedRatePeriodMonths")
	private int FixedRatePeriodMonths;
	
	@JsonProperty(value = "NMLSID")
	private String NMLSID;
	
	@JsonProperty(value = "Links")
	private ArrayList<Links> links;
	
	public int getFixedRatePeriodMonths() {
		return FixedRatePeriodMonths;
	}

	public void setFixedRatePeriodMonths(int fixedRatePeriodMonths) {
		FixedRatePeriodMonths = fixedRatePeriodMonths;
	}


	public boolean isIsTelephoneLeadEnabled() {
		return IsTelephoneLeadEnabled;
	}

	public void setIsTelephoneLeadEnabled(boolean isTelephoneLeadEnabled) {
		IsTelephoneLeadEnabled = isTelephoneLeadEnabled;
	}

	public String getTelephoneNumber() {
		return TelephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		TelephoneNumber = telephoneNumber;
	}

	public boolean isShowTelephoneNumber() {
		return ShowTelephoneNumber;
	}

	public void setShowTelephoneNumber(boolean showTelephoneNumber) {
		ShowTelephoneNumber = showTelephoneNumber;
	}

	public int getRequestedLoanTypeId() {
		return RequestedLoanTypeId;
	}

	public void setRequestedLoanTypeId(int requestedLoanTypeId) {
		RequestedLoanTypeId = requestedLoanTypeId;
	}

	public int getLenderId() {
		return LenderId;
	}

	public void setLenderId(int lenderId) {
		LenderId = lenderId;
	}

	public String getOfferId() {
		return OfferId;
	}

	public void setOfferId(String offerId) {
		OfferId = offerId;
	}

	public int getTotalRatingsAndReviews() {
		return TotalRatingsAndReviews;
	}

	public void setTotalRatingsAndReviews(int totalRatingsAndReviews) {
		TotalRatingsAndReviews = totalRatingsAndReviews;
	}

	public float getAverageOverallRating() {
		return AverageOverallRating;
	}

	public void setAverageOverallRating(float averageOverallRating) {
		AverageOverallRating = averageOverallRating;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public float getPoints() {
		return Points;
	}

	public void setPoints(float points) {
		Points = points;
	}

	public int getTotalFees() {
		return TotalFees;
	}

	public void setTotalFees(int totalFees) {
		TotalFees = totalFees;
	}

	public int getTotalMonthlyPayment() {
		return TotalMonthlyPayment;
	}

	public void setTotalMonthlyPayment(int totalMonthlyPayment) {
		TotalMonthlyPayment = totalMonthlyPayment;
	}

	public int getRelevanceSortScore() {
		return RelevanceSortScore;
	}

	public void setRelevanceSortScore(int relevanceSortScore) {
		RelevanceSortScore = relevanceSortScore;
	}

	public int getLoanProgramId() {
		return LoanProgramId;
	}

	public void setLoanProgramId(int loanProgramId) {
		LoanProgramId = loanProgramId;
	}

	public String getLoanProductName() {
		return LoanProductName;
	}

	public void setLoanProductName(String loanProductName) {
		LoanProductName = loanProductName;
	}

	public double getAPRPercentage() {
		return APRPercentage;
	}

	public void setAPRPercentage(double aPRPercentage) {
		APRPercentage = aPRPercentage;
	}

	public double getRatePercentage() {
		return RatePercentage;
	}

	public void setRatePercentage(double ratePercentage) {
		RatePercentage = ratePercentage;
	}

	public ArrayList<Links> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Links> links) {
		this.links = links;
	}

	public boolean isIsFHALoan() {
		return IsFHALoan;
	}

	public void setIsFHALoan(boolean isFHALoan) {
		IsFHALoan = isFHALoan;
	}

	public String getNMLSID() {
		return NMLSID;
	}

	public void setNMLSID(String nMLSID) {
		NMLSID = nMLSID;
	}

	
	
}
