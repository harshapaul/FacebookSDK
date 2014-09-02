package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lendingtree.model.Filters;
import com.lendingtree.util.Constants;

@EActivity(R.layout.filter_your_results)
public class FilterYourResultsActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	Filters filters = new Filters(true, true, true, true, true, true, "Relevance");

	boolean isLoanType,isSortBy,isPoints;

	

	String loanTypeValue = ""; 
	String pointsValue = ""; 

	@ViewById(R.id.svFilters)
	ScrollView filterScroller;

	@ViewById(R.id.tvFilterBack)
	TextView filterBack;

	@ViewById(R.id.btDone)
	Button doneButton;

	@ViewById(R.id.llLoanTypeHeader)
	LinearLayout loanTypeHeader;

	@ViewById(R.id.llSortByHeader)
	LinearLayout sortByHeader;

	@ViewById(R.id.llPointsHeader)
	LinearLayout pointsHeader;

	@ViewById(R.id.llLoanTypeChild)
	LinearLayout loanTypeChild;

	@ViewById(R.id.llSortByChild)
	LinearLayout sortByChild;

	@ViewById(R.id.llPointsChild)
	LinearLayout pointsChild;

	@ViewById(R.id.tv_LoanType_Header)
	TextView loanType_Header;

	@ViewById(R.id.tvLoanTypeSelectedValue)
	TextView loanTypeSelectedValue;

	@ViewById(R.id.ivLoanTypeImage)
	ImageView loanTypeImage;

	@ViewById(R.id.tv_SortBy_Header)
	TextView sortBy_Header;

	@ViewById(R.id.tvSortBySelectedValue)
	TextView sortBySelectedValue;

	@ViewById(R.id.ivSortByImage)
	ImageView sortByImage;

	@ViewById(R.id.tv_Points_Header)
	TextView points_Header;

	@ViewById(R.id.tvPointsSelectedValue)
	TextView pointsSelectedValue;

	@ViewById(R.id.ivPointsImage)
	ImageView pointsImage;

	@ViewById(R.id.cbLoanType30)
	CheckBox loanType30;

	@ViewById(R.id.cbLoanType15)
	CheckBox loanType15;

	@ViewById(R.id.cbLoanType5)
	CheckBox loanType5;

	@ViewById(R.id.rbRelevance)
	CheckBox relevance;

	@ViewById(R.id.rbRate)
	CheckBox rate;

	@ViewById(R.id.rbApr)
	CheckBox apr;

	@ViewById(R.id.rbRatings)
	CheckBox ratings;

	@ViewById(R.id.rbPoints)
	CheckBox points;

	@ViewById(R.id.rbLender)
	CheckBox lender;

	@ViewById(R.id.cbPoint0)
	CheckBox point0;

	@ViewById(R.id.cbPoint1)
	CheckBox point1;

	@ViewById(R.id.cbPoint2)
	CheckBox point2;

	public FilterYourResultsActivity() {
		super(R.string.instant_mortgage_quotes_title);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) 
		{
			final float scale = this.getResources().getDisplayMetrics().density;
			loanType30.setPadding(loanType30.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType30.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType30.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType30.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			loanType15.setPadding(loanType15.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType15.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType15.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType15.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			loanType5.setPadding(loanType5.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType5.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType5.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        loanType5.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			relevance.setPadding(relevance.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					relevance.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					relevance.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					relevance.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			rate.setPadding(rate.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					rate.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					rate.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					rate.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			apr.setPadding(apr.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					apr.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					apr.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					apr.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			ratings.setPadding(ratings.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					ratings.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					ratings.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					ratings.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			points.setPadding(points.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					points.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					points.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					points.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			lender.setPadding(lender.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					lender.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					lender.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					lender.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			point0.setPadding(point0.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point0.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point0.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point0.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			point1.setPadding(point1.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point1.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point1.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point1.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
			point2.setPadding(point2.getPaddingLeft() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point2.getPaddingTop() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
					point2.getPaddingRight() + (int)(Constants.FIVE_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE),
			        point2.getPaddingBottom() + (int)(Constants.TEN_POINT_ZERO * scale + Constants.ZERO_POINT_FIVE));
		}
		loanType30.setOnCheckedChangeListener(this);
		loanType15.setOnCheckedChangeListener(this);
		loanType5.setOnCheckedChangeListener(this);
		point0.setOnCheckedChangeListener(this);
		point1.setOnCheckedChangeListener(this);
		point2.setOnCheckedChangeListener(this);
		relevance.setOnClickListener(this);
		rate.setOnClickListener(this);
		apr.setOnClickListener(this);
		ratings.setOnClickListener(this);
		points.setOnClickListener(this);
		lender.setOnClickListener(this);
		if(application.getFilters() == null)
		{
			sortBySelectedValue.setText("Relevance");
			loanTypeSelectedValue.setText("30 yr, 15 yr, 5/1 ARM");
			pointsSelectedValue.setText("0, 1, 2");
			//application.isFirst = false;
		}
		else
		{
			filters = application.getFilters();
			sortBySelectedValue.setText(application.getFilters().getSortBy());
			if(application.getFilters().getSortBy().equalsIgnoreCase("Rate"))
			{
				relevance.setChecked(false);
				rate.setChecked(true);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else if(application.getFilters().getSortBy().equalsIgnoreCase("APR"))
			{
				relevance.setChecked(false);
				rate.setChecked(false);
				apr.setChecked(true);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else if(application.getFilters().getSortBy().equalsIgnoreCase("Ratings"))
			{
				relevance.setChecked(false);
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(true);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else if(application.getFilters().getSortBy().equalsIgnoreCase("Points"))
			{
				relevance.setChecked(false);
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(true);
				lender.setChecked(false);
			}
			else if(application.getFilters().getSortBy().equalsIgnoreCase("Lender"))
			{
				relevance.setChecked(false);
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(true);
			}
			else if(application.getFilters().getSortBy().equalsIgnoreCase("Relevance"))
			{
				relevance.setChecked(true);
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
				relevance.setChecked(false);
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			if(application.getFilters().isIs30YearFixed() && application.getFilters().isIs15YearFixed() && application.getFilters().isIs5Year())
			{
				loanTypeSelectedValue.setText("30 yr, 15 yr, 5/1 ARM");
				loanType30.setChecked(true);
				loanType15.setChecked(true);
				loanType5.setChecked(true);
			}
			else if(application.getFilters().isIs30YearFixed() && application.getFilters().isIs15YearFixed())
			{
				loanTypeSelectedValue.setText("30 yr, 15yr");
				loanType30.setChecked(true);
				loanType15.setChecked(true);
				loanType5.setChecked(false);
			}
			else if(application.getFilters().isIs15YearFixed() && application.getFilters().isIs5Year())
			{
				loanTypeSelectedValue.setText("15 yr, 5/1 ARM");
				loanType30.setChecked(false);
				loanType15.setChecked(true);
				loanType5.setChecked(true);
			}
			else if(application.getFilters().isIs30YearFixed() && application.getFilters().isIs5Year())
			{
				loanTypeSelectedValue.setText("30 yr, 5/1 ARM");
				loanType30.setChecked(true);
				loanType15.setChecked(false);
				loanType5.setChecked(true);
			}
			else if(application.getFilters().isIs30YearFixed())
			{
				loanTypeSelectedValue.setText("30 yr");
				loanType30.setChecked(true);
				loanType15.setChecked(false);
				loanType5.setChecked(false);
			}
			else if(application.getFilters().isIs15YearFixed())
			{
				loanTypeSelectedValue.setText("15 yr");
				loanType30.setChecked(false);
				loanType15.setChecked(true);
				loanType5.setChecked(false);
			}
			else if(application.getFilters().isIs5Year())
			{
				loanTypeSelectedValue.setText("5/1 ARM");
				loanType30.setChecked(false);
				loanType15.setChecked(false);
				loanType5.setChecked(true);
			}
			else if(!application.getFilters().isIs30YearFixed() && !application.getFilters().isIs15YearFixed() && !application.getFilters().isIs5Year())
			{
				loanTypeSelectedValue.setText("");
				loanType30.setChecked(false);
				loanType15.setChecked(false);
				loanType5.setChecked(false);
			}
			if(application.getFilters().isPoint0() && application.getFilters().isPoint1() && application.getFilters().isPoint2())
			{
				pointsSelectedValue.setText("0, 1, 2");
				point0.setChecked(true);
				point1.setChecked(true);
				point2.setChecked(true);
			}
			else if (application.getFilters().isPoint0() && application.getFilters().isPoint1()) 
			{
				pointsSelectedValue.setText("0, 1");
				point0.setChecked(true);
				point1.setChecked(true);
				point2.setChecked(false);
			}
			else if(application.getFilters().isPoint1() && application.getFilters().isPoint2())
			{
				pointsSelectedValue.setText("1, 2");
				point0.setChecked(false);
				point1.setChecked(true);
				point2.setChecked(true);
			}
			else if(application.getFilters().isPoint0() && application.getFilters().isPoint2()) 
			{
				pointsSelectedValue.setText("0, 2");
				point0.setChecked(true);
				point1.setChecked(false);
				point2.setChecked(true);
			}
			else if(application.getFilters().isPoint0())
			{
				pointsSelectedValue.setText("0");
				point0.setChecked(true);
				point1.setChecked(false);
				point2.setChecked(false);
			}
			else if(application.getFilters().isPoint1())
			{
				pointsSelectedValue.setText("1");
				point0.setChecked(false);
				point1.setChecked(true);
				point2.setChecked(false);
			}
			else if(application.getFilters().isPoint2())
			{
				pointsSelectedValue.setText("2");
				point0.setChecked(false);
				point1.setChecked(false);
				point2.setChecked(true);
			}
			else if(!application.getFilters().isPoint0() && !application.getFilters().isPoint1() && !application.getFilters().isPoint2())
			{
				pointsSelectedValue.setText("");
				point0.setChecked(false);
				point1.setChecked(false);
				point2.setChecked(false);
			}
		}
	}

	@AfterViews
	void start()
	{
		Typeface font=Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		loanType_Header.setTypeface(font);
		loanTypeSelectedValue.setTypeface(font);
		loanType30.setTypeface(font);
		loanType15.setTypeface(font);
		loanType5.setTypeface(font);
		sortBy_Header.setTypeface(font);
		sortBySelectedValue.setTypeface(font);
		relevance.setTypeface(font);
		rate.setTypeface(font);
		apr.setTypeface(font);
		ratings.setTypeface(font);
		points.setTypeface(font);
		lender.setTypeface(font);
		points_Header.setTypeface(font);
		pointsSelectedValue.setTypeface(font);
		point0.setTypeface(font);
		point1.setTypeface(font);
		point2.setTypeface(font);
		//		getSupportFragmentManager().beginTransaction()
		//		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
		//
		//		setSlidingActionBarEnabled(true);
	}

	@Click(R.id.llLoanTypeHeader)
	void onLoanTypeHeaderClick()
	{
		if(isLoanType)
		{
			loanTypeChild.setVisibility(View.GONE);
			loanTypeImage.setImageResource(R.drawable.whitearrow);
			isLoanType = false;
		}
		else
		{
			loanTypeChild.setVisibility(View.VISIBLE);
			loanTypeImage.setImageResource(R.drawable.whitearrowdown);
			isLoanType = true;
		}
	}

	@Click(R.id.llSortByHeader)
	void onSortByHeaderClick()
	{
		if(isSortBy)
		{
			sortByChild.setVisibility(View.GONE);
			sortByImage.setImageResource(R.drawable.whitearrow);
			isSortBy = false;
		}
		else
		{
			sortByChild.setVisibility(View.VISIBLE);
			sortByImage.setImageResource(R.drawable.whitearrowdown);
			isSortBy = true;
		}
	}

	@Click(R.id.llPointsHeader)
	void onPointsHeaderClick()
	{
		if(isPoints)
		{
			pointsChild.setVisibility(View.GONE);
			pointsImage.setImageResource(R.drawable.whitearrow);
			isPoints = false;
		}
		else
		{
			pointsChild.setVisibility(View.VISIBLE);
			pointsImage.setImageResource(R.drawable.whitearrowdown);
			ViewTreeObserver.OnGlobalLayoutListener globalListener = new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					// Ready, move up
					//scrollView.fullScroll(View.FOCUS_DOWN);
					filterScroller.fullScroll(View.FOCUS_DOWN);
					filterScroller.getViewTreeObserver().removeGlobalOnLayoutListener(this);

				}
			};
			// Wait until my scrollView is ready
			filterScroller.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
			isPoints = true;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch(buttonView.getId())
		{
		case R.id.cbLoanType30:
			if(isChecked)
			{
				if(filters.isIs15YearFixed() && filters.isIs5Year())
				{
					loanTypeSelectedValue.setText("30 yr, 15 yr, 5/1 ARM");
					filters.setIs30YearFixed(true);
				}
				else if(filters.isIs15YearFixed())
				{
					loanTypeSelectedValue.setText("30 yr, 15 yr");
					filters.setIs30YearFixed(true);
				}
				else if(filters.isIs5Year())
				{
					loanTypeSelectedValue.setText("30 yr, 5/1 ARM");
					filters.setIs30YearFixed(true);
				}
				else
				{
					loanTypeSelectedValue.setText("30 yr");
					filters.setIs30YearFixed(true);
				}

			}
			else
			{
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().replace("30 yr", ""));
				if(loanTypeSelectedValue.getText().toString().trim().endsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(0, loanTypeSelectedValue.getText().toString().trim().length()-1));
				}
				if(loanTypeSelectedValue.getText().toString().trim().startsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(1, loanTypeSelectedValue.getText().toString().trim().length()));
				}
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setIs30YearFixed(false);
			}
			break;
		case R.id.cbLoanType15:
			if(isChecked)
			{
				if(filters.isIs30YearFixed() && filters.isIs5Year())
				{
					loanTypeSelectedValue.setText("30 yr, 15 yr, 5/1 ARM");
					filters.setIs15YearFixed(true);
				}
				else if(filters.isIs30YearFixed())
				{
					loanTypeSelectedValue.setText("30 yr, 15 yr");
					filters.setIs15YearFixed(true);
				}
				else if(filters.isIs5Year())
				{
					loanTypeSelectedValue.setText("15 yr, 5/1 ARM");
					filters.setIs15YearFixed(true);
				}
				else
				{
					loanTypeSelectedValue.setText("15 yr");
					filters.setIs15YearFixed(true);
				}

			}
			else
			{
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().replace("15 yr", ""));
				if(loanTypeSelectedValue.getText().toString().trim().endsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(0, loanTypeSelectedValue.getText().toString().trim().length()-1));
				}
				if(loanTypeSelectedValue.getText().toString().trim().startsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(1, loanTypeSelectedValue.getText().toString().trim().length()));
				}
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setIs15YearFixed(false);
			}
			break;
		case R.id.cbLoanType5:
			if(isChecked)
			{
				if(filters.isIs30YearFixed() && filters.isIs15YearFixed())
				{
					loanTypeSelectedValue.setText("30 yr, 15 yr, 5/1 ARM");
					filters.setIs5Year(true);
				}
				else if(filters.isIs30YearFixed())
				{
					loanTypeSelectedValue.setText("30 yr, 5/1 ARM");
					filters.setIs5Year(true);
				}
				else if(filters.isIs15YearFixed())
				{
					loanTypeSelectedValue.setText("15 yr, 5/1 ARM");
					filters.setIs5Year(true);
				}
				else
				{
					loanTypeSelectedValue.setText("5/1 ARM");
					filters.setIs5Year(true);
				}
			}
			else
			{
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().replace("5/1 ARM", ""));
				if(loanTypeSelectedValue.getText().toString().trim().endsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(0, loanTypeSelectedValue.getText().toString().trim().length()-1));
				}
				if(loanTypeSelectedValue.getText().toString().trim().startsWith(","))
				{
					loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().substring(1, loanTypeSelectedValue.getText().toString().trim().length()));
				}
				loanTypeSelectedValue.setText(loanTypeSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setIs5Year(false);
			}
			break;
		case R.id.cbPoint0:
			if(isChecked)
			{
				if(filters.isPoint1() && filters.isPoint2())
				{
					pointsSelectedValue.setText("0, 1, 2");
					filters.setPoint0(true);
				}
				else if(filters.isPoint1())
				{
					pointsSelectedValue.setText("0, 1");
					filters.setPoint0(true);
				}
				else if(filters.isPoint2())
				{
					pointsSelectedValue.setText("0, 2");
					filters.setPoint0(true);
				}
				else
				{
					pointsSelectedValue.setText("0");
					filters.setPoint0(true);
				}	
			}
			else
			{
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().replace("0", ""));
				if(pointsSelectedValue.getText().toString().trim().endsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(0, pointsSelectedValue.getText().toString().trim().length()-1));
				}
				if(pointsSelectedValue.getText().toString().trim().startsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(1, pointsSelectedValue.getText().toString().trim().length()));
				}
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setPoint0(false);
			}
			break;
		case R.id.cbPoint1:
			if(isChecked)
			{
				if(filters.isPoint0() && filters.isPoint2())
				{
					pointsSelectedValue.setText("0, 1, 2");
					filters.setPoint1(true);
				}
				else if(filters.isPoint0())
				{
					pointsSelectedValue.setText("0, 1");
					filters.setPoint1(true);
				}
				else if(filters.isPoint2())
				{
					pointsSelectedValue.setText("1, 2");
					filters.setPoint1(true);
				}
				else
				{
					pointsSelectedValue.setText("1");
					filters.setPoint1(true);
				}	
			}
			else
			{
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().replace("1", ""));
				if(pointsSelectedValue.getText().toString().trim().endsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(0, pointsSelectedValue.getText().toString().trim().length()-1));
				}
				if(pointsSelectedValue.getText().toString().trim().startsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(1, pointsSelectedValue.getText().toString().trim().length()));
				}
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setPoint1(false);
			}
			break;
		case R.id.cbPoint2:
			if(isChecked)
			{
				if(filters.isPoint0() && filters.isPoint1())
				{
					pointsSelectedValue.setText("0, 1, 2");
					filters.setPoint2(true);
				}
				else if(filters.isPoint0())
				{
					pointsSelectedValue.setText("0, 2");
					filters.setPoint2(true);
				}
				else if(filters.isPoint1())
				{
					pointsSelectedValue.setText("1, 2");
					filters.setPoint2(true);
				}
				else
				{
					pointsSelectedValue.setText("2");
					filters.setPoint2(true);
				}
			}
			else
			{
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().replace("2", ""));
				if(pointsSelectedValue.getText().toString().trim().endsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(0, pointsSelectedValue.getText().toString().trim().length()-1));
				}
				if(pointsSelectedValue.getText().toString().trim().startsWith(","))
				{
					pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().substring(1, pointsSelectedValue.getText().toString().trim().length()));
				}
				pointsSelectedValue.setText(pointsSelectedValue.getText().toString().trim().replace(", ,", ","));
				filters.setPoint2(false);
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		//ActionBar Layout Backgroung color changing GREEN == Ramesh Gundala
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
				Intent intent = new Intent();
				intent.putExtra("Filters", filters);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		menu.findItem(R.id.btnDone).setActionView(b);
		menu.findItem(R.id.github).setVisible(false);
		menu.findItem(R.id.btnDone).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) 
		{
		case android.R.id.home:
			finish();
			return true;
		case R.id.btnDone:
			Intent intent = new Intent();
			intent.putExtra("Filters", filters);
			setResult(RESULT_OK, intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Click(R.id.btDone)
	void onDoneClick()
	{
		application.setFilters(filters);
		Intent intent = new Intent();
		intent.putExtra("Filters", filters);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Click(R.id.tvFilterBack)
	void onFilterBack()
	{
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.rbRelevance:
			if(relevance.isChecked())
			{
				sortBySelectedValue.setText(relevance.getText().toString());
				filters.setSortBy(relevance.getText().toString());
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}	
			break;
		case R.id.rbRate:
			if(rate.isChecked())
			{
				sortBySelectedValue.setText(rate.getText().toString());
				filters.setSortBy(rate.getText().toString());
				relevance.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}
			
			break;
		case R.id.rbApr:
			if(apr.isChecked())
			{
				sortBySelectedValue.setText(apr.getText().toString());
				filters.setSortBy(apr.getText().toString());
				rate.setChecked(false);
				relevance.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}
			
			break;
		case R.id.rbRatings:
			if(ratings.isChecked())
			{
				sortBySelectedValue.setText(ratings.getText().toString());
				filters.setSortBy(ratings.getText().toString());
				rate.setChecked(false);
				apr.setChecked(false);
				relevance.setChecked(false);
				points.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}
			
			break;
		case R.id.rbPoints:
			if(points.isChecked())
			{
				sortBySelectedValue.setText(points.getText().toString());
				filters.setSortBy(points.getText().toString());
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				relevance.setChecked(false);
				lender.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}
			
			break;
		case R.id.rbLender:
			if(lender.isChecked())
			{
				sortBySelectedValue.setText(lender.getText().toString());
				filters.setSortBy(lender.getText().toString());
				rate.setChecked(false);
				apr.setChecked(false);
				ratings.setChecked(false);
				points.setChecked(false);
				relevance.setChecked(false);
			}
			else
			{
					 sortBySelectedValue.setText("");
					 filters.setSortBy("");
			}
			
			break;
		}
	}
}
