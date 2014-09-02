package com.lendingtree.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.model.MortgageNegotiator;
import com.lendingtree.model.OfferContainer;
import com.lendingtree.model.Offers;
import com.lendingtree.util.Constants;
import com.shinobicontrols.charts.ColumnSeries;
import com.shinobicontrols.charts.ColumnSeriesStyle;
import com.shinobicontrols.charts.DataAdapter;
import com.shinobicontrols.charts.DataPoint;
import com.shinobicontrols.charts.NumberAxis;
import com.shinobicontrols.charts.NumberRange;
import com.shinobicontrols.charts.SeriesStyle.FillStyle;
import com.shinobicontrols.charts.ShinobiChart;
import com.shinobicontrols.charts.SimpleDataAdapter;
import com.shinobicontrols.charts.SupportChartFragment;
import com.shinobicontrols.charts.TickStyle;

@SuppressLint("UseSparseArrays")
@EActivity(R.layout.mortgage_barchart)
public class MortgageNegotiatorChartActivity extends BaseActivity {

	private static final String TAG = "MortgageNegotiatorChartActivity";

	public MortgageNegotiatorChartActivity() {
		super(R.string.compare_your_current_offer_title);
	}

	@ViewById(R.id.btnBestOffers)
	Button btnBestOffers;

	@ViewById(R.id.tvIrRate)
	TextView tvIrRate;
	
	@ViewById(R.id.tvResultsGFE)
	TextView tvResultsGFE;

	@ViewById(R.id.tvIrRateText)
	TextView tvIrRateText;

	@ViewById(R.id.tvOriginationCharge)
	TextView tvOriginationCharge;

	@ViewById(R.id.tvOriginationChargeText)
	TextView tvOriginationChargeText;

	@ViewById(R.id.tvOriginationDetail)
	TextView tvOriginationDetail;

	@ViewById(R.id.tvMonthlyPayment)
	TextView tvMonthlyPayment;

	@ViewById(R.id.tvMonthlyPaymentText)
	TextView tvMonthlyPaymentText;

	@ViewById(R.id.tvMonthlyPaymentDetail)
	TextView tvMonthlyPaymentDetail;

	@ViewById(R.id.tvFixBar)
	TextView tvFixBar;

	@ViewById(R.id.tvMovingBar)
	TextView tvMovingBar;

	@ViewById(R.id.tvAvgText)
	TextView tvAvgText;

	@ViewById(R.id.ivTick)
	Button ivTick;

	@ViewById(R.id.llRelative)
	RelativeLayout llRelative;

	@ViewById(R.id.tvFixBarMonthlyPay)
	TextView tvFixBarMonthlyPay;

	@ViewById(R.id.tvMovingBarMonthlyPay)
	TextView tvMovingBarMonthlyPay;

	@ViewById(R.id.tvAvgTextMonthlyPay)
	TextView tvAvgTextMonthlyPay;

	@ViewById(R.id.ivTickMonthlyPay)
	Button ivTickMonthlyPay;

	@ViewById(R.id.llRelative1)
	RelativeLayout llRelative1;

	@ViewById(R.id.tvFooter)
	TextView tvFooter;
	
	/*@ViewById(R.id.tvLoanExplorerFooterDetails)
	TextView tvLoanExplorerFooterDetails;*/
	
	Typeface tf, tf1;
//	OfferContainer container;
	 
	MortgageNegotiator userData;
	int totalOffers;
	double maxIntrestRate;
	double minIntrestRate;
	ShinobiChart shinobiChart;
	
	@Extra("offersdata")
	OfferContainer container;
	
	@Extra("MortgageData")
	ArrayList<Offers> tempList;
	
	@Extra("ipaddress")
	String ipAddress;
	
	boolean redraw = true;

	@SuppressLint("NewApi")
	@AfterViews
	void initializeBarGraph() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		setHyperLink(tvFooter);
		/*container = (OfferContainer) getIntent()
				.getSerializableExtra("MortgageData");*/
		userData = (MortgageNegotiator) getIntent().getSerializableExtra(
				"UserInput");
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
		setSlidingActionBarEnabled(true);
		// For Bar Chart
		SupportChartFragment chartFragment = (SupportChartFragment) getSupportFragmentManager()
				.findFragmentById(R.id.bar_chart);

		// Get the a reference to the ShinobiChart from the ChartFragment

		shinobiChart = chartFragment.getShinobiChart();

		// TODO: replace <license_key_here> with you trial license key
		shinobiChart.setLicenseKey(Constants.CHART_KEY);
		shinobiChart.getStyle().setBackgroundColor(Color.WHITE);
		shinobiChart.getStyle().setCanvasBackgroundColor(Color.WHITE);
		shinobiChart.getStyle().setPlotAreaBackgroundColor(Color.WHITE);

		setFontAllView();
		
	}

	private void setFontAllView() {
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		btnBestOffers.setTypeface(tf);
		tvResultsGFE.setTypeface(tf);
		tvIrRate.setTypeface(tf);
		tvIrRateText.setTypeface(tf);
		tvOriginationCharge.setTypeface(tf);
		tvOriginationChargeText.setTypeface(tf);
		tvOriginationDetail.setTypeface(tf);
		tvMonthlyPayment.setTypeface(tf);
		tvMonthlyPaymentDetail.setTypeface(tf);
		tvMonthlyPaymentText.setTypeface(tf);
		tvAvgText.setTypeface(tf);
		tvAvgTextMonthlyPay.setTypeface(tf);
		tvFixBar.setTypeface(tf);
		tvFixBarMonthlyPay.setTypeface(tf);
		tvFooter.setTypeface(tf);
		//tvLoanExplorerFooterDetails.setTypeface(tf);
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		super.onStart();
		if (redraw) {
			designCustomViewWithResultData();
			designShinobiChartWithResultData();
			createChartAxis();
			shinobiChart.redrawChart();

			redraw = false;
		}

	}

	@Click(R.id.btnBestOffers)
	void onClickOfBestOffers() {
		Intent intent = new Intent(this,
				MortgageNagotiatorBestOfferActivity_.class);
		
		intent.putExtra("offersdata", container);
		intent.putExtra("offerslist", this.tempList);
		intent.putExtra("ipaddress", ipAddress);
		intent.putExtra("motrageNegotiater", userData);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar));

		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}

	private void designCustomViewWithResultData() {
	
		setInterestRate();
		setOriginationCharges();
		setMonthlyPayment();

	}

	private void setMonthlyPayment() {
		// Monthly Payment

		double tempTotalMonthlyPayment = 0.0;
		int maxMonthlyPayment = 0;
		int totalOffer = tempList.size();

		for (int i = 0; i < tempList.size(); i++) {
			tempTotalMonthlyPayment += tempList.get(i).getTotalMonthlyPayment();

			int tmpCharge = (int) tempList.get(i).getTotalMonthlyPayment();

			if (maxMonthlyPayment < tmpCharge) {
				maxMonthlyPayment = tmpCharge;
			}
		}

		double tempAvgMonthlyPayment = tempTotalMonthlyPayment / totalOffer;

		int avgMonthlyPayment = (int) (tempAvgMonthlyPayment + Constants.ZERO_POINT_FIVE);
		int userMonthlyPayment = Integer.parseInt(userData
				.getCurrentMonthlyPayment());

		double tempMPRelativePercentage = ((avgMonthlyPayment - userMonthlyPayment) / (userMonthlyPayment * Constants.ONE_POINT_ZERO)) * Constants.HUNDRED;

		int mpRelativePercentage = (int) ((Math.abs(tempMPRelativePercentage)) + Constants.ZERO_POINT_FIVE);

		double relativeMonthlyPayment = Constants.ZERO_POINT_ZERO_TWO * avgMonthlyPayment;
		double diffMonthlyPayment = Math.abs(userMonthlyPayment - avgMonthlyPayment);
		
		if (diffMonthlyPayment < relativeMonthlyPayment) {
			tvMonthlyPayment.setTextColor(getResources().getColor(
					R.color.Bar_Blue));
	       
	    } else if (diffMonthlyPayment < avgMonthlyPayment) {
	    	tvMonthlyPayment.setTextColor(getResources()
					.getColor(R.color.Green));
	       
	    } else{
	    	tvMonthlyPayment.setTextColor(getResources().getColor(
					R.color.Bar_Blue));
	    }
		
		if (mpRelativePercentage <= 1) {
			tvMonthlyPaymentText.setText(getResources().getString(
					R.string.monthly_par));
			
			tvMonthlyPaymentDetail.setText(getResources().getString(
					R.string.payment_par));

		} else if (mpRelativePercentage > 1
				&& userMonthlyPayment <= avgMonthlyPayment) {
			tvMonthlyPaymentText.setText(getResources().getString(
					R.string.monthly_low));
			
			tvMonthlyPaymentDetail.setText(String.format(getResources()
					.getString(R.string.payment_low), addCommasToNumericString(mpRelativePercentage+"")
					+ "%"));

		} else if (mpRelativePercentage > 1
				&& userMonthlyPayment >= avgMonthlyPayment) {
			tvMonthlyPaymentText.setText(getResources().getString(
					R.string.monthly_high));
			
			tvMonthlyPaymentDetail.setText(String.format(getResources()
					.getString(R.string.payment_high), addCommasToNumericString(mpRelativePercentage+"")
					+ "%"));
		}

		tvMonthlyPayment.setText("$" + addCommasToNumericString(userMonthlyPayment+""));

		setMonthlyPaymentBar(userMonthlyPayment, maxMonthlyPayment,
				avgMonthlyPayment,mpRelativePercentage);

	}

	@SuppressWarnings("deprecation")
	private void setMonthlyPaymentBar(final int userMonthlyPayment,
			final int maxMonthlyPayment, final int avgMonthlyPayment, final int relativePercentage) {
		
		
		ViewTreeObserver vto = llRelative1.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @Override 
		    public void onGlobalLayout() { 
		        llRelative1.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
		        double userSelected = userMonthlyPayment;
				double maxOrginatingCharges = maxMonthlyPayment;
				double average = avgMonthlyPayment;
		        Log.v("MAX", maxOrginatingCharges + "-----" + average + "---"
						+ userSelected+"===="+llRelative1.getWidth());
				
				double width = (userSelected / maxOrginatingCharges) * llRelative1.getWidth();
				tvFixBarMonthlyPay.setWidth((int) width);
				tvFixBarMonthlyPay.setText("$" + addCommasToNumericString((int) userSelected+""));

				if (userMonthlyPayment >= avgMonthlyPayment) {
					tvFixBarMonthlyPay.setBackgroundColor(getResources().getColor(
							R.color.Bar_Blue));
					ivTickMonthlyPay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.cross));
				}else{
					tvFixBarMonthlyPay.setBackgroundColor(getResources().getColor(
							R.color.Green));
					ivTickMonthlyPay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.tick));
				}
				
				/*if (relativePercentage <= 1) {
					tvFixBarMonthlyPay.setBackgroundColor(getResources().getColor(
							R.color.Bar_Blue));
					ivTickMonthlyPay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.cross));
				} else if (relativePercentage > 1
						&& userMonthlyPayment <= avgMonthlyPayment) {
					tvFixBarMonthlyPay.setBackgroundColor(getResources().getColor(
							R.color.Green));
					ivTickMonthlyPay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.tick));
				}else if (relativePercentage > 1
						&& userMonthlyPayment >= avgMonthlyPayment) {
					tvFixBarMonthlyPay.setBackgroundColor(getResources().getColor(
							R.color.Bar_Blue));
					ivTickMonthlyPay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.cross));
				}*/

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.MATCH_PARENT);
				double width1 = (average / maxOrginatingCharges) * llRelative1.getWidth();
				// params.setMargins(0, 0, (int)(200 - width1), 0);

				int width2 = (int) (llRelative1.getWidth() - width1);
				Log.v("WIDTH", width2 + "");
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				if (width2 <= 0) {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							tvMovingBar.getId());
					params1.leftMargin = (int)(llRelative1.getWidth() / Constants.ONE_POINT_ONE_SEVEN_ONE);
				} else if (width2 > 0 && width2 <= (int)(llRelative1.getWidth() / Constants.FIVE_POINT_SIX_ZERO_SIX)) {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							tvMovingBar.getId());
					params1.leftMargin = (int)(llRelative1.getWidth() / Constants.ONE_POINT_ONE_SEVEN_ONE);

				} else if (width2 > (int)(llRelative1.getWidth() / Constants.FIVE_POINT_SIX_ZERO_SIX) && width2 <= (int)(llRelative1.getWidth() / Constants.ONE_POINT_TWO_SEVEN_ONE)) {
					params.rightMargin = (int) (width1);
					params.addRule(RelativeLayout.RIGHT_OF, tvMovingBar.getId());
					params1.leftMargin = (int) (width1 - Constants.THIRTY_FIVE);
				} else if (width2 > (int)(llRelative1.getWidth() / Constants.ONE_POINT_TWO_SEVEN_ONE) && width2 <= llRelative1.getWidth()) {
					params.rightMargin = (int) (width1);
					params.addRule(RelativeLayout.RIGHT_OF, tvMovingBar.getId());
					params1.leftMargin = 0;
				} else {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_START,
							tvMovingBar.getId());
					params1.leftMargin = 0;
				}

				tvMovingBarMonthlyPay.setLayoutParams(params);
				tvAvgTextMonthlyPay.setText("$" + addCommasToNumericString((int) average+"") + " Avg");
				tvAvgTextMonthlyPay.setLayoutParams(params1);
		        
		    } 
		});
		
		

	}

	private void setOriginationCharges() {
		// originationCharges

		double tempTotalOriginationCharge = 0.0;
		int maxOriginationCharge = 0;
		int totalOffer = tempList.size();

		for (int i = 0; i < tempList.size(); i++) {
			tempTotalOriginationCharge += tempList.get(i).getTotalFees();

			int tmpCharge = (int) tempList.get(i).getTotalFees();

			if (maxOriginationCharge < tmpCharge) {
				maxOriginationCharge = tmpCharge;
			}
		}

		double tempAvgOriginationCharge = tempTotalOriginationCharge
				/ totalOffer;

		int avgOriginationCharge = (int) (tempAvgOriginationCharge + Constants.ZERO_POINT_FIVE);
		int userOriginationCharge = Integer.parseInt(userData
				.getOriginationCharges());

		double tempRelativePercentage;
		if (userOriginationCharge > 0) { // If user OriginationCharge is more
											// than 0
			tempRelativePercentage = ((avgOriginationCharge - userOriginationCharge) / (userOriginationCharge * Constants.ONE_POINT_ZERO)) * Constants.HUNDRED;
		} else {
			tempRelativePercentage = Constants.HUNDRED;
		}

		int relativePercentage = (int) ((Math.abs(tempRelativePercentage)) + Constants.ZERO_POINT_FIVE);

		double relativeOriginationCharge = Constants.ZERO_POINT_ZERO_TWO * avgOriginationCharge;
	    
	    double diffOriginationCharge = Math.abs(userOriginationCharge - avgOriginationCharge);
	    
	    if (diffOriginationCharge < relativeOriginationCharge) {
	    	tvOriginationCharge.setTextColor(getResources().getColor(
					R.color.Bar_Blue));
	       
	    } else if (userOriginationCharge < avgOriginationCharge) {
	    	tvOriginationCharge.setTextColor(getResources().getColor(
					R.color.Green));
	       
	    } else{
	    	tvOriginationCharge.setTextColor(getResources().getColor(
					R.color.Bar_Blue));
	    }
		
		if (relativePercentage <= 1) {
			
			tvOriginationChargeText.setText(getResources().getString(
					R.string.origination_par));
			tvOriginationDetail.setText(getResources().getString(
					R.string.charges_par));
			// markImageName = @"Tick";
		} else if (relativePercentage > 1
				&& userOriginationCharge <= avgOriginationCharge) {
			
			tvOriginationChargeText.setText(getResources().getString(
					R.string.origination_low));
			tvOriginationDetail.setText(String.format(
					getResources().getString(R.string.charges_low),
					addCommasToNumericString(relativePercentage+"") + "%"));

			// markImageName = @"Tick";
		} else if (relativePercentage > 1
				&& userOriginationCharge >= avgOriginationCharge) {
			
			tvOriginationChargeText.setText(getResources().getString(
					R.string.origination_high));
			tvOriginationDetail.setText(String.format(
					getResources().getString(R.string.charges_high),
					addCommasToNumericString(relativePercentage+"") + "%"));

			// markImageName = @"Cross";
		}

		tvOriginationCharge.setText("$" + addCommasToNumericString(userOriginationCharge+""));

		setOriginationBar(userOriginationCharge, maxOriginationCharge,
				avgOriginationCharge,relativePercentage);

	}

	@SuppressWarnings("deprecation")
	private void setOriginationBar(final int userOriginationCharge,
			final int maxOriginationCharge, final int avgOriginationCharge, final int relativePercentage) {
		

		
		
		ViewTreeObserver vto = llRelative.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @Override 
		    public void onGlobalLayout() { 
		        llRelative.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
		        double userSelected = userOriginationCharge;
				double maxOrginatingCharges = maxOriginationCharge;
				double average = avgOriginationCharge;
		        Log.v("MAX Origination", maxOrginatingCharges + "-----" + average + "---"
						+ userSelected+"===="+llRelative.getWidth());
				
				double width = (userSelected / maxOrginatingCharges) * llRelative.getWidth();
				tvFixBar.setWidth((int) width);
				tvFixBar.setText("$" + addCommasToNumericString((int) userSelected+""));

				if (userSelected >= average) {
					tvFixBar.setBackgroundColor(getResources().getColor(
							R.color.Bar_Blue));
					ivTick.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.cross));
				}else{
					tvFixBar.setBackgroundColor(getResources().getColor(
							R.color.Green));
					ivTick.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.tick));
				}
				
				/*if (relativePercentage <= 1) {
					tvFixBar.setBackgroundColor(getResources().getColor(
							R.color.Green));
					ivTick.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.tick));
				} else if (relativePercentage > 1
						&& userSelected <= average) {
					tvFixBar.setBackgroundColor(getResources().getColor(
							R.color.Green));
					ivTick.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.tick));
				}else if (relativePercentage > 1
						&& userSelected >= average) {
					tvFixBar.setBackgroundColor(getResources().getColor(
							R.color.Bar_Blue));
					ivTick.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.cross));
				}*/

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.MATCH_PARENT);
				double width1 = (average / maxOrginatingCharges) * llRelative.getWidth();
				// params.setMargins(0, 0, (int)(200 - width1), 0);

				int width2 = (int) (llRelative.getWidth() - width1);
				Log.v("WIDTH", width2 + "");
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				if (width2 <= 0) {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							tvMovingBar.getId());
					params1.leftMargin = (int)(llRelative.getWidth() / Constants.ONE_POINT_ONE_SEVEN_ONE);
				} else if (width2 > 0 && width2 <= (int)(llRelative1.getWidth() / Constants.FIVE_POINT_SIX_ZERO_SIX)) {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
							tvMovingBar.getId());
					params1.leftMargin = (int)(llRelative.getWidth() / Constants.ONE_POINT_ONE_SEVEN_ONE);

				} else if (width2 > (int)(llRelative.getWidth() / Constants.FIVE_POINT_SIX_ZERO_SIX) && width2 <= (int)(llRelative.getWidth() / Constants.ONE_POINT_TWO_SEVEN_ONE)) {
					params.rightMargin = (int) (width1);
					params.addRule(RelativeLayout.RIGHT_OF, tvMovingBar.getId());
					params1.leftMargin = (int) (width1 - Constants.THIRTY_FIVE);
				} else if (width2 > (int)(llRelative.getWidth() / Constants.ONE_POINT_TWO_SEVEN_ONE) && width2 <= llRelative.getWidth()) {
					params.rightMargin = (int) (width1);
					params.addRule(RelativeLayout.RIGHT_OF, tvMovingBar.getId());
					params1.leftMargin = 0;
				} else {
					params.rightMargin = width2;
					params.addRule(RelativeLayout.ALIGN_PARENT_START,
							tvMovingBar.getId());
					params1.leftMargin = 0;
				}

				tvMovingBar.setLayoutParams(params);
				tvAvgText.setText("$" + addCommasToNumericString((int) average+"") + " Avg");
				tvAvgText.setLayoutParams(params1);
		        
		    } 
		});
		
	
	}

	
	private void setInterestRate() {
		// Interest Rate

		double userPercentRate = Double.parseDouble(userData
				.getCurrentMortgageInterestRatePercent());

		ArrayList<Double> tempList = new ArrayList<Double>();

		for (Offers tempOffer : this.tempList) {
			tempList.add(tempOffer.getRatePercentage());
		}

		Collections.sort(tempList);

		double lowestPercentRate = tempList.get(0);

		if (userPercentRate <= lowestPercentRate) {

			tvIrRateText.setText(getResources().getString(
					R.string.interest_great));
			tvIrRate.setTextColor(getResources().getColor(R.color.Green));
		} else {
			tvIrRateText.setText(getResources().getString(
					R.string.interest_high));
			tvIrRate.setTextColor(getResources().getColor(R.color.Bar_Blue));
		}
		tvIrRate.setText((String.format("%.3f", userPercentRate)) + "%");

	}

	void createChartAxis() {
		// Set up the axes and the custom formatter, and apply to the chart
		NumberAxis axisX = new NumberAxis();
		// axisX.setDefaultRange(new NumberRange(0.0, 11.0));
		axisX.getStyle().setLineColor(Color.BLACK);
		axisX.getStyle().setLineWidth(Constants.ONE_POINT_FIVE);
		axisX.getStyle().setInterSeriesPadding(Constants.ONE_POINT_ONE);
		TickStyle styleTickX = new TickStyle();

		styleTickX.setLineColor(Color.TRANSPARENT);
		styleTickX.setLabelsShown(false);
		axisX.getStyle().setTickStyle(styleTickX);
		axisX.setTitle("Top " + (totalOffers - 1) + " offers");
		// axisX.configureBarColumns(1.0);
		axisX.getStyle().getGridlineStyle().setLineColor(Color.GRAY);

		// Set the custom tick mark frequency for the X axis
		// axisX.setMajorTickFrequency(0.5);

		// shinobiChart.setXAxis(axisX);

		NumberAxis axisY = new NumberAxis();

		axisY.getStyle().getGridlineStyle().setGridlinesShown(true);
		axisY.getStyle().getGridlineStyle().setLineColor(Color.LTGRAY);
		axisY.getStyle().setLineColor(Color.TRANSPARENT);
		axisY.setDefaultRange(new NumberRange(0.0, 9.0));
		axisY.setTitle("Rate(%)");

		TickStyle styleTickY = new TickStyle();

		styleTickY.setLineColor(Color.TRANSPARENT);
		styleTickY.setLabelTextSize(Constants.ELEVEN_POINT_ZERO);

		axisY.getStyle().setTickStyle(styleTickY);

		// Set the custom tick mark frequency for the X axis
		axisY.setMajorTickFrequency(2.5);

		// Set the custom tick mark formatter for the Y axis
		DecimalFormat formatY = new DecimalFormat("0.0");
		formatY.setDecimalSeparatorAlwaysShown(true);
		axisY.setLabelFormat(formatY);

		// anchorPoint
		/*
		 * double maxVal = (double) Math.ceil(maxIntrestRate); double minVal =
		 * (double) Math.floor(minIntrestRate);
		 * 
		 * if ((maxVal - minVal) > 2 && (maxVal - minVal) <= 5) {
		 * axisY.setMajorTickFrequency(1.0); // yAxis.anchorPoint = [NSNumber
		 * numberWithInt:minVal + 1]; }else if ((maxVal - minVal) > 5 && (maxVal
		 * - minVal) <= 9) { axisY.setMajorTickFrequency(2.0); //
		 * yAxis.anchorPoint = [NSNumber numberWithInt:minVal + 1]; }else if
		 * ((maxVal - minVal) > 9) { axisY.setMajorTickFrequency(5.0);
		 * //yAxis.anchorPoint = [NSNumber numberWithInt:minVal + 2]; }else if
		 * ((maxVal - minVal) == 1) { axisY.setMajorTickFrequency(0.5); //
		 * yAxis.rangePaddingLow = @0.5; }else{ if (minIntrestRate == 3.0 ||
		 * minIntrestRate == 2.0 || minIntrestRate == 1.0) { //yAxis.set }
		 * axisY.setMajorTickFrequency(0.5); }
		 * 
		 * axisY.setDefaultRange(new NumberRange(minVal, maxVal));
		 */

		shinobiChart.setYAxis(axisY);

		DecimalFormat formatX = new DecimalFormat();
		formatX.setDecimalSeparatorAlwaysShown(true);
		axisX.setLabelFormat(formatX);

		shinobiChart.setXAxis(axisX);

	}

	@SuppressWarnings("unused")
	void designShinobiChartWithResultData() {
		HashMap<Integer, Double> firstSeries = new HashMap<Integer, Double>();
		HashMap<Integer, Double> secondSeries = new HashMap<Integer, Double>();
		HashMap<Integer, Double> thirdSeries = new HashMap<Integer, Double>();

		ArrayList<Integer> allKeysArray1 = new ArrayList<Integer>();
		ArrayList<Integer> allKeysArray2 = new ArrayList<Integer>();
		ArrayList<Integer> allKeysArray3 = new ArrayList<Integer>();

		double userRate = Double.parseDouble(userData
				.getCurrentMortgageInterestRatePercent());
		maxIntrestRate = userRate;
		minIntrestRate = userRate;

		int firstSeriesCount = 1;

		int seriesCount = 1;
		boolean flag = true;
		ArrayList<Double> tempList = new ArrayList<Double>();

		for (Offers tempOffer : this.tempList) {
			tempList.add(tempOffer.getRatePercentage());
		}
		int thirdSeriesCount = tempList.size() - 1;
		Collections.sort(tempList);

		for (Offers tempOffer : this.tempList) {

			double offerIntrestRate = tempList.get(thirdSeriesCount);

			if (maxIntrestRate < offerIntrestRate) {
				maxIntrestRate = offerIntrestRate;
			}

			if (minIntrestRate > offerIntrestRate) {
				minIntrestRate = offerIntrestRate;
			}

			if (userRate < offerIntrestRate) {

				firstSeries.put(seriesCount, offerIntrestRate);
				allKeysArray1.add(seriesCount);
				firstSeriesCount++;
			} else {
				if (flag) {
					seriesCount++;
					flag = false;
				}

				thirdSeries.put(seriesCount, offerIntrestRate);
				allKeysArray3.add(seriesCount);

			}
			seriesCount++;
			thirdSeriesCount--;
		}

		secondSeries.put(firstSeriesCount, userRate);
		allKeysArray2.add(firstSeriesCount);

		totalOffers = firstSeries.size() + secondSeries.size()
				+ thirdSeries.size();

		createOffersBarChart(1, allKeysArray1, firstSeries);
		createOffersBarChart(Constants.TWO, allKeysArray2, secondSeries);
		createOffersBarChart(Constants.THREE, allKeysArray3, thirdSeries);

	}

	private void createOffersBarChart(int count, ArrayList<Integer> arr1,
			HashMap<Integer, Double> value1) {
		ColumnSeries offers = new ColumnSeries();
		// Create some data...
		DataAdapter<Integer, Double> SalesData = new SimpleDataAdapter<Integer, Double>();
		for (int i = 0; i < arr1.size(); i++) {

			Log.v(TAG, arr1.get(i)+"*****"+ value1.get(arr1.get(i)));
			SalesData.add(new DataPoint<Integer, Double>(arr1.get(i), value1
					.get(arr1.get(i))));
		}

		// ... and give it to the column series
		offers.setDataAdapter(SalesData);
		// offers.getXAxis().enableAnimation(true);
		shinobiChart.addSeries(offers);

		ColumnSeriesStyle style = offers.getStyle();

		switch (count) {
		case 1:
			style.setAreaColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.TWO_HUNDRED_FURTEEN, Constants.TWO_ZERO_THREE, Constants.TWO_THIRTY_NINE));
			style.setFillStyle(FillStyle.GRADIENT);
			style.setLineColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.TWO_HUNDRED_FURTEEN, Constants.TWO_ZERO_THREE, Constants.TWO_THIRTY_NINE));
			style.setAreaColorGradient(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.TWO_HUNDRED_FURTEEN, Constants.TWO_ZERO_THREE, Constants.TWO_THIRTY_NINE));
			// style.setLineWidth(4.0f);

			break;
		case 2:
			style.setAreaColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.ONE_ZERO_FIVE, Constants.EIGHTY_TWO, Constants.ONE_FIFTY_EIGHT));
			style.setFillStyle(FillStyle.GRADIENT);
			style.setLineColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.ONE_ZERO_FIVE, Constants.EIGHTY_TWO, Constants.ONE_FIFTY_EIGHT));
			style.setAreaColorGradient(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.ONE_ZERO_FIVE, Constants.EIGHTY_TWO, Constants.ONE_FIFTY_EIGHT));
			// style.setLineWidth(4.0f);

			break;
		case 3:
			style.setAreaColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.NINTY_EIGHT, Constants.ONE_FIFTY, Constants.FIFTY));
			style.setFillStyle(FillStyle.GRADIENT);
			style.setLineColor(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.NINTY_EIGHT, Constants.ONE_FIFTY, Constants.FIFTY));
			style.setAreaColorGradient(Color.argb(Constants.TWO_FIFTY_FIVE, Constants.NINTY_EIGHT, Constants.ONE_FIFTY, Constants.FIFTY));

			// style.setLineWidth(4.0f);
			break;

		default:
			break;
		}

	}

}
