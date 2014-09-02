/*
 * This class is used to make a fragment for ViewPage Indicator implementing Remaining Balance in Loan Explorer
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

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
@EFragment(R.layout.down_payment)
public class RemainingBalanceFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private static final String TAG = "RemainingBalanceFragment";

	private String mContent = "???";

	boolean isEditing;

	int value;

	@ViewById(R.id.tv_Remaining_Balance_dollar)
	TextView dollar;
	
	@ViewById(R.id.sb_Remaining_Balance)
	SeekBar seekBarRemainingBalance;
	
	@ViewById(R.id.tv_0_Val)
	TextView tv0Val;
	
	@ViewById(R.id.tv_100_Val)
	TextView tv100Val;

	@ViewById(R.id.et_Remaining_Balance)
	EditText editTextRemainingBalance;
	
	@ViewById(R.id.tvDownPaymentPercentage)
	TextView tvDownPaymentPercentage;

	@ViewById(R.id.btContinue_Reamining_Balance)
	Button continueButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.remaining_balance, container, false);
		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static RemainingBalanceFragment newInstance(String content)
	{
		RemainingBalanceFragment fragment = new RemainingBalanceFragment_();
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

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		int defaultVal = ((LoanExplorerActivity_)getActivity()).defaultValue;
		seekBarRemainingBalance.setMax(Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue)/Constants.FIVE_THOUSAND);
		seekBarRemainingBalance.setProgress(defaultVal/Constants.FIVE_THOUSAND);
		
		((LoanExplorerActivity_)getActivity()).defaultValue = defaultVal;
		editTextRemainingBalance.setText(((LoanExplorerActivity_)getActivity()).defaultValue+"");
		tvDownPaymentPercentage.setText("("+(Math.round(((LoanExplorerActivity_)getActivity()).defaultValue * Constants.HUNDRED)/Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue))+"%)");
		Log.i("OnStart", ((LoanExplorerActivity_)getActivity()).defaultValue+"");
	}
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		Typeface fontBold=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		tv0Val.setTypeface(fontBold);
		tv100Val.setTypeface(fontBold);
		dollar.setTypeface(font);
		editTextRemainingBalance.setTypeface(font);
		tvDownPaymentPercentage.setTypeface(fontBold);
		//editTextRemainingBalance.setText(((LoanExplorerActivity_)getActivity()).defaultValue);
	}

	@SeekBarProgressChange(R.id.sb_Remaining_Balance)
	void onProgressChangeEstimatedValue(SeekBar seekBar, int progress, boolean fromUser)
	{
	//	((LoanExplorerActivity_)getActivity()).application.setDownPaymentState(true);
		Log.i(TAG,"progress = " + progress);
		if(!isEditing)
		{
			value = progress * Constants.FIVE_THOUSAND;
			editTextRemainingBalance.setText(value+"");
		
			tvDownPaymentPercentage.setText("("+(Math.round(value * 100)/Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue))+"%)");
			((LoanExplorerActivity_)getActivity()).defaultValue = value;
			if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
			{
				((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment(value+"");
				((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance("0");
			}
			else
			{
				((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance(value+"");
				((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment("0");
			}
		}
	}

	@AfterTextChange(R.id.et_Remaining_Balance)
	void afterTextChangeHomePrice(final Editable s) {
		
		//		if (isEditing)
		//			return;
		//		isEditing = true;
		//
		//		String str = s.toString().replaceAll("[^\\d]", "");
		//		if (!str.isEmpty()) {
		//			double s1 = Double.parseDouble(str);
		//			if (s1 < 0) {
		//				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
		//				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
		//				s.replace(0, s.length(), nf2.format(s1));
		//			}
		//
		//			if (s1 > Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertValue)) {
		//				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
		//				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
		//				s.replace(0, s.length(), nf2.format(Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertValue)));
		//			}
		//			if (s1 >= 0.00 && s1 <= Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertValue)) {
		//				Log.v("+++++++++", s1+"");
		//				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
		//				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
		//				s.replace(0, s.length(), nf2.format(s1));
		//				value = (int) s1;
		//				seekBarRemainingBalance.setProgress(value);
		//				((LoanExplorerActivity_)getActivity()).downPaymentDefaultValue = value+"";
		//				if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
		//				{
		//					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment(value+"");
		//					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance("0");
		//				}
		//				else
		//				{
		//					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance(value+"");
		//					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment("0");
		//				}
		//			}
		//			Log.v("11111111", s1 + "");
		//		}
		//		isEditing = false;
		//		
		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");
		editTextRemainingBalance.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
					if (!s.toString().isEmpty()) {
						v.setText(Integer.parseInt(s.toString())+"");
					}else {
						v.setText("0");
					}
					//s.replace(0, s.length(), "0");

				}    
				return false;
			}
		});
		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);

			if (s1 < Constants.ZERO_POINT_ZERO_ZERO) {
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				
			}
			if (s1 > Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertyValue)) {
				editTextRemainingBalance.setOnEditorActionListener(null);
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("$ ###,###.###");
				s.replace(0, s.length(), nf2.format(Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertyValue)));
				value = Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue);
				seekBarRemainingBalance.setProgress(value/Constants.FIVE_THOUSAND);
				((LoanExplorerActivity_)getActivity()).defaultValue = value;
				tvDownPaymentPercentage.setText("("+(Math.round(((LoanExplorerActivity_)getActivity()).defaultValue * Constants.HUNDRED)/Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue))+"%)");
				if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
				{
					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment(value+"");
					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance("0");
				}
				else
				{
					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance(value+"");
					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment("0");
				}
			}
			if (s1 >= 0.00 && s1 <= Double.parseDouble(((LoanExplorerActivity_)getActivity()).propertyValue)) {
				editTextRemainingBalance.setOnEditorActionListener(null);
				Log.v("+++++++++", s1 + "");
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				value = (int) s1;
				seekBarRemainingBalance.setProgress(value/Constants.FIVE_THOUSAND);
				((LoanExplorerActivity_)getActivity()).defaultValue = value;
				tvDownPaymentPercentage.setText("("+(Math.round(((LoanExplorerActivity_)getActivity()).defaultValue * Constants.HUNDRED)/Integer.parseInt(((LoanExplorerActivity_)getActivity()).propertyValue))+"%)");
				
				if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("1"))
				{
					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment(value+"");
					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance("0");
				}
				else
				{
					((LoanExplorerActivity_)getActivity()).loanExplorer.setCurrentMortgageBalance(value+"");
					((LoanExplorerActivity_)getActivity()).loanExplorer.setEstimatedDownPayment("0");
				}
			}
			
			Log.v("11111111", s1 + "");

		}
		isEditing = false;
	}

	@Click(R.id.btContinue_Reamining_Balance)
	void onContinueClick()
	{
		if(((LoanExplorerActivity_)getActivity()).getViewPager().getCurrentItem() < ((LoanExplorerActivity_)getActivity()).mAdapter.getCount())
		{
			((LoanExplorerActivity_)getActivity()).getViewPager().setCurrentItem(((LoanExplorerActivity_)getActivity()).getViewPager().getCurrentItem() + 1);

		}
		if (((LoanExplorerActivity_)getActivity()).getViewPager().getCurrentItem() == Constants.THREE) {
			((LoanExplorerActivity_)getActivity()).getPageTitle().setText("How's Your Credit");
			((LoanExplorerActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
			((LoanExplorerActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		}
	}
}
