package com.example.a48101040.promedic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 48101040 on 1/29/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentsList = new ArrayList<>();
    private List<String> mFragmentTitlesList = new ArrayList<>();

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitlesList.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentsList.add(fragment);
        mFragmentTitlesList.add(title);
    }
}
