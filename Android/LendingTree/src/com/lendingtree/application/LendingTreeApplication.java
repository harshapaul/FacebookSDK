package com.lendingtree.application;

import org.androidannotations.annotations.EApplication;

import com.lendingtree.model.Filters;
import com.lendingtree.model.OfferContainer;

import android.app.Application;

@EApplication
public class LendingTreeApplication extends Application {
	
	//Used for checking the network status
	public int checkForNetwork = 0;
	
	//Used for checking the network state
	private boolean bNetworkState;
	
	//
	public boolean downPaymentState = false;
	
	//stored the value of filters
	Filters filters;
	
	//stored the property value
	String propertyValue;
	
	//public boolean isFirst = true;
	private OfferContainer offerContainer;
	
	
	public OfferContainer getOfferContainer() {
		return offerContainer;
	}

	public void setOfferContainer(OfferContainer offerContainer) {
		this.offerContainer = offerContainer;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Filters getFilters() {
		return filters;
	}

	public void setFilters(Filters filters) {
		this.filters = filters;
	}

	public int getCheckForNetwork() {
		return checkForNetwork;
	}

	public void setCheckForNetwork(int checkForNetwork) {
		this.checkForNetwork = checkForNetwork;
	}

	
	public boolean isDownPaymentState() {
		return downPaymentState;
	}

	public void setDownPaymentState(boolean downPaymentState) {
		this.downPaymentState = downPaymentState;
	}

	public boolean isbNetworkState() {
		return bNetworkState;
	}

	public void setbNetworkState(boolean bNetworkState) {
		this.bNetworkState = bNetworkState;
	}

	

}
