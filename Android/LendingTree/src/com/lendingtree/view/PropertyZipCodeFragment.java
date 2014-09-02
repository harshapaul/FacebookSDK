package com.lendingtree.view;

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

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.lendingtree.application.LendingTreeApplication;
import com.lendingtree.model.CustomerAddress;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.PostalAddress;
import com.lendingtree.networkutils.IpAddressAdapter;
import com.lendingtree.networkutils.LoanExplorerAdapter;
import com.lendingtree.networkutils.LoanExplorerOffersAdapter;
import com.lendingtree.networkutils.PostalAddressAdapter;
import com.lendingtree.util.Constants;

@EFragment(R.layout.property_zip_code)
public class PropertyZipCodeFragment extends Fragment {

	@App
	LendingTreeApplication application;

	@Bean
	IpAddressAdapter ipAddressAdapter;

	@Bean
	PostalAddressAdapter postalAddressAdapter;

	@Bean
	LoanExplorerAdapter loanExplorerAdapter;

	@Bean
	LoanExplorerOffersAdapter loanExplorerOffersAdapter;

	CustomerAddress customerAddress;

	String ipAddress;
	long timerStart = 0;
	PostalAddress postalAddress;

	OfferContainer offerContainer;

	Dialog dialog;

	private static final String KEY_CONTENT = "TestFragment:Content";

	private static final String TAG = "PropertyZipCodeFragment";

	private String mContent = "???";

	@ViewById(R.id.tvZipHeader)
	TextView zipHeader;
	
	@ViewById(R.id.etZip)
	EditText zipCode;

	@ViewById(R.id.btGetOffers)
	Button getOffers;
	
	@ViewById(R.id.llZipCode)
	LinearLayout llZipCode;
	
	Typeface font;
	private int offersPendingCount = 0;
	TextView progresTextView ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.compare_loan_offers, container, false);
		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static PropertyZipCodeFragment newInstance(String content)
	{
		PropertyZipCodeFragment fragment = new PropertyZipCodeFragment_(); 
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}
	
	@AfterViews
	void start()
	{
		font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		zipHeader.setTypeface(font);
		zipCode.setTypeface(font);
		if (!(((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedPropertyValue().equals("0"))) {
			application.setPropertyValue(((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedPropertyValue());
		}
		llZipCode.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				Rect gootFaithHeight = new Rect();
				llZipCode.getWindowVisibleDisplayFrame(gootFaithHeight);
				
				int heightDiff = llZipCode.getRootView()
						.getHeight()
						- (gootFaithHeight.bottom - gootFaithHeight.top);
				if (((LoanExplorerActivity_) getActivity()).b != null && ((LoanExplorerActivity_) getActivity()).btRight != null && getOffers != null ) {
					if (heightDiff > 100) {
						((LoanExplorerActivity_) getActivity()).b.setEnabled(false);
						((LoanExplorerActivity_) getActivity()).btRight.setEnabled(false);
						((LoanExplorerActivity_) getActivity()).btRight.setAlpha(0.5f);
						((LoanExplorerActivity_) getActivity()).b.setAlpha(0.5f);
						getOffers.setEnabled(false);
						getOffers.setAlpha(0.5f);
						// keyboard is up
											
					} else {
						((LoanExplorerActivity_) getActivity()).b.setEnabled(true);
						((LoanExplorerActivity_) getActivity()).btRight.setEnabled(true);
						((LoanExplorerActivity_) getActivity()).btRight.setAlpha(1f);
						((LoanExplorerActivity_) getActivity()).b.setAlpha(1f);
						getOffers.setEnabled(true);
						getOffers.setAlpha(1f);
					}
				}
				
				
			}
		});
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
		
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Click(R.id.btGetOffers)
	void onGetOffersClick()
	{
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY);
		title.setTypeface(font);
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		if(zipCode.getText().toString().trim().equalsIgnoreCase(""))
		{
			//alert zip code is empty
			title.setText(getResources().getText(R.string.please_enter_zip_code));
			builder.setCustomTitle(title);
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			builder.show();
		}
		else if (zipCode.getText().toString().trim().length() < Constants.FIVE) {
			title.setText(getResources().getText(R.string.invalid_zip_code));
			builder.setCustomTitle(title);
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			builder.show();
		}
		else
		{
			//background call
			Log.i("Click", "Click");
			ipAddressRequestTask();
		}
	}

	@UiThread
	void startProgress()
	{
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.progress_layout);
		progresTextView = (TextView) dialog.findViewById(R.id.tvProgressText);
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		progresTextView.setTypeface(font);
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.Dialog_Green));
		drawable.setAlpha(Constants.HUNDRED);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}

	@UiThread
	void stopProgress()
	{
		if (dialog != null && dialog.isShowing())
		{			
/*			Log.i("Offers", offerContainer.getOffers().get(0).getOfferGuid());
			Intent intent = new Intent(getActivity(), LoanOffersActivity_.class);
			intent.putExtra("offers", offerContainer);
			intent.putExtra("ipAddress", ipAddress);
			startActivity(intent);*/
			dialog.dismiss();
		}
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY);
		title.setTypeface(font);
		if(application.checkForNetwork == 1 ){

			
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
		else if(postalAddress == null || postalAddress.getPostalCode() == null)
		{
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			title.setText(getResources().getText(R.string.invalid_zip_code));
			builder.setCancelable(false);
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			
		}
	}
	
	@UiThread
	void networkDataLoad()
	{
		Intent intent = new Intent(getActivity(), LoanOffersActivity_.class);
		application.setOfferContainer(offerContainer);
		//intent.putExtra("offers", offerContainer);
		intent.putExtra("ipAddress", ipAddress);
		startActivity(intent);
		dialog.dismiss();
	}

	@Background
	void ipAddressRequestTask()
	{
		startProgress();

		setTimerWithIntervals("Personalizing your offers",2000L);
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
				postalCodeRequestTask();
				Log.d("Response", ipAddress);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Background
	void postalCodeRequestTask()
	{
		postalAddress = postalAddressAdapter.callPostalAddressRequestTask(zipCode.getText().toString().trim());

		if(postalAddress == null || postalAddress.getPostalCode() == null)
		{
			stopProgress();
		}
		else
		{
			Log.i("Postal Address", postalAddress.getCity());
			((LoanExplorerActivity_)getActivity()).loanExplorer.setPropertyZipCode(zipCode.getText().toString().trim());
			((LoanExplorerActivity_)getActivity()).loanExplorer.setPropertyTypeId("1");
			
			onLoanExplorerRequestTask();
			 timerStart = System.currentTimeMillis();
			Log.v(TAG, "TIMER---Outside"+System.currentTimeMillis());
		}
	}
	
	@UiThread
	void unableToFetch()
	{
		Toast.makeText(getActivity(), "Unable to fetch data", Toast.LENGTH_SHORT).show();
	}

	
	
	@Background
	void onLoanExplorerRequestTask()
	{
		
		//offersPendingCount++ ;
		
		if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
		{
			((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPurchasePrice(application.getPropertyValue());
			((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue("0");

		}
		else if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("2"))
		{
			((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPurchasePrice("0");
			((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue(application.getPropertyValue());

		}
		Log.i("getRequestedLoanTypeId", ((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId());
		Log.i("getCurrentMortgageBalance", ((LoanExplorerActivity_)getActivity()).loanExplorer.getCurrentMortgageBalance());
		//Log.i("getEstimatedCreditScore", ((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedCreditScore());
		Log.i("getEstimatedDownPayment", ((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedDownPayment());
		Log.i("getEstimatedPropertyValue", application.getPropertyValue());
		Log.i("getEstimatedPurchasePrice", ((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedPurchasePrice());
		Log.i("getPropertyTypeId", ((LoanExplorerActivity_)getActivity()).loanExplorer.getPropertyTypeId());
		Log.i("getPropertyZipCode", ((LoanExplorerActivity_)getActivity()).loanExplorer.getPropertyZipCode());
		Log.i("ip Address", ipAddress);

		offerContainer = loanExplorerAdapter.callRequestStatusRequestTask(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId(), ipAddress, ((LoanExplorerActivity_)getActivity()).loanExplorer.getPropertyTypeId(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getPropertyZipCode(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getVeteranStatusTypeId(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getPropertyUseId()
				, ((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanProgramIds(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getPrepaymentPenaltyAccepted()
				, ((LoanExplorerActivity_)getActivity()).loanExplorer.getBankruptcyDischargedId(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getForeclosureDischargedId()
				, ((LoanExplorerActivity_)getActivity()).loanExplorer.getSecondLienMortgageBalance(),((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedPurchasePrice(), 
				((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedDownPayment(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedPropertyValue(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getCurrentMortgageBalance(), ((LoanExplorerActivity_)getActivity()).loanExplorer.getEstimatedCreditScoreBandId());
		
		if (offerContainer == null || offerContainer.isOffersPending()) 
		{
			loanOffersRequestTask();
			
		}else {
			if (offerContainer.getOffers() == null || offerContainer.getOffers().size() == 0) {
				Log.v(TAG, "Inside onLoanExplorerRequestTask else if");
				stopProgress();
				alertDialog(getResources().getText(R.string.no_loan_offers)
						+ "");
				// netwokDataLoad();
			} else {
				Log.v(TAG, "Inside onLoanExplorerRequestTask else");
				// stopProgress();
				offersPendingCount = 0;
				networkDataLoad();
			}
		
		}
	}

	private void setTimerWithIntervals(final String message, long time) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				((LoanExplorerActivity_) getActivity()).runOnUiThread(new Runnable() {
					
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
		Log.v(TAG, offerContainer.getQuotesId()+"--------Quotes ID");
		offerContainer = loanExplorerOffersAdapter.callLoanOffersRequestTask(offerContainer.getQuotesId());
		if (offerContainer == null || offerContainer.isOffersPending()) 
		{
			if (offerContainer != null && offersPendingCount <= Constants.THREE) {
				Log.v(TAG, "Inside loanOffersRequestTask if"
						+ offerContainer + offerContainer.isOffersPending());
				Log.v(TAG, "TIMER---Inside"+System.currentTimeMillis());
				if (timerStart + Constants.EIGHT_THOUSAND >= System.currentTimeMillis()) {
					((LoanExplorerActivity_) getActivity()).runOnUiThread(new Runnable() {
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
					if (offerContainer.getOffers() == null || offerContainer.getOffers().size() == 0) {
						Log.v(TAG, "Inside onLoanExplorerRequestTask else if");
						stopProgress();
						alertDialog(getResources().getText(R.string.no_loan_offers)
								+ "");
						// netwokDataLoad();
					} else {
						Log.v(TAG, "Inside onLoanExplorerRequestTask else");
						// stopProgress();
						offersPendingCount = 0;
						networkDataLoad();
					}
				
				}
				
			} else if (offerContainer == null && offersPendingCount <= Constants.THREE) {
				
				offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				alertDialog(getResources().getText(R.string.error_occured)
						+ "");
			} else {
				//offersPendingCount = 0;
				stopProgress();
				// No Offers Available
				alertDialog(getResources().getText(R.string.no_loan_offers)
						+ "");
			}
			
		}else {
			if (offerContainer.getOffers() == null || offerContainer.getOffers().size() == 0) {
				Log.v(TAG, "Inside onLoanExplorerRequestTask else if");
				stopProgress();
				alertDialog(getResources().getText(R.string.no_loan_offers)
						+ "");
				// netwokDataLoad();
			} else {
				Log.v(TAG, "Inside onLoanExplorerRequestTask else");
				// stopProgress();
				offersPendingCount = 0;
				networkDataLoad();
			}
		
		}
		
	
	}

	@UiThread
	void alertDialog(String message)
	{
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY_THREE);
		//title.setTypeface(font);
		title.setText(getResources().getText(R.string.no_result_found));
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setCustomTitle(title).setCancelable(false);
		builder.setMessage(message);
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
				

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		TextView textView = (TextView) alert.findViewById(android.R.id.message);
		textView.setTypeface(font);
		
	}
}
