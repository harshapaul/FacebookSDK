/*
 * This class is used to make a fragment for ViewPage Indicator implementing How's Your Credit in Loan Explorer
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

@EFragment(R.layout.hows_your_credit)
public class HowsYourCreditFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private String mContent = "???";

	@ViewById(R.id.tv_Excellent_Loan_Explorer)
	TextView excellent;

	@ViewById(R.id.tv_Good_Loan_Explorer)
	TextView good;

	@ViewById(R.id.tv_Fair_Loan_Explorer)
	TextView fair;

	@ViewById(R.id.tv_Not_So_Good_Loan_Explorer)
	TextView notSoGood;
	String styledText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		// View view = inflater.inflate(R.layout.hows_your_credit, container,
		// false);
		return null;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (((LoanExplorerActivity_) getActivity()).loanExplorer
				.getEstimatedCreditScoreBandId().equals("4")) {
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.drawable.selected_border);
			fair.setBackgroundResource(R.color.White);
			notSoGood.setBackgroundResource(R.color.White);
		}
		if (((LoanExplorerActivity_) getActivity()).loanExplorer
				.getEstimatedCreditScoreBandId().equals("6")) {
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.color.White);
			fair.setBackgroundResource(R.drawable.selected_border);
			notSoGood.setBackgroundResource(R.color.White);
		}
		if (((LoanExplorerActivity_) getActivity()).loanExplorer
				.getEstimatedCreditScoreBandId().equals("8")) {
			excellent.setBackgroundResource(R.color.White);
			good.setBackgroundResource(R.color.White);
			fair.setBackgroundResource(R.color.White);
			notSoGood.setBackgroundResource(R.drawable.selected_border);
		}
	}

	@AfterViews
	void start() {
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Regular.ttf");
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

	// Returns a Fragment Object that can be used to create a Fragment for
	// ViewPage Indicator using Fragment Adapter
	public static HowsYourCreditFragment newInstance(String content) {
		HowsYourCreditFragment fragment = new HowsYourCreditFragment_();
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Click(R.id.tv_Excellent_Loan_Explorer)
	void onExcellentClick() {
		((LoanExplorerActivity_) getActivity()).loanExplorer
				.setEstimatedCreditScoreBandId("3");
		/*
		 * ((LoanExplorerActivity_)getActivity()).HowsYourCreditText =
		 * excellent.getText().toString();
		 */
		((LoanExplorerActivity_) getActivity()).getViewPager().setCurrentItem(
				Constants.FOUR);
		((LoanExplorerActivity_) getActivity()).getPageTitle().setText(
				"Property Zip Code");
		((LoanExplorerActivity_) getActivity()).getLeftButton().setVisibility(
				View.VISIBLE);
		((LoanExplorerActivity_) getActivity()).getRightButton().setVisibility(
				View.VISIBLE);
		excellent.setBackgroundResource(R.drawable.selected_border);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.color.White);
		((LoanExplorerActivity_) getActivity()).userRegistered = true;
		((LoanExplorerActivity_) getActivity()).supportInvalidateOptionsMenu();
		// ((LoanExplorerActivity_)getActivity()).layoutHowsYourCredit.setVisibility(View.VISIBLE);
		// ((LoanExplorerActivity_)getActivity()).howsYourCredit.setText(((LoanExplorerActivity_)getActivity()).HowsYourCreditText);
	}

	@Click(R.id.tv_Good_Loan_Explorer)
	void onGoodClick() {
		((LoanExplorerActivity_) getActivity()).loanExplorer
				.setEstimatedCreditScoreBandId("5");
		/*
		 * ((LoanExplorerActivity_)getActivity()).HowsYourCreditText =
		 * good.getText().toString();
		 */
		((LoanExplorerActivity_) getActivity()).getViewPager().setCurrentItem(
				Constants.FOUR);
		((LoanExplorerActivity_) getActivity()).getPageTitle().setText(
				"Property Zip Code");
		((LoanExplorerActivity_) getActivity()).getLeftButton().setVisibility(
				View.VISIBLE);
		((LoanExplorerActivity_) getActivity()).getRightButton().setVisibility(
				View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.drawable.selected_border);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.color.White);
		((LoanExplorerActivity_) getActivity()).userRegistered = true;
		((LoanExplorerActivity_) getActivity()).supportInvalidateOptionsMenu();
		// ((LoanExplorerActivity_)getActivity()).layoutHowsYourCredit.setVisibility(View.VISIBLE);
		// ((LoanExplorerActivity_)getActivity()).howsYourCredit.setText(((LoanExplorerActivity_)getActivity()).HowsYourCreditText);
	}

	@Click(R.id.tv_Fair_Loan_Explorer)
	void onFairClick() {
		((LoanExplorerActivity_) getActivity()).loanExplorer
				.setEstimatedCreditScoreBandId("7");
		/*
		 * ((LoanExplorerActivity_)getActivity()).HowsYourCreditText =
		 * fair.getText().toString();
		 */
		((LoanExplorerActivity_) getActivity()).getViewPager().setCurrentItem(
				Constants.FOUR);
		((LoanExplorerActivity_) getActivity()).getPageTitle().setText(
				"Property Zip Code");
		((LoanExplorerActivity_) getActivity()).getLeftButton().setVisibility(
				View.VISIBLE);
		((LoanExplorerActivity_) getActivity()).getRightButton().setVisibility(
				View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.drawable.selected_border);
		notSoGood.setBackgroundResource(R.color.White);
		((LoanExplorerActivity_) getActivity()).userRegistered = true;
		((LoanExplorerActivity_) getActivity()).supportInvalidateOptionsMenu();
		// ((LoanExplorerActivity_)getActivity()).layoutHowsYourCredit.setVisibility(View.VISIBLE);
		// ((LoanExplorerActivity_)getActivity()).howsYourCredit.setText(((LoanExplorerActivity_)getActivity()).HowsYourCreditText);
	}

	@Click(R.id.tv_Not_So_Good_Loan_Explorer)
	void onNotSoGoodClick() {
		((LoanExplorerActivity_) getActivity()).loanExplorer
				.setEstimatedCreditScoreBandId("8");
		/*
		 * ((LoanExplorerActivity_)getActivity()).HowsYourCreditText =
		 * notSoGood.getText().toString();
		 */
		((LoanExplorerActivity_) getActivity()).getViewPager().setCurrentItem(
				Constants.FOUR);
		((LoanExplorerActivity_) getActivity()).getPageTitle().setText(
				"Property Zip Code");
		((LoanExplorerActivity_) getActivity()).getLeftButton().setVisibility(
				View.VISIBLE);
		((LoanExplorerActivity_) getActivity()).getRightButton().setVisibility(
				View.VISIBLE);
		excellent.setBackgroundResource(R.color.White);
		good.setBackgroundResource(R.color.White);
		fair.setBackgroundResource(R.color.White);
		notSoGood.setBackgroundResource(R.drawable.selected_border);
		((LoanExplorerActivity_) getActivity()).userRegistered = true;
		((LoanExplorerActivity_) getActivity()).supportInvalidateOptionsMenu();
		// ((LoanExplorerActivity_)getActivity()).layoutHowsYourCredit.setVisibility(View.VISIBLE);
		// ((LoanExplorerActivity_)getActivity()).howsYourCredit.setText(((LoanExplorerActivity_)getActivity()).HowsYourCreditText);
	}
}
