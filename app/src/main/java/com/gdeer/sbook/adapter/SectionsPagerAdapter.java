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

    private String BookId;

    public SectionsPagerAdapter(FragmentManager fm, String BookId) {
        super(fm);
        this.BookId = BookId;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
           return BookInfoFragment.newInstance(BookId);
        } else {
            return SellingInfoFragment.newInstance(BookId);
        }
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "书籍信息";
            case 1:
                return "待售信息";
        }
        return null;
    }
}
