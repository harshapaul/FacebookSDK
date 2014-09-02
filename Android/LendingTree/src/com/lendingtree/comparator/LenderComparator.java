package com.lendingtree.comparator;

import java.util.Comparator;

import com.lendingtree.model.Offers;

public class LenderComparator implements Comparator<Offers> {

	@Override
	public int compare(Offers lhs, Offers rhs) {
		// TODO Auto-generated method stub
		return lhs.getName().compareToIgnoreCase(rhs.getName());
	}

}
