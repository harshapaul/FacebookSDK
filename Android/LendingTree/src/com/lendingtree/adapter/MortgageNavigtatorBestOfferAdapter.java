package com.lendingtree.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lendingtree.model.MortgageNegotiator;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;
import com.lendingtree.util.Constants;
import com.lendingtree.view.BaseActivity;
import com.lendingtree.view.MortgageOfferEmailActivity_;
import com.lendingtree.view.R;

public class MortgageNavigtatorBestOfferAdapter extends BaseAdapter {

	
	TextView tvFirstHeaderTitle, tvRateAPRValue, tvRateAPRValue_higher, tvOriginationChargsValue, 
	tvOriginationChargsValue_higher, tvMonthlyPaymentValue, tvMonthlyPaymentValue_highers;
	
	ImageView btnArrowRateAPR, btnArrowOriginationCharges, btnArrowMonthlyPayment;
	
	RatingBar ratingBar1;
	Button btnCall, btnEmail;
	
	ArrayList<Offers> offersList;
	
	private final Context context;
	MortgageNegotiator mortgageNegotiator;
	
	OfferContainer container;

	String ipAddress;
	public MortgageNavigtatorBestOfferAdapter(Context context,
			OfferContainer container2,MortgageNegotiator mortgageNegotiator, ArrayList<Offers> templList, String ipAddress) {

		this.context = context;
		this.offersList = templList;
		this.container = container2;
		this.ipAddress = ipAddress;
		this.mortgageNegotiator = mortgageNegotiator;

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.mortgage_nevigator_best_offfer_child, parent, false);
		
		final Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
		Typeface tf1 = Typeface.createFromAsset(context.getAssets(),"OpenSans-Bold.ttf");
		
		btnCall = (Button) rowView.findViewById(R.id.btnCall);
		btnEmail = (Button) rowView.findViewById(R.id.btnEmail); 
		
		btnArrowRateAPR = (ImageView) rowView.findViewById(R.id.btnArrowRateAPR);
		btnArrowOriginationCharges = (ImageView) rowView.findViewById(R.id.btnArrowOriginationCharges);
		btnArrowMonthlyPayment  = (ImageView) rowView.findViewById(R.id.btnArrowMonthlyPayment);
		 
		((TextView) rowView.findViewById(R.id.tvRateAPR)).setTypeface(tf);
		((TextView) rowView.findViewById(R.id.tvOriginationChargs)).setTypeface(tf);
		((TextView) rowView.findViewById(R.id.tvMonthlyPayment)).setTypeface(tf);
		
		tvFirstHeaderTitle = (TextView) rowView.findViewById(R.id.tvFirstHeaderTitle);
		tvFirstHeaderTitle.setTypeface(tf);
		ratingBar1 = (RatingBar) rowView.findViewById(R.id.ratingBar1);
		tvRateAPRValue = (TextView) rowView.findViewById(R.id.tvRateAPRValue);
		tvRateAPRValue.setTypeface(tf1);
		tvRateAPRValue_higher = (TextView)rowView.findViewById(R.id.tvRateAPRValue_higher);
		tvRateAPRValue_higher.setTypeface(tf);
		tvOriginationChargsValue = (TextView) rowView.findViewById(R.id.tvOriginationChargsValue);
		tvOriginationChargsValue.setTypeface(tf1);
		tvOriginationChargsValue_higher = (TextView) rowView.findViewById(R.id.tvOriginationChargsValue_higher);
		tvOriginationChargsValue_higher.setTypeface(tf);
		tvMonthlyPaymentValue = (TextView) rowView.findViewById(R.id.tvMonthlyPaymentValue);
		tvMonthlyPaymentValue_highers = (TextView) rowView.findViewById(R.id.tvMonthlyPaymentValue_highers);
		tvMonthlyPaymentValue_highers.setTypeface(tf);
		tvMonthlyPaymentValue.setTypeface(tf1);
		tvFirstHeaderTitle.setText(offersList.get(position).getName());
		ratingBar1.setRating(offersList.get(position).getAverageOverallRating());
		//tvRateAPRValue.setText(offersList.get(position).getRatePercentage() + "% / "+offersList.get(position).getAprPercentage()+"%");
		tvRateAPRValue.setText(String.format("%.3f", offersList.get(position).getRatePercentage()) + "% / "+String.format("%.3f", offersList.get(position).getAPRPercentage())+"%");
		tvOriginationChargsValue.setText(" $"+((BaseActivity) context).addCommasToNumericString((int)offersList.get(position).getTotalFees()+""));
		tvMonthlyPaymentValue.setText(" $"+((BaseActivity) context).addCommasToNumericString((int)offersList.get(position).getTotalMonthlyPayment()+""));
		
		if(position==getCount()-1)
			((TextView) rowView.findViewById(R.id.tvDivider)).setVisibility(View.GONE);
		
		
		btnCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				final String phoneNo = (offersList.get(position).getTelephoneNumber());
				TextView messageText = new TextView(context);
				messageText.setPadding(Constants.TEN, Constants.TWENTY_FIVE, Constants.TEN, Constants.TWENTY_FIVE);
				messageText.setGravity(Gravity.CENTER);
				messageText.setTextSize(Constants.EIGHTEEN);
				messageText.setTypeface(tf);
				if (offersList.get(position).isShowTelephoneNumber() && offersList.get(position).isIsTelephoneLeadEnabled()) {
					messageText.setText(String.format("(%s) %s-%s", phoneNo.substring(Constants.TWO, Constants.FIVE),
									phoneNo.substring(Constants.FIVE, Constants.EIGHT), phoneNo.substring(Constants.EIGHT, Constants.TWELVE)));

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
							Intent intent = new Intent(context, MortgageOfferEmailActivity_.class);
							intent.putExtra("oferdetail", offersList.get(position));
							intent.putExtra("ipaddress", ipAddress);
							intent.putExtra("lendername", offersList.get(position).getName()+"");
							intent.putExtra("searchid", container.getQuotesId());
							context.startActivity(intent);
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
		
		btnEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, MortgageOfferEmailActivity_.class);
				intent.putExtra("oferdetail", offersList.get(position));
				intent.putExtra("ipaddress", ipAddress);
				intent.putExtra("lendername", offersList.get(position).getName()+"");
				intent.putExtra("searchid", container.getQuotesId());
				context.startActivity(intent);
		
			}
		});
		 //    originationCharges
	    
	    double tempTotalOriginationCharge = 0.0;
	    int maxOriginationCharge = 0;
	    
	 
		//mortgageNegotiator = new MortgageNegotiator();
		double ratePercentage = offersList.get(position).getRatePercentage();
        double totalFees = offersList.get(position).getTotalFees();
        double totalMonthlyPayment = offersList.get(position).getTotalMonthlyPayment();
        
        double grandTotalFees = 0.0;
        double grandTotalMonthlyPayment = 0.0;
      // Log.i("value 123",  mortgageNegotiator.getCurrentMortgageInterestRate());
        double userInterestRate = Double.parseDouble(mortgageNegotiator.getCurrentMortgageInterestRatePercent());
        double originationCharges = Double.parseDouble(mortgageNegotiator.getOriginationCharges());
        double monthlyPayment = Double.parseDouble(mortgageNegotiator.getCurrentMonthlyPayment());
        
        grandTotalFees += totalFees;
        grandTotalMonthlyPayment += totalMonthlyPayment;
        
        if (ratePercentage < userInterestRate) {
        	
            //[tempOfferDict setObject:@"LOWER" forKey:@"ratePercentageTag"];
        	tvRateAPRValue_higher.setText("LOWER");
        	tvRateAPRValue_higher.setTextColor(Color.parseColor("#528A49"));
        	btnArrowRateAPR.setImageResource(R.drawable.downtriangle_negotitor);
        	//Image as to set upArrow
            
        }else{
            //[tempOfferDict setObject:@"HIGHER" forKey:@"ratePercentageTag"];
        	tvRateAPRValue_higher.setText("HIGHER");
        	tvRateAPRValue_higher.setTextColor(Color.parseColor("#736AFF"));
        	btnArrowRateAPR.setImageResource(R.drawable.up_trianglenegotitor);
        	//Image as to set upArrow
        }
        
        if (totalFees < originationCharges) {
           // [tempOfferDict setObject:@"LOWER" forKey:@"originationChargesTag"];
        	tvOriginationChargsValue_higher.setText("LOWER");
        	tvOriginationChargsValue_higher.setTextColor(Color.parseColor("#528A49"));
        	btnArrowOriginationCharges.setImageResource(R.drawable.downtriangle_negotitor);
        }else{
            //[tempOfferDict setObject:@"HIGHER" forKey:@"originationChargesTag"];
        	tvOriginationChargsValue_higher.setText("HIGHER");
        	tvOriginationChargsValue_higher.setTextColor(Color.parseColor("#736AFF"));
        	btnArrowOriginationCharges.setImageResource(R.drawable.up_trianglenegotitor);
        }
        
        if (totalMonthlyPayment < monthlyPayment) {
            //[tempOfferDict setObject:@"LOWER" forKey:@"totalMonthlyPaymentTag"];
        	tvMonthlyPaymentValue_highers.setText("LOWER");
        	tvMonthlyPaymentValue_highers.setTextColor(Color.parseColor("#528A49"));
        	btnArrowMonthlyPayment.setImageResource(R.drawable.downtriangle_negotitor);
        }else{
            //[tempOfferDict setObject:@"HIGHER" forKey:@"totalMonthlyPaymentTag"];
        	tvMonthlyPaymentValue_highers.setText("HIGHER");
        	tvMonthlyPaymentValue_highers.setTextColor(Color.parseColor("#736AFF"));
        	btnArrowMonthlyPayment.setImageResource(R.drawable.up_trianglenegotitor);
        }
		
		//String originationCharges = mortgageNegotiator.getOriginationCharges();
		
		return rowView;
	}
	
	

}
