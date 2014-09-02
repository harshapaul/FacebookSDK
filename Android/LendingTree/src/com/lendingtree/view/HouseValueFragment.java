/*
 * This class is used to make a fragment for ViewPage Indicator implementing House Value in Mortgage Negotiator
 * 
 *  @Sanchit
 */

package com.lendingtree.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import com.lendingtree.util.Constants;

import android.content.Context;
import android.graphics.Rect;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
@EFragment(R.layout.house_value)
public class HouseValueFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private String mContent = "???";

	boolean isEditing;

	int value;
	
	@ViewById(R.id.tv_House_Value_dollar)
	TextView dollar;

	@ViewById(R.id.sb_House_Value)
	SeekBar seekBarHouseValue;
	
	@ViewById(R.id.tv_50_Value)
	TextView tv50Val;
	
	@ViewById(R.id.tv_1_Value)
	TextView tv1Val;
	
	@ViewById(R.id.llHouseValue)
	LinearLayout llHouseValue;

	@ViewById(R.id.et_House_Value)
	EditText editTextHouseValue;

	@ViewById(R.id.btContinue_House_Value)
	Button contiButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.house_value, container, false);

		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static HouseValueFragment newInstance(String content)
	{
		HouseValueFragment fragment = new HouseValueFragment_(); 
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
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

	ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {

			Rect gootFaithHeight = new Rect();
			llHouseValue.getWindowVisibleDisplayFrame(gootFaithHeight);
			
			int heightDiff = llHouseValue.getRootView()
					.getHeight()
					- (gootFaithHeight.bottom - gootFaithHeight.top);
			
			if (heightDiff > 100) {
				// keyboard is up
				

				/*if (tvMortgageRateValue.isFocused()) {
					if (tvMortgageRateValue.getText().toString().trim()
							.equals("0")) {
						tvMortgageRateValue.setText("");
					}
				}*/

			} else {
				if(editTextHouseValue.isFocused())
				{
					if (editTextHouseValue.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						editTextHouseValue.setText("50000");
					} 
					else if(Double.parseDouble(editTextHouseValue.getText().toString().replaceAll("[^\\d]", "")) < 50000.00)
					{
						editTextHouseValue.setText("50000");
					}
				} 
				
				
			}
			/*llPayment.getViewTreeObserver()
			.removeGlobalOnLayoutListener(this);*/
		}
	};
	
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		Typeface fontBold=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		tv50Val.setTypeface(fontBold);
		tv1Val.setTypeface(fontBold);
		dollar.setTypeface(font);
		editTextHouseValue.setTypeface(font);
		editTextHouseValue.setText("400,000");
		llHouseValue.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
	}

	@SeekBarProgressChange(R.id.sb_House_Value)
	void onProgressChangeEstimatedValue(SeekBar seekBar, int progress, boolean fromUser)
	{

		if(!isEditing){
			//value = 50000 + progress;
			
			if (progress <= Constants.SIXTY) {
				value = seekBarChange(Constants.FIFTY_THOUSAND, progress, Constants.TWO_THOUSAND_FIVE_HUNDRED);
			} else if (progress > Constants.SIXTY && progress <= Constants.ONE_TWENTY) {
				
				value = seekBarChange(Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO, progress - 60,5000);
			} else if (progress > Constants.ONE_TWENTY && progress <= Constants.TWO_SEVENTY) {
				
				value = seekBarChange(500000,progress - 120,10000);
			} 
			editTextHouseValue.setText(value+"");
			/*((MortgageNegotiatorActivity_)getActivity()).houseValueText = value+"";*/
			if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanTypeId().equals("1"))
			{
				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice(value+"");
			}
			else
			{
				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice("0");
			}
			//seekBarEstimatedHomeValue.setProgress(progress);
		}
	}
	
	private int seekBarChange(int initialValue, int progress, int multiplier) {
		return (initialValue + (progress * multiplier));
		
	}


	@AfterTextChange(R.id.et_House_Value)
	void afterTextChangeHomePrice(final Editable s) {
		//		if (isEditing)
		//			return;
		//		isEditing = true;
		//
		//		String str = s.toString().replaceAll("[^\\d]", "");
		//		if (!str.isEmpty()) {
		//			double s1 = Double.parseDouble(str);
		//			if (s1 < 50000.00) {
		//				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
		//				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
		//				s.replace(0, s.length(), nf2.format(s1));
		//			}
		//			
		//			if (s1 > 1000000.00) {
		//				s.replace(0, s.length(), "$1,000,000");
		//			}
		//			if (s1 >= 50000.00 && s1 <= 1000000.00) {
		//				Log.v("+++++++++", s1+"");
		//				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
		//				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
		//				s.replace(0, s.length(), nf2.format(s1));
		//				value = (int) s1;
		//				seekBarHouseValue.setProgress(value-50000);
		//				/*((MortgageNegotiatorActivity_)getActivity()).houseValueText = value+"";*/
		//				if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanTypeId().equals("1"))
		//				{
		//					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
		//					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice(value+"");
		//				}
		//				else
		//				{
		//					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
		//					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice("0");
		//				}
		//			}
		//			Log.v("11111111", s1 + "");
		//		}
		//		isEditing = false;

		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");

		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);

			if (s1 < Constants.FIFTY_THOUSAND_POINT_ZERO_ZERO) {
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				editTextHouseValue.setOnEditorActionListener(new OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

						if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
							s.replace(0, s.length(), "50,000");

						}    
						return false;
					}
				});
			}
			if (s1 > Constants.TWO_MILLAN_POINT_ZERO_ZERO) {
				editTextHouseValue.setOnEditorActionListener(null);
				s.replace(0, s.length(), "2,000,000");
				value = Constants.TWO_MILLAN;
				//seekBarHouseValue.setProgress(value - 50000);
				seekBarHouseValue.setProgress(270);
				if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanTypeId().equals("1"))
				{
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice(value+"");
				}
				else
				{
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice("0");
				}
			}
			if (s1 >= Constants.FIFTY_THOUSAND_POINT_ZERO_ZERO && s1 <= Constants.TWO_MILLAN_POINT_ZERO_ZERO) {
				editTextHouseValue.setOnEditorActionListener(null);
				Log.v("+++++++++", s1 + "");
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				
				if ((int) s1 <= Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO) {
					seekBarHouseValue.setProgress((int) (s1 - Constants.FIFTY_THOUSAND) / Constants.TWO_THOUSAND_FIVE_HUNDRED); 
					
				} else if ((int) s1 > Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO && (int) s1 <= Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO) {
					
					seekBarHouseValue.setProgress(((int) (s1 - Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO) / Constants.FIVE_THOUSAND) + Constants.SIXTY); 
					//changedValue = seekBarChange(200000,progress - 60,5000);
				} else if ((int) s1 > Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO && (int) s1 <= Constants.TWO_MILLAN) {
					
					seekBarHouseValue.setProgress(((int) (s1 - Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO) / Constants.TEN_THUSAND) + Constants.ONE_TWENTY); 
					//changedValue = seekBarChange(500000,progress - 120,20000);
				} 
				
				value = (int) s1;
				//seekBarHouseValue.setProgress(value - 50000);
				
				if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanTypeId().equals("1"))
				{
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice(value+"");
				}
				else
				{
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPropertyValue(value+"");
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setEstimatedPurchasePrice("0");
				}
			}
			Log.v("11111111", s1 + "");

		}
		isEditing = false;
	}

	@Click(R.id.btContinue_House_Value)
	void onContinueClick()
	{
		if(((InputMethodManager) ((MortgageNegotiatorActivity_)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
		{
			try
			{
				((InputMethodManager) ((MortgageNegotiatorActivity_)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((MortgageNegotiatorActivity_)getActivity()).getCurrentFocus().getWindowToken(), 0); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		if(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() < ((MortgageNegotiatorActivity_)getActivity()).mAdapter.getCount())
		{
			((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() + 1);

		}
		if(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() == Constants.THREE)
		{
			((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Credit Rating");
			((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
			((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);

		}
	}
}
