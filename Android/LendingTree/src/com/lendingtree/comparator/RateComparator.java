package com.lendingtree.comparator;

import java.util.Comparator;

import com.lendingtree.model.Offers;

public class RateComparator implements Comparator<Offers> {

	@Override
	public int compare(Offers lhs, Offers rhs) {
		// TODO Auto-generated method stub
		if(lhs.getRatePercentage() > rhs.getRatePercentage()){
            return 1;
        } else {
            return -1;
        }
	}

}
