package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.adapter.MortgageNegotiatorFragmentAdapter;
import com.lendingtree.model.MortgageNegotiator;
import com.lendingtree.util.Constants;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

@EActivity(R.layout.mortgage_negotiator)
public class MortgageNegotiatorActivity extends BaseActivity {

	MortgageNegotiator mortgageNegotiator = new MortgageNegotiator("1","1","28277","1","1","1,3,6","true","9","9","0","400000","80000","0","0","2","1574","4.250");

	@ViewById(R.id.scrollMortgageNegotiator)
	ScrollView mortgageScroller;

	//Container container;

	Dialog dialog;

	boolean userRegistered;

	MortgageNegotiatorFragmentAdapter mAdapter;

	@ViewById(R.id.pager1)
	ViewPager mPager;

	PageIndicator mIndicator;

	@ViewById(R.id.btLeft1)
	ImageButton btLeft;

	@ViewById(R.id.btRight1)
	ImageButton btRight;

	@ViewById(R.id.tvPageTitle1)
	TextView pageTitle;

	@ViewById(R.id.tvMortgageNegotiatorFooter)
	TextView mortgageFooter;

	/*@ViewById(R.id.tvMortgageNegotiatorFooterDetails)
	TextView mortgageFooterDetails;*/

	ImageButton b;

	public MortgageNegotiatorActivity() {
		super(R.string.compare_your_current_offer_title);
	}

	public ViewPager getViewPager()
	{
		if (null == mPager) {
			mPager = (ViewPager) findViewById(R.id.pager1);
		}
		return mPager;
	}

	public ImageButton getLeftButton()
	{
		if(null == btLeft)
		{
			btLeft = (ImageButton) findViewById(R.id.btLeft1);
		}
		return btLeft;
	}

	public ImageButton getRightButton()
	{
		if(null == btRight)
		{
			btRight = (ImageButton) findViewById(R.id.btRight1);
		}
		return btRight;
	}

	public TextView getPageTitle()
	{
		if(null == pageTitle)
		{
			pageTitle = (TextView) findViewById(R.id.tvPageTitle1);
		}
		return pageTitle;
	}

	@AfterViews
	void start()
	{
		setHyperLink(mortgageFooter);
		Typeface font=Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		pageTitle.setTypeface(font);
		mortgageFooter.setTypeface(font);
		//mortgageFooterDetails.setTypeface(font);
		//		getSupportFragmentManager()
		//		.beginTransaction()
		//		.replace(R.id.menu_frame, new SlidingMenuFragment())
		//		.commit();
		//
		//		setSlidingActionBarEnabled(true);

		mAdapter = new MortgageNegotiatorFragmentAdapter(getSupportFragmentManager());

		mPager.setAdapter(mAdapter);

		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator1);
		mIndicator.setViewPager(mPager);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}

	@Click(R.id.btLeft1)
	void onLeftClick()
	{
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
		if(mPager.getCurrentItem() == 1)
		{
			pageTitle.setText("Loan Purpose");
			btLeft.setVisibility(View.GONE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.TWO)
		{
			pageTitle.setText("Type of Home");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.THREE)
		{
			pageTitle.setText("Home Value");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.FOUR)
		{
			pageTitle.setText("Credit Rating");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.FIVE)
		{
			pageTitle.setText("Property Zip Code");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.SIX) 
		{
			pageTitle.setText("Good Faith Estimate");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
			userRegistered = false;
			supportInvalidateOptionsMenu();
		}
		if(mPager.getCurrentItem() > 0)
		{
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
		enableLeftButton();
	}

	@Click(R.id.btRight1)
	void onRightClick()
	{
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
		if(mPager.getCurrentItem() == 0)
		{
			pageTitle.setText("Type of Home");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == 1)
		{
			pageTitle.setText("Home Value");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.TWO)
		{
			pageTitle.setText("Credit Rating");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		if(mPager.getCurrentItem() == Constants.THREE)
		{
			pageTitle.setText("Property Zip Code");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
		}
		//		if(mPager.getCurrentItem() == 4)
		//		{
		//			pageTitle.setText("Good Faith Estimate");
		//			btLeft.setVisibility(View.VISIBLE);
		//			btRight.setVisibility(View.VISIBLE);
		//		}
		if(mPager.getCurrentItem() == Constants.FIVE)
		{
			pageTitle.setText("Good Faith Estimate");
			btLeft.setVisibility(View.VISIBLE);
			btRight.setVisibility(View.VISIBLE);
			userRegistered = true;
			supportInvalidateOptionsMenu();
		}
		if(mPager.getCurrentItem() < mAdapter.getCount())
		{
			if(mPager.getCurrentItem() == Constants.FOUR)
			{
				PropertyZipLoanTermFragment propertyZipLoanTermFragment = (PropertyZipLoanTermFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
				propertyZipLoanTermFragment.onContinueClick();
			}
			else if(mPager.getCurrentItem() == Constants.SIX)
			{
				GoodFaithEstimateDataFragment goodFaithEstimateDataFragment = (GoodFaithEstimateDataFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
				goodFaithEstimateDataFragment.onGetOffersClick();
			}
			else
			{
				mPager.setCurrentItem(mPager.getCurrentItem() + 1);
				Log.i("Right", "Right click");
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

	@Touch(R.id.pager1)
	void onTouchClick(MotionEvent event)
	{
		ViewParent parent = getViewPager();
		parent.requestDisallowInterceptTouchEvent(false);
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
		getSupportActionBar().setDisplayShowHomeEnabled(true); //Ramesh Gundala
		//ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
		b = new ImageButton(getApplicationContext());
		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.compare));

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GoodFaithEstimateDataFragment goodFaithEstimateDataFragment = (GoodFaithEstimateDataFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
				goodFaithEstimateDataFragment.onGetOffersClick();
			}
		});

		menu.findItem(R.id.btnOffers).setActionView(b); 
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
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
			GoodFaithEstimateDataFragment goodFaithEstimateDataFragment = (GoodFaithEstimateDataFragment) mAdapter.getRegisteredFragment(mPager.getCurrentItem());
			goodFaithEstimateDataFragment.onGetOffersClick();
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	
	//	@Override
	//	protected boolean fitSystemWindows(Rect insets) {
	//		int leftPadding = insets.left;
	//		int rightPadding = insets.right;
	//		int topPadding = insets.top;
	//		int bottomPadding = insets.bottom;
	//		if (!mActionbarOverlay) {
	//			Log.v(TAG, "setting padding!");
	//			setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
	//		}
	//		return true;
	//	}

}
