package com.kinpa200296.android.labs.calculatormk3;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Pavel on 11/22/2015.
 */
public class CalculatorBlockAdapter extends FragmentPagerAdapter {

    Context context;

    public CalculatorBlockAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return CalculatorBlockFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CalculatorBlockFragment.getTitle(context, position);
    }
}
