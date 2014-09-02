package com.lendingtree.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("LenderReviews")
public class LenderReviewsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "ReviewText")
	private String reviewText;
	
	@JsonProperty(value = "ReviewTitle")
	private String reviewTitle;
	
	@JsonProperty(value = "UserNickname")
	private String userNickname;

	@JsonProperty(value = "CustomerServiceRating")
	private float CustomerServiceRating;
	
	@JsonProperty(value = "UserLocation")
	private String userLocation;
	
	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public float getCustomerServiceRating() {
		return CustomerServiceRating;
	}

	public void setCustomerServiceRating(float customerServiceRating) {
		CustomerServiceRating = customerServiceRating;
	}
	
	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
}
