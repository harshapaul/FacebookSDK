package com.lendingtree.model;

import java.io.Serializable;

public class Filters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean is30YearFixed,is15YearFixed,is5Year,isPoint0,isPoint1,isPoint2;
	private String sortBy;
	
	
	
	public Filters(boolean is30YearFixed, boolean is15YearFixed,
			boolean is5Year, boolean isPoint0, boolean isPoint1,
			boolean isPoint2, String sortBy) {
		super();
		this.is30YearFixed = is30YearFixed;
		this.is15YearFixed = is15YearFixed;
		this.is5Year = is5Year;
		this.isPoint0 = isPoint0;
		this.isPoint1 = isPoint1;
		this.isPoint2 = isPoint2;
		this.sortBy = sortBy;
	}
	
	public boolean isIs30YearFixed() {
		return is30YearFixed;
	}
	public void setIs30YearFixed(boolean is30YearFixed) {
		this.is30YearFixed = is30YearFixed;
	}
	public boolean isIs15YearFixed() {
		return is15YearFixed;
	}
	public void setIs15YearFixed(boolean is15YearFixed) {
		this.is15YearFixed = is15YearFixed;
	}
	public boolean isIs5Year() {
		return is5Year;
	}
	public void setIs5Year(boolean is5Year) {
		this.is5Year = is5Year;
	}
	public boolean isPoint0() {
		return isPoint0;
	}
	public void setPoint0(boolean isPoint0) {
		this.isPoint0 = isPoint0;
	}
	public boolean isPoint1() {
		return isPoint1;
	}
	public void setPoint1(boolean isPoint1) {
		this.isPoint1 = isPoint1;
	}
	public boolean isPoint2() {
		return isPoint2;
	}
	public void setPoint2(boolean isPoint2) {
		this.isPoint2 = isPoint2;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
