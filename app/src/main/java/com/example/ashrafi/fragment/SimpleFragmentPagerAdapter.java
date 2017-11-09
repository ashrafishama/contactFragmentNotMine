package com.example.ashrafi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HP on 10/17/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Contacts", "ChatBox", "CallLog" };
    // configure icons

    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position


        return tabTitles[position];
    }

    /*@Override
    public Fragment getItem(int i) {
        Fragment fragment = new PageFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(PageFragment.ARG_PAGE, i + 1);
        fragment.setArguments(args);
        return fragment;
    }*/
}
