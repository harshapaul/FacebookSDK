package com.lendingtree.view;

import java.util.ArrayList;
import java.util.Collections;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.adapter.LoanExplorerListOffersAdapter;
import com.lendingtree.comparator.AprComparator;
import com.lendingtree.comparator.LenderComparator;
import com.lendingtree.comparator.PointsComparator;
import com.lendingtree.comparator.RateComparator;
import com.lendingtree.comparator.RatingsComparator;
import com.lendingtree.comparator.RelevanceComparator;
import com.lendingtree.model.Filters;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;

/**
 * @category This Activity class is for the loan explorer offers
 * @author ankit
 * 
 */
@EActivity(R.layout.loan_offer_parent)
public class LoanOffersActivity extends BaseActivity {

	Button btnFilter;

	@ViewById(R.id.lvOffers)
	ListView lvOffers;
	Typeface tf;
	TextView tvFooter;
	RelativeLayout layoutHeader;

	ArrayList<Offers> offersList;
	ArrayList<Offers> tempOffersList;
	String ipAddress, requestId;
	LinearLayout llFooter;
	boolean redraw = true;

	public LoanOffersActivity() {
		super(R.string.instant_mortgage_quotes_title);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if (redraw) {
			OfferContainer offerContainer =  application.getOfferContainer();/*(OfferContainer) getIntent()
					.getSerializableExtra("offers");*/
			ipAddress = getIntent().getStringExtra("ipAddress");
			requestId = offerContainer.getQuotesId();

			offersList = new ArrayList<Offers>();
			offersList = offerContainer.getOffers();
			
			tempOffersList = offersList;
			
			Collections.sort(tempOffersList, new RelevanceComparator());
			Collections.reverse(tempOffersList);

			LoanExplorerListOffersAdapter adapter = new LoanExplorerListOffersAdapter(
					this, tempOffersList, ipAddress, requestId);
			lvOffers.addHeaderView(layoutHeader);
			lvOffers.addFooterView(llFooter);

			lvOffers.setAdapter(adapter);
			redraw = false;
		}

	}

	@AfterViews
	void afterViews() {
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();

		setSlidingActionBarEnabled(true);
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		
		View view = getLayoutInflater().inflate(R.layout.footer, null);
		llFooter = (LinearLayout) view.findViewById(R.id.llFooter);
		tvFooter = (TextView) view.findViewById(R.id.tvFooter);
		//TextView tvFooter1 = (TextView) view.findViewById(R.id.tvLoanExplorerFooterDetails);

		View view1 = getLayoutInflater().inflate(R.layout.offerlist_header,
				null);
		layoutHeader = (RelativeLayout) view1.findViewById(R.id.rloffer);
		btnFilter = (Button) view1.findViewById(R.id.btnFilters);
		Button tvOffer = (Button) view1.findViewById(R.id.tvOffer);
		btnFilter.setTypeface(tf);
		tvOffer.setTypeface(tf);
		tvFooter.setTypeface(tf);
		//tvFooter1.setTypeface(tf);
		btnFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoanOffersActivity.this,
						FilterYourResultsActivity_.class);
				startActivityForResult(intent, 1);

			}
		});
		setHyperLink(tvFooter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
					.isAcceptingText()) {
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
			}
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		// ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));

		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Log.i("On Activity Result", "On Activity Result");
			Filters filters = (Filters) data.getSerializableExtra("Filters");
			tempOffersList = new ArrayList<Offers>();
			for (Offers offers : offersList) {
				if ((offers.getLoanProgramId() + "").contains("1")
						&& filters.isIs30YearFixed()) {
					// for 30 year
					if ((offers.getPoints() + "").contains("0.")
							&& filters.isPoint0()) {
						// for points 0
						tempOffersList.add(offers);
						
					} else if ((offers.getPoints() + "")
							.contains("1.") && filters.isPoint1()) {
						// for points 1
						tempOffersList.add(offers);
					} else if ((offers.getPoints() + "")
							.contains("2.") && filters.isPoint2()) {
						// for points 2
						tempOffersList.add(offers);
					} else if(!filters.isPoint0() && !filters.isPoint1() && !filters.isPoint2()) {
						tempOffersList.add(offers);
					}
				} else if ((offers.getLoanProgramId() + "").contains("3")
						&& filters.isIs15YearFixed()) {
					// for 15 year
					if ((offers.getPoints() + "").contains("0.")
							&& filters.isPoint0()) {
						// for points 0
						tempOffersList.add(offers);
						
					} else if ((offers.getPoints() + "")
							.contains("1.") && filters.isPoint1()) {
						// for points 1
						tempOffersList.add(offers);
					} else if ((offers.getPoints() + "")
							.contains("2.") && filters.isPoint2()) {
						// for points 2
						tempOffersList.add(offers);
					} else if(!filters.isPoint0() && !filters.isPoint1() && !filters.isPoint2()) {
						tempOffersList.add(offers);
					}
				} else if ((offers.getLoanProgramId() + "").contains("6")
						&& filters.isIs5Year()) {
					// for 5 year
					if ((offers.getPoints() + "").contains("0.")
							&& filters.isPoint0()) {
						// for points 0
						tempOffersList.add(offers);
						
					} else if ((offers.getPoints() + "")
							.contains("1.") && filters.isPoint1()) {
						// for points 1
						tempOffersList.add(offers);
					} else if ((offers.getPoints() + "")
							.contains("2.") && filters.isPoint2()) {
						// for points 2
						tempOffersList.add(offers);
					} else if(!filters.isPoint0() && !filters.isPoint1() && !filters.isPoint2()) {
						tempOffersList.add(offers);
					}
				} else if (filters.isIs30YearFixed() == false
						&& filters.isIs15YearFixed() == false
						&& filters.isIs5Year() == false) {

					if ((offers.getPoints() + "").contains("0.")
							&& filters.isPoint0()) {
						// for points 0
						tempOffersList.add(offers);
					} else if ((offers.getPoints() + "")
							.contains("1.") && filters.isPoint1()) {
						// for points 1
						tempOffersList.add(offers);
					} else if ((offers.getPoints() + "")
							.contains("2.") && filters.isPoint2()) {
						// for points 2
						tempOffersList.add(offers);
					} else if(!filters.isPoint0() && !filters.isPoint1() && !filters.isPoint2()) {
						tempOffersList.add(offers);
					}
				}
			}
			
			if (filters.getSortBy().equalsIgnoreCase("Rate")) {
				Collections.sort(tempOffersList, new RateComparator());
			} else if (filters.getSortBy().equalsIgnoreCase("APR")) {
				Collections.sort(tempOffersList, new AprComparator());
			} else if (filters.getSortBy().equalsIgnoreCase("Ratings")) {
				Collections.sort(tempOffersList, new RatingsComparator());
			} else if (filters.getSortBy().equalsIgnoreCase("Points")) {
				Collections.sort(tempOffersList, new PointsComparator());
			} else if (filters.getSortBy().equalsIgnoreCase("Lender")) {
				Collections.sort(tempOffersList, new LenderComparator());
			} else if (filters.getSortBy().equalsIgnoreCase("Relevance")) {
				Collections.sort(tempOffersList, new RelevanceComparator());
				Collections.reverse(tempOffersList);
			}
			
			//Collections.reverse(tempOffersList);
			LoanExplorerListOffersAdapter adapter = new LoanExplorerListOffersAdapter(this, tempOffersList, ipAddress, requestId);
			// lvOffers.addFooterView(tvFooter);
			lvOffers.setAdapter(adapter);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		application.setFilters(null);
		//application.isFirst = true;
	}

}
