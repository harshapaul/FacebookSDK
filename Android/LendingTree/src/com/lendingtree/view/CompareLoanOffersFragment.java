/*
 *  This class is used to make a fragment for ViewPage Indicator implementing Compare Loan Offers in Loan Explorer
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
@EFragment(R.layout.compare_loan_offers)
public class CompareLoanOffersFragment extends Fragment {
	
	private static final String KEY_CONTENT = "TestFragment:Content";
	
	private String mContent = "???";
	
	@ViewById(R.id.tv_Purchase_Loan_Explorer)
	TextView purchase;
	
	@ViewById(R.id.tv_Refinance_Loan_Explorer)
	TextView refinance;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.compare_loan_offers, container, false);
		//((LoanExplorerActivity_)getActivity()).getPageTitle().setText("Compare Loan Offers");
        return null;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(((LoanExplorerActivity_)getActivity()).loanExplorer.getRequestedLoanTypeId().equals("2"))
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
	public static CompareLoanOffersFragment newInstance(String content)
	{
		CompareLoanOffersFragment fragment = new CompareLoanOffersFragment_(); 
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

	@Click(R.id.tv_Purchase_Loan_Explorer)
	void onPurchaseClick()
	{
		((LoanExplorerActivity_)getActivity()).loanExplorer.setRequestedLoanTypeId("1");
		/*((LoanExplorerActivity_)getActivity()).compareLoanOffersText = purchase.getText().toString();*/
		((LoanExplorerActivity_)getActivity()).getViewPager().setCurrentItem(1);
		((LoanExplorerActivity_)getActivity()).getPageTitle().setText("Estimated Home Value");
		((LoanExplorerActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((LoanExplorerActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		purchase.setBackgroundResource(R.drawable.selected_border);
		refinance.setBackgroundResource(R.color.White);
//		((LoanExplorerActivity_)getActivity()).layoutCompareLoanOffers.setVisibility(View.VISIBLE);
//		((LoanExplorerActivity_)getActivity()).compareLoanOffers.setText(((LoanExplorerActivity_)getActivity()).compareLoanOffersText);
	}
	
	@Click(R.id.tv_Refinance_Loan_Explorer)
	void onRefinanceClick()
	{
		((LoanExplorerActivity_)getActivity()).loanExplorer.setRequestedLoanTypeId("2");
		/*((LoanExplorerActivity_)getActivity()).compareLoanOffersText = refinance.getText().toString();*/
		((LoanExplorerActivity_)getActivity()).getViewPager().setCurrentItem(1);
		((LoanExplorerActivity_)getActivity()).getPageTitle().setText("Estimated Home Value");
		((LoanExplorerActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((LoanExplorerActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		purchase.setBackgroundResource(R.color.White);
		refinance.setBackgroundResource(R.drawable.selected_border);
//		((LoanExplorerActivity_)getActivity()).layoutCompareLoanOffers.setVisibility(View.VISIBLE);
//		((LoanExplorerActivity_)getActivity()).compareLoanOffers.setText(((LoanExplorerActivity_)getActivity()).compareLoanOffersText);
	}
	
	
	
}
