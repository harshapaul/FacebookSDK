package com.lendingtree.adapter;

import java.util.ArrayList;

import com.lendingtree.util.Constants;
import com.lendingtree.view.Detail;

/**
 * @category For handling calculations of Mortgage Payment 
 * @author ankit
 *
 */

public class MortgageData {

	public double propertyPriceAmt, downPaymentAmt, annualyInterestRate,
			annualyPropertyTaxRate, annualyHomeOwnerInsuranceAmt,
			annualyHOADuesAmt, loanTermYear;
	public boolean addPMI;
	public double loanAmt, monthlyInterestRate, monthlyPropertyTaxRate,
			numberofLoanTerms;
	public double monthlyMortgageTotalAmt, monthlyMortgageBaseAmt,
			monthlyPropertyTaxAmt, monthlyHomeOwnerInsuranceAmt,
			monthlyHOADuesAmt, monthlyPMIAmt;
	public double monthlyPI;
	public static ArrayList<Detail> months;
	public static double monthlyMortgageData;

	// constructor
	public MortgageData() {
		this.propertyPriceAmt = 0.0;
		this.downPaymentAmt = 0.0;
		this.annualyInterestRate = 0.0;
		this.annualyPropertyTaxRate = 0.0;
		this.annualyHomeOwnerInsuranceAmt = 0.0;
		this.annualyHOADuesAmt = 0.0;
		this.loanTermYear = 0.0;
		this.addPMI = false;

		this.loanAmt = 0.0;
		this.monthlyInterestRate = 0.0;
		this.monthlyPropertyTaxRate = 0.0;
		this.numberofLoanTerms = 0.0;

		this.monthlyMortgageTotalAmt = 0;
		this.monthlyMortgageBaseAmt = 0;
		this.monthlyPropertyTaxAmt = 0;
		this.monthlyHomeOwnerInsuranceAmt = 0;
		this.monthlyHOADuesAmt = 0;
		this.monthlyPMIAmt = 0;

		this.monthlyPI = 0.0;
		//this.amortizationArray = new ArrayList<HashMap<String, Double>>();

	}

	/**
	 * @category Calculates Private Mortgage Insurance Amount
	 * @return double
	 * @author ankit
	 */
	private double calculatePrivateMortgageInsuranceAmount() {

		double pmi = 0.0;
		double ltv = this.loanAmt / this.propertyPriceAmt;
		if (ltv >= Constants.ZERO_POINT_EIGHT) {
			pmi = (this.loanAmt * Constants.ZERO_POINT_ZERO_ZERO_FOUR_FOUR) / Constants.TWELVE;
		}
		
		return pmi;
	}

	/**
	 * @category Calculates Base Monthly Mortgage
	 * @return double
	 * @author ankit
	 */
	private double calculateBaseMonthlyMortgage() {
		double powerVal = Math.pow((1.0 + this.monthlyInterestRate),
				this.numberofLoanTerms);
		double numerator = this.monthlyInterestRate * powerVal;
		double denominator = powerVal - 1;
		double total = this.loanAmt * (numerator / denominator);

		return total;
	}

	/**
	 * @category Rounding double value
	 * @return int
	 * @author ankit
	 */
	private int roundToint(double doubleValue) {
		int nbi = (int) Math.round(doubleValue);
		return nbi;
	}

	/**
	 * @category Calculates Monthly Mortgage
	 * @return void
	 * @author ankit
	 */
	public void calculateMonthlyMortgage() {
		this.loanAmt = this.propertyPriceAmt - this.downPaymentAmt;

		this.monthlyInterestRate = this.annualyInterestRate / (Constants.TWELVE_POINT_ZERO * Constants.HUNDRED_POINT_ZERO);
		this.numberofLoanTerms = this.loanTermYear * Constants.TWELVE_POINT_ZERO;

		this.monthlyPI = calculateBaseMonthlyMortgage();
		this.monthlyMortgageBaseAmt = roundToint(monthlyPI);

		// Calculate monthly property tax amount
		this.monthlyPropertyTaxRate = this.annualyPropertyTaxRate
				/ (Constants.TWELVE_POINT_ZERO * Constants.HUNDRED_POINT_ZERO);
		this.monthlyPropertyTaxAmt = roundToint(this.propertyPriceAmt
				* this.monthlyPropertyTaxRate);

		// calculate Home Owner insurance amount
		if (this.annualyHomeOwnerInsuranceAmt > 0.0) {
			this.monthlyHomeOwnerInsuranceAmt = roundToint(this.annualyHomeOwnerInsuranceAmt / Constants.TWELVE_POINT_ZERO);
		}

		// Calculate monthly HOA due amount
		if (this.annualyHOADuesAmt > 0.0) {
			this.monthlyHOADuesAmt = roundToint(this.annualyHOADuesAmt / Constants.TWELVE_POINT_ZERO);
		}

		if (this.addPMI) {
			this.monthlyPMIAmt = roundToint(calculatePrivateMortgageInsuranceAmount());
			
		}else{
			this.monthlyPMIAmt = 0;
		}

		this.monthlyMortgageTotalAmt = this.monthlyMortgageBaseAmt
				+ this.monthlyPropertyTaxAmt
				+ this.monthlyHomeOwnerInsuranceAmt + this.monthlyHOADuesAmt
				+ this.monthlyPMIAmt;
		monthlyMortgageData = monthlyMortgageTotalAmt;
	}

	/**
	 * @category Calculates Monthly Mortgage With Amortization
	 * @return void
	 * @author ankit
	 */
	public void calculateMonthlyMortgageWithAmortization() {
		calculateMonthlyMortgage();

		double balance = this.loanAmt;
		double monthlyPIAmt = this.monthlyPI;

		monthlyPIAmt = Math.round(monthlyPIAmt * Constants.HUNDRED) / Constants.HUNDRED_POINT_ZERO;
		
		months = new ArrayList<Detail>();
		
		for (int count = 1; count <= this.numberofLoanTerms; count++) {

			Detail detail = new Detail();
			double interest = ((balance * this.annualyInterestRate) / Constants.TWELVE_POINT_ZERO) / Constants.HUNDRED_POINT_ZERO;
			double principal = monthlyPIAmt - interest;
			balance = balance - principal;

			if (interest < 0.00) {
				interest = 0.00;
			}
			if (principal < 0.00) {
				principal = 0.00;
			}
			if (balance < 0.00) {
				balance = 0.00;
			}
			
			detail.setMonths(count);
			detail.setPrincipal(Math.round(principal * Constants.HUNDRED) / Constants.HUNDRED_POINT_ZERO);
			detail.setInterest(Math.round(interest * Constants.HUNDRED) / Constants.HUNDRED_POINT_ZERO);
			detail.setBalance(Math.round(balance * Constants.HUNDRED) / Constants.HUNDRED_POINT_ZERO);
			
			months.add(detail);

		}

	}

}