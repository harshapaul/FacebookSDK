package com.lendingtree.view;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * Author: Ramesh Gundala
 * <p>This demonstrates the how to write an activity that looks like 
 * a pop-up dialog with a custom theme using a different text color.</p>
 */

public class CustomDialogActivity extends Activity {

	
	TextView txt;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);
        
        // See assets/res/any/layout/dialog_activity.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.alertdialog_transparent);
        
        
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//dialog.setTitle("Android Custom Dialog Box Ramesh Gundala Android Custom Dialog Box Ramesh Gundala Android Custom Dialog Box Ramesh Gundala Android Custom Dialog Box Ramesh Gundala Android Custom Dialog Box Ramesh Gundala");

        txt = (TextView) findViewById(R.id.txt);

        if (getIntent().getBooleanExtra("source", true)) {
        	txt.setText(R.string.monthly_alert_transparent);
		} else {
			txt.setText(R.string.phone_alert_transparent);
			
		}
		

		ImageView dialogButton = (ImageView) findViewById(R.id.dialogImgButton);

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		//dialog.show();// TODO Auto-generated method stub  
		settingFontAllViews();
        
    }
    private void settingFontAllViews() {
		// TODO Auto-generated method stub
    	Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
		Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
		
		txt.setTypeface(tf);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		this.finish();
	    return true;
	}
}
