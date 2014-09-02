package com.lendingtree.view;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.lendingtree.util.Constants;

import com.lendingtree.application.LendingTreeApplication;
import com.lendingtree.model.PostalAddress;
import com.lendingtree.networkutils.PostalAddressAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

@EFragment(R.layout.property_zip_loan_term)
public class PropertyZipLoanTermFragment extends Fragment /*implements OnTouchListener*/ {

	private static final String KEY_CONTENT = "TestFragment:Content";

	private String mContent = "???";

	boolean isClicked;
	
	@App
	LendingTreeApplication application;

	//	private QuickAction quickLoanTerm;

	@ViewById(R.id.llproperty)
	LinearLayout propertyLayout;

	@ViewById(R.id.tv_LoanTerm_Header)
	TextView loanTermHeader;

	//	@ViewById(R.id.btLoanTerm)
	//	Button loanTerm;

	@ViewById(R.id.tv_ZipMortgage_Header)
	TextView zipMortgageHeader;

	@ViewById(R.id.etZipMortgage)
	EditText zipMortgage;

	@ViewById(R.id.btContinue_Property_Zip_Loan_Term)
	Button continueButton;

	@ViewById(R.id.spLoanTerm)
	Spinner spinner;
	
	@Bean
	PostalAddressAdapter postalAddressAdapter;
	
	Dialog dialog;
	
	PostalAddress postalAddress;
	
	Typeface font;
	
	int selectedItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Generate a view for Fragment
		/*if(container == null)
		{
			return null;
		}
		return inflater.inflate(R.layout.property_zip_loan_term, container, false);*/
		return null;
	}

	//Returns a Fragment Object that can be used to create a Fragment for ViewPage Indicator using Fragment Adapter
	public static PropertyZipLoanTermFragment newInstance(String content)
	{
		PropertyZipLoanTermFragment fragment = new PropertyZipLoanTermFragment_(); 
		Bundle b = new Bundle();
		b.putString("msg", content);

		fragment.setArguments(b);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(!((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanProgramIds().equalsIgnoreCase("1,3,6"))
		{
			//loanTerm.setText(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getLoanTerm());
		}
	}

	@AfterViews
	void start()
	{
		font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		loanTermHeader.setTypeface(font);
		//loanTerm.setTypeface(font);
		zipMortgageHeader.setTypeface(font);
		zipMortgage.setTypeface(font);
		final ArrayList<String> loanTermList = new ArrayList<String>();
		loanTermList.add("30 Year Fixed");
		loanTermList.add("20 Year Fixed");
		loanTermList.add("15 Year Fixed");
		loanTermList.add("10 Year Fixed");
		loanTermList.add("7/1 ARM");
		loanTermList.add("5/1 ARM");
		loanTermList.add("3/1 ARM");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, loanTermList) {
			
			LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				final View v = vi.inflate(android.R.layout.simple_spinner_item,null);
				final TextView t = (TextView) v.findViewById(android.R.id.text1);
				DisplayMetrics metrics = new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
				t.setTypeface(font);
				t.setText(loanTermList.get(position));
				return v;
			}
			
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				final View v = vi.inflate(
						android.R.layout.simple_spinner_dropdown_item, null);
				v.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, Constants.FIFTY));
				final TextView textviewSpinner = (TextView) v.findViewById(android.R.id.text1);
				DisplayMetrics metrics = new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
				textviewSpinner.setGravity(Gravity.CENTER);
				if (position == selectedItem) {
					textviewSpinner.setBackgroundColor(getResources().getColor(R.color.silver));
				}
				textviewSpinner.setTypeface(font);
				textviewSpinner.setText(loanTermList.get(position));
				return v;
			}
		};
			
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				selectedItem = pos;
				switch (pos) {
				case 0:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("1");
					//homeaff_data.loanTermYear = 1;
					break;
				case 1:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("2");
					//homeaff_data.loanTermYear = 2;
					break;
				case 2:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("3");
					//homeaff_data.loanTermYear = 3;
					break;
				case 3:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("4");
					//homeaff_data.loanTermYear = 4;
					break;
				case 4:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("5");
					//homeaff_data.loanTermYear = 5;
					break;
				case 5:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("6");
					//homeaff_data.loanTermYear = 10;
					break;
				case 6:
					//((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds(loanTermList.get(pos));
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("7");
					//homeaff_data.loanTermYear = 15;
					break;

				default:
					
					((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("1");
					//homeaff_data.loanTermYear = 30;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//getAllChilds();
		//((MortgageNegotiatorActivity_)getActivity()).mortgageScroller.setOnTouchListener(this);
		//		quickLoanTerm = new QuickAction(getActivity(), QuickAction.VERTICAL);
		//		ActionItem item1 =  new ActionItem(1, "30 Year Fixed",null);
		//		ActionItem item2 =  new ActionItem(2, "20 Year Fixed",null);
		//		ActionItem item3 =  new ActionItem(3, "15 Year Fixed",null);
		//		ActionItem item4 =  new ActionItem(4, "10 Year Fixed",null);
		//		ActionItem item5 =  new ActionItem(5, "7/1 ARM",null);
		//		ActionItem item6 =  new ActionItem(6, "5/1 ARM",null);
		//		ActionItem item7 =  new ActionItem(7, "3/1 ARM",null);
		//		
		//		quickLoanTerm.addActionItem(item1);
		//		quickLoanTerm.addActionItem(item2);
		//		quickLoanTerm.addActionItem(item3);
		//		quickLoanTerm.addActionItem(item4);
		//		quickLoanTerm.addActionItem(item5);
		//		quickLoanTerm.addActionItem(item6);
		//		quickLoanTerm.addActionItem(item7);
		//		quickLoanTerm.setOnActionItemClickListener(quick_LoanTerm);
	}

	//private QuickAction.OnActionItemClickListener quick_LoanTerm = new OnActionItemClickListener() {
	//		
	//		@Override
	//		public void onItemClick(QuickAction source, int pos, int actionId) {
	//			// TODO Auto-generated method stub
	//			switch (pos) {
	//			case 0:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("1");
	//				//homeaff_data.loanTermYear = 1;
	//				break;
	//			case 1:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("2");
	//				//homeaff_data.loanTermYear = 2;
	//				break;
	//			case 2:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("3");
	//				//homeaff_data.loanTermYear = 3;
	//				break;
	//			case 3:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("4");
	//				//homeaff_data.loanTermYear = 4;
	//				break;
	//			case 4:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("5");
	//				//homeaff_data.loanTermYear = 5;
	//				break;
	//			case 5:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("6");
	//				//homeaff_data.loanTermYear = 10;
	//				break;
	//			case 6:
	//				loanTerm.setText(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm(source.getActionItem(pos).getTitle());
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("7");
	//				//homeaff_data.loanTermYear = 15;
	//				break;
	//
	//			default:
	//				loanTerm.setText("30 Year Fixed");
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setLoanTerm("30 Year Fixed");
	//				((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("1");
	//				//homeaff_data.loanTermYear = 30;
	//				break;
	//			}
	//		}
	//	};
	
	@UiThread
	void startProgress() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.progress_layout);
		TextView progresTextView = (TextView) dialog.findViewById(R.id.tvProgressText);
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		progresTextView.setTypeface(font);
		progresTextView.setText("Validating Zip Code...");
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.Dialog_Green));
		drawable.setAlpha(100);
		dialog.getWindow().setBackgroundDrawable(drawable);
		dialog.show();
	}
	
	@UiThread
	void stopProgress() {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
		TextView title = new TextView(getActivity());
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(20);
		title.setTypeface(font);
		if (application.checkForNetwork == 1) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			title.setText("Error");
			builder.setCustomTitle(title)
			.setMessage("No Internet Connectivity!")
			.setCancelable(false)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {

					dialog.dismiss();

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			TextView textView = (TextView) alert.findViewById(android.R.id.message);
			textView.setTypeface(font);
		}
		else if(postalAddress == null || postalAddress.getPostalCode() == null)
		{
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			title.setText(getResources().getText(R.string.invalid_zip_code));
			builder.setCustomTitle(title).setCancelable(false);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		else
		{
			afterPostalCodeRequest();
		}
	}
	
	@Background
	void postalCodeRequestTask()
	{
		startProgress();
		postalAddress = postalAddressAdapter.callPostalAddressRequestTask(zipMortgage.getText().toString().trim());
		if(postalAddress == null || postalAddress.getPostalCode() == null)
		{
			Log.i("Postal Address IF", "Inside If");
			stopProgress();
		}
		else
		{
			Log.i("Postal Address", postalAddress.getCity());
			stopProgress();
//			mortgageNegotiatorRequestTask();
//			timerStart  = System.currentTimeMillis();
			

		}
	}
	
	@UiThread
	void afterPostalCodeRequest()
	{
		((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setPropertyZipCode(zipMortgage.getText().toString().trim());
		/*if(((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.getRequestedLoanProgramIds() == null)
		{
			((MortgageNegotiatorActivity_)getActivity()).mortgageNegotiator.setRequestedLoanProgramIds("1,3,6");
		}*/
		if(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() < ((MortgageNegotiatorActivity_)getActivity()).mAdapter.getCount())
		{
			((MortgageNegotiatorActivity_)getActivity()).getViewPager().setCurrentItem(((MortgageNegotiatorActivity_)getActivity()).getViewPager().getCurrentItem() + 1);
			Log.i("Continue", "Continue click");
		}
		((MortgageNegotiatorActivity_)getActivity()).getPageTitle().setText("Good Faith Estimate");
		((MortgageNegotiatorActivity_)getActivity()).getLeftButton().setVisibility(View.VISIBLE);
		((MortgageNegotiatorActivity_)getActivity()).getRightButton().setVisibility(View.VISIBLE);
	}

	@Click(R.id.btContinue_Property_Zip_Loan_Term)
	void onContinueClick()
	{
		TextView title = new TextView(getActivity());
		title.setPadding(Constants.TEN, Constants.TEN, Constants.TEN, Constants.TEN);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(Constants.TWENTY);
		title.setTypeface(font);
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		//quickLoanTerm.dismiss();
		if(zipMortgage.getText().toString().trim().equals(""))
		{
			//alert zip code is empty
			title.setText(getResources().getText(R.string.please_enter_zip_code));
			builder.setCustomTitle(title);
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			builder.show();
		}
		else if(zipMortgage.getText().toString().trim().length() < Constants.FIVE)
		{
			title.setText(getResources().getText(R.string.invalid_zip_code));
			builder.setCustomTitle(title);
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			builder.show();
		}
		else
		{
			postalCodeRequestTask();
		}
	}

	//	@Click(R.id.btLoanTerm)
	//	void onLoanTermClick()
	//	{
	//		if(isClicked)
	//		{
	//			quickLoanTerm.dismiss();
	//			isClicked = false;
	//		}
	//		else
	//		{
	//			quickLoanTerm.show(loanTerm);
	//			isClicked = true;
	//		}
	//	}

	//	private void getAllChilds() {
	//		int childcount = propertyLayout.getChildCount();
	//		for (int i = 0; i < childcount; i++) {
	//			View v = propertyLayout.getChildAt(i);
	//			v.setOnTouchListener(this);
	//		}
	//	}

	//	@Override
	//	public boolean onTouch(View v, MotionEvent event) {
	//		// TODO Auto-generated method stub
	//		if (event != null) {
	//			if(event.getAction() == MotionEvent.ACTION_MOVE)
	//			{
	//				quickLoanTerm.dismiss();
	//			}
	//			if(event.getAction() == MotionEvent.ACTION_DOWN)
	//			{
	//				quickLoanTerm.dismiss();
	//			}
	//		}
	//		return false;
	//	}


}
