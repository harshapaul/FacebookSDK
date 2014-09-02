/*
 * This class is used for creating custom view and data implementation for Sliding Menu
 * 
 * @Sanchit
 */
package com.lendingtree.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lendingtree.model.Menu;
import com.lendingtree.util.Constants;
import com.lendingtree.view.R;

public class SlidingMenuAdapter extends BaseAdapter {

	Context context;
	ArrayList<Menu> menuItems;

	public SlidingMenuAdapter(Context context, ArrayList<Menu> menuItems) {
		super();
		this.context = context;
		this.menuItems = menuItems;
	}

	//creating custom view using layout inflater for Sliding menu list
	public View getView(int position, View convertView, ViewGroup parent) {
		Typeface font=Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
		Menu menu = menuItems.get(position);
		TextView tvText;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.list,parent,false);
			tvText = (TextView) convertView.findViewById(R.id.tvText);
			if(position == Constants.TWO || position == Constants.THREE)
			{
				DisplayMetrics metrics = new DisplayMetrics();
				WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				windowManager.getDefaultDisplay().getMetrics(metrics);
				switch(metrics.densityDpi)
				{
				case DisplayMetrics.DENSITY_LOW:

					break;
				case DisplayMetrics.DENSITY_MEDIUM:
					tvText.setPadding(Constants.TWO, 0, 0, 0);
					break;
				case DisplayMetrics.DENSITY_HIGH:
					tvText.setPadding(Constants.EIGHT, 0, 0, 0);
					break;
				case DisplayMetrics.DENSITY_XHIGH:
					tvText.setPadding(Constants.TEN, 0, 0, 0);
					break;
				case DisplayMetrics.DENSITY_XXHIGH:
					tvText.setPadding(Constants.TEN, 0, 0, 0);
					break;
				default:
					tvText.setPadding(Constants.TWO, 0, 0, 0);
				}
			
			}
			
			/*if (position == Constants.FOUR ) {
				tvText.setTypeface(font,Typeface.BOLD);
			}else{*/
				tvText.setTypeface(font);
		//	}
			tvText.setText(menu.getMenuTitle());
			tvText.setCompoundDrawablesWithIntrinsicBounds(menu.getIconId(), 0, 0, 0);
			convertView.setTag(R.id.tvText, tvText);
			convertView.setBackgroundResource(R.color.Green);
		}
		else
		{
			tvText = (TextView) convertView.getTag(R.id.tvText);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
