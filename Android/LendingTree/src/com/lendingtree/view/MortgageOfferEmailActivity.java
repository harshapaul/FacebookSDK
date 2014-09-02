package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.model.ContactDetails;
import com.lendingtree.model.ContactForm;
import com.lendingtree.model.Offers;
import com.lendingtree.model.PostResponse;
import com.lendingtree.networkutils.MortgageNegotiatorContactFormAdapter;
import com.lendingtree.util.Constants;

@EActivity(R.layout.email_form)
public class MortgageOfferEmailActivity extends BaseActivity {
	public MortgageOfferEmailActivity() {
		super(R.string.loan_explorer_email);
	}

	String name, phone, email;

	ContactForm contactForm;
	ContactDetails contactDetails;

	@Extra("oferdetail")
	Offers offersMortgage;

	@Extra("lendername")
	String lenderName;

	@Extra("searchid")
	String searchRequestId;
	
	@Extra("ipaddress")
	String ipAddress;

	@Bean
	MortgageNegotiatorContactFormAdapter mortgageNegotiatorContactFormAdapter;

	@ViewById(R.id.tvEmailTitle)
	TextView title;

	@ViewById(R.id.edtFirstName)
	EditText edtFirstName;

	@ViewById(R.id.edtLastName)
	EditText edtLastName;

	@ViewById(R.id.edtPhoneNum)
	EditText edtPhoneNum;

	@ViewById(R.id.edtEmail)
	EditText edtEmail;

	/*@ViewById(R.id.tv_SecurityFooter)
	TextView securityFooter;*/

	@ViewById(R.id.emailFooter)
	TextView emailFooter;

	/*@ViewById(R.id.emailFooterDetails)
	TextView emailFooterDetails;*/
	
	@ViewById(R.id.tvNLMSemail)
	TextView tvNLMSemail;
	
	@ViewById(R.id.tvheaderPhoneNo)
	TextView tvheaderPhoneNo;
	
	@ViewById(R.id.ratingBarEmail)
	RatingBar ratingBarEmail;
	
	Dialog dialog;

	PostResponse postResponse;
	Typeface font;
	@AfterViews
	void start()
	{
		setHyperLink(emailFooter);
		font=Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		title.setTypeface(font);
		edtFirstName.setTypeface(font);
		edtLastName.setTypeface(font);
		edtPhoneNum.setTypeface(font);
		edtEmail.setTypeface(font);
		emailFooter.setTypeface(font);
		//securityFooter.setTypeface(font);
		//emailFooterDetails.setTypeface(font);
		title.setText(lenderName);
		contactForm = new ContactForm();
		//contactForm.setVersion("1");
		contactForm.setApiKey(Constants.API_KEY);
		contactForm.setSearchRequestId(searchRequestId);
		contactForm.setOfferId(offersMortgage.getOfferId());
		contactForm.setLenderId(offersMortgage.getLenderId()+"");
		contactForm.setClientIP(ipAddress);
		
		ratingBarEmail.setRating(offersMortgage.getAverageOverallRating());
		tvNLMSemail.setText("NMLS # "+offersMortgage.getNMLSID()+".");
		if (offersMortgage.isShowTelephoneNumber() && offersMortgage.isIsTelephoneLeadEnabled()) {
			tvheaderPhoneNo.setVisibility(View.VISIBLE);
			tvheaderPhoneNo.setText(String.format("(%s) %s-%s", offersMortgage.getTelephoneNumber().substring(Constants.TWO, Constants.FIVE),
					offersMortgage.getTelephoneNumber().substring(Constants.FIVE, Constants.EIGHT), offersMortgage.getTelephoneNumber().substring(Constants.EIGHT, Constants.TWELVE)));
		} else {
			tvheaderPhoneNo.setVisibility(View.GONE);
		}
	}

	@Click(R.id.ivPhoneNo)
	void onClickPhoneNo(){
		Intent intent = new Intent(getBaseContext(), CustomDialogActivity.class);
		intent.putExtra("source", false);
		startActivity(intent);
	}
	
	@UiThread
	void startProgress()
	{
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.progress_layout);
		TextView progresTextView = (TextView) dialog.findViewById(R.id.tvProgressText);
		Typeface font=Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		progresTextView.setTypeface(font);
		progresTextView.setText("Please Wait");
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
			finish();
			Toast toast = Toast.makeText(getBaseContext(), getResources().getString(R.string.request_sent), Toast.LENGTH_SHORT);
			toast.getView().setBackgroundResource(R.color.black);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		if(application.checkForNetwork == 1 ){

			TextView title = new TextView(this);
			title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
			title.setGravity(Gravity.CENTER);
			title.setTextSize(Constants.TWENTY_THREE);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			title.setText("Error");
			builder.setCustomTitle(title)
			.setMessage("No Internet Connectivity!")
			.setCancelable(false)
			.setPositiveButton("Ok",
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		//ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
		ImageButton b = new ImageButton(getApplicationContext());
		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.buttonsend));

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+";
				name = edtFirstName.getText().toString().trim()+" "+edtLastName.getText().toString().trim();
				phone = edtPhoneNum.getText().toString().trim();
				email = edtEmail.getText().toString().trim();
				TextView title = new TextView(MortgageOfferEmailActivity.this);
				title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
				title.setGravity(Gravity.CENTER);
				title.setTextSize(Constants.TWENTY);
				title.setTypeface(font);
				AlertDialog.Builder builder=new AlertDialog.Builder(MortgageOfferEmailActivity.this);
				if(edtFirstName.getText().toString().trim().equals("") && edtLastName.getText().toString().trim().equals("") && phone.equals("") && email.equals(""))
				{
					title.setText(getResources().getText(R.string.all_fields_are_empty));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else if(edtLastName.getText().toString().trim().equals(""))
				{
					title.setText(getResources().getText(R.string.please_enter_last_name));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else if(phone.equals(""))
				{
					title.setText(getResources().getText(R.string.please_enter_contact_number));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else if(phone.length() < Constants.TEN)
				{
					title.setText(getResources().getText(R.string.invalid_phone_number));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else if(email.equals(""))
				{
					title.setText(getResources().getText(R.string.please_enter_email_address));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else if(!email.matches(emailPattern))
				{
					title.setText(getResources().getText(R.string.invalid_email_address));
					builder.setCustomTitle(title);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//Log.d("jxh","skdj");
						}
					});
					builder.show();
				}
				else
				{
					startProgress();
					Log.i("background", "background");
					contactDetails = new ContactDetails();
					contactDetails.setFirstName(edtFirstName.getText().toString().trim());
					contactDetails.setLastName(edtLastName.getText().toString().trim());
					contactDetails.setPhone(edtPhoneNum.getText().toString().trim());
					contactDetails.setEmailAddress(edtEmail.getText().toString().trim());
					//contactDetails.setEsourceId(Constants.ESOURCEID_MN);
					contactDetails.setComments("I am interested in this " + offersMortgage.getFixedRatePeriodMonths()/12 +  " Year Fixed loan from "+offersMortgage.getName()+" and would like more information. Thank you!");
					contactForm.setContactDetails(contactDetails);
					onContactFormRequestTask();
				}
			}
		});

		menu.findItem(R.id.btnSend).setActionView(b); 
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);
		menu.findItem(R.id.btnSend).setVisible(true);

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			if(((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
			{
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); 
			}
			finish();
			return true;
		case R.id.btnSend:
			onSendClick();
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}

	@Click(R.id.btSendEmail)
	void onSendEmailClick(){
		onSendClick();
	}
	
	private void onSendClick() {
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		name = edtFirstName.getText().toString().trim()+" "+edtLastName.getText().toString().trim();
		phone = edtPhoneNum.getText().toString().trim();
		email = edtEmail.getText().toString().trim();
		TextView title = new TextView(MortgageOfferEmailActivity.this);
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY);
		title.setTypeface(font);
		AlertDialog.Builder builder=new AlertDialog.Builder(MortgageOfferEmailActivity.this);
		if(edtFirstName.getText().toString().trim().equals("") && edtLastName.getText().toString().trim().equals("") && phone.equals("") && email.equals(""))
		{
			title.setText(getResources().getText(R.string.all_fields_are_empty));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else if(edtLastName.getText().toString().trim().equals(""))
		{
			title.setText(getResources().getText(R.string.please_enter_last_name));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else if(phone.equals(""))
		{
			title.setText(getResources().getText(R.string.please_enter_contact_number));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else if(phone.length() < Constants.TEN)
		{
			title.setText(getResources().getText(R.string.invalid_phone_number));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else if(email.equals(""))
		{
			title.setText(getResources().getText(R.string.please_enter_email_address));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else if(!email.matches(emailPattern))
		{
			title.setText(getResources().getText(R.string.invalid_email_address));
			builder.setCustomTitle(title);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Log.d("jxh","skdj");
				}
			});
			builder.show();
		}
		else
		{
			startProgress();
			Log.i("background", "background");
			contactDetails = new ContactDetails();
			contactDetails.setFirstName(edtFirstName.getText().toString().trim());
			contactDetails.setLastName(edtLastName.getText().toString().trim());
			contactDetails.setPhone(edtPhoneNum.getText().toString().trim());
			contactDetails.setEmailAddress(edtEmail.getText().toString().trim());
			contactDetails.setComments("I am interested in this " + offersMortgage.getFixedRatePeriodMonths()/Constants.TWELVE +  " Year Fixed loan from "+offersMortgage.getName()+" and would like more information. Thank you!");
			contactForm.setContactDetails(contactDetails);
			onContactFormRequestTask();
		}
		
	}

	@Background
	void onContactFormRequestTask()
	{
		postResponse = mortgageNegotiatorContactFormAdapter.callContactFormRequestTask(contactForm);
		//		Log.i("background Task", "background Task");
		//		emailForm = new EmailForm(customerIp, searchId, offerId, lenderId, productTypeId, name, phone, email, isTest, acti);
		//		postResponse = contactFormAdapter.callEmailFormRequestTask(emailForm);
		stopProgress();
	}

}
