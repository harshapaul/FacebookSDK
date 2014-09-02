package com.lendingtree.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.lendingtree.application.LendingTreeApplication;
import com.lendingtree.model.CustomerAddress;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;
import com.lendingtree.model.PostalAddress;
import com.lendingtree.networkutils.IpAddressAdapter;
import com.lendingtree.networkutils.LoanExplorerAdapter;
import com.lendingtree.networkutils.LoanExplorerOffersAdapter;
import com.lendingtree.networkutils.PostalAddressAdapter;
import com.lendingtree.util.Constants;

@EFragment(R.layout.good_faith_estimate_data)
public class GoodFaithEstimateDataFragment extends Fragment {



	private static final String KEY_CONTENT = "TestFragment:Content";

	private static final String TAG = "GoodFaithEstimateDataFragment";

	private String mContent = "???";
	/*private boolean bPostalCodeRequest=false;
	private boolean bPostalMortgageNegotiatorRequest=false;
	private boolean bPostalMortgageNegotiatorResponseRequest=false;*/

	double value = 0;
	int i = 0;
	String ipAddress;
	Typeface font;
	@App
	LendingTreeApplication application;

	/*@Bean
	MortgageNegotiatorAdapter mortgageNegotiatorAdapter;*/

	@Bean
	IpAddressAdapter ipAddressAdapter;
	
	@Bean
	LoanExplorerAdapter loanExplorerAdapter;
	
	@Bean
	LoanExplorerOffersAdapter loanExplorerOffersAdapter;
	
//	@Bean
//	PostalAddressAdapter postalAddressAdapter;

	//PostalAddress postalAddress;

	CustomerAddress customerAddress;
	
	OfferContainer container;

	Dialog dialog;
	TextView progresTextView;
	@ViewById(R.id.tv_InitialLoanAmount_Header)
	TextView initialLoanAmountHeader;

	@ViewById(R.id.tv_InitialLoanAmount_dollar)
	TextView initialLoanAmountDollar;
	
	@ViewById(R.id.tvPercInterestRate)
	TextView tvPercInterestRate;

	@ViewById(R.id.etInitialLoanAmount)
	EditText initialLoanAmountText;

	@ViewById(R.id.tv_InterestRate_Header)
	TextView interestRateHeader;
	
	@ViewById(R.id.etInterestRate)
	EditText interestRateText;

	@ViewById(R.id.tv_MonthlyPayment_Header)
	TextView monthlyPaymentHeader;

	@ViewById(R.id.tv_MonthlyPayment_dollar)
	TextView monthlyPaymentDollar;

	@ViewById(R.id.etMonthlyPayment)
	EditText monthlyPaymentText;

	@ViewById(R.id.tv_OriginationCharges_Header)
	TextView originationChargesHeader;

	@ViewById(R.id.tv_OriginationCharges_dollar)
	TextView originationChargesDollar;

	@ViewById(R.id.etOriginationCharges)
	EditText originationChargesText;

	@ViewById(R.id.btGetOffers_Good_Faith)
	Button getOffers;

	@ViewById(R.id.llGoodFaithEstimate)
	LinearLayout goodFaith;

	@ViewById(R.id.ivInitialLoanAmount)
	ImageView imageInitialLoanAmount;

	@ViewById(R.id.ivInterestRate)
	ImageView imageInterestRate;

	@ViewById(R.id.ivMonthlyPayment)
	ImageView imageMonthlyPayment;

	@ViewById(R.id.ivOriginationCharges)
	ImageView imageOriginationCharges;

	private int offersPendingCount = 0;

	private long timerStart = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		// View view = inflater.inflate(R.layout.compare_loan_offers, container,
		// false);
		return null;
	}



	// Returns a Fragment Object that can be used to create a Fragment for
	// ViewPage Indicator using Fragment Adapter
	public static GoodFaithEstimateDataFragment newInstance(String content) {
		GoodFaithEstimateDataFragment fragment = new GoodFaithEstimateDataFragment_();
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//		Log.i(TAG,"before dialog.dismiss()");		
		//		dialog.dismiss();
		//		Log.i(TAG,"after dialog.dismiss()");		
	}

	@Click(R.id.tvPercInterestRate)
	void onClickofPercentageView() {
		interestRateText.requestFocus(View.FOCUS_LEFT);
		
		((InputMethodManager) ((MortgageNegotiatorActivity_) getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE))
				.showSoftInput(interestRateText, 0);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();	

		/*        Rect gootFaithHeight = new Rect(); 
        goodFaith.getWindowVisibleDisplayFrame(gootFaithHeight);
        Log.i(TAG,"goodFaith.getHeight() = " + goodFaith.getHeight());
        Log.i(TAG,"goodFaith.getActualHeight() = " + (gootFaithHeight.bottom - gootFaithHeight.top));
		 */        
		goodFaith.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{
				Log.i(TAG,"goodFaith.getRootView().getHeight() = " + goodFaith.getRootView().getHeight());

				Rect gootFaithHeight = new Rect(); 
				goodFaith.getWindowVisibleDisplayFrame(gootFaithHeight);
				Log.i(TAG,"goodFaith.getHeight() = " + (gootFaithHeight.bottom - gootFaithHeight.top));	            

				int heightDiff = goodFaith.getRootView().getHeight() - (gootFaithHeight.bottom - gootFaithHeight.top);
				Log.i(TAG,"heightDiff = " + heightDiff);

				//InputMethodManager mgr = (InputMethodManager) ((MortgageNegotiatorActivity_) getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
				if(heightDiff > 100)
				{
					//keyboard is up
					Log.i("Keyboard UP", "KeyBoard UP");
					((MortgageNegotiatorActivity_) getActivity()).b.setEnabled(false);
					((MortgageNegotiatorActivity_) getActivity()).btRight.setEnabled(false);
					getOffers.setEnabled(false);
					((MortgageNegotiatorActivity_) getActivity()).b.setAlpha(0.5f);
					((MortgageNegotiatorActivity_) getActivity()).btRight.setAlpha(0.5f);
					getOffers.setAlpha(0.5f);

					if(initialLoanAmountText.isFocused())
					{
						if(initialLoanAmountText.getText().toString().trim().equals("0"))
						{
							initialLoanAmountText.setText("");
						}
					}
					/*
	            	else if(interestRateText.isFocused())
	            	{
	            		interestRateText.requestFocus(EditText.FOCUS_BACKWARD);
	            	}
	            	else if (monthlyPaymentText.isFocused()) 
	            	{
						monthlyPaymentText.requestFocus(EditText.FOCUS_BACKWARD);
					}
	            	else
	            	{
	            		originationChargesText.requestFocus(EditText.FOCUS_BACKWARD);
	            	}*/
				}
				else
				{
					Log.i("Keyboard DOWN", "KeyBoard Down");
					((MortgageNegotiatorActivity_) getActivity()).b.setEnabled(true);
					((MortgageNegotiatorActivity_) getActivity()).btRight.setEnabled(true);
					getOffers.setEnabled(true);
					((MortgageNegotiatorActivity_) getActivity()).b.setAlpha(1f);
					((MortgageNegotiatorActivity_) getActivity()).btRight.setAlpha(1f);
					getOffers.setAlpha(1f);
					//initialLoanAmountText.requestFocus(EditText.FOCUS_BACKWARD);
					if(initialLoanAmountText.isFocused())
					{
						if(initialLoanAmountText.getText().toString().trim().equals(""))
						{
							initialLoanAmountText.setText("0");
						}
						else
						{

							if(initialLoanAmountText.getText().toString().trim().contains(","))
							{
								initialLoanAmountText.setText(initialLoanAmountText.getText().toString().trim().replaceAll(",", ""));
							}
							if(initialLoanAmountText.getText().toString().trim().length() > 10)
							{
								initialLoanAmountText.setText("2,147,483,647");
							}
							else
							{
								long userValue = Long.parseLong(initialLoanAmountText.getText().toString());
								if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
								{
									initialLoanAmountText.setText("2,147,483,647");
								}
								else
								{
									NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
									((DecimalFormat) nf2).applyPattern("###,###.###");
									initialLoanAmountText.setText(nf2.format(userValue));
								}
							}
						}
						if(!initialLoanAmountText.getText().toString().equals("") && !initialLoanAmountText.getText().toString().equals("0") )
						{
							monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
						}
						else
						{
							monthlyPaymentText.setText("0");
						}
					}
					if(interestRateText.isFocused())
					{
						if(!interestRateText.getText().toString().trim().equals("") && !interestRateText.getText().toString().trim().equals("0"))
						{
							/*if(!interestRateText.getText().toString().trim().contains("%"))
							{
								interestRateText.setText(interestRateText.getText().toString().trim()+"%");
							}*/
							if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
							{
								monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
							}
						}
						else
						{
							interestRateText.setText("4.250");
							if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
							{
								monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().subSequence(0, interestRateText.getText().toString().trim().length()-1).toString()));
							}

						}
					}
					if(monthlyPaymentText.isFocused())
					{
						if(monthlyPaymentText.getText().toString().trim().equals(""))
						{
							monthlyPaymentText.setText("0");
						}
						else
						{
							if(monthlyPaymentText.getText().toString().trim().contains(","))
							{
								monthlyPaymentText.setText(monthlyPaymentText.getText().toString().trim().replaceAll(",", ""));
							}
							if(monthlyPaymentText.getText().toString().trim().length() > Constants.TEN)
							{
								monthlyPaymentText.setText("2,147,483,647");
							}
							else
							{
								long userValue = Long.parseLong(monthlyPaymentText.getText().toString());
								if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
								{
									monthlyPaymentText.setText("2,147,483,647");
								}
								else
								{
									NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
									((DecimalFormat) nf2).applyPattern("###,###.###");
									monthlyPaymentText.setText(nf2.format(userValue));
								}
							}
						}
					}
					if(originationChargesText.isFocused())
					{
						if(originationChargesText.getText().toString().trim().equals(""))
						{
							originationChargesText.setText("0");
						}
						else
						{
							if(originationChargesText.getText().toString().trim().contains(","))
							{
								originationChargesText.setText(originationChargesText.getText().toString().trim().replaceAll(",", ""));
							}
							if(originationChargesText.getText().toString().trim().length() > Constants.TEN)
							{
								originationChargesText.setText("2,147,483,647");
							}
							else
							{
								long userValue = Long.parseLong(originationChargesText.getText().toString());
								if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
								{
									originationChargesText.setText("2,147,483,647");
								}
								else
								{
									NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
									((DecimalFormat) nf2).applyPattern("###,###.###");
									originationChargesText.setText(nf2.format(userValue));
								}
							}
						}
					}
				}
			}
		});	

		initialLoanAmountText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus && initialLoanAmountText.getText().toString().trim().equals("0"))
				{
					initialLoanAmountText.setText("");
				}
				else
				{
					if(initialLoanAmountText.getText().toString().trim().equals(""))
					{
						initialLoanAmountText.setText("0");
					}
					else
					{

						if(initialLoanAmountText.getText().toString().trim().contains(","))
						{
							initialLoanAmountText.setText(initialLoanAmountText.getText().toString().trim().replaceAll(",", ""));
						}
						if(initialLoanAmountText.getText().toString().trim().length() > Constants.TEN)
						{
							initialLoanAmountText.setText("2,147,483,647");
						}
						else
						{
							long userValue = Long.parseLong(initialLoanAmountText.getText().toString());
							if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
							{
								initialLoanAmountText.setText("2,147,483,647");
							}
							else
							{
								NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
								((DecimalFormat) nf2).applyPattern("###,###.###");
								initialLoanAmountText.setText(nf2.format(userValue));
							}
						}
					}
					if(!initialLoanAmountText.getText().toString().equals("") && !initialLoanAmountText.getText().toString().equals("0") )
					{
						monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
					}
					else
					{
						monthlyPaymentText.setText("0");
					}
				}
			}
		});
		interestRateText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{

				}
				else 
				{
					if(!interestRateText.getText().toString().trim().equals("") && !interestRateText.getText().toString().trim().equals("0"))
					{
						/*if(!interestRateText.getText().toString().trim().contains("%"))
						{
							interestRateText.setText(interestRateText.getText().toString().trim()+"%");
						}*/
						if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
						{
							monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
						}
					}
					else
					{
						interestRateText.setText("4.250");
						if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
						{
							monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().subSequence(0, interestRateText.getText().toString().trim().length()-1).toString()));
						}

					}
				}
			}
		});
		monthlyPaymentText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus && monthlyPaymentText.getText().toString().trim().equals("0"))
				{
					monthlyPaymentText.setText("");
				}
			}
		});
		originationChargesText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus && originationChargesText.getText().toString().equals("0"))
				{
					originationChargesText.setText("");
				}
			}
		});
		initialLoanAmountText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) 
				{
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					if(initialLoanAmountText.getText().toString().trim().equals(""))
					{
						initialLoanAmountText.setText("0");
					}
					else
					{
						if(initialLoanAmountText.getText().toString().trim().contains(","))
						{
							initialLoanAmountText.setText(initialLoanAmountText.getText().toString().trim().replaceAll(",", ""));
						}
						if(initialLoanAmountText.getText().toString().trim().length() > Constants.TEN)
						{
							initialLoanAmountText.setText("2,147,483,647");
						}
						else
						{
							long userValue = Long.parseLong(initialLoanAmountText.getText().toString());
							if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
							{
								initialLoanAmountText.setText("2,147,483,647");
							}
							else
							{
								NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
								((DecimalFormat) nf2).applyPattern("###,###.###");
								initialLoanAmountText.setText(nf2.format(userValue));
							}
						}
					}
					if(!initialLoanAmountText.getText().toString().equals("") && !initialLoanAmountText.getText().toString().equals("0") )
					{
						monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
					}
					else
					{
						monthlyPaymentText.setText("0");
					}
					//initialLoanAmountText.clearFocus();
					return false;
				case EditorInfo.IME_ACTION_NEXT:
					// next stuff
					break;
				default:
					return false;
				}
				return false;
			}
		});
		interestRateText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) 
				{
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					if(!interestRateText.getText().toString().trim().equals("") && !interestRateText.getText().toString().trim().equals("0"))
					{
						/*if(!interestRateText.getText().toString().trim().contains("%"))
						{
							interestRateText.setText(interestRateText.getText().toString().trim()+"%");
						}*/
						if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
						{
							monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
						}
					}
					else
					{
						interestRateText.setText("4.250");
						if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
						{
							monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().subSequence(0, interestRateText.getText().toString().trim().length()-1).toString()));
						}

					}
					//interestRateText.clearFocus();
					return false;
				case EditorInfo.IME_ACTION_NEXT:
					// next stuff
					break;
				default:
					return false;
				}
				return false;
			}
		});
		monthlyPaymentText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) 
				{
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					if(monthlyPaymentText.getText().toString().trim().equals(""))
					{
						monthlyPaymentText.setText("0");
					}
					else
					{
						if(monthlyPaymentText.getText().toString().trim().contains(","))
						{
							monthlyPaymentText.setText(monthlyPaymentText.getText().toString().trim().replaceAll(",", ""));
						}
						if(monthlyPaymentText.getText().toString().trim().length() > Constants.TEN)
						{
							monthlyPaymentText.setText("2,147,483,647");
						}
						else
						{
							long userValue = Long.parseLong(monthlyPaymentText.getText().toString());
							if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
							{
								monthlyPaymentText.setText("2,147,483,647");
							}
							else
							{
								NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
								((DecimalFormat) nf2).applyPattern("###,###.###");
								monthlyPaymentText.setText(nf2.format(userValue));
							}
						}
					}
					return false;
				case EditorInfo.IME_ACTION_NEXT:
					// next stuff
					break;
				default:
					return false;
				}
				return false;
			}
		});

		originationChargesText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) 
				{
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					if(originationChargesText.getText().toString().trim().equals(""))
					{
						originationChargesText.setText("0");
					}
					else
					{
						if(originationChargesText.getText().toString().trim().contains(","))
						{
							originationChargesText.setText(originationChargesText.getText().toString().trim().replaceAll(",", ""));
						}
						if(originationChargesText.getText().toString().trim().length() > Constants.TEN)
						{
							originationChargesText.setText("2,147,483,647");
						}
						else
						{
							long userValue = Long.parseLong(originationChargesText.getText().toString());
							if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
							{
								originationChargesText.setText("2,147,483,647");
							}
							else
							{
								NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
								((DecimalFormat) nf2).applyPattern("###,###.###");
								originationChargesText.setText(nf2.format(userValue));
							}
						}
					}
					return false;
				case EditorInfo.IME_ACTION_NEXT:
					// next stuff
					break;
				default:
					return false;
				}
				return false;
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@AfterViews
	void start() {
		font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		initialLoanAmountHeader.setTypeface(font);
		initialLoanAmountDollar.setTypeface(font);
		initialLoanAmountText.setTypeface(font);
		interestRateHeader.setTypeface(font);
		interestRateText.setTypeface(font);
		monthlyPaymentHeader.setTypeface(font);
		monthlyPaymentDollar.setTypeface(font);
		monthlyPaymentText.setTypeface(font);
		originationChargesHeader.setTypeface(font);
		originationChargesDollar.setTypeface(font);
		originationChargesText.setTypeface(font);
		tvPercInterestRate.setTypeface(font);
	}

	@Click(R.id.btGetOffers_Good_Faith)
	void onGetOffersClick() {

		if(monthlyPaymentText.getText().toString().equals("0"))
		{
			
		}
		
		if(initialLoanAmountText.isFocused())
		{
			if(initialLoanAmountText.getText().toString().trim().equals(""))
			{
				initialLoanAmountText.setText("0");
			}
			else
			{

				if(initialLoanAmountText.getText().toString().trim().contains(","))
				{
					initialLoanAmountText.setText(initialLoanAmountText.getText().toString().trim().replaceAll(",", ""));
				}
				if(initialLoanAmountText.getText().toString().trim().length() > Constants.TEN)
				{
					initialLoanAmountText.setText("2,147,483,647");
				}
				else
				{
					long userValue = Long.parseLong(initialLoanAmountText.getText().toString());
					if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
					{
						initialLoanAmountText.setText("2,147,483,647");
					}
					else
					{
						NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
						((DecimalFormat) nf2).applyPattern("###,###.###");
						initialLoanAmountText.setText(nf2.format(userValue));
					}
				}
			}
			if(!initialLoanAmountText.getText().toString().equals("") && !initialLoanAmountText.getText().toString().equals("0") )
			{
				monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
			}
			else
			{
				monthlyPaymentText.setText("0");
			}
		}
		if(interestRateText.isFocused())
		{
			if(!interestRateText.getText().toString().trim().equals("") && !interestRateText.getText().toString().trim().equals("0"))
			{
				/*if(!interestRateText.getText().toString().trim().contains("%"))
				{
					interestRateText.setText(interestRateText.getText().toString().trim()+"%");
				}*/
				if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
				{
					monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().toString()));
				}
			}
			else
			{
				interestRateText.setText("4.250");
				if(!initialLoanAmountText.getText().toString().trim().equals("") && !initialLoanAmountText.getText().toString().trim().equals("0"))
				{
					monthlyPaymentText.setText(calculateMonthlyPayment(initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", ""), interestRateText.getText().toString().trim().subSequence(0, interestRateText.getText().toString().trim().length()-1).toString()));
				}

			}
		}
		if(monthlyPaymentText.isFocused())
		{
			if(monthlyPaymentText.getText().toString().trim().equals(""))
			{
				monthlyPaymentText.setText("0");
			}
			else
			{
				if(monthlyPaymentText.getText().toString().trim().contains(","))
				{
					monthlyPaymentText.setText(monthlyPaymentText.getText().toString().trim().replaceAll(",", ""));
				}
				if(monthlyPaymentText.getText().toString().trim().length() > Constants.TEN)
				{
					monthlyPaymentText.setText("2,147,483,647");
				}
				else
				{
					long userValue = Long.parseLong(monthlyPaymentText.getText().toString());
					if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
					{
						monthlyPaymentText.setText("2,147,483,647");
					}
					else
					{
						NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
						((DecimalFormat) nf2).applyPattern("###,###.###");
						monthlyPaymentText.setText(nf2.format(userValue));
					}
				}
			}
		}
		if(originationChargesText.isFocused())
		{
			if(originationChargesText.getText().toString().trim().equals(""))
			{
				originationChargesText.setText("0");
			}
			else
			{
				if(originationChargesText.getText().toString().trim().contains(","))
				{
					originationChargesText.setText(originationChargesText.getText().toString().trim().replaceAll(",", ""));
				}
				if(originationChargesText.getText().toString().trim().length() > Constants.TEN)
				{
					originationChargesText.setText("2,147,483,647");
				}
				else
				{
					long userValue = Long.parseLong(originationChargesText.getText().toString());
					if(userValue > Constants.TWO_ONE_FOUR_SEVEN_FOUR_EIGHT_THREE_SIX_FOUR_SEVEN)
					{
						originationChargesText.setText("2,147,483,647");
					}
					else
					{
						NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
						((DecimalFormat) nf2).applyPattern("###,###.###");
						originationChargesText.setText(nf2.format(userValue));
					}
				}
			}
		}
		
		
		String loanAmount = initialLoanAmountText.getText().toString().trim().replaceAll("[^\\d]", "");
		String interestRate = interestRateText.getText().toString().trim().substring(0, interestRateText.getText().toString().length()-1);
		String monthlyPayment = monthlyPaymentText.getText().toString().trim().replaceAll("[^\\d]", "");
		String originationCharges = originationChargesText.getText().toString().trim().replaceAll("[^\\d]", "");
		
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY);
		title.setTypeface(font);
		//title.setText(getResources().getText(R.string.invalid_error));
		title.setText(getResources().getText(R.string.invalid_data));
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setCustomTitle(title).setCancelable(false);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		
		
		if (loanAmount.equals("") || interestRate.equals("")
				|| monthlyPayment.equals("") || originationCharges.equals("")) {

			builder.show();

		}
		else if(loanAmount.equals("0") || interestRate.equals("0")
				|| monthlyPayment.equals("0"))
		{		
			builder.show();
		}
		else {
			if (((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
					.getRequestedLoanTypeId().equals("1")) {
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMortgageBalance("0");
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMortgageInterestRatePercent(interestRate);
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMonthlyPayment(monthlyPayment);
				// ((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyZipCode("28277");
				// ((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice("400000");
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setEstimatedDownPayment(Double.parseDouble(((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPurchasePrice())-Double.parseDouble(loanAmount)+"");
				// ((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue("400000");
			} else {
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMortgageBalance(loanAmount);
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMortgageInterestRatePercent(interestRate);
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setCurrentMonthlyPayment(monthlyPayment);
				// ((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyZipCode("28277");
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setEstimatedPurchasePrice("0");
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.setEstimatedDownPayment("0");
			}
			((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator.setOriginationCharges(originationCharges);
			ipAddressRequestTask();
		}
	}

	@Background
	void ipAddressRequestTask()
	{
		startProgress();
		
		setTimerWithIntervals("Comparing  your offer ",2000L);
		setTimerWithIntervals("Narrowing your results",4000L);
		setTimerWithIntervals("Sorry, this is taking a bit\nlonger than usual but\nwe are almost done",6000L);
		
		String response = ipAddressAdapter.callAddressRequestTask();
		if(response == null)
		{
			stopProgress();
		}
		else
		{
			try {
				JSONObject obj;obj = new JSONObject(response);
				customerAddress = new CustomerAddress(obj.getString("AreaCode"), obj.getString("City"), obj.getString("CountryCode"), obj.getString("CountryName"), obj.getString("DMACode"), obj.getString("Latitude"), obj.getString("Longitude"), obj.getString("MetroCode"), obj.getString("PostalCode"), obj.getString("Region"), obj.getString("RegionName"), obj.getString("ipAddress"));
				Log.i("Result", customerAddress.getIpAddress());
				ipAddress = customerAddress.getIpAddress();
//				postalCodeRequestTask();
//				
				mortgageNegotiatorRequestTask();
				timerStart  = System.currentTimeMillis();
				Log.d("Response", ipAddress);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@UiThread
	void startProgress() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.progress_layout);
		progresTextView = (TextView) dialog.findViewById(R.id.tvProgressText);
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		progresTextView.setTypeface(font);
		//progresTextView.setText("Analyzing Your Data");
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.Dialog_Green));
		drawable.setAlpha(Constants.HUNDRED);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

	@UiThread
	void stopProgress() {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
		if (application.checkForNetwork == 1) {

			TextView title = new TextView(getActivity());
			title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
			title.setGravity(Gravity.CENTER);
			title.setTextSize(Constants.TWENTY_THREE);
			title.setTypeface(font);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			title.setText("Error");
			builder.setCustomTitle(title)
			.setMessage("No Internet Connectivity!")
			.setCancelable(false)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {

					dialog.dismiss();

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			TextView textView = (TextView) alert.findViewById(android.R.id.message);
			textView.setTypeface(font);
		}
//		else if(postalAddress == null || postalAddress.getPostalCode() == null)
//		{
//			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//			builder.setMessage(getResources().getText(R.string.invalid_zip_code)).setCancelable(false);
//			builder.setPositiveButton("OK", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//
//				}
//			});
//			builder.show();
//		}
	}

	@UiThread
	void onNoOffers(String message)
	{
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY_THREE);
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		title.setText("Information");
		title.setTypeface(font);
		builder.setCustomTitle(title);
		builder.setMessage(message).setCancelable(false);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startProgress();
				loanOffersRequestTask();

			}
		});
		builder.setNegativeButton("Cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		
		AlertDialog alert = builder.create();
		alert.show();
		TextView textView = (TextView) alert.findViewById(android.R.id.message);
		textView.setTypeface(font);
		
	}

	@UiThread
	void netwokDataLoad()
	{
		if (container != null && container.getOffers().size() > 0) {
			ArrayList<Offers> tempList = new ArrayList<Offers>();

			int loanProgramID = Integer.parseInt(((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
					.getRequestedLoanProgramIds());

			int totalLoanTerm = 0;

			switch (loanProgramID) {
			case 1:
				totalLoanTerm = Constants.THIRTY * Constants.TWELVE;
				break;

			case 2:
				totalLoanTerm = Constants.TWENTY * Constants.TWELVE;
				break;

			case 3:
				totalLoanTerm = Constants.FIFTEEN * Constants.TWELVE;
				break;

			case 4:
				totalLoanTerm = Constants.TEN * Constants.TWELVE;
				break;

			case 5:
				totalLoanTerm = Constants.SEVEN * Constants.TWELVE;
				break;

			case 6:
				totalLoanTerm = Constants.FIVE * Constants.TWELVE;
				break;

			case 7:
				totalLoanTerm = Constants.THREE * Constants.TWELVE;
				break;

			default:
				break;

			}

			for (int i = 0; i < container.getOffers().size(); i++) {
				double points = container.getOffers().get(i).getPoints();
				int fixedRatePeriodMonths = container.getOffers().get(i)
						.getFixedRatePeriodMonths();

				if ((points < 1.0 && fixedRatePeriodMonths == totalLoanTerm)) {

					tempList.add(container.getOffers().get(i));
				}

			}
			
			if (tempList.size() > 0) {
				Intent intent = new Intent(getActivity(), MortgageNegotiatorChartActivity_.class);
				intent.putExtra("MortgageData", tempList);
				intent.putExtra("offersdata", container);
				intent.putExtra("ipaddress", ipAddress);
				intent.putExtra("UserInput", ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator);
				startActivity(intent);
				dialog.dismiss();
			}else{
				stopProgress();
				onNoOffers(getResources().getText(R.string.no_loan_offers)+"");
			}
		}
		
		
	}

//	@Background
//	void postalCodeRequestTask()
//	{
//		postalAddress = postalAddressAdapter.callPostalAddressRequestTask(((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator.getPropertyZipCode());
//		if(postalAddress == null || postalAddress.getPostalCode() == null)
//		{
//			stopProgress();
//		}
//		else
//		{
//			Log.i("Postal Address", postalAddress.getCity());
//			mortgageNegotiatorRequestTask();
//			timerStart  = System.currentTimeMillis();
//			
//
//		}
//	}

	@Background
	void mortgageNegotiatorRequestTask() {
		//offersPendingCount ++;
		/*((MortgageNegotiatorActivity_) getActivity()).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (offersPendingCount > 1) {
					progresTextView.setText("Fetching your offers");
				} else {
					progresTextView.setText("Sending your information");
				}
				
			}
		});*/
		Log.i("getRequestedLoanTypeId",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getRequestedLoanTypeId());
		Log.i("getRequestedLoanProgramIds",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getRequestedLoanProgramIds());
		Log.i("getPropertyTypeId",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getPropertyTypeId());
		Log.i("getEstimatedPropertyValue",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getEstimatedPropertyValue());
		Log.i("getPropertyZipCode",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getPropertyZipCode());
		Log.i("getCurrentMonthlyPayment",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getCurrentMonthlyPayment());
		Log.i("getEstimatedCreditScoreBandId",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getEstimatedCreditScoreBandId());
		Log.i("getCurrentMortgageInterestRate",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getCurrentMortgageInterestRatePercent());
		Log.i("getCurrentMortgageBalance",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getCurrentMortgageBalance());
		Log.i("getEstimatedPurchasePrice",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getEstimatedPurchasePrice());
		Log.i("getEstimatedDownPayment",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getEstimatedDownPayment());
		Log.i("getEstimatedCreditScoreBandId",
				((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
				.getEstimatedCreditScoreBandId());
		/*container = mortgageNegotiatorAdapter
				.callContainerRequestTask(
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanTypeId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyTypeId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPropertyValue(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyZipCode(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMonthlyPayment(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedCreditScoreBandId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageInterestRate(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageBalance(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPurchasePrice(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedDownPayment(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanProgramIds());*/
		
		container = loanExplorerAdapter.callNegotiatorRequestTask(((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanTypeId(), ipAddress, ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyTypeId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyZipCode(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getVeteranStatusTypeId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyUseId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanProgramIds(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPrepaymentPenaltyAccepted(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getBankruptcyDischargedId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getForeclosureDischargedId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getSecondLienMortgageBalance(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPurchasePrice(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedDownPayment(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPropertyValue(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageBalance(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedCreditScoreBandId(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMonthlyPayment(), ((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageInterestRatePercent());

		if (container == null || container.isOffersPending()) 
		{
			loanOffersRequestTask();
			/*
			if (container != null && offersPendingCount <= Constants.THREE) {
				Log.v(TAG, "Inside onLoanExplorerRequestTask if"
						+ container + container.isOffersPending());
				if (timerStart + Constants.EIGHT_THOUSAND >= System.currentTimeMillis()) {
					((MortgageNegotiatorActivity_) getActivity()).runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {

	                    	if (timerStart + Constants.THREE_THOUSAND >= System.currentTimeMillis()) {
	    						progresTextView.setText("Collecting your information");
	    					} else if (timerStart + Constants.THREE_THOUSAND < System.currentTimeMillis() && timerStart + Constants.SIX_THOUSAND >= System.currentTimeMillis()) {
	    						progresTextView.setText("Sending your information");
	    					} else {
	    						progresTextView.setText("Fetching your offers");
	    					}
	                    	 if (offersPendingCount >= Constants.ONE) {
	 							new Handler().postDelayed(new Runnable() {
	 							    public void run () {
	 							    	Log.v(TAG, "COming>>>>>Inside"+System.currentTimeMillis());
	 							    	mortgageNegotiatorRequestTask();
	 							    }
	 							}, 1500); //1.5 seconds delay 
	 						} else {
	 							mortgageNegotiatorRequestTask();
	 						}
	                 	
	                    }
	                });
						
				} else {
					//offersPendingCount = 0;
					stopProgress();
					// No Offers Available
					onNoOffers(getResources().getText(R.string.no_loan_offers)
							+ "");
				}
				
			} else if (container == null && offersPendingCount <= Constants.THREE) {
				offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				onNoOffers(getResources().getText(R.string.error_occured)
						+ "");
			} else {
				//offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				onNoOffers(getResources().getText(R.string.no_loan_offers)
						+ "");
			}
			
		
		*/}else {
			if (container.getOffers() != null && container.getOffers().size() > 0) {
				Log.v(TAG, "Inside onLoanExplorerRequestTask if");
				// stopProgress();
				offersPendingCount = 0;
				netwokDataLoad();
				
			} else {
				/*Log.v(TAG, "Inside onLoanExplorerRequestTask else "
						+ container.getOffers().size());*/
				stopProgress();
				onNoOffers(getResources().getText(R.string.no_loan_offers)
						+ "");
				// netwokDataLoad();
			}
		
		}

	}

	private void setTimerWithIntervals(final String message, long time) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				((MortgageNegotiatorActivity_) getActivity()).runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progresTextView.setText(message);
						
						/*if (offersPendingCount > 1) {
							progresTextView.setText("Fetching your offers");
						} else {
							progresTextView.setText("Sending your information");
						}*/
						
						
					}
				});
					
			}
		}, time);
		
	}
	
	@Background
	void loanOffersRequestTask()
	{
		offersPendingCount++ ;
		Log.v(TAG, container.getQuotesId()+"--------Quotes ID");
		container = loanExplorerOffersAdapter.callLoanOffersRequestTask(container.getQuotesId());
		if (container == null || container.isOffersPending()) 
		{
			if (container != null && offersPendingCount <= Constants.THREE) {
				Log.v(TAG, "Inside loanOffersRequestTask if"
						+ container + container.isOffersPending());
				Log.v(TAG, "TIMER---Inside"+System.currentTimeMillis());
				if (timerStart + Constants.EIGHT_THOUSAND >= System.currentTimeMillis()) {
					((MortgageNegotiatorActivity_) getActivity()).runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                    	
	                    	/*if (timerStart + Constants.THREE_THOUSAND >= System.currentTimeMillis()) {
	    						progresTextView.setText("Collecting your information");
	    					} else if (timerStart + Constants.THREE_THOUSAND < System.currentTimeMillis() && timerStart + Constants.SIX_THOUSAND >= System.currentTimeMillis()) {
	    						progresTextView.setText("Sending your information");
	    					} else {
	    						progresTextView.setText("Fetching your offers");
	    					}*/
	                    	 if (offersPendingCount >= Constants.ONE) {
	 							new Handler().postDelayed(new Runnable() {
	 							    public void run () {
	 							    	Log.v(TAG, "COming>>>>>Inside"+System.currentTimeMillis());
	 							
	 							    	loanOffersRequestTask();
	 							    }
	 							}, 1500); //1.5 seconds delay 
	 						} else {
	 							
	 							//offerContainer = loanExplorerOffersAdapter.callLoanOffersRequestTask(offerContainer.getQuotesId());
	 							loanOffersRequestTask();
	 						}
	                    }
	                   
	                });
					
					
				} else {
					if (container.getOffers() != null && container.getOffers().size() > 0) {
						Log.v(TAG, "Inside onLoanExplorerRequestTask if");
						// stopProgress();
						offersPendingCount = 0;
						netwokDataLoad();
						
					} else {
						/*Log.v(TAG, "Inside onLoanExplorerRequestTask else "
								+ container.getOffers().size());*/
						stopProgress();
						onNoOffers(getResources().getText(R.string.no_loan_offers)
								+ "");
						// netwokDataLoad();
					}
				
				}
				
			} else if (container == null && offersPendingCount <= Constants.THREE) {
				
				offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				onNoOffers(getResources().getText(R.string.error_occured)
						+ "");
			} else {
				//offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				onNoOffers(getResources().getText(R.string.no_loan_offers)
						+ "");
			}
			
		}else {
			if (container.getOffers() != null && container.getOffers().size() > 0) {
				Log.v(TAG, "Inside onLoanExplorerRequestTask if");
				// stopProgress();
				offersPendingCount = 0;
				netwokDataLoad();
				
			} else {
				/*Log.v(TAG, "Inside onLoanExplorerRequestTask else "
						+ container.getOffers().size());*/
				stopProgress();
				onNoOffers(getResources().getText(R.string.no_loan_offers)
						+ "");
				// netwokDataLoad();
			}
		
		}
		
	}
	/*@Background
	void mortgageNegotiatorResponseRequestTask() {
		container = mortgageNegotiatorAdapter
				.callContainerResponseRequestTask(
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanTypeId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyTypeId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPropertyValue(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getPropertyZipCode(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMonthlyPayment(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedCreditScoreBandId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageInterestRatePercent(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getCurrentMortgageBalance(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedPurchasePrice(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getEstimatedDownPayment(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getSearchRequestId(),
						((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator
						.getRequestedLoanProgramIds());
		if (container != null) {
			Log.i("Response Result", container.getRequestDetail()
					.getSearchRequestId());
			//Log.i("Dynamic", container.getLenders().get("28").getNmlsId());
			// call Intent
						Intent intent = new Intent(getActivity(),
					MortgageNegotiatorChartActivity_.class);
			intent.putExtra("MortgageData", container);
			intent.putExtra(
					"UserInput",
					((MortgageNegotiatorActivity_) getActivity()).mortgageNegotiator);
			startActivity(intent);
			 		
			//			bPostalMortgageNegotiatorResponseRequest=true;
			//			if( bPostalMortgageNegotiatorRequest==true && bPostalMortgageNegotiatorResponseRequest==true && bPostalCodeRequest==true)
			//			{
			//				stopProgress();
			//				bPostalMortgageNegotiatorRequest=false;
			//				bPostalMortgageNegotiatorResponseRequest=false; 
			//				bPostalCodeRequest=false;
			if (container.getOffers().size() != 0 ) 
			{

				netwokDataLoad();
			} else {
				stopProgress();
				onNoOffers();
			}

		}
		else {
			stopProgress();
			Log.i("NULL", "NULL");
		}
	}*/

	public String calculateMonthlyPayment(String loanAmount,String interestRate)
	{
		Log.i("interest", interestRate);
		double loanAmt = Double.parseDouble(loanAmount);

		String interest = interestRate.substring(0, interestRate.length());

		double newInterest = Double.valueOf(interest);

		double monthlyInterestRate = newInterest / (Constants.TWELVE_POINT_ZERO * Constants.HUNDRED);

		double numberofLoanTerms = Constants.THIRTY * Constants.TWELVE;

		double powerVal = Math.pow((1.0 + monthlyInterestRate), numberofLoanTerms) ;

		double numerator = monthlyInterestRate * powerVal;

		double denominator = powerVal - 1;

		double total = loanAmt * (numerator/denominator);

		return (int)Math.round(total)+"";
	}

	@Click(R.id.ivInitialLoanAmount)
	void imageInitialLoanAmountClick()
	{
		dialog = new Dialog(((MortgageNegotiatorActivity_) getActivity())) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				this.dismiss();
			    return true;
			}
		};
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_layout);
		dialog.setCancelable(true);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.ivSample);
		imageView.setImageResource(R.drawable.initialloanamount);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(android.graphics.Color.BLACK);
		drawable.setAlpha(Constants.ONE_TWENTY_EIGHT);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

	@Click(R.id.ivInterestRate)
	void imageInterestRateClick()
	{
		dialog = new Dialog(((MortgageNegotiatorActivity_) getActivity())) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				this.dismiss();
			    return true;
			}
		};
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_layout);
		dialog.setCancelable(true);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.ivSample);
		imageView.setImageResource(R.drawable.interestrate);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(android.graphics.Color.BLACK);
		drawable.setAlpha(Constants.ONE_TWENTY_EIGHT);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

	@Click(R.id.ivMonthlyPayment)
	void imageMonthlyPaymentClick()
	{
		dialog = new Dialog(((MortgageNegotiatorActivity_) getActivity())) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				this.dismiss();
			    return true;
			}
		};
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_layout);
		dialog.setCancelable(true);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.ivSample);
		imageView.setImageResource(R.drawable.monthlypayment);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(android.graphics.Color.BLACK);
		drawable.setAlpha(Constants.ONE_TWENTY_EIGHT);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

	@Click(R.id.ivOriginationCharges)
	void imageOriginationCharges()
	{
		dialog = new Dialog(((MortgageNegotiatorActivity_) getActivity())) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				// TODO Auto-generated method stub
				this.dismiss();
			    return true;
			}
		};
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.image_layout);
		dialog.setCancelable(true);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.ivSample);
		imageView.setImageResource(R.drawable.originationcharges);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(android.graphics.Color.BLACK);
		drawable.setAlpha(Constants.ONE_TWENTY_EIGHT);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

}
