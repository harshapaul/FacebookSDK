package com.lendingtree.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferContainer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "OffersPending")
	private boolean offersPending;
	
	@JsonProperty(value = "Offers")
	private ArrayList<Offers> offers;
	
	@JsonProperty(value = "Links")
	private ArrayList<Links> links;
	
	@JsonProperty(value = "QuotesId")
	private String QuotesId;

	public boolean isOffersPending() {
		return offersPending;
	}

	public void setOffersPending(boolean offersPending) {
		this.offersPending = offersPending;
	}

	public ArrayList<Links> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Links> links) {
		this.links = links;
	}

	public String getQuotesId() {
		return QuotesId;
	}

	public void setQuotesId(String quotesId) {
		QuotesId = quotesId;
	}

	public ArrayList<Offers> getOffers() {
		return offers;
	}

	public void setOffers(ArrayList<Offers> offers) {
		this.offers = offers;
	}

}
