package com.lendingtree.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.lendingtree.util.Constants;
import com.lendingtree.view.CompareLoanOffersFragment;
import com.lendingtree.view.EstimatedHomeValueFragment;
import com.lendingtree.view.HowsYourCreditFragment;
import com.lendingtree.view.PropertyZipCodeFragment;
import com.lendingtree.view.RemainingBalanceFragment;
import com.lendingtree.view.TestFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class LoanExplorerFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	protected static final String[] CONTENT = new String[] { "Loan Purpose", "Estimated Home Value", "Down Payment", "How's Your Credit", "Property Zip Code"};

	private int mCount = CONTENT.length;
	
	SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	
	Context context;

	public LoanExplorerFragmentAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		
	}

	@Override
	public Fragment getItem(int position) {
		switch(position)
		{
		case 0:
			CompareLoanOffersFragment compareLoanOffersFragment = CompareLoanOffersFragment.newInstance(CONTENT[position % CONTENT.length]);
			return compareLoanOffersFragment;
		case 1:
			EstimatedHomeValueFragment estimatedHomeValueFragment = EstimatedHomeValueFragment.newInstance(CONTENT[position % CONTENT.length]);
			return estimatedHomeValueFragment;
		case 2:
			RemainingBalanceFragment remainingBalanceFragment = RemainingBalanceFragment.newInstance(CONTENT[position % CONTENT.length]);
			return remainingBalanceFragment;
		case Constants.THREE:
			HowsYourCreditFragment howsYourCreditFragment = HowsYourCreditFragment.newInstance(CONTENT[position % CONTENT.length]);
			return howsYourCreditFragment;
		case Constants.FOUR:
			PropertyZipCodeFragment propertyZipCodeFragment = PropertyZipCodeFragment.newInstance(CONTENT[position % CONTENT.length]);
			return propertyZipCodeFragment;
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
		return LoanExplorerFragmentAdapter.CONTENT[position % CONTENT.length];
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