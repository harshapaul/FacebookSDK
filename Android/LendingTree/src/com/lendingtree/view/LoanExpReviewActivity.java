package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.adapter.LoanReviewAdapter;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;
import com.lendingtree.model.ReviewContainer;
import com.lendingtree.networkutils.LoanExplorerReviewAdapter;
import com.lendingtree.util.Constants;

@EActivity(R.layout.loan_explorer__review_parent)
public class LoanExpReviewActivity extends BaseActivity implements
		android.view.View.OnClickListener {

	@ViewById(R.id.listreview)
	ListView listReview;

	@Bean
	LoanExplorerReviewAdapter loanReviewAdapter;

	Offers offers;
	String requestId;
	String ipAddress;

	TextView tvFooter, tvHeaderTitle, tvReview;

	// ListView listReview;
	boolean redraw = true;
	Button btnCallReview, btnMailReview;
	TextView tvNLMS;
	RatingBar rbReview;
	View viewHeader;
	LinearLayout llFooter;
	// boolean isDown = false;

	String lenderId;

	Dialog dialog;

	Typeface tf;
	Typeface tf1;

	// Constructor without parameter
	public LoanExpReviewActivity() {
		super(R.string.loan_exploree_details_title);

	}

	@AfterViews
	void displayReviewDetails() {
		OfferContainer offerContainer = (OfferContainer) getIntent()
				.getSerializableExtra("offers");
		lenderId = getIntent().getStringExtra("lenderId");

		// getActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		viewHeader = getLayoutInflater().inflate(
				R.layout.loan_rxp_review_header, null);
		btnCallReview = (Button) viewHeader
				.findViewById(R.id.btnCall_reviewParent);

		btnCallReview.setOnClickListener(this);

		btnMailReview = (Button) viewHeader
				.findViewById(R.id.btnMail_reviewParent);
		btnMailReview.setOnClickListener(this);

		tvHeaderTitle = (TextView) viewHeader
				.findViewById(R.id.tvheaderRatingUp);
		tvReview = (TextView) viewHeader
				.findViewById(R.id.tvheaderRatingReview);
		tvNLMS = (TextView) viewHeader.findViewById(R.id.tvNLMS);

		// listReview = (ListView) viewHeader.findViewById(R.id.listreview);

		rbReview = (RatingBar) viewHeader
				.findViewById(R.id.ratingBarReviewParent);
		offers = (Offers) getIntent().getSerializableExtra("offersdetail");

		ipAddress = getIntent().getStringExtra("ipaddress");
		requestId = getIntent().getStringExtra("requestid");
		tvNLMS.setText("NMLS # "+offers.getNMLSID()+".");
		tvHeaderTitle.setText(offers.getName());
		rbReview.setRating(offers.getAverageOverallRating());
		tvReview.setText("(" + offers.getTotalRatingsAndReviews() + " Reviews)");

		View view = getLayoutInflater().inflate(R.layout.footer, null);
		llFooter = (LinearLayout) view.findViewById(R.id.llFooter);
		tvFooter = (TextView) view.findViewById(R.id.tvFooter);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {

		case DisplayMetrics.DENSITY_HIGH:
			tvFooter.setTextSize(11.0f);
			break;
		}

		// TextView tvFooter1 = (TextView)
		// view.findViewById(R.id.tvLoanExplorerFooterDetails);
		// tvFooter1.setTypeface(tf);
		setHyperLink(tvFooter);
		settingFontAllViews();
		/*
		 * listReview.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent,View view,
		 * int position, long id) { // TODO Auto-generated method stub for(int
		 * i=0; i<parent.getChildCount(); i++) { if(i == position) {
		 * parent.getChildAt(i).setBackgroundColor(Color.parseColor("#aaaaaa"));
		 * } else {
		 * parent.getChildAt(i).setBackgroundColor(Color.parseColor("#aaaaaa"));
		 * }
		 * 
		 * } } });
		 */

		if (redraw) {

			showProgressDialog();
			redraw = false;
		}

	}

	@UiThread
	void showProgressDialog() {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.progress_layout);
		TextView progresTextView = (TextView) dialog
				.findViewById(R.id.tvProgressText);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"OpenSans-Bold.ttf");
		progresTextView.setText("Please Wait...");
		progresTextView.setTypeface(font);
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(getResources().getColor(
				R.color.Green));
		drawable.setAlpha(Constants.ONE_TWENTY_EIGHT);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
		LoanReviewTask();

	}

	@Background
	void LoanReviewTask() {

		ReviewContainer reviewContainer = loanReviewAdapter
				.callLoanOffersReview(lenderId);

		if (reviewContainer != null) {

			if (reviewContainer.getData() != null
					&& reviewContainer.getData().size() >= 0) {
				setListAdapter(reviewContainer);
			} else {
				onNoOffers();
				// setListAdapter(reviewContainer);
			}

		} else {
			onNoOffers();
			// setListAdapter(reviewContainer);
			Log.v("Inside else LoanReviewTask", "" + reviewContainer);
		}

	}

	@UiThread
	void onNoOffers() {

		if (dialog.isShowing() && dialog != null)
			dialog.dismiss();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		TextView messageText = new TextView(this);
		messageText.setPadding(Constants.TEN, Constants.TEN, Constants.TEN,
				Constants.TEN);
		messageText.setGravity(Gravity.CENTER);
		messageText.setTextSize(Constants.EIGHTEEN);
		messageText.setTypeface(tf);
		messageText.setText(getResources().getText(R.string.no_data_found));
		builder.setCustomTitle(messageText);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		builder.show();
	}

	@UiThread
	void setListAdapter(ReviewContainer reviewContainer) {
		if (dialog != null && dialog.isShowing()) {
			LoanReviewAdapter adapter = new LoanReviewAdapter(this,
					reviewContainer);
			listReview.addHeaderView(viewHeader);
			listReview.addFooterView(llFooter);
			listReview.setAdapter(adapter);
			dialog.dismiss();
			dialog.cancel();
		}

	}

	private void settingFontAllViews() {
		// TODO Auto-generated method stub
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		tvFooter.setTypeface(tf);
		tvHeaderTitle.setTypeface(tf);
		tvReview.setTypeface(tf);
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
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		// ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));

		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnCall_reviewParent:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoanExpReviewActivity.this);
			final String phoneNo = (offers.getTelephoneNumber());
			TextView messageText = new TextView(this);
			messageText.setPadding(Constants.TEN, Constants.TWENTY_FIVE,
					Constants.TEN, Constants.TWENTY_FIVE);
			messageText.setGravity(Gravity.CENTER);
			messageText.setTextSize(Constants.EIGHTEEN);
			messageText.setTypeface(tf);

			if (offers.isShowTelephoneNumber()
					&& offers.isIsTelephoneLeadEnabled()) {
				messageText.setText(String.format("(%s) %s-%s",
						phoneNo.substring(Constants.TWO, Constants.FIVE),
						phoneNo.substring(Constants.FIVE, Constants.EIGHT),
						phoneNo.substring(Constants.EIGHT, Constants.TWELVE)));
				builder.setPositiveButton("Call",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Intent.ACTION_CALL);
								intent.setData(Uri.parse("tel:" + phoneNo));
								startActivity(intent);
							}
						});
			} else {

				messageText.setText(getResources().getString(
						R.string.no_phoneno));
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intentemail = new Intent(
										LoanExpReviewActivity.this,
										LoanOfferEmailActivity_.class);
								intentemail.putExtra("offersdetail", offers);
								intentemail.putExtra("ipaddress", ipAddress);
								intentemail.putExtra("requestid", requestId);
								startActivity(intentemail);
							}
						});

			}
			builder.setCustomTitle(messageText);

			// cancel button with dismiss.
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
			break;

		case R.id.btnMail_reviewParent:
			Intent intent = new Intent(LoanExpReviewActivity.this,
					LoanOfferEmailActivity_.class);
			intent.putExtra("offersdetail", offers);
			intent.putExtra("ipaddress", ipAddress);
			intent.putExtra("requestid", requestId);
			startActivity(intent);
			break;
		}

	}

	/*
	 * @Click(R.id.btnCall_reviewParent) void callReview(){
	 * 
	 * AlertDialog.Builder builder = new AlertDialog.Builder(this); final String
	 * phoneNo = (offers.getOfferPhoneNumber().getPhoneNumber()); TextView
	 * messageText = new TextView(this); messageText.setPadding(10, 10, 10, 10);
	 * messageText.setGravity(Gravity.CENTER); messageText.setTextSize(18);
	 * messageText.setText("Please call" + "\n" + String.format("(%s) %s-%s",
	 * phoneNo.substring(2, 5), phoneNo.substring(5, 8), phoneNo.substring(8,
	 * 12))); builder.setCustomTitle(messageText);
	 * builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { Intent intent =
	 * new Intent(Intent.ACTION_CALL); intent.setData(Uri.parse("tel:" +
	 * phoneNo)); startActivity(intent); } }); // cancel button with dismiss.
	 * builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
	 * { public void onClick(DialogInterface dialog, int which) { // dismiss();
	 * } });
	 * 
	 * AlertDialog alert = builder.create(); alert.show(); }
	 * 
	 * @Click(R.id.btnMail_reviewParent) void mailReview(){
	 * 
	 * Intent intent = new Intent(LoanExpReviewActivity.this,
	 * LoanOfferEmailActivity_.class); intent.putExtra("offersdetail", offers);
	 * intent.putExtra("ipaddress", ipAddress); intent.putExtra("requestid",
	 * requestId); startActivity(intent); }
	 */

}
