package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@EFragment(R.layout.good_faith_estimate)
public class GoodFaithEstimateFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private String mContent = "???";
	
	@ViewById(R.id.tv_enterData_Header)
	TextView header;
	
	@ViewById(R.id.btEnterData)
	Button enterData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.compare_loan_offers, container, false);
		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static GoodFaithEstimateFragment newInstance(String content)
	{
		GoodFaithEstimateFragment fragment = new GoodFaithEstimateFragment_();
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		header.setTypeface(font);
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
	
	@Click(R.id.btEnterData)
	void onEnterDataClick()
	{
		if(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() < ((MortgageNegotiatorActivity_)getActivity()).mAdapter.getCount())
		{
			((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() + 1);
			Log.i("Continue", "Continue click");
		}
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Good Faith Estimate");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).userRegistered = true;
		((MortgageNegotiatorActivity_)getActivity()).supportInvalidateOptionsMenu();
	}
}
