package com.lendingtree.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lendingtree.model.Offers;
import com.lendingtree.model.ReviewContainer;
import com.lendingtree.view.R;

public class LoanReviewAdapter extends BaseAdapter {
	private Context context;
	private  ReviewContainer reviewContainer;
	TextView tvreviewHeader,tvreviewBy, tvReviewDescription, tvbyOnly;
	
	LinearLayout llrev;
	

	public LoanReviewAdapter(Context context, ReviewContainer reviewContainer) {
		this.context = context;
		this.reviewContainer = reviewContainer;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reviewContainer.getData().size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.loan_exploree_review_child, parent, false);
		
		tvbyOnly = (TextView)rowView.findViewById(R.id.tvbyOnly);
		tvreviewHeader = (TextView) rowView.findViewById(R.id.tvreviewHeader);
		final RatingBar ratingBarReview = (RatingBar) rowView.findViewById(R.id.ratingBarReview);
		
		tvreviewBy = (TextView) rowView.findViewById(R.id.tvreviewBy);
		tvReviewDescription = (TextView) rowView.findViewById(R.id.tvReviewDescription);
		
		ratingBarReview.setRating(reviewContainer.getData().get(position).getCustomerServiceRating());
		tvreviewHeader.setText(reviewContainer.getData().get(position).getReviewTitle());
		tvreviewBy.setText(reviewContainer.getData().get(position).getUserNickname()+
				" ("+reviewContainer.getData().get(position).getUserLocation()+")");
		tvReviewDescription.setText(reviewContainer.getData().get(position).getReviewText());
		settingFontAllViews();
		
		/*llrev = (LinearLayout) rowView.findViewById(R.id.llrev);
		 
		llrev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				llrev.setBackgroundColor(Color.BLUE);
				
			}
		});*/
		
		return rowView;
	}

	private void settingFontAllViews() {
		// TODO Auto-generated method stub
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
		//Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
		tvreviewHeader.setTypeface(tf);
		tvreviewBy.setTypeface(tf);
		tvReviewDescription.setTypeface(tf);
		tvbyOnly.setTypeface(tf);
	}

}
