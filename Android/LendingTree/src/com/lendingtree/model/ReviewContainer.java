package com.lendingtree.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewContainer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "Count")
	private int count;
	
	@JsonProperty(value = "Data")
	private ArrayList<Data> data;
	
	@JsonProperty(value = "Links")
	private ArrayList<Links> links;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<Data> getData() {
		return data;
	}

	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

	public ArrayList<Links> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Links> links) {
		this.links = links;
	}

	
}
