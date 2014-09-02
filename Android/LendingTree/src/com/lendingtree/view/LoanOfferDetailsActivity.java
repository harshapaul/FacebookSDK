package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.model.Offers;
import com.lendingtree.util.Constants;

@EActivity(R.layout.loan_explorer_pt_lt)
public class LoanOfferDetailsActivity extends BaseActivity {

	Offers offers;
	String requestId;
	String ipAddress;
	@ViewById(R.id.btnCall_LX)
	Button btnCallLX;

	@ViewById(R.id.btnMessage_LX)
	Button btnMessageLX;

	@ViewById(R.id.tvheaderRatingReview)
	TextView ratingReview;

	@ViewById(R.id.tvRateValue)
	TextView tvRateValue;

	@ViewById(R.id.tvRate)
	TextView tvRate;

	@ViewById(R.id.tvFeesValue)
	TextView tvFeesValue;

	@ViewById(R.id.tvFees)
	TextView tvFees;

	@ViewById(R.id.tvAPRValue)
	TextView tvAPRValue;

	@ViewById(R.id.tvAPR)
	TextView tvAPR;

	@ViewById(R.id.tvPaymentValue)
	TextView tvPaymentValue;

	/*@ViewById(R.id.tvLoanExplorerFooterDetails)
	TextView tvLoanExplorerFooterDetails;*/

	@ViewById(R.id.tvPayment)
	TextView tvPayment;

	@ViewById(R.id.tvtypesValue)
	TextView tvTypeValue;

	@ViewById(R.id.tvtypes)
	TextView tvtypes;

	@ViewById(R.id.tvheaderRatingUp)
	TextView tvHeaderRatingUp;

	@ViewById(R.id.tvFooter1)
	TextView tvFooter;
	
	@ViewById(R.id.tvNLMS)
	TextView tvNLMS;
	
	Typeface tf;

	Typeface tf2;

	@Click(R.id.btnCall_LX)
	void callBtnClick() {

		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 * 
		 * builder.setTitle(offers.getOfferPhoneNumber().getPhoneNumber());
		 * builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		 * { public void onClick(DialogInterface dialog, int which) { // do
		 * stuff here } }); // cancel button with dismiss.
		 * builder.setNegativeButton("Cancel", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int which) { // dismiss(); } });
		 * 
		 * AlertDialog alert = builder.create(); alert.show();
		 */

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String phoneNo = (offers.getTelephoneNumber());
		TextView messageText = new TextView(this);
		messageText.setPadding(Constants.TEN, Constants.TWENTY_FIVE, Constants.TEN, Constants.TWENTY_FIVE);
		messageText.setGravity(Gravity.CENTER);
		messageText.setTextSize(Constants.EIGHTEEN);
		messageText.setTypeface(tf);
		if (offers.isShowTelephoneNumber() && offers.isIsTelephoneLeadEnabled()) {
			messageText.setText(String.format("(%s) %s-%s", phoneNo.substring(Constants.TWO, Constants.FIVE),
							phoneNo.substring(Constants.FIVE, Constants.EIGHT), phoneNo.substring(Constants.EIGHT, Constants.TWELVE)));

			builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phoneNo));
					startActivity(intent);
				}
			});
		} else {

			messageText.setText(getResources().getString(R.string.no_phoneno));
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intentemail = new Intent(LoanOfferDetailsActivity.this, LoanOfferEmailActivity_.class);
					intentemail.putExtra("offersdetail", offers);
					intentemail.putExtra("ipaddress", ipAddress);
					intentemail.putExtra("requestid", requestId);
					startActivity(intentemail);
				}
			});
		
		}
		
		builder.setCustomTitle(messageText);
		
		// cancel button with dismiss.
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	@Click(R.id.btnMessage_LX)
	void messageClick() {

		Intent intent = new Intent(LoanOfferDetailsActivity.this,
				LoanOfferEmailActivity_.class);
		intent.putExtra("offersdetail", offers);
		intent.putExtra("ipaddress", ipAddress);
		intent.putExtra("requestid", requestId);
		startActivity(intent);
	}

	@AfterViews
	void DisplayValue() {
		// TODO Auto-generated method stub

		/* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { */
		// getActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// }

		RatingBar rbReview = (RatingBar) findViewById(R.id.ratingBar1);
		// super.onCreate(savedInstanceState);
		offers = (Offers) getIntent().getSerializableExtra("offersdetail");

		ipAddress = getIntent().getStringExtra("ipaddress");
		requestId = getIntent().getStringExtra("requestid");

		tvRateValue.setText(String.format("%.3f", offers.getRatePercentage())
				+ " %");
		tvFeesValue.setText("$" + (int) offers.getTotalFees() + "");
		tvAPRValue.setText(String.format("%.3f", offers.getAPRPercentage())
				+ " %");
		tvPaymentValue
				.setText("$" + (int) offers.getTotalMonthlyPayment() + "");
		if (offers.isIsFHALoan()) {
			if (offers.getLoanProductName().contains("30")) {
				tvTypeValue.setText("30 Year Fixed FHA"); 
			}else if (offers.getLoanProductName().contains("15")) {
				tvTypeValue.setText("15 Year Fixed FHA"); 
			}else if (offers.getLoanProductName().contains("ARM")) {
				tvTypeValue.setText("5/1 ARM FHA"); 
			}
		} else {
			if (offers.getLoanProductName().contains("30")) {
				tvTypeValue.setText("30 Year Fixed"); 
			}else if (offers.getLoanProductName().contains("15")) {
				tvTypeValue.setText("15 Year Fixed"); 
			}else if (offers.getLoanProductName().contains("ARM")) {
				tvTypeValue.setText("5/1 ARM"); 
			}
		}
		/*if (offers.isIsFHALoan()) {
			tvTypeValue.setText(offers.getLoanProductName().replaceAll("Yr|Year",
					" Year ") +" FHA");
		} else {
			tvTypeValue.setText(offers.getLoanProductName().replaceAll("Yr|Year",
					" Year "));
		}*/
		
		ratingReview.setText("("
				+ offers.getTotalRatingsAndReviews() + " Reviews)");
		if (offers.getTotalRatingsAndReviews() == 0) {
			ratingReview.setTextColor(getResources().getColor(R.color.black));
			ratingReview.setEnabled(false);
		}
		tvHeaderRatingUp.setText(offers.getName());
		rbReview.setRating(offers.getAverageOverallRating());
		tvNLMS.setText("NMLS # "+offers.getNMLSID()+".");
		ratingReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoanOfferDetailsActivity.this,
						LoanExpReviewActivity_.class);
				intent.putExtra("offersdetail", offers);
				intent.putExtra("ipaddress", ipAddress);
				intent.putExtra("requestid", requestId);
				intent.putExtra("lenderId", offers.getLenderId()+"");
				startActivity(intent);
			}
		});

		setHyperLink(tvFooter);
		settingFontAllViews();
	}

	private void settingFontAllViews() {
		// TODO Auto-generated method stub
		tf = Typeface.createFromAsset(getAssets(),
				"OpenSans-Regular.ttf");
		tf2 = Typeface.createFromAsset(getAssets(),
				"OpenSans-Light.ttf");
		
		ratingReview.setTypeface(tf);

		tvRate.setTypeface(tf);
		tvRateValue.setTypeface(tf);
		tvFeesValue.setTypeface(tf2);
		tvFees.setTypeface(tf);

		tvAPR.setTypeface(tf);
		tvAPRValue.setTypeface(tf);
		tvPaymentValue.setTypeface(tf2);
		tvPayment.setTypeface(tf);
		tvTypeValue.setTypeface(tf2);
		tvtypes.setTypeface(tf);
		tvHeaderRatingUp.setTypeface(tf);
		tvFooter.setTypeface(tf);
		//tvLoanExplorerFooterDetails.setTypeface(tf);
		tvNLMS.setTypeface(tf);
	}

	public LoanOfferDetailsActivity() {
		super(R.string.loan_exploree_details_title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
					.isAcceptingText()) {
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
			}
			finish();
			return true;

		case R.id.btnDone:

			Intent intent = new Intent(this, LoanExplorerActivity_.class);
			intent.putExtra("offersdetail", offers);
			intent.putExtra("ipaddress", ipAddress);
			intent.putExtra("requestid", requestId);
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);

		getSupportActionBar().setDisplayShowHomeEnabled(false);
		// ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
		ImageButton b = new ImageButton(getApplicationContext());
		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.buttondone));

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						LoanExplorerActivity_.class);

				startActivity(intent);
			}
		});

		menu.findItem(R.id.btnDone).setActionView(b);
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(true);

		return true;
	}

}
