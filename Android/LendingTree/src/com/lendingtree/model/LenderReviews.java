package com.lendingtree.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("LenderReviews")
public class LenderReviews implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "PageIndex")
	private int pageIndex;
	
	@JsonProperty(value = "PageSize")
	private int pageSize;
	
	@JsonProperty(value = "TotalRecords")
	private int totalRecords;
	
	@JsonProperty(value = "LenderReviews")
	private ArrayList<LenderReviewsDetail> lenderReviewsDetails;

	public ArrayList<LenderReviewsDetail> getLenderReviewsDetails() {
		return lenderReviewsDetails;
	}

	public void setLenderReviewsDetails(
			ArrayList<LenderReviewsDetail> lenderReviewsDetails) {
		this.lenderReviewsDetails = lenderReviewsDetails;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
