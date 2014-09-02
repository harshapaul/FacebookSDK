/*
 * This Fragment class creates the view and functions for Sliding Menu
 * 
 */

package com.lendingtree.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lendingtree.adapter.SlidingMenuAdapter;
import com.lendingtree.model.Menu;
import com.lendingtree.util.Constants;

public class SlidingMenuFragment extends Fragment {

	// @ViewById(R.id.lvIphone)
	ListView lviphone;

	ArrayList<Menu> menuItems = new ArrayList<Menu>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.sliding_menu, container, false);
		lviphone = (ListView) view.findViewById(R.id.lvIphone);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		menuItems.add(new Menu("Mortgage Payment Calculator",
				R.drawable.iconmortgagecalc));
		menuItems.add(new Menu("Home Affordability Calculator",
				R.drawable.iconhacalc));
		menuItems.add(new Menu("Instant Mortgage Quotes",
				R.drawable.iconinstantmortgage));
		menuItems.add(new Menu("Compare Your Current Offer",
				R.drawable.iconcompare));
		menuItems.add(new Menu("Call LendingTree", R.drawable.call_icon));
		SlidingMenuAdapter adapter = new SlidingMenuAdapter(getActivity(),
				menuItems);
		lviphone.setAdapter(adapter);
		lviphone.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					Intent mortgagePaymentIntent = new Intent(getActivity(),
							MortgagePaymentActivity_.class);
					mortgagePaymentIntent.putExtra("Slide", true);
					startActivity(mortgagePaymentIntent);
					getActivity().finish();
					break;
				case 1:
					Intent homeAffordabilityIntent = new Intent(getActivity(),
							HomeAffordabilityActivity_.class);
					homeAffordabilityIntent.putExtra("Slide", true);
					startActivity(homeAffordabilityIntent);
					getActivity().finish();
					break;
				case 2:
					Intent loanExplorerIntent = new Intent(getActivity(),
							LoanExplorerActivity_.class);
					loanExplorerIntent.putExtra("Slide", true);
					startActivity(loanExplorerIntent);
					getActivity().finish();
					break;
				case 3:
					Intent mortgageNegotiatorIntent = new Intent(getActivity(),
							MortgageNegotiatorActivity_.class);
					mortgageNegotiatorIntent.putExtra("Slide", true);
					startActivity(mortgageNegotiatorIntent);
					getActivity().finish();
					break;
				case 4:
					Typeface tf = Typeface.createFromAsset(getActivity()
							.getAssets(), "OpenSans-Regular.ttf");
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					final String phoneNo = "8005558733";
					TextView messageText = new TextView(getActivity());
					messageText.setPadding(Constants.TEN, Constants.TWENTY_FIVE,
							Constants.TEN, Constants.TWENTY_FIVE);
					messageText.setGravity(Gravity.CENTER);
					messageText.setTextSize(Constants.EIGHTEEN);
					messageText.setTypeface(tf);
					messageText.setText(String.format(
							"(%s) %s-%s",
							phoneNo.substring(Constants.ZERO, Constants.THREE),
							phoneNo.substring(Constants.THREE, Constants.SIX),
							phoneNo.substring(Constants.SIX, Constants.TEN)));

					builder.setPositiveButton("Call",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Intent.ACTION_CALL);
									intent.setData(Uri
											.parse("tel:+18005558733"));
									startActivity(intent);
									//getActivity().finish();
								}
							});
					builder.setCustomTitle(messageText);
					
					// cancel button with dismiss.
					builder.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									// dismiss();
								}
							});

					AlertDialog alert = builder.create();
					alert.show();
					break;
				}
			}
		});
	}
}