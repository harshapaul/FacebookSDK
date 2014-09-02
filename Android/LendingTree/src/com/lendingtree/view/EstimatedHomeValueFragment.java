/*
 * This class is used to make a fragment for ViewPage Indicator implementing Estimated Home Value in Loan Explorer
 * 
 * @Sanchit
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
@EFragment(R.layout.estimated_home_value)
public class EstimatedHomeValueFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private static final String TAG = "EstimatedHomeValueFragment";

	private String mContent = "???";

	boolean isEditing;

	int value;
	
	@ViewById(R.id.tv_Estimated_Home_Value_dollar)
	TextView dollar;

	@ViewById(R.id.sb_Esimated_Home_Value)
	SeekBar seekBarEstimatedHomeValue;
	
	@ViewById(R.id.tv_50_val)
	TextView tv50Val;
	
	@ViewById(R.id.tv_1_val)
	TextView tv1Val;
	
	@ViewById(R.id.llHomeValue)
	LinearLayout llHomeValue;

	@ViewById(R.id.et_Estimated_Home_Value)
	EditText editTextEstimatedHomeValue;

	@ViewById(R.id.btContinue_Estimated_Home_Value)
	Button continueButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.estimated_home_value, container, false);
		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static EstimatedHomeValueFragment newInstance(String content)
	{
		EstimatedHomeValueFragment fragment = new EstimatedHomeValueFragment_(); 
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}

	ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {

			Rect gootFaithHeight = new Rect();
			llHomeValue.getWindowVisibleDisplayFrame(gootFaithHeight);
			
			int heightDiff = llHomeValue.getRootView()
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
				if(editTextEstimatedHomeValue.isFocused())
				{
					if (editTextEstimatedHomeValue.getText().toString().replaceAll("[^\\d]", "").isEmpty()) 
					{
						editTextEstimatedHomeValue.setText("50000");
					} 
					else if(Double.parseDouble(editTextEstimatedHomeValue.getText().toString().replaceAll("[^\\d]", "")) < 50000.00)
					{
						editTextEstimatedHomeValue.setText("50000");
					}
				} 
				
				
			}
			/*llPayment.getViewTreeObserver()
			.removeGlobalOnLayoutListener(this);*/
		}
	};
	
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

	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		Typeface fontBold=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		tv50Val.setTypeface(fontBold);
		tv1Val.setTypeface(fontBold);
		dollar.setTypeface(font);
		editTextEstimatedHomeValue.setTypeface(font);
		editTextEstimatedHomeValue.setText("400,000");
		llHomeValue.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
	}

	@SeekBarProgressChange(R.id.sb_Esimated_Home_Value)
	void onProgressChangeEstimatedValue(SeekBar seekBar, int progress, boolean fromUser)
	{
		((LoanExplorerActivity_)getActivity()).application.setDownPaymentState(false);
		if(!isEditing){
			//value = 50000 + progress;
			if (progress <= 60) {
				value = seekBarChange(50000,progress,2500);
			} else if (progress > 60 && progress <= 120) {
				
				value = seekBarChange(200000,progress - 60,5000);
			} else if (progress > 120 && progress <= 270) {
				
				value = seekBarChange(500000,progress - 120,10000);
			} 
			editTextEstimatedHomeValue.setText(value+"");
			((LoanExplorerActivity_)getActivity()).propertyValue = value+"";
			//seekBarEstimatedHomeValue.setProgress(progress);
			/*((LoanExplorerActivity_)getActivity()).estimateHomeValueText = value+"";*/
			((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue(value+"");
		}
	}

	private int seekBarChange(int initialValue, int progress, int multiplier) {
		return (initialValue + (progress * multiplier));
		
	}
	
	@AfterTextChange(R.id.et_Estimated_Home_Value)
	void afterTextChangeHomePrice(final Editable s) {
		((LoanExplorerActivity_)getActivity()).application.setDownPaymentState(false);
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
		//				seekBarEstimatedHomeValue.setProgress(value-50000);
		//				/*((LoanExplorerActivity_)getActivity()).estimateHomeValueText = value+"";*/
		//				((LoanExplorerActivity_)getActivity()).propertValue = value+"";
		//				((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue(value+"");
		//			}
		//			Log.v("11111111", s1 + "");
		//		}
		//		isEditing = false;
		//		Log.v(TAG, isEditing + "" + s.toString());
		// isTextChange = false;
		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");

		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);

			if (s1 < 50000.00) {
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				editTextEstimatedHomeValue.setOnEditorActionListener(new OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

						if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
							s.replace(0, s.length(), "50,000");

						}    
						return false;
					}
				});
			}
			if (s1 > 2000000.00) {
				editTextEstimatedHomeValue.setOnEditorActionListener(null);
				s.replace(0, s.length(), "2,000,000");
				value = 2000000;
				seekBarEstimatedHomeValue.setProgress(270);
				((LoanExplorerActivity_)getActivity()).propertyValue = value+"";
				((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue(value+"");
			}
			if (s1 >= 50000.00 && s1 <= 2000000.00) {
				editTextEstimatedHomeValue.setOnEditorActionListener(null);
				Log.v("+++++++++", s1 + "");
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				if ((int) s1 <= 200000) {
					seekBarEstimatedHomeValue.setProgress((int) (s1 - 50000) / 2500); 
					
				} else if ((int) s1 > 200000 && (int) s1 <= 500000) {
					
					seekBarEstimatedHomeValue.setProgress(((int) (s1 - 200000) / 5000) + 60); 
					//changedValue = seekBarChange(200000,progress - 60,5000);
				} else if ((int) s1 > 500000 && (int) s1 <= 2000000) {
					
					seekBarEstimatedHomeValue.setProgress(((int) (s1 - 500000) / 10000)+120); 
					//changedValue = seekBarChange(500000,progress - 120,20000);
				} 
				value = (int) s1;
			//	seekBarEstimatedHomeValue.setProgress(value - 50000);
				((LoanExplorerActivity_)getActivity()).propertyValue = value+"";
				((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedPropertyValue(value+"");
			}
			Log.v("11111111", s1 + "");

		}
		isEditing = false;

	}


	@Click(R.id.btContinue_Estimated_Home_Value)
	void onContinueClick()
	{
		if(((InputMethodManager) ((LoanExplorerActivity_)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
		{
			try
			{
				((InputMethodManager) ((LoanExplorerActivity_)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((LoanExplorerActivity_)getActivity()).getCurrentFocus().getWindowToken(), 0); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		int defaultValue=0;
		Log.i(TAG,"Inside onContinueClick");
		if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
		{
			defaultValue = (int) (Double.parseDouble(value+"")*0.2);	
		}
		else
		{	
			defaultValue = (int) (Double.parseDouble(value+"")*0.8);
		}
		RemainingBalanceFragment remainingBalanceFragment = (RemainingBalanceFragment) ((LoanExplorerActivity_)getActivity()).mAdapter.getRegisteredFragment(((LoanExplorerActivity_)getActivity()).mPager.getCurrentItem()+1);
		
		if (((LoanExplorerActivity_)getActivity()).application.isDownPaymentState()) {
			
		} else {
			remainingBalanceFragment.seekBarRemainingBalance.setMax(Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue) / Constants.FIVE_THOUSAND);	
			remainingBalanceFragment.seekBarRemainingBalance.setProgress(defaultValue / Constants.FIVE_THOUSAND);
			remainingBalanceFragment.editTextRemainingBalance.setText(defaultValue+"");
			((LoanExplorerActivity_)getActivity()).defaultValue = defaultValue;
		}
		
		((LoanExplorerActivity_)getActivity()).getPageTitle().setText("Loan Balance");
		((LoanExplorerActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((LoanExplorerActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);

		if(((LoanExplorerActivity_)getActivity()).getViewPager().getCurrentItem() < ((LoanExplorerActivity_)getActivity()).mAdapter.getCount())
		{
			((LoanExplorerActivity_)getActivity()).getViewPager().setCurrentItem(((LoanExplorerActivity_)getActivity()).getViewPager().getCurrentItem() + 1);
		}
	}

}

