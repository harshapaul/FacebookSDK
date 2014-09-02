/*
 * @author Ramesh Gundala
 * This Activity for the Header and details on click of Details Button in the ActionBar.
 *
 */

package com.lendingtree.view;

import java.text.DecimalFormat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.lendingtree.adapter.HomeAffCalculator;
import com.lendingtree.util.Constants;

@EActivity(R.layout.mortgage_payment_details)
public class PinnedSectionListActivity extends BaseActivity {
	Typeface tf;
	Typeface tf1;
	Context context = this;

	public PinnedSectionListActivity() {
		super(R.string.mortgage_payment_title);
	}

	@ViewById(R.id.mpd_totalPayments)
	TextView textTotalPayment;

	@ViewById(R.id.mpd_totalPayments_Amt)
	TextView textTotalPaymentAmt;

	@ViewById(R.id.mpd_totalprinicpal)
	TextView textPrincipal;

	@ViewById(R.id.mpd_totalprinicpal_Amt)
	TextView textPrincipalAmt;

	@ViewById(R.id.mpd_totalprinicpal_int)
	TextView textBalance;

	@ViewById(R.id.mpd_totalprinicpalAmt_int)
	TextView textBalanceAmt;

	@ViewById(R.id.mpd_totalInterest_data)
	TextView textInterest;

	@ViewById(R.id.mpd_totalInterest_Amt)
	TextView textInterestAmt;

	@ViewById(R.id.monthHeader)
	TextView monthHeader;

	@ViewById(R.id.principalHeader)
	TextView principalHeader;

	@ViewById(R.id.interestHeader)
	TextView interestHeader;

	@ViewById(R.id.balanceHeader)
	TextView balanceHeader;

	@ViewById(R.id.lvDetails)
	ListView lvDetails;
	LinearLayout llFooter;
	TextView tvFooter;
	private boolean redraw = true;

	@AfterViews
	void afterViews() {

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(
				R.layout.custom_actionbar_centertext);
		View tempView = getSupportActionBar().getCustomView();
		TextView tempTextView = (TextView) tempView
				.findViewById(R.id.actionbarText);
		tempTextView.setTypeface(tf);
		Button imgbtn = (Button) tempView.findViewById(R.id.btnUp);

		tempTextView.setText("Your Payment $"
				+ addCommasToNumericString(Math
						.round(HomeAffCalculator.totalMonthlyPaymentAmt) + "")
				+ " / month");
		View view = getLayoutInflater().inflate(R.layout.footer, null);
		llFooter = (LinearLayout) view.findViewById(R.id.llFooter);
		tvFooter = (TextView) view.findViewById(R.id.tvFooter);
		tvFooter.setText(getResources().getString(R.string.footer_text));
		/*DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {

		case DisplayMetrics.DENSITY_MEDIUM:
			
			break;
		case DisplayMetrics.DENSITY_HIGH:
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			break;
		}*/
		imgbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		tempTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar));

		setHeaderData();
		setFontAllData();
		setHyperLink(tvFooter);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (redraw) {

			DetailDataAdapter dataAdapter = new DetailDataAdapter();
			lvDetails.addFooterView(llFooter);
			lvDetails.setAdapter(dataAdapter);
			redraw = false;
		}
	}

	private void setFontAllData() {
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

		textTotalPayment.setTypeface(tf);
		textBalance.setTypeface(tf);
		textPrincipal.setTypeface(tf);
		textInterest.setTypeface(tf);
		monthHeader.setTypeface(tf1);
		principalHeader.setTypeface(tf1);
		interestHeader.setTypeface(tf1);
		balanceHeader.setTypeface(tf1);
		textTotalPaymentAmt.setTypeface(tf);
		textBalanceAmt.setTypeface(tf);
		textPrincipalAmt.setTypeface(tf);
		textInterestAmt.setTypeface(tf);
		tvFooter.setTypeface(tf);
	}

	private void setHeaderData() {
		textTotalPaymentAmt
				.setText("$"
						+ addCommasToNumericString(Math
								.round((HomeAffCalculator.monthlyMortgagePaymentAmt * HomeAffCalculator.months
										.size()))
								+ "")
						+ " / "
						+ (int) (HomeAffCalculator.months.size() / Constants.TWELVE)
						+ " yrs");
		double totalPrincipal = 0;
		double totalInterest = 0;

		for (int j = 0; j < HomeAffCalculator.months.size(); j++) {

			totalInterest = (totalInterest + Double
					.parseDouble(new DecimalFormat("#.##")
							.format(HomeAffCalculator.months.get(j)
									.getInterest())));
			totalPrincipal = (totalPrincipal + Double
					.parseDouble(new DecimalFormat("#.##")
							.format(HomeAffCalculator.months.get(j)
									.getInterest())));

			// add(item);
		}

		/*
		 * totalPrincipal = (Math.round(totalPrincipal * 100) / 100.0) + 0.5;
		 * totalInterest = (Math.round(totalInterest * 100) / 100.0) + 0.5;
		 */
		totalPrincipal = totalPrincipal + Constants.ZERO_POINT_FIVE;
		totalInterest = totalInterest + Constants.ZERO_POINT_FIVE;
		double totalBalance;
		if (totalPrincipal > Constants.ZERO_POINT_FIVE
				&& totalPrincipal > Constants.ZERO_POINT_FIVE) {
			totalBalance = totalPrincipal + totalInterest
					+ Constants.ZERO_POINT_FIVE;
		} else {
			totalBalance = totalPrincipal - totalInterest;
		}

		Log.v(" After VALUE------", totalInterest + "-----" + totalPrincipal
				+ "-----" + totalBalance);

		textBalanceAmt.setText("$"
				+ addCommasToNumericString((int) totalBalance + "") + " / "
				+ (int) (HomeAffCalculator.months.size() / Constants.TWELVE)
				+ " yrs");
		textPrincipalAmt.setText("$"
				+ addCommasToNumericString((int) (totalPrincipal) + "") + " / "
				+ (int) (HomeAffCalculator.months.size() / Constants.TWELVE)
				+ " yrs");
		textInterestAmt.setText("$"
				+ addCommasToNumericString((int) (totalInterest) + "") + " / "
				+ (int) (HomeAffCalculator.months.size() / Constants.TWELVE)
				+ " yrs");

	}

	public class DetailDataAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return HomeAffCalculator.months.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			TextView monthTV;
			TextView interestTV;
			TextView balanceTV;
			TextView principalTV;
			int position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null)
				convertView = newView();

			ViewHolder holder = (ViewHolder) convertView.getTag();

			holder.monthTV.setTypeface(tf);
			holder.interestTV.setTypeface(tf);
			holder.principalTV.setTypeface(tf);
			holder.balanceTV.setTypeface(tf);
			Detail detailObj = HomeAffCalculator.months.get(position);

			holder.monthTV.setText(detailObj.getMonths() + "");

			holder.interestTV.setText("$"
					+ (new DecimalFormat("#,###,##0.00")).format(detailObj
							.getInterest()));

			holder.principalTV.setText("$"
					+ (new DecimalFormat("#,###,##0.00")).format(detailObj
							.getPrincipal()));

			holder.balanceTV.setText("$"
					+ (new DecimalFormat("#,###,##0.00")).format(detailObj
							.getBalance()));
			return convertView;
		}

		private View newView() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.pinned_section_row, null);
			ViewHolder holder = new ViewHolder();
			rowView.setTag(holder);
			holder.monthTV = (TextView) rowView.findViewById(R.id.monthTV);
			holder.interestTV = (TextView) rowView
					.findViewById(R.id.interestTV);
			holder.balanceTV = (TextView) rowView.findViewById(R.id.balanceTV);
			holder.principalTV = (TextView) rowView
					.findViewById(R.id.principalTV);

			return rowView;
		}

	}

}
