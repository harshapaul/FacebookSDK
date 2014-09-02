package com.lendingtree.view;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.adapter.MortgageNavigtatorBestOfferAdapter;
import com.lendingtree.model.MortgageNegotiator;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;

@EActivity(R.layout.mortgage_negatitor_best_offer_parent)
public class MortgageNagotiatorBestOfferActivity extends BaseActivity {

	public MortgageNagotiatorBestOfferActivity() {
		super(R.string.mortgage_negotitor_bestoffer);
		// TODO Auto-generated constructor stub
	}

	Intent intent;
	ArrayList<Offers> offersList;
	@ViewById(R.id.lvnegatitorBestOffer)
	ListView lvnegatitorBestOffer;

	@Extra("offersdata")
	OfferContainer container;

	@Extra("offerslist")
	ArrayList<Offers> templList;

	@Extra("ipaddress")
	String ipAddress;

	@Extra("motrageNegotiater")
	MortgageNegotiator userData;

	TextView tvFooter, tvHeader;
	LinearLayout llFooter;
	View viewHeader;
	boolean redraw = true;
	Typeface tf;

	@AfterViews
	void displayMethod() {

		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();

		setSlidingActionBarEnabled(true);
		intent = getIntent();
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		viewHeader = getLayoutInflater().inflate(
				R.layout.footer_mortgage_neg_bestoffre, null);
		tvHeader = (TextView) viewHeader.findViewById(R.id.textHeader);
		View view = getLayoutInflater().inflate(R.layout.footer, null);
		llFooter = (LinearLayout) view.findViewById(R.id.llFooter);
		tvFooter = (TextView) view.findViewById(R.id.tvFooter);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {

		case DisplayMetrics.DENSITY_HIGH:
			tvFooter.setTextSize(11.6f);
			//tvFooter.setPadding(15, 10, 15, 10);
			// tvFooter.setText(getResources().getString(R.string.footer_details));
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			tvFooter.setTextSize(14f);
			break;
		}
		// TextView tvFooter1 = (TextView)
		// view.findViewById(R.id.tvLoanExplorerFooterDetails);
		// tvFooter1.setTypeface(tf);
		tvFooter.setTypeface(tf);
		tvHeader.setTypeface(tf);
		setHyperLink(tvFooter);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (redraw) {
			MortgageNavigtatorBestOfferAdapter adapter = new MortgageNavigtatorBestOfferAdapter(
					this, container, userData, templList, ipAddress);
			lvnegatitorBestOffer.addHeaderView(tvHeader);
			lvnegatitorBestOffer.addFooterView(llFooter);
			lvnegatitorBestOffer.setAdapter(adapter);
			redraw = false;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (((InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE))
					.isAcceptingText()) {
				((InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
			}
			toggle();
			return true;

		case R.id.btnDone:
			Intent intent = new Intent(this, MortgageNegotiatorActivity_.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		// ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));
		ImageButton b = new ImageButton(getApplicationContext());
		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.buttondone));

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						MortgageNegotiatorActivity_.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		menu.findItem(R.id.btnDone).setActionView(b);
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(true);
		return true;
	}

}
