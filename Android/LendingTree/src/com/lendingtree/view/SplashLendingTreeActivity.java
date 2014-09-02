package com.lendingtree.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.lendingtree.util.Constants;



@EActivity
public class SplashLendingTreeActivity extends Activity{

	@ViewById(R.id.mortagebtn)
	TextView mortagePaymentbtn;

	@ViewById(R.id.homeAffBtn)
	TextView homaAffbtn;

	@ViewById(R.id.loanExpBtn)
	TextView loanExploBtn;

	@ViewById(R.id.mortgagenegBtn)
	TextView mortgageNegBtn;
	
	@ViewById(R.id.tvHomeFooter)
	TextView tvHomeFooter;
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	@Click(R.id.imgHome)
	void homeIconClick(){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://quickmatch.lendingtree.com/LT_housing.asp"));
		startActivity(browserIntent);
	}
	
	/*public SplashLendingTreeActivity() {
		super(R.string.empty_space);
	}*/
	
	Context context = this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*if(Build.VERSION.SDK_INT <= 10 || (Build.VERSION.SDK_INT >= 14 && ViewConfiguration.get(this).hasPermanentMenuKey()))
		{
			Log.i("MenuKeyDashBoard", "menu key is present");
			setContentView(R.layout.lendingtree_launch_screen);
		}
		else
		{
			Log.i("MenuKeyDashBoard", "menu key not present");
			setContentView(R.layout.lendingtree_launch_screen1);
		}*/
		
		if (Build.VERSION.SDK_INT >= Constants.FOURTEEN) {
			boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();

			boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
			if(!hasMenuKey && !hasBackKey) {

			    // Do whatever you need to do, this device has a navigation bar
				Log.i("MenuKeyDashBoard", "menu key is present");
				setContentView(R.layout.lendingtree_launch_screen1);
			} else{
				Log.i("MenuKeyDashBoard", "menu key not present");
				setContentView(R.layout.lendingtree_launch_screen);
			}
		}else{
			Log.i("MenuKeyDashBoard", "menu key not present");
			setContentView(R.layout.lendingtree_launch_screen);
		}
		// AppRater.app_launched(this); 
		setHyperLink(tvHomeFooter);

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
	@Click(R.id.mortagebtn)
	void onMortgagePaymentClick()
	{
		Intent mortgagePaymentIntent = new Intent( this, MortgagePaymentActivity_.class);
		startActivity(mortgagePaymentIntent);
	}
	
	@Click(R.id.homeAffBtn)
	void onHomeAffordabilityClick()
	{
		Intent homeAffordabilityIntent = new Intent( this, HomeAffordabilityActivity_.class);
		startActivity(homeAffordabilityIntent);
	}
	
	@Click(R.id.loanExpBtn)
	void onLoanExplorerClick()
	{
		Intent loanExplorerIntent = new Intent( this, LoanExplorerActivity_.class);
		startActivity(loanExplorerIntent);
	}

	@Click(R.id.mortgagenegBtn)
	void onMortgageNegotiatorClick()
	{
		Intent mortgageNegotiatorIntent = new Intent( this, MortgageNegotiatorActivity_.class);
		startActivity(mortgageNegotiatorIntent);
	}

	@AfterViews
	public void settingFontAllViews(){
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		mortagePaymentbtn.setTypeface(tf);
		homaAffbtn.setTypeface(tf);
		loanExploBtn.setTypeface(tf);
		mortgageNegBtn.setTypeface(tf);
		tvHomeFooter.setTypeface(tf);
	}
}
