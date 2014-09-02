package com.lendingtree.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.lendingtree.adapter.MortgageData;
import com.lendingtree.util.Constants;
import com.lendingtree.util.InputFilterMinMax;

/**
 * 
 * @category This is the activity for the Mortgage Payment Calculator
 * @author ankit
 * 
 */
@SuppressLint("NewApi")
@EActivity(R.layout.mortgage_input)
public class MortgagePaymentActivity extends BaseActivity {

	private static final String TAG = "MortgagePaymentActivity";

	public MortgagePaymentActivity() {
		super(R.string.mortgage_payment_title);
	}

	int progressChangesd1 = 0;
	int changedValue = 50000;
	int stopSeekBarDownPayment = 0;
	boolean overridingText = false;
	int downPaymentPercentage = 20;
	boolean isEditing;
	boolean isToggled = false;
	private boolean redraw = true;

	@ViewById(R.id.chart)
	PieChart pie;

	private Segment s1;
	private Segment s2;
	private Segment s3;
	private Segment s4;
	private Segment s5;

	// private QuickAction selectLoanTerm;
	MortgageData mData;
	int progressChanged = 0;

	@ViewById(R.id.home_price)
	SeekBar homePrice;

	@ViewById(R.id.down_payment)
	SeekBar downPayment;

	@ViewById(R.id.mortgage_rate)
	SeekBar mortgageRate;

	@ViewById(R.id.btnAssumption)
	Button btnAssumption;

	@ViewById(R.id.ltAssumption)
	LinearLayout ltAssumption;

	@ViewById(R.id.llPayment)
	LinearLayout llPayment;

	@ViewById(R.id.tvHomePriceValue)
	EditText tvHomePriceValue;

	@ViewById(R.id.tvDownPaymentValue)
	TextView tvDownPaymentValue;

	@ViewById(R.id.tvMortgageRateValue)
	EditText tvMortgageRateValue;

	@ViewById(R.id.tvPrincipleInterest)
	TextView tvPrincipleInterest;

	@ViewById(R.id.tvPrincipleInterestValue)
	TextView tvPrincipleInterestValue;

	@ViewById(R.id.tvPropTaxes)
	TextView tvPropTaxes;

	@ViewById(R.id.tvPropTaxesValue)
	TextView tvPropTaxesValue;

	@ViewById(R.id.tvHomeIns)
	TextView tvHomeIns;

	@ViewById(R.id.tvHomeInsValue)
	TextView tvHomeInsValue;

	@ViewById(R.id.tvHOA)
	TextView tvHOA;

	@ViewById(R.id.tvHOAvalue)
	TextView tvHOAvalue;

	@ViewById(R.id.tvPMI)
	TextView tvPMI;

	@ViewById(R.id.tvPMIValue)
	TextView tvPMIValue;

	@ViewById(R.id.tvTotal)
	TextView tvTotal;

	@ViewById(R.id.tvTotalValue)
	TextView tvTotalValue;

	@ViewById(R.id.tvHomePrice)
	TextView tvHomePrice;

	@ViewById(R.id.tvDownPayment)
	TextView tvDownPayment;

	@ViewById(R.id.tvMortgageRate)
	TextView tvMortgageRate;

	@ViewById(R.id.tvpercentage)
	TextView tvpercentage;

	@ViewById(R.id.textView1)
	TextView tvDollar;

	@ViewById(R.id.tvAssumption)
	TextView tvAssumption;

	@ViewById(R.id.txt_annual_prpoerty_taxes)
	TextView tvAnnualPropTaxes;

	@ViewById(R.id.tvPercAssumptions)
	TextView tvPercAssumptions;

	@ViewById(R.id.edtAnnualPropTaxes)
	EditText edtAnnualPropTaxes;

	@FocusChange(R.id.edtAnnualPropTaxes)
	void onFocusChangeOfAnnualPropTaxes(View v, boolean hasFocus) {
		if (hasFocus
				&& edtAnnualPropTaxes.getText().toString().trim()
						.equals("0.000")) {
			edtAnnualPropTaxes.setText("");
		}
	}

	@ViewById(R.id.txt_annual_homeowners_insurance)
	TextView tvAnnualHomeIns;

	@ViewById(R.id.tvDollar1)
	TextView tvDollar1;

	@ViewById(R.id.edtAnnualHomeIns)
	EditText edtAnnualHomeIns;

	@FocusChange(R.id.edtAnnualHomeIns)
	void onFocusChangeOfAnnualHomeIns(View v, boolean hasFocus) {
		if (hasFocus
				&& edtAnnualHomeIns.getText().toString().trim().equals("0")) {
			edtAnnualHomeIns.setText("");
		}
	}

	@ViewById(R.id.txt_annual_hoa_dues)
	TextView tvAnnualHOADues;

	@ViewById(R.id.tvDollar2)
	TextView tvDollar2;

	@ViewById(R.id.edtAnnualHOADues)
	EditText edtAnnualHOADues;

	@FocusChange(R.id.edtAnnualHOADues)
	void onFocusChangeOfAnnualHOADues(View v, boolean hasFocus) {
		if (hasFocus
				&& edtAnnualHOADues.getText().toString().trim().equals("0")) {
			edtAnnualHOADues.setText("");
		}
	}

	@ViewById(R.id.spLoanterm)
	Spinner spinner;

	@ViewById(R.id.txt_loan_trem)
	TextView tvLoanterm;

	@ViewById(R.id.txt_private_mortgage_insurance)
	TextView tvPMIassumptions;

	@ViewById(R.id.tvMtgPaymentFooter)
	TextView tvMtgPaymentFooter;

	@ViewById(R.id.tbPMI)
	ToggleButton tbPMI;

	@ViewById(R.id.scrollView)
	ScrollView scrollView;
	Typeface tf, tf1;
	int selectedItem = 0;
	String[] loanTermArray;
	ImageButton b;

	/**
	 * @return void
	 * @category For Loading the initial views
	 * @author ankit
	 * 
	 */
	@AfterViews
	void loadingGraphView() {

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();

		setSlidingActionBarEnabled(true);
		// application.setCheckDetails(true);
		settingFontAllViews();
		setLoanTermSpinner();
		// homePrice.incrementProgressBy(10);
		edtAnnualPropTaxes
				.setFilters(new InputFilter[] { new InputFilterMinMax(
						Constants.ZERO_POINT_ZERO_ZERO_ZERO,
						Constants.FIVE_POINT_ZERO_ZERO_ZERO) });
		edtAnnualHomeIns.setFilters(new InputFilter[] { new InputFilterMinMax(
				"0", "3000") });
		edtAnnualHOADues.setFilters(new InputFilter[] { new InputFilterMinMax(
				"0", "9999") });
		tvMortgageRateValue
				.setFilters(new InputFilter[] { new InputFilterMinMax(
						Constants.TWO_POINT_ZERO_ZERO_ZERO,
						Constants.SEVEN_POINT_FIVE_ZERO_ZERO) });
		b = new ImageButton(this);
		setHyperLink(tvMtgPaymentFooter);
	}

	private void customLoanView(View v, int position) {
		final TextView t = (TextView) v.findViewById(android.R.id.text1);
		t.setText(loanTermArray[position].substring(0, Constants.TWO));

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_MEDIUM:
			t.setTextSize(Constants.SIXTEEN);
			break;
		case DisplayMetrics.DENSITY_HIGH:
			t.setTextSize(Constants.SEVENTEEN);
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			t.setTextSize(Constants.EIGHTEEN);
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			t.setTextSize(Constants.NINTEEN);
			break;
		default:
			t.setTextSize(Constants.SEVENTEEN);
		}

		t.setGravity(Gravity.LEFT);
		t.setTypeface(tf);

	}

	private void customLoanDropDownView(View v, int position) {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_MEDIUM:
			v.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, Constants.FIFTY));
			break;
		case DisplayMetrics.DENSITY_HIGH:
			v.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, Constants.SEVENTY_FIVE));
			break;
		case DisplayMetrics.DENSITY_XHIGH:
			v.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, Constants.HUNDRED));
			break;
		case DisplayMetrics.DENSITY_XXHIGH:
			v.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, Constants.ONE_TWENTY_FIVE));
			break;
		default:
			v.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, Constants.SEVENTY_FIVE));
		}

		final TextView textviewSpinner = (TextView) v
				.findViewById(android.R.id.text1);
		// textviewSpinner.setTextSize(18);

		textviewSpinner.setText(loanTermArray[position].toString() + "\n");
		textviewSpinner.setGravity(Gravity.CENTER);
		if (position == selectedItem) {
			textviewSpinner.setBackgroundColor(getResources().getColor(
					R.color.silver));
		}
		textviewSpinner.setTypeface(tf);

	}

	OnItemSelectedListener spListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			selectedItem = pos;
			switch (pos) {
			case 0:

				mData.loanTermYear = 1;
				break;
			case 1:

				mData.loanTermYear = Constants.TWO;
				break;
			case Constants.TWO:

				mData.loanTermYear = Constants.THREE;
				break;
			case Constants.THREE:

				mData.loanTermYear = Constants.FOUR;
				break;
			case Constants.FOUR:

				mData.loanTermYear = Constants.FIVE;
				break;
			case Constants.FIVE:

				mData.loanTermYear = Constants.TEN;
				break;
			case Constants.SIX:

				mData.loanTermYear = Constants.FIFTEEN;
				break;

			case Constants.SEVEN:

				mData.loanTermYear = Constants.TWENTY;
				break;
			case Constants.EIGHT:

				mData.loanTermYear = Constants.TWENTY_FIVE;
				break;

			case Constants.NINE:

				mData.loanTermYear = Constants.THIRTY;
				break;

			case Constants.TEN:

				mData.loanTermYear = Constants.FOURTY;
				break;

			default:

				mData.loanTermYear = Constants.THIRTY;
				break;
			}
			assigningDataValues();
			mData.calculateMonthlyMortgageWithAmortization();

			// For pie chart change
			setSeriesAdapter();
			createPieChart();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	};

	private void setLoanTermSpinner() {

		loanTermArray = getResources().getStringArray(R.array.loanyear);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, loanTermArray) {

			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				final View v = vi.inflate(android.R.layout.simple_spinner_item,
						null);
				customLoanView(v, position);

				return v;
			}

			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				final View v = vi.inflate(
						android.R.layout.simple_spinner_dropdown_item, null);

				customLoanDropDownView(v, position);

				return v;
			}

		};
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(Constants.NINE);

		spinner.setOnItemSelectedListener(spListener);

	}

	private void settingFontAllViews() {
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		tvPrincipleInterest.setTypeface(tf);
		tvPrincipleInterestValue.setTypeface(tf);
		tvPropTaxes.setTypeface(tf);
		tvPropTaxesValue.setTypeface(tf);
		tvHomeIns.setTypeface(tf);
		tvHomeInsValue.setTypeface(tf);
		tvHOA.setTypeface(tf);
		tvHOAvalue.setTypeface(tf);
		tvPMI.setTypeface(tf);
		tvPMIValue.setTypeface(tf);
		tvTotal.setTypeface(tf1);
		tvTotalValue.setTypeface(tf1);
		tvHomePrice.setTypeface(tf);
		tvHomePriceValue.setTypeface(tf);
		tvDownPayment.setTypeface(tf);
		tvDownPaymentValue.setTypeface(tf);
		tvMortgageRate.setTypeface(tf);
		tvMortgageRateValue.setTypeface(tf);
		tvDollar.setTypeface(tf);
		tvDollar1.setTypeface(tf);
		tvDollar2.setTypeface(tf);
		tvpercentage.setTypeface(tf);
		tvAnnualPropTaxes.setTypeface(tf);
		edtAnnualPropTaxes.setTypeface(tf);
		tvPercAssumptions.setTypeface(tf);
		tvAnnualHomeIns.setTypeface(tf);
		edtAnnualHomeIns.setTypeface(tf);
		tvAnnualHOADues.setTypeface(tf);
		edtAnnualHOADues.setTypeface(tf);
		tvLoanterm.setTypeface(tf);
		tvPMIassumptions.setTypeface(tf);
		tvMtgPaymentFooter.setTypeface(tf);

	}

	ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {

			Rect gootFaithHeight = new Rect();
			llPayment.getWindowVisibleDisplayFrame(gootFaithHeight);

			int heightDiff = llPayment.getRootView().getHeight()
					- (gootFaithHeight.bottom - gootFaithHeight.top);

			if (heightDiff > 100) {
				b.setEnabled(false);
				// keyboard is up

				/*
				 * if (tvMortgageRateValue.isFocused()) { if
				 * (tvMortgageRateValue.getText().toString().trim()
				 * .equals("0")) { tvMortgageRateValue.setText(""); } }
				 */

			} else {
				b.setEnabled(true);
				if (tvHomePriceValue.isFocused()) {
					if (tvHomePriceValue.getText().toString()
							.replaceAll("[^\\d]", "").isEmpty()) {
						tvHomePriceValue.setText("50000");
					} else if (Double.parseDouble(tvHomePriceValue.getText()
							.toString().replaceAll("[^\\d]", "")) < 50000.00) {
						tvHomePriceValue.setText("50000");
					}
				} else if (tvMortgageRateValue.isFocused()) {
					if (tvMortgageRateValue.getText().toString()
							.replaceAll("[^\\d]", "").isEmpty()) {
						tvMortgageRateValue.setText("2.000");
					} else if (Double.parseDouble(tvMortgageRateValue.getText()
							.toString()) < 2.0) {

						tvMortgageRateValue.setText("2.000");
					} else if (Double.parseDouble(tvMortgageRateValue.getText()
							.toString()) > 7.5) {

						tvMortgageRateValue.setText("7.500");
					} else if (Double.parseDouble(tvMortgageRateValue.getText()
							.toString()) >= 2.0
							&& Double.parseDouble(tvMortgageRateValue.getText()
									.toString()) <= 7.5) {
						DecimalFormat format = new DecimalFormat("0.000");
						tvMortgageRateValue.setText(format.format(Double
								.parseDouble(tvMortgageRateValue.getText()
										.toString())));
					}

					tvMortgageRateValue.clearFocus();
					ltAssumption.requestFocus();

				}

			}
			/*
			 * llPayment.getViewTreeObserver()
			 * .removeGlobalOnLayoutListener(this);
			 */
		}
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (redraw) {
			// series = new PieSeries();

			llPayment.getViewTreeObserver().addOnGlobalLayoutListener(
					globalListener);
			// edtAnnualPropTaxes.getViewTreeObserver().addOnGlobalLayoutListener(globalListener2);
			// tvHomePriceValue.getViewTreeObserver().addOnGlobalLayoutListener(globalListener1);

			initializingMortgageValues();
			assigningDataValues();
			setSeriesAdapter();
			createPieChart();
			redraw = false;
		}

	}

	/**
	 * @return void
	 * @category For handling click of assumptions textview
	 * @author ankit
	 * 
	 */
	@Click(R.id.tvPercAssumptions)
	void onClickofPercentageView() {
		edtAnnualPropTaxes.requestFocus(View.FOCUS_LEFT);

		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
				.showSoftInput(edtAnnualPropTaxes, 0);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:

			if (((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
					.isAcceptingText()) {
				try {
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(getCurrentFocus()
									.getWindowToken(), 0);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			toggle();
			return true;
		case R.id.github:
			if (tvHomePriceValue.isFocused()) {
				if (tvHomePriceValue.getText().toString()
						.replaceAll("[^\\d]", "").isEmpty()) {
					tvHomePriceValue.setText("50000");
				} else if (Double.parseDouble(tvHomePriceValue.getText()
						.toString().replaceAll("[^\\d]", "")) < 50000.00) {
					tvHomePriceValue.setText("50000");
				}

			}

			else if (tvMortgageRateValue.isFocused()) {
				if (tvMortgageRateValue.getText().toString()
						.replaceAll("[^\\d]", "").isEmpty()) {
					tvMortgageRateValue.setText("2.000");
				} else if (Double.parseDouble(tvMortgageRateValue.getText()
						.toString()) < 2.0) {

					tvMortgageRateValue.setText("2.000");
				} else if (Double.parseDouble(tvMortgageRateValue.getText()
						.toString()) > 7.5) {

					tvMortgageRateValue.setText("7.500");
				}
				tvMortgageRateValue.clearFocus();
				ltAssumption.requestFocus();
			}
			Intent intent = new Intent(this,
					MortgagePaymentDetailsActivity_.class);
			startActivity(intent);

			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));

		b.setBackgroundDrawable(null);
		b.setImageDrawable(getResources().getDrawable(R.drawable.details));

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tvHomePriceValue.isFocused()) {
					if (tvHomePriceValue.getText().toString()
							.replaceAll("[^\\d]", "").isEmpty()) {
						tvHomePriceValue.setText("50000");
					} else if (Double.parseDouble(tvHomePriceValue.getText()
							.toString().replaceAll("[^\\d]", "")) < 50000.00) {
						tvHomePriceValue.setText("50000");
					}
				}

				else if (tvMortgageRateValue.isFocused()) {
					if (tvMortgageRateValue.getText().toString()
							.replaceAll("[^\\d]", "").isEmpty()) {
						tvMortgageRateValue.setText("2.000");
					} else if (Double.parseDouble(tvMortgageRateValue.getText()
							.toString()) < 2.0) {

						tvMortgageRateValue.setText("2.000");
					} else if (Double.parseDouble(tvMortgageRateValue.getText()
							.toString()) > 7.5) {

						tvMortgageRateValue.setText("7.500");
					}
				}
				Intent intent = new Intent(getApplicationContext(),
						MortgagePaymentDetailsActivity_.class);

				startActivity(intent);
			}
		});

		menu.findItem(R.id.github).setActionView(b);

		menu.findItem(R.id.github).setVisible(true);
		menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}

	/**
	 * @return void
	 * @category Handles the click of assumption
	 * @author ankit
	 * 
	 */
	@SuppressWarnings("deprecation")
	@Click(R.id.llAssumptions)
	void onClickOfAssumption() {

		if (ltAssumption.isShown()) {
			ltAssumption.setVisibility(View.GONE);
			btnAssumption.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.plus));

		} else {

			ltAssumption.setVisibility(View.VISIBLE);
			btnAssumption.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.minus));
			scrollView.post(new Runnable() {

				@Override
				public void run() {

					DisplayMetrics metrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(metrics);
					switch (metrics.densityDpi) {
					case DisplayMetrics.DENSITY_MEDIUM:
						scrollView.scrollTo(0, Constants.TWO_TWENTY_FIVE);
						break;
					case DisplayMetrics.DENSITY_HIGH:
						scrollView.scrollTo(0, Constants.THREE_FOURTY);
						break;
					case DisplayMetrics.DENSITY_XHIGH:
						scrollView.scrollTo(0, Constants.FIVE_SIXTY);
						break;
					case DisplayMetrics.DENSITY_XXHIGH:
						scrollView.scrollTo(0, Constants.SEVEN_EIGHTY);
						break;
					default:
						scrollView.scrollTo(0, Constants.THREE_FOURTY);
					}
				}
			});

		}

	}

	/**
	 * @return void
	 * @category Handles the text change of annual property taxes
	 * @author ankit
	 * 
	 */
	@AfterTextChange(R.id.edtAnnualPropTaxes)
	void changeAnnualPropTaxes(final Editable s) {

		final String annualPropTaxes = s.toString();
		Log.e(TAG, annualPropTaxes + "```Before`````");
		int posDot = annualPropTaxes.indexOf(".");
		if (posDot <= 0)
			return;

		if (annualPropTaxes.length() - posDot - 1 > Constants.THREE) {
			s.delete(posDot + Constants.FOUR, posDot + Constants.FIVE);
		}
		if (!annualPropTaxes.isEmpty()) {
			edtAnnualPropTaxes
					.setOnEditorActionListener(new OnEditorActionListener() {
						public boolean onEditorAction(TextView v, int actionId,
								KeyEvent event) {

							if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
									|| (actionId == EditorInfo.IME_ACTION_DONE)) {
								Log.e(TAG, s.toString() + "````````");
								if (!s.toString().isEmpty()) {
									DecimalFormat format = new DecimalFormat(
											"0.000");
									v.setText(format.format(Double
											.parseDouble(s.toString())));
								} else {
									v.setText("0.000");
								}

							}
							return false;
						}

					});
			mData.annualyPropertyTaxRate = Double.parseDouble(annualPropTaxes);

		} else {

			mData.annualyPropertyTaxRate = 0;
		}
		assigningDataValues();

		// For pie chart change
		setSeriesAdapter();

		createPieChart();
		// edtAnnualPropTaxes.getViewTreeObserver().removeGlobalOnLayoutListener(globalListener2);

	}

	/**
	 * @return void
	 * @category Handles the text change of annual home insurance
	 * @author ankit
	 * 
	 */
	@AfterTextChange(R.id.edtAnnualHomeIns)
	void changedAnnualHomeIns(final Editable s) {
		// selectLoanTerm.dismiss();
		String annualHomeIns = s.toString().replaceAll("[^\\d]", "");

		if (!annualHomeIns.isEmpty()) {

			edtAnnualHomeIns
					.setOnEditorActionListener(new OnEditorActionListener() {
						public boolean onEditorAction(TextView v, int actionId,
								KeyEvent event) {

							if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
									|| (actionId == EditorInfo.IME_ACTION_DONE)) {

								if (!s.toString().isEmpty()) {

									v.setText(Integer.parseInt(s.toString())
											+ "");
								} else {
									v.setText("0");
								}

							}
							return false;
						}

					});

			mData.annualyHomeOwnerInsuranceAmt = Double
					.parseDouble(annualHomeIns);

		} else {

			mData.annualyHomeOwnerInsuranceAmt = 0;
		}
		assigningDataValues();

		// For pie chart change
		setSeriesAdapter();

		createPieChart();

	}

	/**
	 * @return void
	 * @category Handles the toggle of PMI
	 * @author ankit
	 * 
	 */
	@Click(R.id.tbPMI)
	void checkPMI() {
		isToggled = true;
		if (tbPMI.isChecked()) {
			mData.addPMI = true;
			mData.propertyPriceAmt = changedValue;

		} else {
			mData.addPMI = false;

		}

		assigningDataValues();

		// For pie chart change
		setSeriesAdapter();

		createPieChart();
	}

	/**
	 * @return void
	 * @category Handles the text change of annual HOA dues
	 * @author ankit
	 * 
	 */
	@AfterTextChange(R.id.edtAnnualHOADues)
	void changedAnnualHOADues(final Editable s) {

		final String annualHOA = s.toString().replaceAll("[^\\d]", "");
		Log.v(TAG, annualHOA + "-------" + s.toString());
		if (!annualHOA.isEmpty()) {

			mData.annualyHOADuesAmt = Double.parseDouble(annualHOA);
		} else {

			mData.annualyHOADuesAmt = 0;
		}
		edtAnnualHOADues
				.setOnEditorActionListener(new OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
								|| (actionId == EditorInfo.IME_ACTION_DONE)) {

							if (!s.toString().isEmpty()) {
								v.setText(Integer.parseInt(s.toString()) + "");

							} else {
								v.setText("0");
							}

						}
						return false;
					}

				});
		assigningDataValues();

		// For pie chart change
		setSeriesAdapter();

		createPieChart();

	}

	/**
	 * @author ankit
	 * @category For setting the adapter for pie chart
	 * @param series
	 */
	private void setSeriesAdapter() {
		resetMortgageData();
		mData.calculateMonthlyMortgage();
		if (mData.monthlyPropertyTaxAmt >= 0
				&& mData.monthlyHomeOwnerInsuranceAmt >= 0
				&& mData.monthlyHOADuesAmt >= 0 && mData.monthlyPMIAmt >= 0
				&& mData.monthlyMortgageBaseAmt >= 0) {

			s1.setValue(mData.monthlyMortgageBaseAmt);
			s2.setValue(mData.monthlyPropertyTaxAmt);
			s3.setValue(mData.monthlyHomeOwnerInsuranceAmt);
			s4.setValue(mData.monthlyHOADuesAmt);
			s5.setValue(mData.monthlyPMIAmt);

		}

	}

	/**
	 * @author ankit
	 * @return void
	 * @category For creating the pie chart
	 */
	private void createPieChart() {

		SegmentFormatter sf1 = new SegmentFormatter();
		sf1.getFillPaint()
				.setColor(getResources().getColor(R.color.Dark_Green));
		sf1.getOuterEdgePaint().setColor(
				getResources().getColor(R.color.Dark_Green));
		sf1.getRadialEdgePaint().setColor(Color.WHITE);
		sf1.getRadialEdgePaint().setStrokeWidth(1f);

		sf1.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
		sf1.getOuterEdgePaint().setStrokeWidth(1f);
		sf1.getOuterEdgePaint().setAntiAlias(true);

		SegmentFormatter sf2 = new SegmentFormatter();
		sf2.getOuterEdgePaint().setColor(
				getResources().getColor(R.color.Light_Green));
		sf2.getFillPaint().setColor(
				getResources().getColor(R.color.Light_Green));
		sf2.getRadialEdgePaint().setColor(Color.WHITE);
		sf2.getRadialEdgePaint().setStrokeWidth(1f);

		sf2.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
		sf2.getOuterEdgePaint().setStrokeWidth(1f);
		sf2.getOuterEdgePaint().setAntiAlias(true);

		SegmentFormatter sf3 = new SegmentFormatter();
		sf3.getOuterEdgePaint().setColor(
				getResources().getColor(R.color.Medium_Green));
		sf3.getFillPaint().setColor(
				getResources().getColor(R.color.Medium_Green));
		sf3.getRadialEdgePaint().setColor(Color.WHITE);
		sf3.getRadialEdgePaint().setStrokeWidth(1f);

		sf3.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
		sf3.getOuterEdgePaint().setStrokeWidth(1f);
		sf3.getOuterEdgePaint().setAntiAlias(true);

		SegmentFormatter sf4 = new SegmentFormatter();
		sf4.getFillPaint().setColor(getResources().getColor(R.color.Bar_Green));
		sf4.getOuterEdgePaint().setColor(
				getResources().getColor(R.color.Bar_Green));
		sf4.getRadialEdgePaint().setColor(Color.WHITE);
		sf4.getRadialEdgePaint().setStrokeWidth(1f);

		sf4.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
		sf4.getOuterEdgePaint().setStrokeWidth(1f);
		sf4.getOuterEdgePaint().setAntiAlias(true);

		SegmentFormatter sf5 = new SegmentFormatter();
		sf5.getOuterEdgePaint().setColor(
				getResources().getColor(R.color.Piechart_Green));
		sf5.getFillPaint().setColor(
				getResources().getColor(R.color.Piechart_Green));
		sf5.getRadialEdgePaint().setColor(Color.WHITE);
		sf5.getRadialEdgePaint().setStrokeWidth(1f);
		sf5.getOuterEdgePaint().setStrokeWidth(1f);
		sf5.getOuterEdgePaint().setStyle(Paint.Style.STROKE);
		sf5.getOuterEdgePaint().setAntiAlias(true);

		pie.addSeries(s1, sf1);
		pie.addSeries(s2, sf2);
		pie.addSeries(s3, sf3);
		pie.addSeries(s4, sf4);
		pie.addSeries(s5, sf5);
		if (Build.VERSION.SDK_INT > 10) {
			pie.setRotation(270f);
		}

		pie.getBorderPaint().setColor(getResources().getColor(R.color.Cream));
		pie.getBackgroundPaint().setColor(
				getResources().getColor(R.color.Cream));

		pie.getRenderer(PieRenderer.class).setDonutSize(0f,
				PieRenderer.DonutMode.PERCENT);
		pie.redraw();

	}

	/**
	 * @author ankit
	 * @return void
	 * @category For assigning the values to text fields
	 */
	private void assigningDataValues() {

		mData.calculateMonthlyMortgage();
		mData.calculateMonthlyMortgageWithAmortization();

		if (mData.monthlyPropertyTaxAmt >= 0
				&& mData.monthlyHomeOwnerInsuranceAmt >= 0
				&& mData.monthlyHOADuesAmt >= 0 && mData.monthlyPMIAmt >= 0
				&& mData.monthlyMortgageBaseAmt >= 0
				&& mData.monthlyMortgageTotalAmt >= 0) {
			tvPrincipleInterestValue
					.setText("$"
							+ addCommasToNumericString((int) mData.monthlyMortgageBaseAmt
									+ ""));
			tvPropTaxesValue
					.setText("$"
							+ addCommasToNumericString((int) mData.monthlyPropertyTaxAmt
									+ ""));
			tvHomeInsValue
					.setText("$"
							+ addCommasToNumericString((int) mData.monthlyHomeOwnerInsuranceAmt
									+ ""));
			tvHOAvalue.setText("$"
					+ addCommasToNumericString((int) mData.monthlyHOADuesAmt
							+ ""));
			tvPMIValue.setText("$"
					+ addCommasToNumericString((int) mData.monthlyPMIAmt + ""));
			tvTotalValue
					.setText("$"
							+ addCommasToNumericString((int) mData.monthlyMortgageTotalAmt
									+ ""));
		}

	}

	/**
	 * @category For initializing the default values
	 * @return void
	 * @author ankit
	 */
	private void initializingMortgageValues() {
		mData = new MortgageData();

		mData.propertyPriceAmt = Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO;
		mData.downPaymentAmt = (mData.propertyPriceAmt * Constants.TWENTY)
				/ Constants.HUNDRED;
		mData.annualyInterestRate = Constants.FOUR_POINT_TWO_FIVE; // Mortgage
																	// Rate:
		mData.annualyPropertyTaxRate = Constants.ONE_POINT_ONE_FOUR;
		mData.annualyHomeOwnerInsuranceAmt = Constants.EIGHT_HUNDRED;
		mData.annualyHOADuesAmt = 0;
		mData.loanTermYear = Constants.THIRTY;
		mData.addPMI = true;

		mData.calculateMonthlyMortgageWithAmortization();

		// For pie chart change
		s1 = new Segment("", mData.monthlyPropertyTaxAmt);
		s2 = new Segment("", mData.monthlyHomeOwnerInsuranceAmt);
		s3 = new Segment("", mData.monthlyHOADuesAmt);
		s4 = new Segment("", mData.monthlyPMIAmt);
		s5 = new Segment("", mData.monthlyMortgageBaseAmt);

		// setSeriesAdapter();

		createPieChart();

		homePrice
				.setProgress((int) (mData.propertyPriceAmt - Constants.FIFTY_THOUSAND)
						/ Constants.TWO_THOUSAND_FIVE_HUNDRED);
		downPayment
				.setProgress((int) ((mData.downPaymentAmt * Constants.HUNDRED) / mData.propertyPriceAmt));
		mortgageRate
				.setProgress((int) ((mData.annualyInterestRate - Constants.TWO) / Constants.ZERO_POINT_ONE_TWO_FIVE));

	}

	/**
	 * @category For reseting the values
	 * @return void
	 * @author ankit
	 */
	private void resetMortgageData() {
		this.mData.loanAmt = 0.0;
		this.mData.monthlyInterestRate = 0.0;
		this.mData.monthlyPropertyTaxRate = 0.0;
		this.mData.numberofLoanTerms = 0.0;

		this.mData.monthlyMortgageTotalAmt = 0;
		this.mData.monthlyMortgageBaseAmt = 0;
		this.mData.monthlyPropertyTaxAmt = 0;
		this.mData.monthlyHomeOwnerInsuranceAmt = 0;
		this.mData.monthlyHOADuesAmt = 0;
		this.mData.monthlyPMIAmt = 0;

		this.mData.monthlyPI = 0.0;
	}

	/**
	 * @return void
	 * @category For seekbar progress handling of homeprice
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * @author ankit
	 * 
	 */

	@SeekBarProgressChange(R.id.home_price)
	void OnProgressChangeHomePrice(SeekBar seekBar, int progress,
			boolean fromUser) {
		// selectLoanTerm.dismiss();
		if (!isEditing) {

			Log.v(TAG, progress + "------");
			if (progress <= Constants.SIXTY) {
				changedValue = seekBarChange(Constants.FIFTY_THOUSAND,
						progress, Constants.TWO_THOUSAND_FIVE_HUNDRED);
			} else if (progress > Constants.SIXTY
					&& progress <= Constants.ONE_TWENTY) {

				changedValue = seekBarChange(
						Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO, progress - 60,
						5000);
			} else if (progress > Constants.ONE_TWENTY
					&& progress <= Constants.TWO_SEVENTY) {

				changedValue = seekBarChange(
						Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO,
						progress - 120, 10000);
			} /*
			 * else {
			 * 
			 * changedValue = seekBarChange(Constants.ONE_MILLAN, progress -
			 * 145,200000); }
			 */
			Log.v(TAG, changedValue + "++++++" + progress);

			mData.propertyPriceAmt = changedValue;
			mData.downPaymentAmt = (mData.propertyPriceAmt * downPaymentPercentage)
					/ Constants.HUNDRED;
			tvDownPaymentValue.setText(downPaymentPercentage + "% ($"
					+ addCommasToNumericString((int) mData.downPaymentAmt + "")
					+ ")");

			assigningDataValues();

			tvHomePriceValue
					.setText(addCommasToNumericString(changedValue + ""));
			// For pie chart change
			setSeriesAdapter();

			createPieChart();

		}

	}

	private int seekBarChange(int initialValue, int progress, int multiplier) {
		return (initialValue + (progress * multiplier));

	}

	/**
	 * @return void
	 * @category For editext text change handling of homeprice
	 * @author ankit
	 * 
	 */
	@AfterTextChange(R.id.tvHomePriceValue)
	void afterTextChangeHomePrice(final Editable s) {

		
		if (isEditing)
			return;
		isEditing = true;

		String str = s.toString().replaceAll("[^\\d]", "");

		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);

			if (s1 < Constants.FIFTY_THOUSAND_POINT_ZERO_ZERO) {
				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				tvHomePriceValue
						.setOnEditorActionListener(new OnEditorActionListener() {
							public boolean onEditorAction(TextView v,
									int actionId, KeyEvent event) {

								if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
										|| (actionId == EditorInfo.IME_ACTION_DONE)) {
									s.replace(0, s.length(), "50,000");

								}
								return false;
							}

						});

			}
			if (s1 > Constants.TWO_MILLAN_POINT_ZERO_ZERO) {
				tvHomePriceValue.setOnEditorActionListener(null);
				s.replace(0, s.length(), "2,000,000");
			}
			if (s1 >= Constants.FIFTY_THOUSAND_POINT_ZERO_ZERO
					&& s1 <= Constants.TWO_MILLAN_POINT_ZERO_ZERO) {
				tvHomePriceValue.setOnEditorActionListener(null);

				NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
				((DecimalFormat) nf2).applyPattern("###,###.###");
				s.replace(0, s.length(), nf2.format(s1));
				if ((int) s1 <= Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO) {
					homePrice.setProgress((int) (s1 - Constants.FIFTY_THOUSAND)
							/ Constants.TWO_THOUSAND_FIVE_HUNDRED);

				} else if ((int) s1 > Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO
						&& (int) s1 <= Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO) {

					homePrice
							.setProgress(((int) (s1 - Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO) / Constants.FIVE_THOUSAND)
									+ Constants.SIXTY);
					// changedValue = seekBarChange(200000,progress - 60,5000);
				} else if ((int) s1 > Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO
						&& (int) s1 <= Constants.TWO_MILLAN) {

					homePrice
							.setProgress(((int) (s1 - Constants.FIVE_ZERO_ZERO_ZERO_ZERO_ZERO) / Constants.TEN_THUSAND)
									+ Constants.ONE_TWENTY);
					// changedValue = seekBarChange(500000,progress -
					// 120,20000);
				} /*
				 * else { homePrice.setProgress(((int) (s1 -
				 * Constants.ONE_MILLAN) /
				 * Constants.TWO_ZERO_ZERO_ZERO_ZERO_ZERO) +
				 * Constants.ONE_FOURTY_FIVE); // changedValue =
				 * seekBarChange(1000000, progress - 145,200000); }
				 */
				changedValue = (int) s1;
				// homePrice.setProgress((int) (s1 - 50000) / 2500);

				mData.propertyPriceAmt = s1;
				mData.downPaymentAmt = (mData.propertyPriceAmt * downPaymentPercentage)
						/ Constants.HUNDRED;
				tvDownPaymentValue.setText(downPaymentPercentage
						+ "% ($"
						+ addCommasToNumericString((int) mData.downPaymentAmt
								+ "") + ")");
				assigningDataValues();

				// For pie chart change
				setSeriesAdapter();

				createPieChart();
			}

		}
		// tvHomePriceValue.getViewTreeObserver().removeGlobalOnLayoutListener(globalListener1);
		isEditing = false;

	}

	/**
	 * @return void
	 * @category For seekbar progress handling of downpayment
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * @author ankit
	 * 
	 */
	@SeekBarProgressChange(R.id.down_payment)
	void OnProgressChangeDownPayment(SeekBar seekBar, int progress,
			boolean fromUser) {
		// selectLoanTerm.dismiss();
		int downPaymentAmt = (changedValue * progress) / Constants.HUNDRED;
		downPaymentPercentage = (downPaymentAmt * Constants.HUNDRED)
				/ changedValue;

		mData.downPaymentAmt = downPaymentAmt;

		if (!isToggled) {
			if (downPaymentPercentage < Constants.TWENTY) {

				tbPMI.setChecked(true);
				mData.addPMI = true;
				mData.propertyPriceAmt = changedValue;
			} else {

				tbPMI.setChecked(false);
				mData.addPMI = false;
			}
		}
		
		assigningDataValues();

		if (downPaymentAmt != 0)
			tvDownPaymentValue.setText(downPaymentPercentage + "% ($"
					+ addCommasToNumericString(downPaymentAmt + "") + ")");
		else
			tvDownPaymentValue.setText(downPaymentPercentage + "% ($"
					+ downPaymentAmt + ")");
		if (downPaymentPercentage == Constants.HUNDRED) {
			mData.propertyPriceAmt = mData.downPaymentAmt;
		}

		// For pie chart change
		setSeriesAdapter();

		createPieChart();

	}

	/**
	 * @return void
	 * @category For seekbar progress handling of mortgagerate
	 * @param seekBar
	 * @param progress
	 * @param fromUser
	 * @author ankit
	 * 
	 */
	@SeekBarProgressChange(R.id.mortgage_rate)
	void OnProgressChangeMortgageRate(SeekBar seekBar, int progress,
			boolean fromUser) {
		// selectLoanTerm.dismiss();
		if (!isEditing) {

			float changedValue = (float) (Constants.TWO + (progress * Constants.ZERO_POINT_ONE_TWO_FIVE));
			mData.annualyInterestRate = changedValue;
			assigningDataValues();

			tvMortgageRateValue.setText(String.format("%.3f", changedValue));

			// For pie chart change
			setSeriesAdapter();
			// shinobiChart.addSeries(series);
			createPieChart();
		}

	}

	/**
	 * @return void
	 * @category For editext text change handling of mortgage rate
	 * @author ankit
	 * 
	 */
	@AfterTextChange(R.id.tvMortgageRateValue)
	void afterTextChangeMortgageRate(final Editable s) {
		final String str = s.toString();

		int posDot = str.indexOf(".");
		if (posDot <= 0) {
			return;
		}

		if (str.length() - posDot - 1 > Constants.THREE) {
			s.delete(posDot + Constants.FOUR, posDot + Constants.FIVE);
		}
		if (isEditing)
			return;
		isEditing = true;

		if (!str.isEmpty()) {
			double s1 = Double.parseDouble(str);

			if (s1 > Constants.SEVEN_POINT_FIVE) {
				tvMortgageRateValue.setOnEditorActionListener(null);
				s.replace(0, s.length(), "7.500");
			}

			if (s1 >= Constants.TWO_POINT_ZERO_ZERO_ZERO
					&& s1 <= Constants.SEVEN_POINT_FIVE) {
				/*
				 * tvMortgageRateValue .setOnEditorActionListener(new
				 * OnEditorActionListener() { public boolean
				 * onEditorAction(TextView v, int actionId, KeyEvent event) {
				 * 
				 * if ((event != null && (event.getKeyCode() ==
				 * KeyEvent.KEYCODE_ENTER)) || (actionId ==
				 * EditorInfo.IME_ACTION_DONE)) { if (!s.toString().isEmpty()) {
				 * DecimalFormat format = new DecimalFormat( "0.000");
				 * v.setText(format.format(Double .parseDouble(s.toString())));
				 * } else { v.setText("2.000"); }
				 * 
				 * } return false; }
				 * 
				 * });
				 */
				mortgageRate
						.setProgress((int) ((s1 - 2) / Constants.ZERO_POINT_ONE_TWO_FIVE));
				mData.annualyInterestRate = s1;
				assigningDataValues();

				// For pie chart change
				setSeriesAdapter();

				createPieChart();
			}

		}
		// tvMortgageRateValue.getViewTreeObserver().removeGlobalOnLayoutListener(globalListener);
		isEditing = false;
	}

}
