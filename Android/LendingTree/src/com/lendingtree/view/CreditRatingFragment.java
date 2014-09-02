/*
 * This class is used to make a fragment for ViewPage Indicator implementing Credit Rating in Mortgage Negotiator
 * 
 * @Sanchit
 */

package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.lendingtree.util.Constants;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
@EFragment(R.layout.credit_rating)
public class CreditRatingFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private String mContent = "???";
	
	@ViewById(R.id.tv_Excellent_Mortgage_Negotiator)
	TextView excellent;
	
	@ViewById(R.id.tv_Good_Mortgage_Negotiator)
	TextView good;
	
	@ViewById(R.id.tv_Fair_Mortgage_Negotiator)
	TextView fair;
	
	@ViewById(R.id.tv_Not_So_Good_Mortgage_Negotiator)
	TextView notSoGood;
	String styledText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.credit_rating, container, false);

		return null;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getEstimatedCreditScoreBandId().equals("3"))
		{
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.drawable.selected_border);
			fair.setBackgroundResource(R.color.White);
			notSoGood.setBackgroundResource(R.color.White);
		}
		if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getEstimatedCreditScoreBandId().equals("5"))
		{
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.color.White);
			fair.setBackgroundResource(R.drawable.selected_border);
			notSoGood.setBackgroundResource(R.color.White);
		}
		if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getEstimatedCreditScoreBandId().equals("7"))
		{
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.color.White);
			fair.setBackgroundResource(R.color.White);
			notSoGood.setBackgroundResource(R.drawable.selected_border);
		}
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static CreditRatingFragment newInstance(String content)
	{
		CreditRatingFragment fragment = new CreditRatingFragment_(); 
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		excellent.setTypeface(font);
		good.setTypeface(font);
		fair.setTypeface(font);
		notSoGood.setTypeface(font);
		
		styledText = "<font color='#000000'>Excellent</font><font color='#999999'> (>= 720) </font>";
		excellent.setText(Html.fromHtml(styledText),TextView.BufferType.SPANNABLE);
		styledText = "<font color='#000000'>Good</font><font color='#999999'> (680 - 720) </font>";
		good.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
		styledText = "<font color='#000000'>Fair</font><font color='#999999'> (640 - 679) </font>";
		fair.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
		styledText = "<font color='#000000'>Not So Good</font><font color='#999999'> (< 639) </font>";
		notSoGood.setText(Html.fromHtml(styledText),TextView.BufferType.SPANNABLE);
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
	
	@Click(R.id.tv_Excellent_Mortgage_Negotiator)
	void onExcellentClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedCreditScoreBandId("1");
		/*((MortgageNegotiatorActivity_)getActivity()).creditRatingText = excellent.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(Constants.FOUR);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Property Zip Code");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		excellent.setBackgroundResource(R.drawable.selected_border);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutCreditRating.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).creditRating.setText(((MortgageNegotiatorActivity_)getActivity()).creditRatingText);
	}
	
	@Click(R.id.tv_Good_Mortgage_Negotiator)
	void onGoodClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedCreditScoreBandId("3");
		/*((MortgageNegotiatorActivity_)getActivity()).creditRatingText = good.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(Constants.FOUR);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Property Zip Code");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.drawable.selected_border);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutCreditRating.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).creditRating.setText(((MortgageNegotiatorActivity_)getActivity()).creditRatingText);
	}
	
	@Click(R.id.tv_Fair_Mortgage_Negotiator)
	void onFairClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedCreditScoreBandId("5");
		/*((MortgageNegotiatorActivity_)getActivity()).creditRatingText = fair.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(Constants.FOUR);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Property Zip Code");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.drawable.selected_border);
		notSoGood.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutCreditRating.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).creditRating.setText(((MortgageNegotiatorActivity_)getActivity()).creditRatingText);
	}
	
	@Click(R.id.tv_Not_So_Good_Mortgage_Negotiator)
	void onNotSoGoodClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedCreditScoreBandId("7");
		/*((MortgageNegotiatorActivity_)getActivity()).creditRatingText = notSoGood.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(Constants.FOUR);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Property Zip Code");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.drawable.selected_border);
//		((MortgageNegotiatorActivity_)getActivity()).layoutCreditRating.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).creditRating.setText(((MortgageNegotiatorActivity_)getActivity()).creditRatingText);
	}
}
