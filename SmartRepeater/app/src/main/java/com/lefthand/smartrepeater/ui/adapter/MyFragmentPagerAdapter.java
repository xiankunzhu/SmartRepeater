package com.lefthand.smartrepeater.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by v-zxiank on 12/5/2016.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
