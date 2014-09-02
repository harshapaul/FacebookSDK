package com.lendingtree.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lendingtree.model.Offers;
import com.lendingtree.util.Constants;
import com.lendingtree.view.LoanExpReviewActivity_;
import com.lendingtree.view.LoanOfferDetailsActivity_;
import com.lendingtree.view.LoanOfferEmailActivity_;
import com.lendingtree.view.R;

public class LoanExplorerListOffersAdapter extends BaseAdapter {
	private final Activity context;
	  private final ArrayList<? extends Parcelable> offersList;
	  String ipAddress,requestId;
	  Typeface tf,tf1;
	  

	  @SuppressWarnings("unchecked")
	public LoanExplorerListOffersAdapter(Context context, ArrayList<Offers> offersList, String ipAddress, String requestId) {
	   
	    this.context = (Activity) context;
	    this.offersList = (ArrayList<? extends Parcelable>) offersList;
	    this.ipAddress = ipAddress;
	    this.requestId = requestId;
	    tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
		tf1 = Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
	  }
	  
	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.loan_offer_child, parent, false);
		TextView tvRate = (TextView) rowView.findViewById(R.id.tvRate);
		TextView tvAPR = (TextView) rowView.findViewById(R.id.tvAPR);
		TextView tvFixYear = (TextView) rowView.findViewById(R.id.tvFixYear);
		TextView tvFinancialServices = (TextView) rowView.findViewById(R.id.tvFinancialServices);
		TextView tvReviews = (TextView) rowView.findViewById(R.id.tvReviews);
		RatingBar rbReview = (RatingBar) rowView.findViewById(R.id.ratingBar1);
		
		tvRate.setTypeface(tf);
		tvAPR.setTypeface(tf);
		tvFixYear.setTypeface(tf1);
		tvFinancialServices.setTypeface(tf);
		tvReviews.setTypeface(tf);
		
		RelativeLayout layout1 = (RelativeLayout)rowView.findViewById(R.id.layout1);
		Button btnCall = (Button) rowView.findViewById(R.id.btnCall);
		Button btnEmail = (Button) rowView.findViewById(R.id.btnEmail); 
		tvRate.setText(String.format("%.3f", ((Offers) offersList.get(position)).getRatePercentage())+" % RATE");
		tvAPR.setText(String.format("%.3f", ((Offers) offersList.get(position)).getAPRPercentage())+" % APR");
		if (((Offers) offersList.get(position)).isIsFHALoan()) {
			if (((Offers) offersList.get(position)).getLoanProductName().contains("30")) {
				tvFixYear.setText("30 Year Fixed FHA"); 
			}else if (((Offers) offersList.get(position)).getLoanProductName().contains("15")) {
				tvFixYear.setText("15 Year Fixed FHA"); 
			}else if (((Offers) offersList.get(position)).getLoanProductName().contains("ARM")) {
				tvFixYear.setText("5/1 ARM FHA"); 
			}
		} else {
			if (((Offers) offersList.get(position)).getLoanProductName().contains("30")) {
				tvFixYear.setText("30 Year Fixed"); 
			}else if (((Offers) offersList.get(position)).getLoanProductName().contains("15")) {
				tvFixYear.setText("15 Year Fixed"); 
			}else if (((Offers) offersList.get(position)).getLoanProductName().contains("ARM")) {
				tvFixYear.setText("5/1 ARM"); 
			}
		}
		
		tvReviews.setText("("+((Offers) offersList.get(position)).getTotalRatingsAndReviews()+" Reviews)");
		tvFinancialServices.setText(((Offers) offersList.get(position)).getName());
		if (((Offers) offersList.get(position)).getTotalRatingsAndReviews() == 0) {
			tvReviews.setTextColor(context.getResources().getColor(R.color.black));
			tvReviews.setEnabled(false);
		}
		
		rbReview.setRating(((Offers) offersList.get(position)).getAverageOverallRating());
		
		
		if(position==getCount()-1)
			((TextView) rowView.findViewById(R.id.tvDivider)).setVisibility(View.GONE);
		
		tvReviews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, LoanExpReviewActivity_.class);
				intent.putExtra("offersdetail", (Offers) (offersList.get(position)));
				intent.putExtra("ipaddress", ipAddress);
				intent.putExtra("requestid", requestId);
				intent.putExtra("lenderId", ((Offers) offersList.get(position)).getLenderId()+"");
				context.startActivity(intent);
				
			}
		});
		
		layout1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, LoanOfferDetailsActivity_.class);
				intent.putExtra("offersdetail", (Offers) (offersList.get(position)));
				intent.putExtra("ipaddress", ipAddress);
				intent.putExtra("requestid", requestId);
				context.startActivity(intent);
				
			}
		});
		
		btnEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentemail = new Intent(context, LoanOfferEmailActivity_.class);
				intentemail.putExtra("offersdetail", (Offers) (offersList.get(position)));
				intentemail.putExtra("ipaddress", ipAddress);
				intentemail.putExtra("requestid", requestId);
				context.startActivity(intentemail);
			}
		});
		
		btnCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				final String phoneNo = ((Offers) offersList.get(position)).getTelephoneNumber();
				TextView messageText = new TextView(context);
				messageText.setPadding(Constants.TEN, Constants.TWENTY_FIVE, Constants.TEN, Constants.TWENTY_FIVE);
				messageText.setGravity(Gravity.CENTER);
				messageText.setTextSize(Constants.EIGHTEEN);
				messageText.setTypeface(tf);
				if (((Offers) offersList.get(position)).isShowTelephoneNumber() && ((Offers) offersList.get(position)).isIsTelephoneLeadEnabled()) {
					messageText.setText(String.format("(%s) %s-%s", phoneNo.substring(2, Constants.FIVE), phoneNo.substring(Constants.FIVE, Constants.EIGHT), 
							phoneNo.substring(Constants.EIGHT, Constants.TWELVE)));

					builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Intent.ACTION_CALL);
							intent.setData(Uri.parse("tel:" + phoneNo));
							context.startActivity(intent);
						}
					});
				} else {
					
					messageText.setText(context.getResources().getString(R.string.no_phoneno));
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intentemail = new Intent(context, LoanOfferEmailActivity_.class);
							intentemail.putExtra("offersdetail", (Offers) (offersList.get(position)));
							intentemail.putExtra("ipaddress", ipAddress);
							intentemail.putExtra("requestid", requestId);
							context.startActivity(intentemail);
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
		});
		return rowView;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return offersList.size();
	}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
