package com.lendingtree.comparator;

import java.util.Comparator;

import com.lendingtree.model.Offers;

public class RelevanceComparator implements Comparator<Offers> {

	@Override
	public int compare(Offers lhs, Offers rhs) {
		// TODO Auto-generated method stub
		if(lhs.getRelevanceSortScore() > rhs.getRelevanceSortScore()){
            return 1;
        } else {
            return -1;
        }
	}

}
