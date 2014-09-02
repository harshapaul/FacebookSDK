package com.lendingtree.comparator;

import java.util.Comparator;

import com.lendingtree.model.Offers;

public class AprComparator implements Comparator<Offers> {

	@Override
	public int compare(Offers lhs, Offers rhs) {
		// TODO Auto-generated method stub
		if(lhs.getAPRPercentage() > rhs.getAPRPercentage()){
            return 1;
        } else {
            return -1;
        }
	}

}
