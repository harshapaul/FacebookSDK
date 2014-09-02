/*
 * This Activity class is used for Loan Explorer 
 * 
 * @Sanchit
 */

package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.adapter.LoanExplorerFragmentAdapter;
import com.lendingtree.model.LoanExplorer;
import com.lendingtree.util.Constants;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

@EActivity(R.layout.loan_explorer)
public class LoanExplorerActivity extends BaseActivity {

	private static final String TAG = "LoanExplorerActivity";

	String propertyValue = "0";

	LoanExplorer loanExplorer = new LoanExplorer("1", "", "1", "1", "1", "1,3,6", "true", "9","9","0","0","0","0","0","2");

	LoanExplorerFragmentAdapter mAdapter;

	int defaultValue = 0;

	boolean userRegistered;
	
	@ViewById(R.id.pager)
	ViewPager mPager;

	PageIndicator mIndicator;

	@ViewById(R.id.btLeft)
	ImageButton btLeft;

	@ViewById(R.id.btRight)
	ImageButton btRight;

	@ViewById(R.id.tvPageTitle)
	TextView pageTitle;
	
	@ViewById(R.id.tvLoanExplorerFooter)
	TextView loanFooter;
	
	/*@ViewById(R.id.tvLoanExplorerFooterDetails)
	TextView loanFooterDetails;*/
	ImageButton b;
	public LoanExplorerActivity() {
		super(R.string.instant_mortgage_quotes_title);
	}

	public ViewPager getViewPager() {
		if (null == mPager) {
			mPager = (ViewPager) findViewById(R.id.pager);
		}
		return mPager;
	}

	public ImageButton getLeftButton() {
		if (null == btLeft) {
			btLeft = (ImageButton) findViewById(R.id.btLeft);
		}
		return btLeft;
	}

	public ImageButton getRightButton() {
		if (null == btRight) {
			btRight = (ImageButton) findViewById(R.id.btRight);
		}
		return btRight;
	}

	public TextView getPageTitle() {
		if (null == pageTitle) {
			pageTitle = (TextView) findViewById(R.id.tvPageTitle);
		}
		return pageTitle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	void start() {
		setHyperLink(loanFooter);
		Typeface font=Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		pageTitle.setTypeface(font);
		loanFooter.setTypeface(font);
		//loanFooterDetails.setTypeface(font);
//		txtViewEx.setTypeface(font);
//		txtViewEx.setText(getResources().getString(R.string.footer_details), true);
//		txtViewEx.setDrawingCacheEnabled(true);
//		getSupportFragmentManager().beginTransaction()
//		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
//
//		setSlidingActionBarEnabled(true);

		mAdapter = new LoanExplorerFragmentAdapter(this,
				getSupportFragmentManager());

		mPager.setAdapter(mAdapter);

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		
		mIndicator.setViewPager(mPager);
		((CirclePageIndicator)mIndicator).setEnableTouch(false);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && mPager.getCurrentItem() != 0 ) {
	        mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
	        return true;
	    }else {
	       return super.onKeyDown(keyCode, event);
	    }
	}
	
	@Click(R.id.btLeft)
	void onLeftClick() {
		if(((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
		{
			try
			{
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		btLeft.setEnabled(false);
		btLeft.setClickable(false);
		if (mPager.getCurrentItem() == 1) {
			pageTitle.setText("Loan Purpose");
			btLeft.setVisibility(View.GONE);
			btRight.setVisibility(View.VISIBLE);
		}
		if (mPager.getCurrentItem() == Constants.TWO) {
			application.setDownPaymentState(true);
			pageTitle.setText("Estimated Home Value");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if (mPager.getCurrentItem() == Constants.THREE) {
			if(loanExplorer.getRequestedLoanTypeId().equals("1"))
			{
				pageTitle.setText("Down Payment");
			}
			else
			{
				pageTitle.setText("Loan Balance");
			}
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
//			int defaultValue = 0;
//			if(loanExplorer.getRequestedLoanTypeId().equals("1"))
//			{
//				defaultValue = (int) (Double.parseDouble(loanExplorer.getEstimatedPropertyValue())*0.2);
//			}
//			else
//			{
//				defaultValue = (int) (Double.parseDouble(loanExplorer.getEstimatedPropertyValue())*0.8);
//			}
//			RemainingBalanceFragment remainingBalanceFragment = (RemainingBalanceFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem()-1);
//			remainingBalanceFragment.seekBarRemainingBalance.setMax(Integer.parseInt(propertyValue));
//			remainingBalanceFragment.seekBarRemainingBalance.setProgress(defaultValue);
//			this.defaultValue = defaultValue;
//			Log.i("Property Value", propertyValue+"");
//			Log.i("Default Value", defaultValue+"");
//			Log.i("This.Default Value", this.defaultValue+"");
		}
		if(mPager.getCurrentItem() == Constants.FOUR) {
			pageTitle.setText("How's Your Credit");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
			userRegistered = false;
			supportInvalidateOptionsMenu();
		}
		//		if(mPager.getCurrentItem() == 4) {
		//			pageTitle.setText("");
		//			btLeft.setVisibility(View.VISIBLE);
		//			btRight.setVisibility(View.VISIBLE);
		//		}
		if (mPager.getCurrentItem() > 0) {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
		enableLeftButton();
	}

	@Click(R.id.btRight)
	void onRightClick() {
		if(((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
		{
			try
			{
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		btRight.setEnabled(false);
		btRight.setClickable(false);
		Log.i(TAG,"onRightClick()");
		if (mPager.getCurrentItem() == 0) 
		{
			pageTitle.setText("Estimated Home Value");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if (mPager.getCurrentItem() == 1) 
		{
			if(loanExplorer.getRequestedLoanTypeId().equals("1"))
			{
				pageTitle.setText("Down Payment");
			}
			else
			{
				pageTitle.setText("Loan Balance");
			}
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
			int defaultValue = 0;
			if(loanExplorer.getRequestedLoanTypeId().equals("1"))
			{
				defaultValue = (int) (Double.parseDouble(loanExplorer.getEstimatedPropertyValue())*Constants.ZERO_POINT_TWO);
			}
			else
			{
				defaultValue = (int) (Double.parseDouble(loanExplorer.getEstimatedPropertyValue())*Constants.ZERO_POINT_EIGHT);
			}
			
			RemainingBalanceFragment remainingBalanceFragment = (RemainingBalanceFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem()+1);
			//application.setDownPaymentState(false);
			if (application.isDownPaymentState()) {
				Log.v(TAG, this.defaultValue+"IF");
			} else {
				
				remainingBalanceFragment.seekBarRemainingBalance.setMax(Integer.parseInt(propertyValue)/Constants.FIVE_THOUSAND);
				remainingBalanceFragment.seekBarRemainingBalance.setProgress(defaultValue/Constants.FIVE_THOUSAND);
				this.defaultValue = defaultValue;
				Log.v(TAG, this.defaultValue+"ELse");
			}
			
		}
		if (mPager.getCurrentItem() == Constants.TWO) 
		{
			pageTitle.setText("How's Your Credit");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.THREE) 
		{
			pageTitle.setText("Property Zip Code");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
			userRegistered = true;
			supportInvalidateOptionsMenu();
		}
		if (mPager.getCurrentItem() < mAdapter.getCount()) 
		{
			if(mPager.getCurrentItem() == Constants.FOUR)
			{
				PropertyZipCodeFragment propertyZipCodeFragment = (PropertyZipCodeFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
				propertyZipCodeFragment.onGetOffersClick();
			}
			else 
			{
				mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			}
		}
		enableRightButton();
	}
	
	void enableLeftButton()
	{
		btLeft.setEnabled(true);
		btLeft.setClickable(true);
	}
	
	void enableRightButton()
	{
		btRight.setEnabled(true);
		btRight.setClickable(true);
	}

	@Touch(R.id.pager)
	void onTouchClick(MotionEvent event) {
		ViewParent parent = getViewPager();
		parent.requestDisallowInterceptTouchEvent(false);
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			if(((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).isAcceptingText())
			{
				try
				{
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); 
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			toggle();
			return true;
		case R.id.btnOffers:
			PropertyZipCodeFragment propertyZipCodeFragment = (PropertyZipCodeFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
			propertyZipCodeFragment.onGetOffersClick();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onPrepareOptionsMenu(Menu menu)
	{
		MenuItem register = menu.findItem(R.id.btnOffers);      
		if(userRegistered) 
		{           
			register.setVisible(true);
		}
		else
		{
			register.setVisible(false);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		//ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
		b = new ImageButton(getApplicationContext());
		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.buttonoffers));

		b.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
			PropertyZipCodeFragment propertyZipCodeFragment = (PropertyZipCodeFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
			propertyZipCodeFragment.onGetOffersClick();
		}
		});

		menu.findItem(R.id.btnOffers).setActionView(b); 
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);

		return true;
	}

}
