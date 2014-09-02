package com.lendingtree.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.lendingtree.util.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@EActivity
public class SplashActivity extends Activity{

	protected int _splashTime = Constants.FIVE_HUNDRED;
	private ImageView myImageView;
	private Activity _activity;
	private Animation myFadeInAnimation;
	Handler splash_time_handler;
	private Runnable r_callingMenu;

	public static boolean onlyOnce = true;
	
	Context context = this;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*if(Build.VERSION.SDK_INT <= 10 || (Build.VERSION.SDK_INT >= 14 && ViewConfiguration.get(this).hasPermanentMenuKey()))
		{
			Log.i("MenuKeySplash", "menu key is present");
			setContentView(R.layout.splash_screen);
		}
		else
		{
			Log.i("MenuKeySplash", "menu key is not present");
			setContentView(R.layout.splash_screen1);
		}*/
		
		if (Build.VERSION.SDK_INT >= Constants.FOURTEEN) {
			boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();

			boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
			if(!hasMenuKey && !hasBackKey) {

			    // Do whatever you need to do, this device has a navigation bar
				Log.i("MenuKeyDashBoard", "menu key is present");
				setContentView(R.layout.splash_screen1);
			} else{
				Log.i("MenuKeyDashBoard", "menu key not present");
				setContentView(R.layout.splash_screen);
			}
		}else{
			Log.i("MenuKeyDashBoard", "menu key not present");
			setContentView(R.layout.splash_screen);
		}
	
	}
	
	@AfterViews
	void applicationStartExecuting() {
		_activity = this;
		//startFadin();
		onlyOnce = true;// to reset the date to the todays Date
		splash_time_handler = new Handler();
		splash_time_handler.postDelayed(r_callingMenu = new Runnable() {
			public void run() {
				Intent intent = new Intent(_activity,SplashLendingTreeActivity_.class);
            	startActivity(intent);
                finish();
			}
		}, _splashTime);
		
		//Log.d("Shot cut value","value");
		
		 /*if(!getSharedPreferences(Constants.APP_PREFERENCE, Activity.MODE_PRIVATE).getBoolean(Constants.IS_ICON_CREATED, false)){
			  createShortcut();
	          getSharedPreferences(Constants.APP_PREFERENCE, Activity.MODE_PRIVATE).edit().putBoolean(Constants.IS_ICON_CREATED, true).commit();
	      }*/
			
	}

	private void createShortcut() {
		
		Intent shortcutIntent = new Intent();
		shortcutIntent.setClassName("com.lendingtree.view", "com.lendingtree.view.SplashActivity_");
		shortcutIntent.setAction(Intent.ACTION_MAIN);
		/*shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/

		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Lending Tree");
		
		addIntent.putExtra("duplicate", false);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
		Intent.ShortcutIconResource.fromContext(context, R.drawable.appicon));
		
		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		context.sendBroadcast(addIntent);		
	}

	protected void startFadin() {
		// TODO Auto-generated method stub
		myImageView = (ImageView) findViewById(R.id.imageView1);
		myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		myImageView.startAnimation(myFadeInAnimation); // Set animation to your
														// ImageView
		// if(Constants.LOG)Log.d("SplashScreen","Fading Effect");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//removeShortcut();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		splash_time_handler.removeCallbacks(r_callingMenu);
		
	}
	
	/*private void removeShortcut() {
	     
	    //Deleting shortcut for MainActivity 
	    //on Home screen
	    Intent shortcutIntent = new Intent(getApplicationContext(),
	            SplashActivity_.class);
	    shortcutIntent.setAction(Intent.ACTION_MAIN);
	     
	    Intent addIntent = new Intent();
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Lending Tree");
	 
	    addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
	    getApplicationContext().sendBroadcast(addIntent);
	}*/

}

