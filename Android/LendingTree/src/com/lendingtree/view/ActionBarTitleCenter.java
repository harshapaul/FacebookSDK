package com.lendingtree.view;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.lendingtree.util.Constants;

@EBean
public class ActionBarTitleCenter {
	private String Title = null;
	private Context pcontext;

	public void setTitle(Context pcontext) {
		if (pcontext == null || Title == null)
			return;

		View tempView = null;

		((SherlockActivity) pcontext).getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM);
		((SherlockActivity) pcontext).getSupportActionBar().setCustomView(
				R.layout.custom_actionbar_centertext);
		tempView = ((SherlockActivity) pcontext).getSupportActionBar()
				.getCustomView();

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		params.setMargins(Constants.FOURTY_EIGHT, 0, 0, 0);

		TextView tempTextView = (TextView) tempView
				.findViewById(R.id.actionbarText);

		tempTextView.setLayoutParams(params);
		// tempTextView.setGravity(Gravity.CENTER);

		tempTextView.setText(Title);

	}

	/*public void setTitle(String string) {
		// TODO Auto-generated method stub
		if (pcontext == null || Title == null)
			return;

		View tempView = null;

		((SherlockActivity) pcontext).getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM);
		((SherlockActivity) pcontext).getSupportActionBar().setCustomView(
				R.layout.custom_actionbar_centertext);
		tempView = ((SherlockActivity) pcontext).getSupportActionBar()
				.getCustomView();

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		params.setMargins(48, 0, 0, 0);

		TextView tempTextView = (TextView) tempView
				.findViewById(R.id.actionbarText);

		tempTextView.setLayoutParams(params);
		// tempTextView.setGravity(Gravity.CENTER);

		tempTextView.setText(Title);
	}
*/
	
}
