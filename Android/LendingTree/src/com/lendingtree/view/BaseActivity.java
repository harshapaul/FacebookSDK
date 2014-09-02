/*
 * This class is used as a base activity which implements the Sliding Menu in the inherited Activity
 * 
 * @Sanchit
 */
package com.lendingtree.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SystemService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lendingtree.application.LendingTreeApplication;
import com.lendingtree.util.Constants;

@EActivity
public class BaseActivity extends SlidingFragmentActivity {

	int onStartCount;

	private static final String TAG = "BaseActivity";

	private int mTitleRes;
	protected Fragment mFrag;

	@App
	LendingTreeApplication application;

	@SystemService
	ConnectivityManager connectivityManager;

	@Extra("Slide")
	boolean sliding;

	public BaseActivity() {

	}

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Boolean bNetworkState = true;

			Log.d(TAG, "connection status: " + arg1.getAction());

			bNetworkState = haveNetworkConnection(arg0);
			if (bNetworkState == false) {
				// dismissProgressDialog();
				application.setbNetworkState(false);
			} else {
				application.setbNetworkState(true);
			}

			Log.d(TAG, "bNetworkState = " + bNetworkState);

		}

		private boolean haveNetworkConnection(Context context) {
			boolean haveConnectedWifi = false;
			boolean haveConnectedMobile = false;

			NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) {
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
					if (ni.isConnected())
						haveConnectedWifi = true;
				if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
					if (ni.isConnected())
						haveConnectedMobile = true;
			}
			return haveConnectedWifi || haveConnectedMobile;
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new SlidingMenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (Fragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}

		// Ramesh Gundala for Menu Icon showing
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		// ab.setDisplayHomeAsUpEnabled(true);
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(Constants.ZERO_POINT_THREE_FIVE);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch(metrics.densityDpi)
		{
		case DisplayMetrics.DENSITY_LOW:

			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			sm.setTouchmodeMarginThreshold(Constants.SIXTY);
			break;
		case DisplayMetrics.DENSITY_HIGH:
			sm.setTouchmodeMarginThreshold(Constants.SIXTY);
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			sm.setTouchmodeMarginThreshold(Constants.EIGHTY);
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			sm.setTouchmodeMarginThreshold(Constants.HUNDRED);
			break;
		default:
			sm.setTouchmodeMarginThreshold(Constants.SIXTY);
		}
		onStartCount = 1;
		if (savedInstanceState == null) // 1st time
		{
			if(sliding)
			{
				this.overridePendingTransition(0,
						0);
			}
			else
			{
				this.overridePendingTransition(R.anim.anim_slide_in_left,
						R.anim.anim_slide_out_left);
			}
		} else // already created so reverse animation
		{ 
			onStartCount = 2;
		}
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		this.registerReceiver(this.receiver, filter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(this.receiver);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (onStartCount > 1) {

			this.overridePendingTransition(R.anim.anim_slide_in_right,
					R.anim.anim_slide_out_right);
			
		} else if (onStartCount == 1) {
			onStartCount++;
		}
	}

	public static void addLink(TextView textView, String patternToMatch,
			final String link) {
		Linkify.TransformFilter filter = new Linkify.TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				return link;
			}
		};
		Linkify.addLinks(textView, Pattern.compile(patternToMatch), null, null,
				filter);
	}

	void setHyperLink(TextView tvFooter) {
		tvFooter.setLinkTextColor(getResources().getColor(R.color.silver));
		addLink(tvFooter, "Privacy",
				"https://www.lendingtree.com/legal/privacy-policy");
		addLink(tvFooter, "Terms of Use",
				"https://www.lendingtree.com/legal/terms-of-use");
		addLink(tvFooter, "Licenses",
				"https://www.lendingtree.com/legal/licenses-and-disclosures");
		addLink(tvFooter, "Disclosures",
				"https://www.lendingtree.com/legal/advertising-disclosures");

	}

	/**
	 * @category For formatting the integer value
	 * @param digits
	 * @return String
	 */
	public String addCommasToNumericString(String digits) {
		String result = "";
		int length = digits.length();
		int nDigits = 0;

		for (int i = length - 1; i >= 0; i--) {
			result = digits.charAt(i) + result;
			nDigits++;
			if (((nDigits % Constants.THREE) == 0) && (i > 0)) {
				result = "," + result;
			}
		}
		return (result);
	}
	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { case android.R.id.home: toggle(); return true; case
	 * R.id.github:
	 * 
	 * Log.v(TAG, "-----"+application.isCheckDetails());
	 * //Util.goToGitHub(this); if (application.isCheckDetails()) { Intent
	 * intent = new Intent(this, MortgagePaymentDetailsActivity.class);
	 * startActivity(intent); } else { Intent intent = new Intent(this,
	 * PinnedSectionListActivity.class); startActivity(intent); }
	 * 
	 * return true;
	 * 
	 * } return super.onOptionsItemSelected(item); }
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getSupportMenuInflater().inflate(R.menu.main, menu); return true; }
	 */
}
