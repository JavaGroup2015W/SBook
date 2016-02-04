package com.gdeer.sbook.adapter;

/**
 * Created by Gdeer on 2016/2/4.
 * email: gdeer00@163.com
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gdeer.sbook.fragment.BookInfoFragment;
import com.gdeer.sbook.fragment.SellingInfoFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SellingInfoFragment (defined as a static inner class below).
//        if (position == 1) {
//            return BookInfoFragment.newInstance(position + 1);
//        } else {
            return SellingInfoFragment.newInstance(position + 1);
//        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "待售信息";
            case 1:
                return "书籍信息";
        }
        return null;
    }
}
