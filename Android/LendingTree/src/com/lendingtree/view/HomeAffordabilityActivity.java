package com.lendingtree.view;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.lendingtree.adapter.HomeAffCalculator;
import com.lendingtree.util.Constants;
import com.lendingtree.util.InputFilterMinMax;

/**
 * 
 * @category This is the activity for the Home Affordability calculater
 * @author Ramesh Gundala
 *
 */
@EActivity(R.layout.home_affordablitty_activity)
public class HomeAffordabilityActivity extends BaseActivity  /*implements OnKeyListener,OnSeekBarChangeListener*/{

	private static final double CONSERVATIVE_PCT = 0.28;
	private static final double MODERATE_PCT = 0.36;
	private static final double AGGRESSIVE_PCT = 0.41;

	//private boolean YES = true;
	Typeface tf;
	HomeAffCalculator homeaff_data;
	
	private Segment s1;
	private Segment s2;
	private Segment s3;
	private Segment s4;
	
	int changedValue =30000 /*= 50000*/;
	int changedValue1;
	
	boolean isEditing;

	private int btnClick = 1;
	private int CONSERVATIVE = 1;
	private int MODERATE = 2;
	public static boolean onlyOnce = true;

	private boolean redarw = true;
	int selectedItem = 0;
	
	@ViewById(R.id.chart)
	PieChart pie;
	
	@ViewById(R.id.btnlayout)
	LinearLayout btnLayout;

	@ViewById(R.id.ltAssumption)
	LinearLayout ltAssumption;
	
	@ViewById(R.id.llHomeAffordability)
	LinearLayout llHomeAffordability;
	
	@ViewById(R.id.annual_income_ha)
	SeekBar seeBarAnnualIncome;
	
	@ViewById(R.id.down_payment_ha)
	SeekBar seekBardownPaymentS;

	@ViewById(R.id.mortgage_rate_ha)
	SeekBar seekBarMortgageRate;

	@ViewById(R.id.coservativeBtn)
	Button coservativeBtn;

	@ViewById(R.id.moderateBtn)
	Button moderateBtn;

	@ViewById(R.id.aggresiveBtn)
	Button aggresiveBtn;
	
	@ViewById(R.id.assumptionBtn)
	Button assumptionBtn;

	@ViewById(R.id.txtColor1)
	TextView textColor1;

	@ViewById(R.id.txtColor2)
	TextView textColor2;
	
	@ViewById(R.id.txtColor3)
	TextView textColor3;

	@ViewById(R.id.txtColor4)
	TextView textColor4;

	@ViewById(R.id.monthlyDebtsAlert)
	TextView monthlyDebtsAlert;

	@ViewById(R.id.spLoanterm)
	Spinner spinner;
	
	///////=====
	@ViewById(R.id.home_affordability_home_price)
	TextView homePriceText;
	
	@ViewById(R.id.txt_home_price_Amt)
	TextView homePriceHeader;
	
	@ViewById(R.id.home_affordability_loan_price)
	TextView loanPriceText;

	@ViewById(R.id.txt_loan_price_Amt)
	TextView loanPriceHeader;
	
	@ViewById(R.id.home_affordability_monthly_income)
	TextView monthlyIncomeText;
	
	@ViewById(R.id.txt_monthly_income_pichart)
	TextView monthlyIncomePichart;
	
	@ViewById(R.id.home_affordability_monthly_mortgae_pmt)
	TextView monthlyMortgageText;
	
	@ViewById(R.id.txt_monthly_mortgage_pichart)
	TextView monthlyMortgagePichart;
	
	@ViewById(R.id.home_affordability_debt_payment)
	TextView debtPaymentText;
	
	@ViewById(R.id.txt_debt_payments_pichart)
	TextView debtPaymentPichart;

	@ViewById(R.id.home_affordability_Income_taxes)
	TextView incomeTexesText;
	
	@ViewById(R.id.txt_income_taxes_pichart)
	TextView incomeTaxesPichart;
	
	@ViewById(R.id.home_affordability_left_over)
	TextView leftOverText;
	
	@ViewById(R.id.txt_left_over_pichart)
	TextView leftOverPichart;
	
	@ViewById(R.id.home_affordability_annual_income)
	TextView annualIncomeSeekText;
	
	@ViewById(R.id.doller)
	TextView dollertext;
	
	@ViewById(R.id.home_affordability_down_payment)
	TextView doenPayemtTextvalue;
	
	@ViewById(R.id.doller1)
	TextView dollertext1;
	
	@ViewById(R.id.percentMortgage)
	TextView percentageMortgage;
	
	@ViewById(R.id.dollor3)
	TextView dollertext3;
	
	@ViewById(R.id.tvAssuption)
	TextView assumptionText;
	
	@ViewById(R.id.txt_annual_prpoerty_taxes)
	TextView annualPropertyTaxText;
	
	@ViewById(R.id.percent_annual_property_taxes_values)
	TextView annualPropertyTaxTextPercentage;
	
	@ViewById(R.id.doller4)
	TextView doller4;
	
	@ViewById(R.id.txt_annual_homeowners_insurance)
	TextView annualHomeOwnerInsuranceText;
	
	@ViewById(R.id.txt_annual_hoa_dues )
	TextView annualHOADuesText;
	
	@ViewById(R.id.doller5)
	TextView doller5;
	
	@ViewById(R.id.tvMtgPaymentFooter)
	TextView tvMtgPaymentFooter;
	
	@ViewById(R.id.txt_loan_trem)
	TextView loanTermsText;
	
	@ViewById(R.id.txt_private_mortgage_insurance)
	TextView privateMortgageInsuranceText;
	
	@ViewById(R.id.pertax)
	TextView pertax;
	
	@ViewById(R.id.home_affordability_mortgage_rate)
	TextView tvhomeAffordabilityMortgageRate;
	
	
	//  +++++++++ SeekBar TextView values Declaration +++++++++++ 
	
	@ViewById(R.id.txt_annual_income)
	EditText annualIncomeSeekBar;
	
	@ViewById(R.id.edit_monthly_debt)
	EditText monthyDebtSeekBar;
	

	@ViewById(R.id.txt_down_payment)
	EditText downPaymentSeekBar;
	
	@ViewById(R.id.txt_mortgage_rat)
	EditText mortagaeRateSeekBar;
	
	@ViewById(R.id.edit_annual_prpoerty_taxes)
	EditText annualPropertyTaxesAssumption;

	@FocusChange(R.id.edit_annual_prpoerty_taxes)
	void onFocusChangeOfAnnualPropTaxes(View v, boolean hasFocus){
		if(hasFocus && annualPropertyTaxesAssumption.getText().toString().trim().equals("0.000"))
		{
			annualPropertyTaxesAssumption.setText("");
		}
	}
	
	@ViewById(R.id.edit_annual_homeowner_insurance)
	EditText annualHomeownerInsuranceAssumption;
	
	@FocusChange(R.id.edit_annual_homeowner_insurance)
	void onFocusChangeOfAnnualHomeIns(View v, boolean hasFocus){
		if(hasFocus && annualHomeownerInsuranceAssumption.getText().toString().trim().equals("0"))
		{
			annualHomeownerInsuranceAssumption.setText("");
		}
	}
	
	@ViewById(R.id.edit_annual_hoa_dues)
	EditText annualHOAduesAssumptions;
	
	@FocusChange(R.id.edit_annual_hoa_dues)
	void onFocusChangeOfAnnualHOADues(View v, boolean hasFocus){
		if(hasFocus && annualHOAduesAssumptions.getText().toString().trim().equals("0"))
		{
			annualHOAduesAssumptions.setText("");
		}
	}
	@ViewById(R.id.tbPMI)
	ToggleButton tbPMI;
	
	@ViewById(R.id.scrollView)
	ScrollView scrollView;
	ImageButton  b;
	ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {

			Rect gootFaithHeight = new Rect();
			llHomeAffordability.getWindowVisibleDisplayFrame(gootFaithHeight);
			
			int heightDiff = llHomeAffordability.getRootView()
					.getHeight()
					- (gootFaithHeight.bottom - gootFaithHeight.top);
			
			if (heightDiff > 100) {
				b.setEnabled(false);
				// keyboard is up
				

				/*if (tvMortgageRateValue.isFocused()) {
					if (tvMortgageRateValue.getText().toString().trim()
							.equals("0")) {
						tvMortgageRateValue.setText("");
					}
				}*/

			} else {
				b.setEnabled(true);
				if(annualIncomeSeekBar.isFocused())
				{
					if (annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						annualIncomeSeekBar.setText("30000");
					} 
					else if(Double.parseDouble(annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "")) < 30000.00)
					{
						annualIncomeSeekBar.setText("30000");
					}
				} 
				else if(mortagaeRateSeekBar.isFocused())
				{
					if (mortagaeRateSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						mortagaeRateSeekBar.setText("2.000");
					} 
					else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) < 2.0) {
						
						mortagaeRateSeekBar.setText("2.000");
					}
					else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) > 7.5) {
						
						mortagaeRateSeekBar.setText("7.500");
					}
					else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) >= 2.0 && Double.parseDouble(mortagaeRateSeekBar.getText().toString()) <= 7.5) {
						DecimalFormat format = new DecimalFormat(
								"0.000");
						mortagaeRateSeekBar.setText(format.format(Double
								.parseDouble(mortagaeRateSeekBar.getText().toString())));
					}
					
					
				}
				
			}
			/*llPayment.getViewTreeObserver()
			.removeGlobalOnLayoutListener(this);*/
		}
	};
	
	@Override
	protected void onStart() { // TODO Auto-generated method stub
		super.onStart();
		if(redarw){
			llHomeAffordability.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
			defaulyValues();

			assigningDataValues();
			homeaff_data.calculateHomeAffordability();
			
			setSeriesAdapter();
			
			createPieChart();
			redarw = false;
			}

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
			{
				try
				{
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); 
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			toggle();
			return true;
		case R.id.github:
		
			if(seeBarAnnualIncome.isFocused())
			{
				if (annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
				{
					annualIncomeSeekBar.setText("30000");
				} 
				else if(Double.parseDouble(annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "")) < 30000.00)
				{
					annualIncomeSeekBar.setText("30000");
				}
				
			} 
			
			else if(mortagaeRateSeekBar.isFocused())
			{
				if (mortagaeRateSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
				{
					mortagaeRateSeekBar.setText("2.000");
				} 
				else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) < 2.0) {
					
					mortagaeRateSeekBar.setText("2.000");
				}
				else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) > 7.5) {
					
					mortagaeRateSeekBar.setText("7.500");
				}
			}
				Intent intent = new Intent(this, PinnedSectionListActivity_.class);
				
				startActivity(intent);
		
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		
		//getSupportActionBar().setIcon(R.drawable.hdpi_menuicon);
		//ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		 com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		  actionBar.setBackgroundDrawable(getResources().getDrawable(
		    R.drawable.actionbar));
		
		  b.setBackgroundDrawable(null);
		  b.setImageDrawable(getResources().getDrawable(R.drawable.details));
		 
		  b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(annualIncomeSeekBar.isFocused())
				{
					if (annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						annualIncomeSeekBar.setText("30000");
					} 
					else if(Double.parseDouble(annualIncomeSeekBar.getText().toString().replaceAll("[^\\d]", "")) < 30000.00)
					{
						annualIncomeSeekBar.setText("30000");
					}
					
				} 
				
				else if(mortagaeRateSeekBar.isFocused())
				{
					if (mortagaeRateSeekBar.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						mortagaeRateSeekBar.setText("2.000");
					} 
					else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) < 2.0) {
						
						mortagaeRateSeekBar.setText("2.000");
					}
					else if (Double.parseDouble(mortagaeRateSeekBar.getText().toString()) > 7.5) {
						
						mortagaeRateSeekBar.setText("7.500");
					}
				}
	        Intent intent = new Intent(getApplicationContext(), PinnedSectionListActivity_.class);
				
				startActivity(intent);
			}
		});
		  
		  menu.findItem(R.id.github).setActionView(b);
		  menu.findItem(R.id.github).setVisible(true);
		  menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}
	
	
	/**
	 * @return void
	 * @category For Loading the initial views
	 * @author Ramesh Gundala
	 * 
	 */
	@AfterViews
	public void graphDisplayText() {

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		setLoanTermSpinner();
		
		//Action Bar Title Center
		//application.getABCTitle().setTitle("Home Affordability", this);
		/*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.actionbar_title);
		View tempView = getSupportActionBar().getCustomView();
		TextView tempTextView = (TextView) tempView
				.findViewById(R.id.actionbarText);

		tempTextView.setText("Home Affordability");*/
	
		
		setSlidingActionBarEnabled(true);
		

		monthyDebtSeekBar.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				/*if(s.toString().length()<=5 &&( Integer.parseInt(s.toString())<=10000)){
					
				}else{
					monthyDebtSeekBar.setFocusable(false);
				}
			*/}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

				
				String monthlyDebts = monthyDebtSeekBar.getText().toString();
				if(!monthlyDebts.isEmpty()){
					homeaff_data.monthlyDebtsAmt = Double.parseDouble(monthlyDebts);

				}else{
					homeaff_data.monthlyDebtsAmt = 0;
				}
				assigningDataValues();
				Log.v("++++++", monthlyDebts + "!!!!!!");

				setSeriesAdapter();
				createPieChart();

				
			}
		});
		
		annualPropertyTaxesAssumption.setFilters(new InputFilter[]{ new InputFilterMinMax(Constants.ZERO_POINT_ZERO_ZERO_ZERO, Constants.FIVE_POINT_ZERO_ZERO_ZERO)});
		annualHomeownerInsuranceAssumption.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "3000")});
		monthyDebtSeekBar.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "20000")});
		annualHOAduesAssumptions.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "9999")});
		mortagaeRateSeekBar.setFilters(new InputFilter[]{ new InputFilterMinMax(Constants.TWO_POINT_ZERO_ZERO_ZERO, Constants.SEVEN_POINT_FIVE_ZERO_ZERO)});
		
		monthlyDebtsAlert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// create a Dialog component
				
				Intent intent = new Intent(getBaseContext(), CustomDialogActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		//Scroll View UP or Top issue
		scrollView.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollView.scrollTo(0, scrollView.getTop());
			}
		});
		settingFontAllViews();
		b = new ImageButton(getApplicationContext());
		setHyperLink(tvMtgPaymentFooter);
	}
	
	private void setLoanTermSpinner() {
		// TODO Auto-generated method stub

		final ArrayList<String> loanTermList = new ArrayList<String>();
		loanTermList.add("1 year term");
		loanTermList.add("2 year term");
		loanTermList.add("3 year term");
		loanTermList.add("4 year term");
		loanTermList.add("5 year term");
		loanTermList.add("10 year term");
		loanTermList.add("15 year term");
		loanTermList.add("20 year term");
		loanTermList.add("25 year term");
		loanTermList.add("30 year term");
		loanTermList.add("40 year term");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, loanTermList) {
			
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				final View v = vi.inflate(android.R.layout.simple_spinner_item,
						null);
				
				final TextView t = (TextView) v
						.findViewById(android.R.id.text1);
				t.setText(loanTermList.get(position).substring(0, Constants.TWO));
				
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				switch (metrics.densityDpi) {
				case DisplayMetrics.DENSITY_MEDIUM:
					t.setTextSize(Constants.SIXTEEN);
					break;
				case DisplayMetrics.DENSITY_HIGH:
					t.setTextSize(Constants.SEVENTEEN);
					break;
				case DisplayMetrics.DENSITY_XHIGH:
					t.setTextSize(Constants.EIGHTEEN);
					break;
				case DisplayMetrics.DENSITY_XXHIGH:
					t.setTextSize(Constants.NINTEEN);
					break;
				default:
					t.setTextSize(Constants.SEVENTEEN);
				}
				
				t.setGravity(Gravity.LEFT);
				t.setTypeface(tf);
				return v;
			}

			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				final View v = vi.inflate(
						android.R.layout.simple_spinner_dropdown_item, null);
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				switch (metrics.densityDpi) {
				case DisplayMetrics.DENSITY_MEDIUM:
					v.setLayoutParams(new AbsListView.LayoutParams(
							LayoutParams.MATCH_PARENT, Constants.FIFTY));
					break;
				case DisplayMetrics.DENSITY_HIGH:
					v.setLayoutParams(new AbsListView.LayoutParams(
							LayoutParams.MATCH_PARENT, Constants.SEVENTY_FIVE));
					break;
				case DisplayMetrics.DENSITY_XHIGH:
					v.setLayoutParams(new AbsListView.LayoutParams(
							LayoutParams.MATCH_PARENT, Constants.HUNDRED));
					break;
				case DisplayMetrics.DENSITY_XXHIGH:
					v.setLayoutParams(new AbsListView.LayoutParams(
							LayoutParams.MATCH_PARENT, Constants.ONE_TWENTY_FIVE));
					break;
				default:
					v.setLayoutParams(new AbsListView.LayoutParams(
							LayoutParams.MATCH_PARENT, Constants.SEVENTY_FIVE));
				}
				
				
				final TextView textviewSpinner = (TextView) v
						.findViewById(android.R.id.text1);
				//textviewSpinner.setTextSize(18);
				
				textviewSpinner.setText(loanTermList.get(position).toString()
						+ "\n");
				textviewSpinner.setGravity(Gravity.CENTER);
				if (position == selectedItem) {
					textviewSpinner.setBackgroundColor(getResources().getColor(R.color.silver));
				}
					textviewSpinner.setTypeface(tf);
				
				return v;
			}
		};
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(Constants.NINE);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				selectedItem = pos;
				switch (pos) {
				case 0:

					homeaff_data.loanTermYear = 1;
					break;
				case 1:

					homeaff_data.loanTermYear = Constants.TWO;
					break;
				case Constants.TWO:

					homeaff_data.loanTermYear = Constants.THREE;
					break;
				case Constants.THREE:

					homeaff_data.loanTermYear = Constants.FOUR;
					break;
				case Constants.FOUR:

					homeaff_data.loanTermYear = Constants.FIVE;
					break;
				case Constants.FIVE:

					homeaff_data.loanTermYear = Constants.TEN;
					break;
				case Constants.SIX:

					homeaff_data.loanTermYear = Constants.SIXTEEN;
					break;

				case Constants.SEVEN:

					homeaff_data.loanTermYear = Constants.TWENTY;
					break;
				case Constants.EIGHT:

					homeaff_data.loanTermYear = Constants.TWENTY_FIVE;
					break;

				case Constants.NINE:

					homeaff_data.loanTermYear = Constants.THIRTY;
					break;

				case Constants.TEN:

					homeaff_data.loanTermYear = Constants.FOURTY;
					break;

				default:

					homeaff_data.loanTermYear = Constants.THIRTY;
					break;
				}
				assigningDataValues();
				homeaff_data.calculateHomeAffordability();

				/*// For pie chart change
				setSeriesAdapter();
				// shinobiChart.addSeries(series);
				createPieChart();*/
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
	
	}

	private void settingFontAllViews() {
		// TODO Auto-generated method stub
		Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		
		monthlyIncomeText.setTypeface(tf1);
		monthlyIncomePichart.setTypeface(tf1);
		monthlyDebtsAlert.setTypeface(tf);
		
		homePriceText.setTypeface(tf1);
		homePriceHeader.setTypeface(tf1);
		loanPriceText.setTypeface(tf1);
		loanPriceHeader.setTypeface(tf1);
		
		monthlyMortgageText.setTypeface(tf);
		monthlyMortgagePichart.setTypeface(tf);
		debtPaymentText.setTypeface(tf);
		debtPaymentPichart.setTypeface(tf);
		incomeTexesText.setTypeface(tf);
		incomeTaxesPichart.setTypeface(tf);
		leftOverText.setTypeface(tf);
		leftOverPichart.setTypeface(tf);
		annualIncomeSeekText.setTypeface(tf);
		dollertext.setTypeface(tf);
		doenPayemtTextvalue.setTypeface(tf);
		dollertext1.setTypeface(tf);
		percentageMortgage.setTypeface(tf);
		dollertext3.setTypeface(tf);
		assumptionText.setTypeface(tf);
		annualPropertyTaxText.setTypeface(tf);
		annualPropertyTaxTextPercentage.setTypeface(tf);
		doller4.setTypeface(tf);
		annualHomeOwnerInsuranceText.setTypeface(tf);
		annualHOADuesText.setTypeface(tf);
		doller5.setTypeface(tf);
		loanTermsText.setTypeface(tf);
		privateMortgageInsuranceText.setTypeface(tf);
		pertax.setTypeface(tf);
		tvhomeAffordabilityMortgageRate.setTypeface(tf);
		
		annualIncomeSeekBar.setTypeface(tf);
		monthyDebtSeekBar.setTypeface(tf);
		downPaymentSeekBar.setTypeface(tf);
		mortagaeRateSeekBar.setTypeface(tf);
		annualPropertyTaxesAssumption.setTypeface(tf);
		annualHomeownerInsuranceAssumption.setTypeface(tf);
		annualHOAduesAssumptions.setTypeface(tf);
		
		moderateBtn.setTypeface(tf);
		aggresiveBtn.setTypeface(tf);
		assumptionBtn.setTypeface(tf);
		coservativeBtn.setTypeface(tf);
		tvMtgPaymentFooter.setTypeface(tf);
	}

	/**
	 * @return void
	 * @category For handling click of assumptions textview
	 * @author ankit
	 * 
	 */
	@Click(R.id.percent_annual_property_taxes_values)
	void onClickofPercentageView() {
		annualPropertyTaxesAssumption.requestFocus(View.FOCUS_LEFT);
		
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.showSoftInput(annualPropertyTaxesAssumption, 0);

	}

	/**
	 * @return void
	 * @category Handles the click of Conservative Button and pie chart color changes.
	 * 
	 */

	@Click(R.id.coservativeBtn)
	public void conservativeDetails(View v) {
		coservativeBtn.setBackgroundResource(R.drawable.conserve_btn_on)/*(getResources().getDrawable(R.drawable.conserve_btn_on))*/;
		btnLayout.setBackgroundResource(R.drawable.segment_button_home_aff)/*getResources().getDrawable(R.drawable.segment_button_home_aff))*/;
		moderateBtn.setBackgroundColor(Color.parseColor("#000000"));
		aggresiveBtn.setBackgroundResource(R.drawable.aggresive_btn)/*getResources().getDrawable(R.drawable.aggresive_btn))*/;
		//Color Change code Conservation
		//textColor1.setBackgroundColor(Color.BLACK);
		textColor1.setBackgroundColor(Color.parseColor("#528A49"));
		textColor2.setBackgroundColor(Color.parseColor("#52D017"));
		textColor3.setBackgroundColor(Color.parseColor("#99C68E"));
		textColor4.setBackgroundColor(Color.parseColor("#BBD86E"));
		btnClick = 1;
		homeaff_data.pct = CONSERVATIVE_PCT;
		setSeriesAdapter();
		//shinobiChart.redrawChart();
		//calculatePercentagePieChart(homeaff_data.totalMonthlyPaymentAmt, homeaff_data.debtsPaymentAmt);
		createPieChart();
		assigningDataValues();
		//defaulyValues();
	}

	/**
	 * @return void
	 * @category Handles the click of Moderate Button and pie chart color changes.
	 * 
	 */

	@Click(R.id.moderateBtn)
	public void moderateDetails(View v) {
		

		moderateBtn.setBackgroundColor(Color.parseColor("#F8991D"));
		btnLayout.setBackgroundResource(R.drawable.moderate_shape);/*getResources().getDrawable(R.drawable.moderate_shape));*/
		coservativeBtn.setBackgroundResource(R.drawable.conserve_btn);/*getResources().getDrawable(R.drawable.conserve_btn));*/
		aggresiveBtn.setBackgroundResource(R.drawable.aggresive_btn);/*getResources().getDrawable(R.drawable.aggresive_btn));*/
		btnClick = 2;

		// Color Change code Moderate
		textColor1.setBackgroundColor(Color.parseColor("#FDD921"));
		textColor2.setBackgroundColor(Color.parseColor("#FAB14A"));
		textColor3.setBackgroundColor(Color.parseColor("#FEDFCA"));
		textColor4.setBackgroundColor(Color.parseColor("#F38321")); 
		//getSupportFragmentManager().beginTransaction().replace(R.id.chart, chartFragment);
		homeaff_data.pct = MODERATE_PCT;
		setSeriesAdapter();
		//shinobiChart.redrawChart();
		//calculatePercentagePieChartModerate(homeaff_data.totalMonthlyPaymentAmt, homeaff_data.debtsPaymentAmt);
		//createPieChart(shinobiChart);
		//graphDisplay(series);
		createPieChart();
		assigningDataValues();
		//defaulyValues();

	}

	/**
	 * @return void
	 * @category Handles the click of Aggressive Button and pie chart color changes.
	 * 
	 */

	@Click(R.id.aggresiveBtn)
	public void aggressiveDetails(View v) {
		aggresiveBtn.setBackgroundResource(R.drawable.aggresive_btn_on)/*getResources().getDrawable(R.drawable.aggresive_btn_on))*/;
		btnLayout.setBackgroundResource(R.drawable.aggresive_shape)/*getResources().getDrawable(R.drawable.aggresive_shape))*/;
		moderateBtn.setBackgroundColor(Color.parseColor("#000000"));
		coservativeBtn.setBackgroundResource(R.drawable.conserve_btn)/*getResources().getDrawable(R.drawable.conserve_btn))*/;
		btnClick = Constants.THREE;
	/*	mSeries = new CategorySeries("");
		mRenderer = new DefaultRenderer();*/
		//mChartView = null;
		//Color Change code Aggressive
		textColor1.setBackgroundColor(Color.parseColor("#BF2025"));
		textColor2.setBackgroundColor(Color.parseColor("#F9B6AD"));
		textColor3.setBackgroundColor(Color.parseColor("#802C2A"));
		textColor4.setBackgroundColor(Color.parseColor("#ED422E"));
		//getSupportFragmentManager().beginTransaction().replace(R.id.chart, chartFragment);
		homeaff_data.pct = AGGRESSIVE_PCT;
		setSeriesAdapter();
		//shinobiChart.redrawChart();
		//calculatePercentagePieChartAggresive(homeaff_data.totalMonthlyPaymentAmt, homeaff_data.debtsPaymentAmt);
		//createPieChart(shinobiChart);
		//graphDisplay(series);
		
		//defaulyValues();
		createPieChart();
		assigningDataValues();
	}
	
	/**
	 * @return void
	 * @category Handles the toggle of PMI
	 * @author Ramesh Gundala
	 * 
	 */

	@Click(R.id.tbPMI)
	void checkPMI(){
		if (tbPMI.isChecked()){
			homeaff_data.addPMI = true;
			homeaff_data.homePriceAmt = changedValue;
			//mData.downPaymentAmt = stopSeekBarDownPayment;
			
			
		}else{
			homeaff_data.addPMI = false;
			
			
		}
		assigningDataValues();
	}
	
	/**
	 * @return void
	 * @category For handling click of assumptions textview
	 * @author Ramesh Gundala
	 * 
	 */

	@SuppressWarnings("deprecation")
	@Click(R.id.llassumptionBtn)
	public void assumptionsOpen(View v){
		if(ltAssumption.isShown()){
			ltAssumption.setVisibility(View.GONE);
			assumptionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.plus));
		}else{
			ltAssumption.setVisibility(View.VISIBLE);
			assumptionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.minus));
			
			 ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {
			        @Override
			        public void onGlobalLayout() {
			            // Ready, move up
			            //scrollView.fullScroll(View.FOCUS_DOWN);
			        	scrollView.fullScroll(View.FOCUS_DOWN);
			            scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			            
			        }
			    };
				// Wait until my scrollView is ready
				scrollView.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
		}
	}


	


	/**
	 * @return void
	 * @category Handles the text change of annual property taxes
	 * 
	 */

	@AfterTextChange(R.id.edit_annual_prpoerty_taxes)
	void changeAnnualPropTaxes(final Editable s) {
		String annualPropTaxes = s.toString();
		int posDot = annualPropTaxes.indexOf(".");
		if (posDot <= 0)
			return;
		if (annualPropTaxes.length() - posDot - 1 > Constants.THREE) {
			s.delete(posDot + Constants.FOUR, posDot + Constants.FIVE);
		}
		if (!annualPropTaxes.isEmpty()) {

			annualPropertyTaxesAssumption
					.setOnEditorActionListener(new OnEditorActionListener() {
						public boolean onEditorAction(TextView v, int actionId,
								KeyEvent event) {

							if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
									|| (actionId == EditorInfo.IME_ACTION_DONE) || (event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
								if (!s.toString().isEmpty()) {
									DecimalFormat format = new DecimalFormat(
											"0.000");
									v.setText(format.format(Double
											.parseDouble(s.toString())));

								} else {
									v.setText("0.000");
								}
							}
							return false;
						}

					});
			homeaff_data.annualyPropertyTaxRate = Double
					.parseDouble(annualPropTaxes);

		} else {
			homeaff_data.annualyPropertyTaxRate = 0;
		}
		assigningDataValues();
		Log.v("++++++", annualPropTaxes + "!!!!!!");

	}

	

	@AfterTextChange(R.id.edit_annual_homeowner_insurance)
	void changedAnnualHomeIns(final Editable s) {
		String annualHomeIns = s.toString().replaceAll("[^\\d]", "");
		if (!annualHomeIns.isEmpty()) {

			annualHomeownerInsuranceAssumption
					.setOnEditorActionListener(new OnEditorActionListener() {
						public boolean onEditorAction(TextView v, int actionId,
								KeyEvent event) {

							if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
									|| (actionId == EditorInfo.IME_ACTION_DONE) || (event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {

								if (!s.toString().isEmpty()) {
									/*
									 * DecimalFormat format = new DecimalFormat(
									 * "0.000");
									 */
									v.setText(Integer.parseInt(s.toString())+"");
								} else {
									v.setText("0");
								}

							}
							return false;
						}

					});
			homeaff_data.annualyHomeOwnerInsuranceAmt = Double
					.parseDouble(annualHomeIns);
			// edtAnnualHomeIns.setText(addCommasToNumericString(annualHomeIns));

		} else {
			homeaff_data.annualyHomeOwnerInsuranceAmt = 0;
		}
		assigningDataValues();
		Log.v("++++++", annualHomeIns + "!!!!!");

	}

	/**
	 * @return void
	 * @category Handles the text change of annual home insurance
	 * @author Ramesh Gundala
	 * 
	 */
	
	@AfterTextChange(R.id.edit_annual_hoa_dues)
	void changedAnnualHOADues(final Editable s) {
		final String annualHOA = s.toString().replaceAll("[^\\d]", "");
		if (!annualHOA.isEmpty()) {
		
		homeaff_data.annualyHOADuesAmt = Double
				.parseDouble(annualHOA);
		}else{
			homeaff_data.annualyHOADuesAmt = 0;
		}
		annualHOAduesAssumptions
		.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
						|| (actionId == EditorInfo.IME_ACTION_DONE)) {

					if (!s.toString().isEmpty()) {
						v.setText(Integer.parseInt(s.toString())+"");
						
					} else {
						v.setText("0");
					}

				}
				return false;
			}

		});
		//homeaff_data.annualyHOADuesAmt = Double.parseDouble(annualHOA);
		// edtAnnualHOADues.setText(addCommasToNumericString(annualHOA));

		assigningDataValues();
		homeaff_data.calculateHomeAffordability();
		Log.v("++++++", "!!!!!" + annualHOA);

	}

	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	/**
	 * 
	 * @category For setting the adapter for pie chart
	 * @param series
	 * @author Ramesh Gundala
	 */
	private void setSeriesAdapter() {

		Log.d("homeaff_data.monthlyMortgagePaymentAmt", ""
				+ homeaff_data.monthlyMortgagePaymentAmt);
		Log.d("homeaff_data.debtsPaymentAmt", "" + homeaff_data.debtsPaymentAmt);
		Log.d("homeaff_data.monthlyIncomeTaxAmt", ""
				+ homeaff_data.monthlyIncomeTaxAmt);
		Log.d("homeaff_data.monthlyLeftOverAmt", ""
				+ homeaff_data.monthlyLeftOverAmt);

		/*if (!homeaff_data.flag) {*/
		try{
			s1.setValue(homeaff_data.monthlyMortgagePaymentAmt);
			s2.setValue(homeaff_data.debtsPaymentAmt);
			s3.setValue(homeaff_data.monthlyIncomeTaxAmt);
			s4.setValue(homeaff_data.monthlyLeftOverAmt);
			/*DataAdapter<String, Double> dataAdapter = new SimpleDataAdapter<String, Double>();
			dataAdapter.add(new DataPoint<String, Double>("",
					homeaff_data.monthlyMortgagePaymentAmt));
			dataAdapter.add(new DataPoint<String, Double>("",
					(double) homeaff_data.debtsPaymentAmt));
			dataAdapter.add(new DataPoint<String, Double>("",
					(double) homeaff_data.monthlyIncomeTaxAmt));
			dataAdapter.add(new DataPoint<String, Double>("",
					(double) homeaff_data.monthlyLeftOverAmt));
			series.setDataAdapter(dataAdapter);*/
		}
		catch(Throwable e){
			e.printStackTrace();
			
			if(btnClick == Constants.TWO){
				
				leftOverPichart.setText("$ 1,842");
				
			}else if(btnClick == Constants.THREE){
				leftOverPichart.setText("$ 1,571");
			}else{
				leftOverPichart.setText("$ 2,275");
			}
			
		}
		/*} else {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// final String phoneNo =
			// (offers.getOfferPhoneNumber().getPhoneNumber());
			TextView messageText = new TextView(this);
			messageText.setPadding(10, 10, 10, 10);
			messageText.setGravity(Gravity.CENTER);
			messageText.setTextSize(18);
			messageText.setText("The amounts you've entered exceed the maximum allowable debt-to-income ratios. Please check the amounts you entered for income and monthly debts.");
			builder.setCustomTitle(messageText);
			builder.setPositiveButton("Close",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							
							 * Intent intent = new Intent(Intent.ACTION_CALL);
							 * intent.setData(Uri.parse("tel:" + phoneNo));
							 * startActivity(intent);
							 
							// monthyDebtSeekBar.setText((monthyDebtSeekBar.getText().toString()).substring(0,
							// (monthyDebtSeekBar.getText().toString().length()-1)));
						}
					});
			// cancel button with dismiss.

			AlertDialog alert = builder.create();
			if (onlyOnce) {
				alert.show();
				onlyOnce = false;
			}
		}*/
	}


	public void createPieChart() {
		
		// Apply styling to the Pie Series
		//PieSeriesStyle style = series.getStyle();
		if (btnClick == CONSERVATIVE) {
			// Create a PieSeries and give it the data adapter
			SegmentFormatter sf1 = new SegmentFormatter();
			Log.i("Conservative Color","Conservative Color");
			sf1.getFillPaint().setColor(getResources().getColor(R.color.conserv_Piechart_Darkred_green));
			sf1.getOuterEdgePaint().setColor(getResources().getColor(R.color.conserv_Piechart_Darkred_green));
			sf1.getRadialEdgePaint().setColor(Color.WHITE);
			sf1.getRadialEdgePaint().setStrokeWidth(1f);

			sf1.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf1.getOuterEdgePaint().setStrokeWidth(1f);
			sf1.getOuterEdgePaint().setAntiAlias(true);

			SegmentFormatter sf2 = new SegmentFormatter();
			sf2.getOuterEdgePaint().setColor(getResources().getColor(R.color.conserv_Piechart_lightfgreen));
			sf2.getFillPaint().setColor(getResources().getColor(R.color.conserv_Piechart_lightfgreen));
			sf2.getRadialEdgePaint().setColor(Color.WHITE);
			sf2.getRadialEdgePaint().setStrokeWidth(1f);

			sf2.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf2.getOuterEdgePaint().setStrokeWidth(1f);
			sf2.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf3 = new SegmentFormatter();
			sf3.getOuterEdgePaint().setColor(getResources().getColor(R.color.conserv_Piechart_midemungreen));
			sf3.getFillPaint().setColor(getResources().getColor(R.color.conserv_Piechart_midemungreen));
			sf3.getRadialEdgePaint().setColor(Color.WHITE);
			sf3.getRadialEdgePaint().setStrokeWidth(1f);

			sf3.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf3.getOuterEdgePaint().setStrokeWidth(1f);
			sf3.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf4 = new SegmentFormatter();
			sf4.getFillPaint().setColor(getResources().getColor(R.color.conserv_Piechart_green));
			sf4.getOuterEdgePaint().setColor(getResources().getColor(R.color.conserv_Piechart_green));
			sf4.getRadialEdgePaint().setColor(Color.WHITE);
			sf4.getRadialEdgePaint().setStrokeWidth(1f);

			sf4.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf4.getOuterEdgePaint().setStrokeWidth(1f);
			sf4.getOuterEdgePaint().setAntiAlias(true);
			
			pie.removeSeries(s1);
			pie.removeSeries(s2);
			pie.removeSeries(s3);
			pie.removeSeries(s4);
			
			pie.addSeries(s1, sf1);
			pie.addSeries(s4, sf4);
			pie.addSeries(s2, sf2);
			pie.addSeries(s3, sf3);
			
		}else if (btnClick == MODERATE) {
			SegmentFormatter sf1 = new SegmentFormatter();
			Log.i("Moderate Color","Moderate Color");
			sf1.getFillPaint().setColor(getResources().getColor(R.color.moderate_Piechart_yellow));
			sf1.getOuterEdgePaint().setColor(getResources().getColor(R.color.moderate_Piechart_yellow));
			sf1.getRadialEdgePaint().setColor(Color.WHITE);
			sf1.getRadialEdgePaint().setStrokeWidth(1f);

			sf1.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf1.getOuterEdgePaint().setStrokeWidth(1f);
			sf1.getOuterEdgePaint().setAntiAlias(true);

			SegmentFormatter sf2 = new SegmentFormatter();
			sf2.getOuterEdgePaint().setColor(getResources().getColor(R.color.moderate_Piechart_lightpink));
			sf2.getFillPaint().setColor(getResources().getColor(R.color.moderate_Piechart_lightpink));
			sf2.getRadialEdgePaint().setColor(Color.WHITE);
			sf2.getRadialEdgePaint().setStrokeWidth(1f);

			sf2.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf2.getOuterEdgePaint().setStrokeWidth(1f);
			sf2.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf3 = new SegmentFormatter();
			sf3.getOuterEdgePaint().setColor(getResources().getColor(R.color.moderate_Piechart_orange));
			sf3.getFillPaint().setColor(getResources().getColor(R.color.moderate_Piechart_orange));
			sf3.getRadialEdgePaint().setColor(Color.WHITE);
			sf3.getRadialEdgePaint().setStrokeWidth(1f);

			sf3.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf3.getOuterEdgePaint().setStrokeWidth(1f);
			sf3.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf4 = new SegmentFormatter();
			sf4.getFillPaint().setColor(getResources().getColor(R.color.moderate_Piechart_darkOrange));
			sf4.getOuterEdgePaint().setColor(getResources().getColor(R.color.moderate_Piechart_darkOrange));
			sf4.getRadialEdgePaint().setColor(Color.WHITE);
			sf4.getRadialEdgePaint().setStrokeWidth(1f);

			sf4.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf4.getOuterEdgePaint().setStrokeWidth(1f);
			sf4.getOuterEdgePaint().setAntiAlias(true);
			
			pie.removeSeries(s1);
			pie.removeSeries(s2);
			pie.removeSeries(s3);
			pie.removeSeries(s4);
			
			pie.addSeries(s1, sf1);
			pie.addSeries(s4, sf4);
			pie.addSeries(s2, sf2);
			pie.addSeries(s3, sf3);
			
		}else{
			SegmentFormatter sf1 = new SegmentFormatter();
			Log.i("Conservative Color","Conservative Color");
			sf1.getFillPaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_Darkred));
			sf1.getOuterEdgePaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_Darkred));
			sf1.getRadialEdgePaint().setColor(Color.WHITE);
			sf1.getRadialEdgePaint().setStrokeWidth(1f);

			sf1.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf1.getOuterEdgePaint().setStrokeWidth(1f);
			sf1.getOuterEdgePaint().setAntiAlias(true);

			SegmentFormatter sf2 = new SegmentFormatter();
			sf2.getOuterEdgePaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_lightpink));
			sf2.getFillPaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_lightpink));
			sf2.getRadialEdgePaint().setColor(Color.WHITE);
			sf2.getRadialEdgePaint().setStrokeWidth(1f);

			sf2.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf2.getOuterEdgePaint().setStrokeWidth(1f);
			sf2.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf3 = new SegmentFormatter();
			sf3.getOuterEdgePaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_chocalate));
			sf3.getFillPaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_chocalate));
			sf3.getRadialEdgePaint().setColor(Color.WHITE);
			sf3.getRadialEdgePaint().setStrokeWidth(1f);

			sf3.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf3.getOuterEdgePaint().setStrokeWidth(1f);
			sf3.getOuterEdgePaint().setAntiAlias(true);
			
			SegmentFormatter sf4 = new SegmentFormatter();
			sf4.getFillPaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_red));
			sf4.getOuterEdgePaint().setColor(getResources().getColor(R.color.aggresiv_Piechart_red));
			sf4.getRadialEdgePaint().setColor(Color.WHITE);
			sf4.getRadialEdgePaint().setStrokeWidth(1f);

			sf4.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
			sf4.getOuterEdgePaint().setStrokeWidth(1f);
			sf4.getOuterEdgePaint().setAntiAlias(true);
			

			pie.removeSeries(s1);
			pie.removeSeries(s2);
			pie.removeSeries(s3);
			pie.removeSeries(s4);
			
			pie.addSeries(s1, sf1);
			pie.addSeries(s4, sf4);
			pie.addSeries(s2, sf2);
			pie.addSeries(s3, sf3);
	
		}
		
		if(Build.VERSION.SDK_INT > Constants.TEN )
		{
			pie.setRotation(270f);
		}
		//pie.setRotation(270f);
		//committed on 11/02/2014
		//pie.getBorderPaint().setColor(Color.TRANSPARENT);
		//pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
		
		pie.getBorderPaint().setColor(getResources().getColor(R.color.Cream));
		pie.getBackgroundPaint().setColor(getResources().getColor(R.color.Cream));

		pie.getRenderer(PieRenderer.class).setDonutSize(0f,
				PieRenderer.DonutMode.PERCENT);
		pie.redraw();
		
	}

	//Constructor without parameter
	public HomeAffordabilityActivity() {
		super(R.string.home_affordability_title);

	}


	// This method for declaring all the default values 
	public void defaulyValues(){
		// defaultValues
		homeaff_data = new HomeAffCalculator();

		homeaff_data.annualIncomeAmt = Constants.SIXTY_FIVE_THOUSAND_POINT_ZERO; // Annual Income:
		homeaff_data.downPaymentAmt = 0.0; //Down Payment Amount:
		//mData.annualyInterestRate = 3.5; //Mortgage Rate:
		homeaff_data.annualyInterestRate = Constants.FOUR_POINT_TWO_FIVE;//2.00; //Mortgage Rate:
		homeaff_data.monthlyDebtsAmt  = 0.0; //Monthly Debts:

		homeaff_data.annualyPropertyTaxRate = Constants.ONE_POINT_ONE_FOUR; //Estimated Annual Property Taxes:
		homeaff_data.annualyHomeOwnerInsuranceAmt = Constants.EIGHT_HUNDRED; //Estimated Annual Homeowner's Insurance:
		homeaff_data.annualyHOADuesAmt = 0; // Annual Homeowner's Association(HOA) Dues:
		homeaff_data.loanTermYear = Constants.THIRTY; //Loan Term

		homeaff_data.addPMI = true; // Include Private Mortgage Insurance(PMI):(Selected then YES else NO)
	
		homeaff_data.pct = CONSERVATIVE_PCT;
		

		s1 = new Segment("",
				homeaff_data.monthlyMortgagePaymentAmt);
		s2 = new Segment("",
				homeaff_data.debtsPaymentAmt);
		s3 = new Segment("",
				homeaff_data.monthlyIncomeTaxAmt);
		s4 = new Segment("",
				homeaff_data.monthlyLeftOverAmt);
		
		
		annualIncomeSeekBar.setText("65,000");
		Log.i("", "0 ***");
		
		try{
			Log.i("", "try");
			//65000 - 30000 = 35000/5000 = 7
			seeBarAnnualIncome.setProgress(Constants.SEVEN);
			//seeBarAnnualIncome.setProgress((int)(homeaff_data.annualIncomeAmt - 50000) / 5000);  
			
			Log.i("", "1");
           
		}catch(Exception e){
			e.printStackTrace();
		}
		mortagaeRateSeekBar.setText("4.250");
		//seekBarMortgageRate.setProgress((int)((homeaff_data.downPaymentAmt - 2) / 0.125));
		try{
			//4.250 - 2 = 2.250/0.125 = 18
			seekBarMortgageRate.setProgress(Constants.EIGHTEEN);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void assigningDataValues(){
		
		homeaff_data.doHomeAffordabilityCalculation();
		homeaff_data.calculateHomeAffordability();
		//homeaff_data.calculatePrivateMortgageInsuranceAmount();
	
		homePriceHeader.setText("$"+addCommasToNumericString((int)homeaff_data.homePriceAmt+""));
		loanPriceHeader.setText("$"+addCommasToNumericString((int)homeaff_data.loanAmt+""));
		
		monthlyIncomePichart.setText("$"+addCommasToNumericString((int)homeaff_data.monthlyIncomeAmt+""));
		monthlyMortgagePichart.setText("$"+addCommasToNumericString((int)homeaff_data.totalMonthlyPaymentAmt+""));
		debtPaymentPichart.setText("$"+addCommasToNumericString((int)homeaff_data.debtsPaymentAmt+""));
		incomeTaxesPichart.setText("$"+addCommasToNumericString((int)homeaff_data.monthlyIncomeTaxAmt+""));
		
		
		if((int)homeaff_data.monthlyLeftOverAmt == 0){

			if(btnClick == Constants.TWO){
				
				leftOverPichart.setText("$ 1,842");
				
			}else if(btnClick == Constants.THREE){
				leftOverPichart.setText("$ 1,571");
			}else{
				leftOverPichart.setText("$ 2,275");
			}
		}else
		
		leftOverPichart.setText("$"+addCommasToNumericString((int)homeaff_data.monthlyLeftOverAmt+""));
	}
	
	
	/**
	 * @return void
	 * @category For seekbar progress handling of Home Affordability on Annual Income
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * 
	 */
	
	@SeekBarProgressChange(R.id.annual_income_ha)
	void changeProgressAnnualIncome(SeekBar seekBar, int progress,
			boolean fromUser){
		if(!isEditing){
		changedValue = Constants.THIRTY_THUSAND + (progress * Constants.FIVE_THOUSAND);
		homeaff_data.annualIncomeAmt = changedValue;
		
		//homeaff_data.calculateHomeAffordability();
		//homeaff_data.doHomeAffordabilityCalculation();
		Log.i("home price SeekBar"," " + homeaff_data.annualIncomeAmt);
		assigningDataValues();
		
		
		annualIncomeSeekBar.setText(addCommasToNumericString(changedValue + ""));
		//annualIncomeSeekBar.setText(changedValue);
		
		//shinobiChart.redrawChart();

		setSeriesAdapter();
		createPieChart();
		}
		
	}

	@AfterTextChange(R.id.txt_annual_income)
	void changedSeeBarEditTextAnnualIncome(final Editable s) {
      
		
		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");
		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);
			if (s1 < Constants.THIRTY_THUSAND_POINT_ZERO_ZERO) {
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				
				annualIncomeSeekBar.setOnEditorActionListener(new OnEditorActionListener() {
			        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

			            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
			            	s.replace(0, s.length(), "30,000");
			            	
			            }    
			            return false;
			        }

			    });
			}
			if (s1 > Constants.ONE_MILLAN_POINT_ZERO_ZERO) {
				annualIncomeSeekBar.setOnEditorActionListener(null);
				s.replace(0, s.length(), "1,000,000");
			}
			if (s1 >= Constants.THIRTY_THUSAND_POINT_ZERO_ZERO && s1 <= Constants.ONE_MILLAN_POINT_ZERO_ZERO) {
				annualIncomeSeekBar.setOnEditorActionListener(null);
			NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
			((DecimalFormat) nf2).applyPattern("###,###.###");
			s.replace(0, s.length(), nf2.format(s1));
			changedValue = (int) s1;
			try{
			seeBarAnnualIncome.setProgress((int) (s1 - Constants.THIRTY_THUSAND) / Constants.FIVE_THOUSAND);
			}catch(Exception e){
				e.printStackTrace();
			}

			homeaff_data.annualIncomeAmt = changedValue;
			assigningDataValues();
			}
			Log.v("11111111", s1 + "");
		}
		isEditing = false;
    }

	/**
	 * @return void
	 * @category For seekbar progress handling of Home Affordability on Down Payment
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * 
	 */
	@SeekBarProgressChange(R.id.down_payment_ha)
	void changeProgressDownPayment(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (!isEditing) {
			// int changedValue = progress * 5000;
			
			// int downPaymentPercentage = (downPaymentAmt * 100) /
			// changedValue;
			int downPaymentAmt;
			if (progress > 0) {
				 downPaymentAmt = Constants.THIRTY_THUSAND + (progress * Constants.FIVE_THOUSAND);
				homeaff_data.downPaymentAmt = downPaymentAmt;
				homeaff_data.downPaymentAmt = downPaymentAmt;
				Log.i("DownPayValue", downPaymentAmt+ "");
            } else {
            	downPaymentAmt = 0;
            	homeaff_data.downPaymentAmt = 0;
            }
			
			assigningDataValues();
			

			if (downPaymentAmt == 0)
				downPaymentSeekBar.setText("$"
						+ addCommasToNumericString(downPaymentAmt + ""));
			else
				downPaymentSeekBar.setText("$"
						+ downPaymentAmt );
			
		}

	}
	
	@AfterTextChange(R.id.txt_down_payment)
	void changedSeeBarEditText(Editable s) {
        
		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");
		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);
			if (s1 <= Constants.ZERO_POINT_ZERO_ZERO) {
			NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
			((DecimalFormat) nf2).applyPattern("###,###.###");
			s.replace(0, s.length(), nf2.format(s1));
			}
			if (s1 > Constants.ONE_MILLAN_POINT_ZERO_ZERO) {
				s.replace(0, s.length(), "1,000,000");
			}
			if (s1 > Constants.ZERO_POINT_ZERO_ZERO && s1 <= Constants.ONE_MILLAN_POINT_ZERO_ZERO) {
			NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
			((DecimalFormat) nf2).applyPattern("###,###.###");
			s.replace(0, s.length(), nf2.format(s1));
			
			//changedValue1 = (int) s1;
			try{
				seekBardownPaymentS.setProgress((int) (s1 - Constants.THIRTY_THUSAND) / Constants.FIVE_THOUSAND);
			}catch(Exception e){
				e.printStackTrace();
			}

			homeaff_data.downPaymentAmt =s1;
			Log.i("DownPayValue", s1+ "");
			assigningDataValues();
			}
			Log.v("11111111", s1 + "");
			
		}
		isEditing = false;
    }

	/**
	 * @return void
	 * @category For seekbar progress handling of Home Affordability on Mortgage Rate
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * 
	 */

	@SeekBarProgressChange(R.id.mortgage_rate_ha)
	void chnageProgressMortgagePayment(SeekBar seekBar, int progress,
			boolean fromUser){
		if(!isEditing){
		float changedValue = (float) (Constants.TWO + (progress * Constants.ZERO_POINT_ONE_TWO_FIVE));
		homeaff_data.annualyInterestRate = changedValue;
		assigningDataValues();
		//tvMortgageRate.setText(changedValue + "%");
		mortagaeRateSeekBar.setText(String.format("%.3f", changedValue));/*.setText(changedValue + "%");*/
		}
	}
	
	@AfterTextChange(R.id.txt_mortgage_rat)
	void changedSeeBarEditTextMortgage(final Editable s) {
		
		String str = s.toString().replaceAll("%", "");
		
		int posDot = str.indexOf(".");
        if (posDot <= 0) return;
        if (str.length() - posDot - 1 > Constants.THREE)
        {
            s.delete(posDot + Constants.FOUR, posDot + Constants.FIVE);
        }
		if (isEditing)
			return;
		isEditing = true;

		// String str = s.toString();
		//str = s.toString().replaceAll("%", "");
		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);
			if (s1 > Constants.SEVEN_POINT_FIVE) {
				mortagaeRateSeekBar.setOnEditorActionListener(null);
				s.replace(0, s.length(), "7.500");
			}

			//if (s1 > Constants.TWO_POINT_ZERO_ZERO_ZERO && s1 <= Constants.SEVEN_POINT_FIVE_ZERO_ZERO) {
				//mortagaeRateSeekBar
			if (s1 >= 2.00 && s1 <= 7.500) {
				/*mortagaeRateSeekBar
						.setOnEditorActionListener(new OnEditorActionListener() {
							public boolean onEditorAction(TextView v,
									int actionId, KeyEvent event) {

								if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
										|| (actionId == EditorInfo.IME_ACTION_DONE)) {
									if (!s.toString().isEmpty()) {
										DecimalFormat format = new DecimalFormat(
												"0.000");
										v.setText(format.format(Double
												.parseDouble(s.toString())));

									} else {
										v.setText("2.000");
									}
								}
								return false;
							}

				});*/
				seekBarMortgageRate.setProgress((int) ((s1 - Constants.TWO) / Constants.ZERO_POINT_ONE_TWO_FIVE));
				
				homeaff_data.annualyInterestRate =s1;
				assigningDataValues();
				// seekBarMortgageRate.setProgress((int)pr);
			}
			// seeBarAnnualIncome.setProgress(30000+(Integer.parseInt(filtered_str)/5000));
		} else {
			seekBarMortgageRate.setProgress(2);
			assigningDataValues();

		}
		isEditing = false;
	}

	/*@AfterTextChange(R.id.edit_monthly_debt)
	void changeMonthlyDebt() {
		
		String annualPropTaxes = monthyDebtSeekBar.getText().toString();
		if(!annualPropTaxes.isEmpty()){
			homeaff_data.monthlyDebtsAmt = Double.parseDouble(annualPropTaxes);

		}else{
			homeaff_data.monthlyDebtsAmt = 0;
		}
		assigningDataValues();
		Log.v("++++++", annualPropTaxes + "!!!!!!");

		
		shinobiChart.redrawChart();

		setSeriesAdapter(series);
		createPieChart(series);

	}*/
}
