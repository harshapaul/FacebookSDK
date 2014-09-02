package com.lendingtree.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.lendingtree.util.Constants;
import com.lendingtree.view.CreditRatingFragment;
import com.lendingtree.view.GoodFaithEstimateDataFragment;
import com.lendingtree.view.GoodFaithEstimateFragment;
import com.lendingtree.view.HouseValueFragment;
import com.lendingtree.view.LoanPurposeFragment;
import com.lendingtree.view.PropertyZipLoanTermFragment;
import com.lendingtree.view.TestFragment;
import com.lendingtree.view.TypeOfHomeFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class MortgageNegotiatorFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Loan Purpose", "Type of Home", "House Value", "Credit Rating", "Property Zip Code", "Good Faith Estimate", "Good Faith Estimate Data"};

    private int mCount = CONTENT.length;
    
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public MortgageNegotiatorFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position)
    	{
    	case 0:
    		return LoanPurposeFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case 1:
    		return TypeOfHomeFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case Constants.TWO:
    		return HouseValueFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case Constants.THREE:
    		return CreditRatingFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case Constants.FOUR:
    		return PropertyZipLoanTermFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case Constants.FIVE:
    		return GoodFaithEstimateFragment.newInstance(CONTENT[position % CONTENT.length]);
    	case Constants.SIX:
    		return GoodFaithEstimateDataFragment.newInstance(CONTENT[position % CONTENT.length]);
    	default:
    		return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    	}
        
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return MortgageNegotiatorFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return 0;
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void setCount(int count) {
        if (count > 0 && count <= Constants.TEN) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}