package com.lendingtree.adapter;

import java.util.ArrayList;

import com.lendingtree.util.Constants;
import com.lendingtree.view.Detail;

/**
 * @category For handling calculations of Home Affordability Payment 
 * @author Ramesh Gundala
 *
 */
public class HomeAffCalculator {
	// Variables Declaration

	public double annualIncomeAmt;
	public double downPaymentAmt;
	public double annualyInterestRate;
	public double monthlyDebtsAmt;
	public double annualyPropertyTaxRate;
	public double annualyHomeOwnerInsuranceAmt;
	public double annualyHOADuesAmt;
	public double loanTermYear;
	public boolean addPMI;

	public double incomeTaxRate;
	public double pct;

	public double monthlyInterestRate;
	public double monthlyPropertyTaxRate;
	public double numberofLoanTerms;
	public int monthlyHOADuesAmt;
	public int monthlyHomeOwnerInsuranceAmt;
	public double monthlyPropertyTaxAmt;
	public static double monthlyMortgagePaymentAmt;

	public double debtToIncomeRatio;
	public int homePriceAmt;
	public int loanAmt;
	public int monthlyIncomeAmt;
	public static int totalMonthlyPaymentAmt;
	public int debtsPaymentAmt;
	public int monthlyIncomeTaxAmt;
	public int monthlyLeftOverAmt;

	public double monthlyPI;
	ArrayList<String> amortizationArray;
	public static ArrayList<Detail> months;

	//public boolean flag;

	// constructor
	public HomeAffCalculator() {
		super();
		annualIncomeAmt = 0.0;
		downPaymentAmt = 0.0;
		annualyInterestRate = 0.0;
		monthlyDebtsAmt = 0.0;
		annualyPropertyTaxRate = 0.0;
		annualyHomeOwnerInsuranceAmt = 0.0;
		annualyHOADuesAmt = 0.0;
		loanTermYear = 0.0;
		addPMI = false;

		pct = 0.0;
		incomeTaxRate = 0.3;

		monthlyInterestRate = 0.0;
		monthlyPropertyTaxRate = 0.0;
		numberofLoanTerms = 0.0;
		monthlyHOADuesAmt = 0;
		monthlyHomeOwnerInsuranceAmt = 0;
		monthlyPropertyTaxAmt = 0.0;

		debtToIncomeRatio = 0;
		homePriceAmt = 0;
		loanAmt = 0;
		monthlyIncomeAmt = 0;
		totalMonthlyPaymentAmt = 0;
		monthlyMortgagePaymentAmt = 0;
		debtsPaymentAmt = 0;
		monthlyIncomeTaxAmt = 0;
		monthlyLeftOverAmt = 0;

		monthlyPI = 0.0;
		amortizationArray = new ArrayList<String>();
	}
	
	/**
	 * @category Calculates Private Mortgage Insurance Amount
	 * @return double
	 */

	public double calculatePrivateMortgageInsuranceAmount()
	{
		 double pmi = 0.0;
		    double ltv = (loanAmt * 1.0)/ homePriceAmt;
		    if (ltv >= Constants.ZERO_POINT_EIGHT) {
		        pmi = (loanAmt * Constants.ZERO_POINT_ZERO_ZERO_FOUR_FOUR) / Constants.TWELVE;
		    }
		    return pmi;
	}
	
	/**
	 * @category Calculates Base Monthly Mortgage
	 * @return double
	 */
	public double calculateBaseMonthlyMortgage()
	{
		 double total = (loanAmt * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, (-1 * numberofLoanTerms))) * Constants.HUNDRED / Constants.HUNDRED;
		    
		    return total;
	}

	/**
	 * @category Rounding double value
	 * @return int
	 */
	private int roundToint(double doubleValue) {
		int nbi = (int) Math.round(doubleValue);
		return nbi;
	}

	/**
	 * @category Calculates Home Affordability Calculation
	 * @return void
	 */
	public void doHomeAffordabilityCalculation()
	{

		double monthlyIncome = annualIncomeAmt / Constants.TWELVE_POINT_ZERO;
	    monthlyIncomeAmt = roundToint(monthlyIncome);
	    
	    debtsPaymentAmt = roundToint(monthlyDebtsAmt);
	    monthlyIncomeTaxAmt = roundToint(monthlyIncomeAmt * incomeTaxRate);
	    numberofLoanTerms = loanTermYear * Constants.TWELVE_POINT_ZERO;
	    //Calculate monthly HOA due amount
	    double monthlyHOADues = 0.0;
	    if (annualyHOADuesAmt > 0.0) {
	        monthlyHOADues = annualyHOADuesAmt / Constants.TWELVE_POINT_ZERO;
	        monthlyHOADuesAmt = roundToint(monthlyHOADues);
	    }
	    
	    //calculate Home Owner insurance amount
	    double monthlyHomeOwnerInsurance = 0.0;
	    if (annualyHomeOwnerInsuranceAmt > 0.0) {
	        monthlyHomeOwnerInsurance = annualyHomeOwnerInsuranceAmt / Constants.TWELVE_POINT_ZERO;
	        monthlyHomeOwnerInsuranceAmt = roundToint(monthlyHomeOwnerInsurance);
	    }

		monthlyInterestRate = annualyInterestRate/ (Constants.TWELVE_POINT_ZERO * Constants.HUNDRED_POINT_ZERO);
	    
	    //Calculate monthly property tax amount
	    monthlyPropertyTaxRate = annualyPropertyTaxRate/ (Constants.TWELVE_POINT_ZERO * Constants.HUNDRED_POINT_ZERO);
	    
	    //Calculations
	    double fixedcosts = debtsPaymentAmt + monthlyHOADues  + monthlyHomeOwnerInsurance;
	    
	    //    self.monthlyMortgagePaymentAmt = (self.monthlyIncomeAmt * self.pct) - fixedcosts;
	    monthlyMortgagePaymentAmt = (monthlyIncome * pct) - fixedcosts;
		  
		
		  
	    double factor = (Math.pow((1.0 + monthlyInterestRate), numberofLoanTerms) - 1.0)/ (Math.pow((1.0 + monthlyInterestRate), numberofLoanTerms) * monthlyInterestRate);
	    
	    double pmiRate = 0.0;
	    
	    double tmpHomePrice = (monthlyMortgagePaymentAmt * factor + downPaymentAmt) / (1.0 + factor * (monthlyPropertyTaxRate + pmiRate));
	    
	    double tmpLoanAmt = tmpHomePrice - downPaymentAmt;
	    
	    double ltv = 0.0;
	    if (addPMI) {
	        ltv = tmpLoanAmt / tmpHomePrice;
	        if (ltv > Constants.ZERO_POINT_EIGHT) {
	            pmiRate = ltv * Constants.ZERO_POINT_ZERO_ZERO_FOUR_FOUR / Constants.TWELVE_POINT_ZERO;
	        }
	    }
	    
	    
	    double homePrice = (monthlyMortgagePaymentAmt * factor + downPaymentAmt) / (1.0 + factor * (monthlyPropertyTaxRate + pmiRate));
	    
	    homePriceAmt = roundToint(homePrice);
	    
	    
	    double loan = homePriceAmt - downPaymentAmt;
	    
	    loanAmt = roundToint(loan);
	    
	    monthlyPropertyTaxAmt = homePriceAmt * monthlyPropertyTaxRate;
	    
//	    ltv = self.loanAmt / self.homePriceAmt;
	    
	    double pmiAmt = 0.0;
	    if (addPMI) {
	        pmiAmt = calculatePrivateMortgageInsuranceAmount();
	    }
	    
	    double monthlyMortgageBaseAmt = calculateBaseMonthlyMortgage();
	    monthlyPI = monthlyMortgageBaseAmt;
	    
	    double totalMonthlyPayment = monthlyMortgageBaseAmt + pmiAmt + monthlyHomeOwnerInsuranceAmt + monthlyHOADuesAmt + monthlyPropertyTaxAmt;
	    
	    totalMonthlyPaymentAmt = roundToint(totalMonthlyPayment);
	    
	    monthlyLeftOverAmt = monthlyIncomeAmt - totalMonthlyPaymentAmt - monthlyIncomeTaxAmt - debtsPaymentAmt;
	    
	    
	    if (monthlyMortgagePaymentAmt < 0 || totalMonthlyPaymentAmt > monthlyIncomeAmt) {
	        homePriceAmt = 0;
	        loanAmt = 0;
	        totalMonthlyPaymentAmt = 0;
	        monthlyLeftOverAmt = 0;
	    }
		
	}

	/**
	 * @category Calculate Home Affordability for Details 
	 * @return void
	 */


	public void calculateHomeAffordability()
	{
		doHomeAffordabilityCalculation();
		
		months = new ArrayList<Detail>();
		months.clear();
		double balance = this.loanAmt;
		double monthlyPIAmt = this.monthlyPI;

		//   double f = 123456.3;
		monthlyPIAmt = roundToint (monthlyPIAmt * 100) / Constants.HUNDRED_POINT_ZERO;


		

		for (int count = 1; count <= numberofLoanTerms; count++) {

			// NSMutableDictionary *details = [[NSMutableDictionary alloc] init];

			Detail detail = new Detail();

			double interest = ((balance * this.annualyInterestRate) / Constants.TWELVE_POINT_ZERO ) / Constants.HUNDRED_POINT_ZERO;
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
			detail.setPrincipal(Math.round(principal * Constants.HUNDRED )/ Constants.HUNDRED_POINT_ZERO);
			detail.setInterest(Math.round(interest * Constants.HUNDRED )/ Constants.HUNDRED_POINT_ZERO);
			detail.setBalance(Math.round(balance * Constants.HUNDRED )/ Constants.HUNDRED_POINT_ZERO);
			months.add(detail);
		}
	}
}
