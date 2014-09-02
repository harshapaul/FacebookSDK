/*
 * This class is used to make a fragment for ViewPage Indicator implementing Loan Type in Mortgage Negotiator
 * 
 * @Sanchit
 */

package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
@EFragment(R.layout.loan_purpose)
public class LoanPurposeFragment extends Fragment {

private static final String KEY_CONTENT = "TestFragment:Content";
	
	private String mContent = "???";
	
	@ViewById(R.id.tv_Purchase_Mortgage_Negotiator)
	TextView purchase;
	
	@ViewById(R.id.tv_Refinance_Mortgage_Negotiator)
	TextView refinance;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.loan_type, container, false);
		//((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Loan Purpose");
        return null;
	}
	
	@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanTypeId().equals("2"))
			{
				purchase.setBackgroundResource(R.color.White);
				refinance.setBackgroundResource(R.drawable.selected_border);
			}
		}
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		purchase.setTypeface(font);
		refinance.setTypeface(font);
	}
	
	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static LoanPurposeFragment newInstance(String content)
	{
		LoanPurposeFragment fragment = new LoanPurposeFragment_(); 
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
	
	@Click(R.id.tv_Purchase_Mortgage_Negotiator)
	void onPurchaseClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanTypeId("1");
		/*((MortgageNegotiatorActivity_)getActivity()).loanTypeText = purchase.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(1);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Type of Home");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		purchase.setBackgroundResource(R.drawable.selected_border);
		refinance.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutLoanType.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).loanType.setText(((MortgageNegotiatorActivity_)getActivity()).loanTypeText);
	}
	
	@Click(R.id.tv_Refinance_Mortgage_Negotiator)
	void onRefinanceClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanTypeId("2");
		/*((MortgageNegotiatorActivity_)getActivity()).loanTypeText = refinance.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(1);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Type of Home");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		purchase.setBackgroundResource(R.color.White);
		refinance.setBackgroundResource(R.drawable.selected_border);
//		((MortgageNegotiatorActivity_)getActivity()).layoutLoanType.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).loanType.setText(((MortgageNegotiatorActivity_)getActivity()).loanTypeText);
	}
}
