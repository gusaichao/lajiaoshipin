package com.linkhand.lajiaoshipin.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles;
    private List<Fragment> fragments;
    private List<String> tabname;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabname) {
        super(fm);
        this.fragments = fragments;
        this.tabname = tabname;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabname.get(position);
    }

}
