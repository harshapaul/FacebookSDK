/*
 * This class is used to make a fragment for ViewPage Indicator implementing Type of Home in Mortgage Negotiator
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
@EFragment(R.layout.type_of_home)
public class TypeOfHomeFragment extends Fragment {

private static final String KEY_CONTENT = "TestFragment:Content";
	
	private String mContent = "???";
	
	@ViewById(R.id.tv_Single_Family)
	TextView singleFamily;
	
	@ViewById(R.id.tv_Town_Home)
	TextView townHome;
	
	@ViewById(R.id.tv_Condo)
	TextView condo;
	
	@ViewById(R.id.tv_Multi_Family)
	TextView multiFamily;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Generate a view for Fragment
		//View view = inflater.inflate(R.layout.type_of_home, container, false);

        return null;
	}
	
	@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getPropertyTypeId().equals("2"))
			{
				singleFamily.setBackgroundResource(R.color.White);
				townHome.setBackgroundResource(R.drawable.selected_border);
				condo.setBackgroundResource(R.color.White);
				multiFamily.setBackgroundResource(R.color.White);
			}
			if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getPropertyTypeId().equals("3"))
			{
				singleFamily.setBackgroundResource(R.color.White);
				townHome.setBackgroundResource(R.color.White);
				condo.setBackgroundResource(R.drawable.selected_border);
				multiFamily.setBackgroundResource(R.color.White);
			}
			if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getPropertyTypeId().equals("5"))
			{
				singleFamily.setBackgroundResource(R.color.White);
				townHome.setBackgroundResource(R.color.White);
				condo.setBackgroundResource(R.color.White);
				multiFamily.setBackgroundResource(R.drawable.selected_border);
			}
		}
	
	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		singleFamily.setTypeface(font);
		townHome.setTypeface(font);
		condo.setTypeface(font);
		multiFamily.setTypeface(font);
	}
	
	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static TypeOfHomeFragment newInstance(String content)
	{
		TypeOfHomeFragment fragment = new TypeOfHomeFragment_(); 
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
	
	@Click(R.id.tv_Single_Family)
	void onSingleFamilyClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyTypeId("1");
		/*((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText = singleFamily.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(2);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Home Value");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		singleFamily.setBackgroundResource(R.drawable.selected_border);
		townHome.setBackgroundResource(R.color.White);
		condo.setBackgroundResource(R.color.White);
		multiFamily.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutTypeOfHome.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).typeOfHome.setText(((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText);
	}
	
	@Click(R.id.tv_Town_Home)
	void onTownHomeClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyTypeId("2");
		/*((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText = townHome.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(2);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Home Value");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		singleFamily.setBackgroundResource(R.color.White);
		townHome.setBackgroundResource(R.drawable.selected_border);
		condo.setBackgroundResource(R.color.White);
		multiFamily.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutTypeOfHome.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).typeOfHome.setText(((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText);
	}
	
	@Click(R.id.tv_Condo)
	void onCondoClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyTypeId("3");
		/*((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText = condo.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(2);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Home Value");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		singleFamily.setBackgroundResource(R.color.White);
		townHome.setBackgroundResource(R.color.White);
		condo.setBackgroundResource(R.drawable.selected_border);
		multiFamily.setBackgroundResource(R.color.White);
//		((MortgageNegotiatorActivity_)getActivity()).layoutTypeOfHome.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).typeOfHome.setText(((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText);
	}
	
	@Click(R.id.tv_Multi_Family)
	void onMultiFamilyClick()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyTypeId("5");
		/*((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText = multiFamily.getText().toString();*/
		((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(2);
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Home Value");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
		singleFamily.setBackgroundResource(R.color.White);
		townHome.setBackgroundResource(R.color.White);
		condo.setBackgroundResource(R.color.White);
		multiFamily.setBackgroundResource(R.drawable.selected_border);
//		((MortgageNegotiatorActivity_)getActivity()).layoutTypeOfHome.setVisibility(View.VISIBLE);
//		((MortgageNegotiatorActivity_)getActivity()).typeOfHome.setText(((MortgageNegotiatorActivity_)getActivity()).typeOfHomeText);
	}
}
