package com.lendingtree.model;


public class LoanExplorer {
	
	private String requestedLoanTypeId;
	private String propertyZipCode;
	private String propertyTypeId;
	
	//private String estimatedCreditScore;
	
	private String veteranStatusTypeId;
	private String propertyUseId;
	private String requestedLoanProgramIds;
	private String prepaymentPenaltyAccepted ;
	private String bankruptcyDischargedId ;
	private String foreclosureDischargedId ;
	private String secondLienMortgageBalance;
	private String estimatedPurchasePrice;
	private String estimatedDownPayment;
	private String estimatedPropertyValue;
	private String currentMortgageBalance;
	private String estimatedCreditScoreBandId;

	public LoanExplorer() {
		
	}

	public LoanExplorer(String requestedLoanTypeId, String propertyZipCode,
			String propertyTypeId, String veteranStatusTypeId,
			String propertyUseId, String requestedLoanProgramIds,
			String prepaymentPenaltyAccepted, String bankruptcyDischargedId,
			String foreclosureDischargedId, String secondLienMortgageBalance,
			String estimatedPurchasePrice, String estimatedDownPayment,
			String estimatedPropertyValue, String currentMortgageBalance,String estimatedCreditScoreBandId) {
		super();
		this.requestedLoanTypeId = requestedLoanTypeId;
		this.propertyZipCode = propertyZipCode;
		this.propertyTypeId = propertyTypeId;
		this.veteranStatusTypeId = veteranStatusTypeId;
		this.propertyUseId = propertyUseId;
		this.requestedLoanProgramIds = requestedLoanProgramIds;
		this.prepaymentPenaltyAccepted = prepaymentPenaltyAccepted;
		this.bankruptcyDischargedId = bankruptcyDischargedId;
		this.foreclosureDischargedId = foreclosureDischargedId;
		this.secondLienMortgageBalance = secondLienMortgageBalance;
		this.estimatedPurchasePrice = estimatedPurchasePrice;
		this.estimatedDownPayment = estimatedDownPayment;
		this.estimatedPropertyValue = estimatedPropertyValue;
		this.currentMortgageBalance = currentMortgageBalance;
		this.estimatedCreditScoreBandId = estimatedCreditScoreBandId;
	}



	public String getEstimatedCreditScoreBandId() {
		return estimatedCreditScoreBandId;
	}

	public void setEstimatedCreditScoreBandId(String estimatedCreditScoreBandId) {
		this.estimatedCreditScoreBandId = estimatedCreditScoreBandId;
	}

	public String getVeteranStatusTypeId() {
		return veteranStatusTypeId;
	}

	public void setVeteranStatusTypeId(String veteranStatusTypeId) {
		this.veteranStatusTypeId = veteranStatusTypeId;
	}

	public String getPropertyUseId() {
		return propertyUseId;
	}

	public void setPropertyUseId(String propertyUseId) {
		this.propertyUseId = propertyUseId;
	}

	public String getRequestedLoanProgramIds() {
		return requestedLoanProgramIds;
	}

	public void setRequestedLoanProgramIds(String requestedLoanProgramIds) {
		this.requestedLoanProgramIds = requestedLoanProgramIds;
	}

	public String getPrepaymentPenaltyAccepted() {
		return prepaymentPenaltyAccepted;
	}

	public void setPrepaymentPenaltyAccepted(String prepaymentPenaltyAccepted) {
		this.prepaymentPenaltyAccepted = prepaymentPenaltyAccepted;
	}

	public String getBankruptcyDischargedId() {
		return bankruptcyDischargedId;
	}

	public void setBankruptcyDischargedId(String bankruptcyDischargedId) {
		this.bankruptcyDischargedId = bankruptcyDischargedId;
	}

	public String getForeclosureDischargedId() {
		return foreclosureDischargedId;
	}

	public void setForeclosureDischargedId(String foreclosureDischargedId) {
		this.foreclosureDischargedId = foreclosureDischargedId;
	}

	public String getSecondLienMortgageBalance() {
		return secondLienMortgageBalance;
	}

	public void setSecondLienMortgageBalance(String secondLienMortgageBalance) {
		this.secondLienMortgageBalance = secondLienMortgageBalance;
	}

	public String getRequestedLoanTypeId() {
		return requestedLoanTypeId;
	}
	public void setRequestedLoanTypeId(String requestedLoanTypeId) {
		this.requestedLoanTypeId = requestedLoanTypeId;
	}
	public String getPropertyTypeId() {
		return propertyTypeId;
	}
	public void setPropertyTypeId(String propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
	public String getPropertyZipCode() {
		return propertyZipCode;
	}
	public void setPropertyZipCode(String propertyZipCode) {
		this.propertyZipCode = propertyZipCode;
	}
	/*public String getEstimatedCreditScore() {
		return estimatedCreditScore;
	}
	public void setEstimatedCreditScore(String estimatedCreditScore) {
		this.estimatedCreditScore = estimatedCreditScore;
	}*/
	public String getEstimatedPropertyValue() {
		return estimatedPropertyValue;
	}
	public void setEstimatedPropertyValue(String estimatedPropertyValue) {
		this.estimatedPropertyValue = estimatedPropertyValue;
	}
	public String getEstimatedPurchasePrice() {
		return estimatedPurchasePrice;
	}
	public void setEstimatedPurchasePrice(String estimatedPurchasePrice) {
		this.estimatedPurchasePrice = estimatedPurchasePrice;
	}
	public String getEstimatedDownPayment() {
		return estimatedDownPayment;
	}
	public void setEstimatedDownPayment(String estimatedDownPayment) {
		this.estimatedDownPayment = estimatedDownPayment;
	}
	public String getCurrentMortgageBalance() {
		return currentMortgageBalance;
	}
	public void setCurrentMortgageBalance(String currentMortgageBalance) {
		this.currentMortgageBalance = currentMortgageBalance;
	}

}
